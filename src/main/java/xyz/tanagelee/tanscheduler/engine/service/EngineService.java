/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.engine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import xyz.tanagelee.tanscheduler.vo.TaskVO;

/**
 * EngineService
 *
 * @author liyunjun
 * @date 2022/3/11 15:16
 */
public interface EngineService {
    JsonNode execute(TaskVO taskVO) throws JsonProcessingException;

    JsonNode afterExecute();
}