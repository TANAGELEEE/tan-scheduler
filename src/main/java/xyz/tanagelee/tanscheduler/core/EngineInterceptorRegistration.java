/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core;

import org.springframework.util.Assert;

/**
 * EngineInterceptorRegistration
 *
 * @author liyunjun
 * @date 2022/3/11 15:30
 */
public class EngineInterceptorRegistration {
    private final EngineHandlerInterceptor interceptor;
    private int order = 0;

    public EngineInterceptorRegistration(EngineHandlerInterceptor interceptor) {
        Assert.notNull(interceptor, "Interceptor is required");
        this.interceptor = interceptor;
    }

    public EngineInterceptorRegistration order(int order) {
        this.order = order;
        return this;
    }

    protected int getOrder() {
        return this.order;
    }

    protected EngineHandlerInterceptor getInterceptor() {
        return this.interceptor;
    }
}