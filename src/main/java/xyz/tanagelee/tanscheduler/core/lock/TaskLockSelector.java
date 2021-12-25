/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import xyz.tanagelee.tanscheduler.common.constant.Constants;
import xyz.tanagelee.tanscheduler.common.context.SpringContextHolder;
import xyz.tanagelee.tanscheduler.common.exception.SystemException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TaskLockSelector
 *
 * @author liyunjun
 * @date 2021/12/25 18:23
 */
@Component
@Slf4j
@DependsOn("springContextHolder")
public class TaskLockSelector {

    private AtomicLong redissonFailCounter = new AtomicLong(0L);

    private static final Integer REDISSON_FAIL_THRESHOLD = 3;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void init() {
        final RedissonClient redissonClient = SpringContextHolder.getBean(RedissonClient.class);
        Runnable runnable = () -> {
            try {
                redissonClient.getAtomicLong(TaskLockSelector.class.getSimpleName());
                redissonFailCounter.set(0L);
            } catch (Exception e) {
                log.error("", e);
                redissonFailCounter.incrementAndGet();
            }
        };
        executorService.scheduleAtFixedRate(runnable, 5000L, 5000L, TimeUnit.MILLISECONDS);
    }

    public TaskLock select(String resource) {
        return select(Constants.MYSQL_LOCK_TYPE, resource);
    }

    public TaskLock select(String lockType, String resource) {
        TaskLock taskLock;
        if (Constants.MYSQL_LOCK_TYPE.equals(lockType)) {
            taskLock = new MySQLTaskLock(resource);
        } else if (Constants.REDIS_LOCK_TYPE.equals(lockType)) {
            if (isRedissonAvailable()) {
                taskLock = new RedisTaskLock(resource);
            } else {
                log.info("redisson not available, switch to MySQL lock");
                taskLock = new MySQLTaskLock(resource);
            }
        } else {
            throw new SystemException("分布式锁类型不支持:" + lockType);
        }
        return taskLock;
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }

    private boolean isRedissonAvailable() {
        return redissonFailCounter.get() <= REDISSON_FAIL_THRESHOLD;
    }
}