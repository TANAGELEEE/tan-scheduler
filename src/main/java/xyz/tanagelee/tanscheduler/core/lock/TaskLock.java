/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core.lock;

/**
 * TaskLock
 *
 * @author liyunjun
 * @date 2021/12/25 18:23
 */
public abstract class TaskLock {

    protected String resource;

    public TaskLock(String resource) {
        this.resource = resource;
    }

    public abstract void acquire();

    public abstract void acquire(long timeout);

    public abstract void release();
}