/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import xyz.tanagelee.tanscheduler.core.scheduler.TaskScheduleHelper;

/**
 * TaskRunner
 *
 * @author liyunjun
 * @date 2021/12/25 18:18
 */
@Configuration
@DependsOn("springContextHolder")
@Slf4j
@Order(1)
public class TaskRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        TaskScheduleHelper.getInstance().start();
        log.info(">>>>>>>>> init task success.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("shutdown start");

            TaskScheduleHelper.getInstance().toStop();

            log.info("shutdown finish");
        }));
    }
}