/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseBO
 *
 * @author liyunjun
 * @date 2021/12/25 21:28
 */
@Data
public class BaseBO implements Serializable {
    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;
}