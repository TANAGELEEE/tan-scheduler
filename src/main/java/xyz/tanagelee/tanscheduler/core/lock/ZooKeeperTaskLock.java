/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core.lock;

/**
 * ZooKeeperTaskLock
 *
 * @author liyunjun
 * @date 2021/12/25 18:32
 */
public class ZooKeeperTaskLock extends TaskLock {

    public ZooKeeperTaskLock(String resource) {
        super(resource);
    }

    @Override
    public void acquire() {

    }

    @Override
    public void acquire(long timeout) {

    }

    @Override
    public void release() {

    }
}