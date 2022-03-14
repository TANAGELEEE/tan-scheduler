/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core.interceptors;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tanagelee.tanscheduler.core.EngineHandlerInterceptor;
import xyz.tanagelee.tanscheduler.dto.EngineOptLogDTO;
import xyz.tanagelee.tanscheduler.engine.manager.EngineOptLogManager;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * OptLogInterceptor
 *
 * @author liyunjun
 * @date 2022/3/11 15:32
 */
@Component
@Slf4j
public class OptLogInterceptor implements EngineHandlerInterceptor {

    @Autowired
    private EngineOptLogManager engineOptLogManager;

    private static EngineOptLogManager optLogManager;

    @PostConstruct
    public void init() {
        optLogManager = engineOptLogManager;
    }

    private EngineOptLogDTO engineOptLog = new EngineOptLogDTO();

    @Override
    public boolean preHandler(Method method, Object[] args) throws JsonProcessingException {
        // TODO
        return true;
    }

    @Override
    public void postHandler(Method method, Object[] args) throws JsonProcessingException {
        log.debug("this is JurisdictionInterceptor postHandler");

        optLogManager.insertOptLog(engineOptLog);
    }

    @Override
    public void afterCompletion(Method method, Object[] args) {
        log.debug("this is JurisdictionInterceptor afterCompletion");
    }

    @Override
    public void afterThrowing(Method method, Object[] args, Throwable ex) {
        optLogManager.insertOptLog(engineOptLog);
    }
}