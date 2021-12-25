/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core.scheduler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import lombok.extern.slf4j.Slf4j;
import xyz.tanagelee.tanscheduler.bo.TaskBO;
import xyz.tanagelee.tanscheduler.common.config.MasterConfig;
import xyz.tanagelee.tanscheduler.common.constant.Constants;
import xyz.tanagelee.tanscheduler.common.context.SpringContextHolder;
import xyz.tanagelee.tanscheduler.core.lock.TaskLock;
import xyz.tanagelee.tanscheduler.core.lock.TaskLockSelector;
import xyz.tanagelee.tanscheduler.core.metric.Identifiable;
import xyz.tanagelee.tanscheduler.service.CronTaskService;
import xyz.tanagelee.tanscheduler.utils.CronExpression;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TaskScheduleHelper
 *
 * @author liyunjun
 * @date 2021/12/25 18:13
 */
@Slf4j
public class TaskScheduleHelper implements Identifiable<String> {

    private TaskLockSelector taskLockSelector;

    private CronTaskService cronTaskService;

    private MasterConfig masterConfig;

    private Timer timer;

    private TaskScheduleHelper() {
        taskLockSelector = SpringContextHolder.getBean(TaskLockSelector.class);
        cronTaskService = SpringContextHolder.getBean(CronTaskService.class);
        masterConfig = SpringContextHolder.getBean(MasterConfig.class);
        this.timer = new HashedWheelTimer(10L, TimeUnit.MILLISECONDS);
    }

    private static volatile TaskScheduleHelper INSTANCE;

    public static TaskScheduleHelper getInstance() {
        if (null == INSTANCE) {
            synchronized (TaskScheduleHelper.class) {
                if (null == INSTANCE) {
                    INSTANCE = new TaskScheduleHelper();
                }
            }
        }
        return INSTANCE;
    }

    public static final long PRE_READ_MS = 5000;    // pre read

    private Thread scheduleThread;
    private volatile boolean scheduleThreadToStop = false;

    public void start() {
        scheduleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(5000 - System.currentTimeMillis() % 1000);
                } catch (InterruptedException e) {
                    if (!scheduleThreadToStop) {
                        log.error(e.getMessage(), e);
                    }
                }
                log.info(">>>>>>>>> init task scheduler success.");

                int preReadCount = (Constants.TRIGGER_POOL_FAST_MAX + Constants.TRIGGER_POOL_SLOW_MAX) * 20;

                while (!scheduleThreadToStop) {
                    // 扫描任务
                    long start = System.currentTimeMillis();
                    boolean preReadSuc = true;
                    TaskLock taskLock = null;
                    try {
                        taskLock = taskLockSelector.select(id());
                        taskLock.acquire();

                        // 开始
                        long nowTime = System.currentTimeMillis();
                        Date maxNextTime = new Date(nowTime + PRE_READ_MS);
                        List<TaskBO> scheduleList = cronTaskService.listAvailableTasks(maxNextTime,
                                preReadCount);
                        if (scheduleList != null && scheduleList.size() > 0) {
                            long currentTime = System.currentTimeMillis();
                            for (TaskBO jobInfo : scheduleList) {
                                Date startTime = jobInfo.getEffectDateStart();
                                Date endTime = jobInfo.getEffectDateEnd();

                                Date nextValidTime = jobInfo.getTriggerNextTime();
                                long diffTime = currentTime - nextValidTime.getTime();

                                if (nowTime > nextValidTime.getTime() + PRE_READ_MS) {
                                    log.warn(">>>>>>>>>>> task schedule misfire, jobId = " + jobInfo.getId());
                                    // 判断本次是否触发
                                    if (masterConfig.isTriggerMisfireEnable() && validIsTrigger(nextValidTime,
                                            startTime, endTime)) {
                                        handleTrigger(jobInfo);
                                    }
                                    // 更新下次时间
                                    refreshNextValidTime(jobInfo, new Date());
                                } else if (nowTime > nextValidTime.getTime()) {
                                    if (validIsTrigger(nextValidTime, startTime, endTime)) {
                                        handleTrigger(jobInfo);
                                    }
                                    Date now = new Date();
                                    nextValidTime = getNextValidTime(jobInfo.getCronExpression(), now);
                                    // 下次触发在5s内
                                    if (nextValidTime != null && nowTime + PRE_READ_MS > nextValidTime.getTime()) {
                                        scheduleOnTimer(nextValidTime.getTime() - now.getTime(), jobInfo);
                                    }
                                    refreshNextValidTime(jobInfo, jobInfo.getTriggerNextTime());
                                } else {
                                    if (validIsTrigger(nextValidTime, startTime, endTime)) {
                                        scheduleOnTimer(Math.abs(diffTime), jobInfo);
                                    }
                                    refreshNextValidTime(jobInfo, jobInfo.getTriggerNextTime());
                                }

                                if (jobInfo.getValid()) {
                                    // 更新定时任务信息
                                    cronTaskService.updateTrigger(jobInfo);
                                } else {
                                    // 删除定时任务信息
                                    cronTaskService.deleteTrigger(jobInfo.getId());
                                }
                            }
                        } else {
                            preReadSuc = false;
                        }
                        // 结束
                    } catch (Exception e) {
                        if (!scheduleThreadToStop) {
                            log.error(">>>>>>>>>>> task JobScheduleHelper#scheduleThread error:{}", e);
                        }
                    } finally {
                        if (null != taskLock) {
                            try {
                                taskLock.release();
                            } catch (Exception e) {
                                log.error("", e);
                            }
                        }
                    }
                    long cost = System.currentTimeMillis() - start;
                    if (cost < 1000) {
                        try {
                            TimeUnit.MILLISECONDS.sleep((preReadSuc ? 1000 : PRE_READ_MS) - System.currentTimeMillis() % 1000);
                        } catch (InterruptedException e) {
                            if (!scheduleThreadToStop) {
                                log.error(e.getMessage(), e);
                            }
                        }
                    }

                }
                log.info(">>>>>>>>>>> task JobScheduleHelper#scheduleThread stop");
            }
        });
        scheduleThread.setDaemon(true);
        scheduleThread.setName("task JobScheduleHelper#scheduleThread");
        scheduleThread.start();
    }

    public void toStop() {
        // 1、停止任务
        scheduleThreadToStop = true;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        if (scheduleThread.getState() != Thread.State.TERMINATED) {
            scheduleThread.interrupt();
            try {
                scheduleThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info(">>>>>>>>>>> taskScheduleHelper stop");
    }

    @Override
    public String id() {
        return Constants.CRON_SCHEDULER;
    }

    private boolean validIsTrigger(Date nextValidTime, Date startTime, Date endTime) {
        if (null != startTime && null != endTime) {
            if (nextValidTime.before(startTime) || nextValidTime.after(endTime)) return false;
        }
        return true;
    }

    private void refreshNextValidTime(TaskBO trigger, Date fromTime) throws Exception {
        String expression = trigger.getCronExpression();
        Date nextValidTime = null;
        if (StringUtils.isNotBlank(expression)) {
            if (CronExpression.isValidExpression(expression)) {
                try {
                    CronExpression cronExpression = new CronExpression(expression);
                    nextValidTime = cronExpression.getNextValidTimeAfter(fromTime);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
        trigger.setTriggerNextTime(nextValidTime);
        trigger.setValid(null != nextValidTime && nextValidTime.getTime() - new Date().getTime() > 0);
    }

    private void scheduleOnTimer(long delay, TaskBO trigger) {
        timer.newTimeout(timeout -> handleTrigger(trigger), delay, TimeUnit.MILLISECONDS);
    }

    private Date getNextValidTime(String expression, Date after) {
        Date nextValidTime = null;
        if (StringUtils.isNotBlank(expression)) {
            if (CronExpression.isValidExpression(expression)) {
                try {
                    CronExpression cronExpression = new CronExpression(expression);
                    nextValidTime = cronExpression.getNextValidTimeAfter(after);
                } catch (ParseException e) {
                    log.error("", e);
                }
            }
        }
        return nextValidTime;
    }

    private void handleTrigger(TaskBO trigger) {
        // do something
    }
}