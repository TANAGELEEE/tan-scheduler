/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.converter;

import xyz.tanagelee.tanscheduler.bo.TaskBO;
import xyz.tanagelee.tanscheduler.domain.TaskSchedulerCronTrigger;
import xyz.tanagelee.tanscheduler.enums.CronTaskTypeEnum;
import xyz.tanagelee.tanscheduler.enums.EnumUtils;

/**
 * CronTriggerConverter
 *
 * @author liyunjun
 * @date 2021/12/25 21:36
 */
public class CronTriggerConverter implements POJOConverter<TaskBO, TaskSchedulerCronTrigger> {
    @Override
    public TaskSchedulerCronTrigger convert(TaskBO source) {
        TaskSchedulerCronTrigger target = new TaskSchedulerCronTrigger();
        target.setId(source.getId());
        target.setTaskId(source.getTaskId());
        target.setTriggerType(source.getTriggerType().getValue().shortValue());
        target.setTriggerData(source.getTriggerData());
        target.setCronExpression(source.getCronExpression());
        target.setTriggerNextTime(source.getTriggerNextTime());
        target.setEffectDateStart(source.getEffectDateStart());
        target.setEffectDateEnd(source.getEffectDateEnd());
        target.setIsValid(source.getValid() ? 1 : 0);
        target.setTaskType(source.getTaskType());
        target.setCreateUser(source.getCreateUser());
        target.setCreateTime(source.getCreateTime());
        target.setUpdateUser(source.getUpdateUser());
        target.setUpdateTime(source.getUpdateTime());
        return target;
    }

    @Override
    public TaskBO reconvert(TaskSchedulerCronTrigger target) {
        TaskBO source = new TaskBO();
        source.setId(target.getId());
        source.setTaskId(target.getTaskId());
        source.setTriggerType(
                EnumUtils.getEnum(target.getTriggerType().intValue(), CronTaskTypeEnum.class));
        source.setTriggerData(target.getTriggerData());
        source.setCronExpression(target.getCronExpression());
        source.setTriggerNextTime(target.getTriggerNextTime());
        source.setEffectDateStart(target.getEffectDateStart());
        source.setEffectDateEnd(target.getEffectDateEnd());
        source.setValid(target.getIsValid() == 1 ? true : false);
        source.setTaskType(target.getTaskType());
        source.setCreateUser(target.getCreateUser());
        source.setCreateTime(target.getCreateTime());
        source.setUpdateUser(target.getUpdateUser());
        source.setUpdateTime(target.getUpdateTime());
        return source;
    }
}