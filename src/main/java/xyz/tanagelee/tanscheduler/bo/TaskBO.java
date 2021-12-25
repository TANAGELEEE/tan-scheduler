/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.bo;

import lombok.Data;
import xyz.tanagelee.tanscheduler.enums.CronTaskTypeEnum;

import java.util.Date;

/**
 * TaskBO
 *
 * @author liyunjun
 * @date 2021/12/25 18:53
 */
@Data
public class TaskBO extends BaseBO {

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
    private CronTaskTypeEnum triggerType;

    /**
     * 触发数据
     */
    private String triggerData;

    /**
     * 调度时间CRON表达式
     */
    private String cronExpression;

    /**
     * 是否有效（1:是 0:否）
     */
    private Boolean valid;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 下次调度时间
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
}