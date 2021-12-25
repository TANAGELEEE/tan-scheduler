-- task_schedule_lock definition

CREATE TABLE `task_schedule_lock`
(
    `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
    PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- task_scheduler_cron_trigger definition

CREATE TABLE `task_scheduler_cron_trigger`
(
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `task_id`           bigint(20) unsigned NOT NULL COMMENT '任务ID',
    `trigger_type`      smallint(4) unsigned NOT NULL COMMENT '触发类型',
    `trigger_data`      longtext COMMENT '触发数据',
    `cron_expression`   varchar(64) DEFAULT NULL COMMENT '调度时间CRON表达式',
    `trigger_next_time` datetime    DEFAULT NULL COMMENT '下次触发时间',
    `effect_date_start` date        DEFAULT NULL COMMENT '生效时间',
    `effect_date_end`   date        DEFAULT NULL COMMENT '失效时间',
    `is_valid`          tinyint(1) NOT NULL COMMENT '是否有效（1:是 0:否）',
    `task_type`         varchar(32) DEFAULT NULL COMMENT '任务类型',
    `create_user`       varchar(64) NOT NULL COMMENT '创建人',
    `create_time`       datetime    NOT NULL COMMENT '创建时间',
    `update_user`       varchar(64) NOT NULL COMMENT '更新人',
    `update_time`       datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY                 `idx_trigger_next_time` (`trigger_next_time`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COMMENT='任务定时触发表';