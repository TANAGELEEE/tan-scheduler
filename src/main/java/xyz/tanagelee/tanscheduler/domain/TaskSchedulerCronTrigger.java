/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.domain;

import lombok.Data;
import xyz.tanagelee.tanscheduler.bo.BaseBO;

import java.util.Date;

/**
 * TaskSchedulerCronTrigger
 *
 * @author liyunjun
 * @date 2021/12/25 21:27
 */
@Data
public class TaskSchedulerCronTrigger extends BaseBO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 触发类型
     */
    private Short triggerType;

    /**
     * 触发数据
     */
    private String triggerData;

    /**
     * 调度时间CRON表达式
     */
    private String cronExpression;

    /**
     * 下次触发时间
     */
    private Date triggerNextTime;

    /**
     * 生效时间
     */
    private Date effectDateStart;

    /**
     * 失效时间
     */
    private Date effectDateEnd;

    /**
     * 是否有效（1:是 0:否）
     */
    private Integer isValid;

    /**
     * 任务类型
     */
    private String taskType;

    private static final long serialVersionUID = 1L;
}