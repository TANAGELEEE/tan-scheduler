/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.Method;

/**
 * EngineHandlerInterceptor
 *
 * @author liyunjun
 * @date 2022/3/11 15:31
 */
public interface EngineHandlerInterceptor {
    boolean preHandler(Method method, Object[] args) throws JsonProcessingException;

    void postHandler(Method method, Object[] args) throws JsonProcessingException;

    void afterCompletion(Method method, Object[] args);

    void afterThrowing(Method method, Object[] args, Throwable ex);
}