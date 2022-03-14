/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.engine.manager;

import xyz.tanagelee.tanscheduler.dto.EngineOptLogDTO;

/**
 * EngineOptLogManager
 *
 * @author liyunjun
 * @date 2022/3/11 15:39
 */
public interface EngineOptLogManager {

    /**
     * 添加操作日志数据
     *
     * @param engineOptLog
     */
    void insertOptLog(EngineOptLogDTO engineOptLog);
}