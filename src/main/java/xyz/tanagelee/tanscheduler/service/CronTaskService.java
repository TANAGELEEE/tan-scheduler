/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.service;

import org.apache.ibatis.annotations.Param;
import xyz.tanagelee.tanscheduler.bo.TaskBO;

import java.util.Date;
import java.util.List;

/**
 * CronTaskService
 *
 * @author liyunjun
 * @date 2021/12/25 18:52
 */
public interface CronTaskService {

    List<TaskBO> listAvailableTasks(@Param("maxNextTime") Date maxNextTime, @Param("size") int size);

    void updateTrigger(TaskBO trigger);

    void deleteTrigger(Long triggerId);
}