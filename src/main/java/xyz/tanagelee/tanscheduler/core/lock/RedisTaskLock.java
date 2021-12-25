/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import xyz.tanagelee.tanscheduler.common.context.SpringContextHolder;

import java.util.concurrent.TimeUnit;

/**
 * RedisTaskLock
 *
 * @author liyunjun
 * @date 2021/12/25 18:34
 */
public class RedisTaskLock extends TaskLock {

    private RLock rLock;

    public RedisTaskLock(String resource) {
        super(resource);
        RedissonClient redissonClient = SpringContextHolder.getBean(RedissonClient.class);
        this.rLock = redissonClient.getLock(resource);
    }

    @Override
    public void acquire() {
        acquire(-1L);
    }

    @Override
    public void acquire(long timeout) {
        if (timeout > 0L) {
            rLock.lock(timeout, TimeUnit.MILLISECONDS);
        } else {
            rLock.lock();
        }
    }

    @Override
    public void release() {
        rLock.unlock();
    }
}