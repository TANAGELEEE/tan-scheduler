/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.model;

import lombok.Data;

/**
 * TaskScheduleModel
 *
 * @author liyunjun
 * @date 2021/12/25 22:36
 */
@Data
public class TaskScheduleModel {
    /**
     * 所选作业类型: 0 ->  执行一次 1  -> 每天 2  -> 每周 3  -> 每月
     */
    Integer jobType;

    /**
     * 一周的哪几天
     */
    Integer[] dayOfWeeks;

    /**
     * 一个月的哪几天
     */
    Integer[] dayOfMonths;

    /**
     * 秒
     */
    Integer second;

    /**
     * 分
     */
    Integer minute;

    /**
     * 时
     */
    Integer hour;

    /**
     * 天
     */
    Integer day;
    /**
     * 月
     */
    Integer month;
    /**
     * 年
     */
    Integer year;
}