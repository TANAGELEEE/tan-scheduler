/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.common.constant;

import java.io.Serializable;

/**
 * Constants
 *
 * @author liyunjun
 * @date 2021/12/25 18:16
 */
public class Constants implements Serializable {

    public static final String CRON_SCHEDULER = "cron_scheduler";

    public static final String MYSQL_LOCK_TYPE = "MySQL_Lock";

    public static final String REDIS_LOCK_TYPE = "Redis_Lock";


    public static final int TRIGGER_POOL_FAST_MAX = 200;

    public static final int TRIGGER_POOL_SLOW_MAX = 100;
}