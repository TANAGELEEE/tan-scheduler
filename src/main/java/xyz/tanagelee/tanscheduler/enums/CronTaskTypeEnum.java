/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.enums;

/**
 * CronTaskTypeEnum
 *
 * @author liyunjun
 * @date 2021/12/25 18:58
 */
public enum CronTaskTypeEnum implements Valuable<Integer> {

    GENERATE_TEMP_INSTANCE(0, "生成临时任务实例"),

    GENERATE_DAY_INSTANCE(1, "生成天任务实例"),

    GENERATE_WEEK_INSTANCE(2, "生成周任务实例"),

    GENERATE_MONTH_INSTANCE(3, "生成月任务实例");

    private int value;

    private String description;

    CronTaskTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}