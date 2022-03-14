/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.vo;

import lombok.Data;

import java.util.Map;

/**
 * TaskVO
 *
 * @author liyunjun
 * @date 2022/3/11 15:17
 */
@Data
public class TaskVO {
    private Long id;
    private String reqId;
    private String submitUser;
    private String executeUser;
    private Map<String, String> source;
    private String executionCode;
}