/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * DynamicProxyInvocationHandler
 *
 * @author liyunjun
 * @date 2022/3/11 15:34
 */
public class DynamicProxyInvocationHandler implements InvocationHandler {
    private Object target;
    private EngineInterceptorRegistry registry;

    /**
     * @param target   需要代理的实例
     * @param registry 拦截器注册器
     */
    public DynamicProxyInvocationHandler(Object target, EngineInterceptorRegistry registry) {
        this.target = target;
        this.registry = registry;
    }

    /**
     * 代理的执行类
     *
     * @param proxy  所生成的代理对象
     * @param method 调用的方法示例
     * @args args 参数数组
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        // 在执行method之前调用interceptor去做什么事
        List<EngineHandlerInterceptor> list = this.registry.getInterceptors();
        if (!list.isEmpty()) {
            for (EngineHandlerInterceptor engineHandlerInterceptor : list) {
                if (!engineHandlerInterceptor.preHandler(method, args)) {
                    return null;
                }
            }
        }
        try {
            // 在这里我们调用原始实例的method
            result = method.invoke(this.target, args);
        } catch (Throwable throwable) {
            // 在发生异常之后调用interceptor去做什么事
            if (!list.isEmpty()) {

                for (EngineHandlerInterceptor engineHandlerInterceptor : list) {
                    engineHandlerInterceptor.afterThrowing(method, args, throwable);
                }
            }
            throw throwable.getCause();
        }
        // 在执行method之后调用interceptor去做什么事
        if (!list.isEmpty()) {
            for (EngineHandlerInterceptor engineHandlerInterceptor : list) {
                engineHandlerInterceptor.postHandler(method, args);
            }
        }
        return result;
    }
}