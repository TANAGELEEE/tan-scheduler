/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.engine.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import xyz.tanagelee.tanscheduler.engine.service.EngineService;
import xyz.tanagelee.tanscheduler.vo.TaskVO;

/**
 * EngineServiceImpl
 *
 * @author liyunjun
 * @date 2022/3/11 15:26
 */
@Service
public class EngineServiceImpl implements EngineService {

    @Override
    public JsonNode execute(TaskVO taskVO) throws JsonProcessingException {
        System.out.println("EngineServiceImpl......");
        return null;
    }

    @Override
    public JsonNode afterExecute() {
        return null;
    }
}