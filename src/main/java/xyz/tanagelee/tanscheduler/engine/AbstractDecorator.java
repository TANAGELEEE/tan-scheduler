/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import xyz.tanagelee.tanscheduler.engine.service.EngineService;
import xyz.tanagelee.tanscheduler.vo.TaskVO;

/**
 * AbstractDecorator
 *
 * @author liyunjun
 * @date 2022/3/11 15:22
 */
public class AbstractDecorator implements EngineService {

    private EngineService engineService;

    public AbstractDecorator() {
    }

    @Override
    public JsonNode execute(TaskVO taskVO) throws JsonProcessingException {
        return engineService.execute(taskVO);
    }

    @Override
    public JsonNode afterExecute() {
        return engineService.afterExecute();
    }

    protected void setEngineService(EngineService engineService) {
        this.engineService = engineService;
    }
}