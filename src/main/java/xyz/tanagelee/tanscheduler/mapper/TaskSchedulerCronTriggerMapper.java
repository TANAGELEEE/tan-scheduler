/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.tanagelee.tanscheduler.domain.TaskSchedulerCronTrigger;

import java.util.Date;
import java.util.List;

/**
 * TaskSchedulerCronTriggerMapper
 *
 * @author liyunjun
 * @date 2021/12/25 21:31
 */
@Mapper
public interface TaskSchedulerCronTriggerMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TaskSchedulerCronTrigger record);

    int insertSelective(TaskSchedulerCronTrigger record);

    TaskSchedulerCronTrigger selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskSchedulerCronTrigger record);

    int updateByPrimaryKey(TaskSchedulerCronTrigger record);

    List<TaskSchedulerCronTrigger> listByTriggerTime(@Param("maxNextTime") Date maxNextTime,
                                                     @Param("limit") Integer limit);
}