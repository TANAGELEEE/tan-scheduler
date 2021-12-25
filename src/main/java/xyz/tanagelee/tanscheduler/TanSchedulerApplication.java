package xyz.tanagelee.tanscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(value = {"xyz.tanagelee"})
public class TanSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TanSchedulerApplication.class, args);
    }

}
