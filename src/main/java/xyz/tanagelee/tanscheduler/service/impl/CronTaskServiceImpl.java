/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.stereotype.Service;
import xyz.tanagelee.tanscheduler.bo.TaskBO;
import xyz.tanagelee.tanscheduler.converter.CronTriggerConverter;
import xyz.tanagelee.tanscheduler.domain.TaskSchedulerCronTrigger;
import xyz.tanagelee.tanscheduler.mapper.TaskSchedulerCronTriggerMapper;
import xyz.tanagelee.tanscheduler.service.CronTaskService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CronTaskServiceImpl
 *
 * @author liyunjun
 * @date 2021/12/25 21:26
 */
@Service
public class CronTaskServiceImpl implements CronTaskService {

    @Resource
    private TaskSchedulerCronTriggerMapper taskSchedulerCronTriggerMapper;

    @Override
    public List<TaskBO> listAvailableTasks(Date maxNextTime, int size) {
        List<TaskSchedulerCronTrigger> taskList =
                taskSchedulerCronTriggerMapper.listByTriggerTime(maxNextTime,
                        size);
        List<TaskBO> triggers = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(taskList)) {
            for (TaskSchedulerCronTrigger triggerDO : taskList) {
                TaskBO trigger = new CronTriggerConverter().reconvert(triggerDO);
                triggers.add(trigger);
            }
        }
        return triggers;
    }

    @Override
    public void updateTrigger(TaskBO trigger) {
        // 更新
        TaskSchedulerCronTrigger triggerDO = new CronTriggerConverter().convert(trigger);
        taskSchedulerCronTriggerMapper.updateByPrimaryKey(triggerDO);
    }

    @Override
    public void deleteTrigger(Long triggerId) {
        // 删除
        taskSchedulerCronTriggerMapper.deleteByPrimaryKey(triggerId);
    }
}