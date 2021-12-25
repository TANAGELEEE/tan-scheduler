/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * MasterConfig
 *
 * @author liyunjun
 * @date 2021/12/25 19:01
 */
@Configuration
@Data
public class MasterConfig {

    @Value("${cron-scheduler.trigger-misfire-enable:true}")
    private boolean triggerMisfireEnable;
}