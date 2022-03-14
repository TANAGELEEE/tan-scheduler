/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * DynamicProxyFactoryImpl
 *
 * @author liyunjun
 * @date 2022/3/11 15:34
 */
public class DynamicProxyFactoryImpl implements DynamicProxyFactory{
    @Override
    public <T> T createProxy(T target, EngineInterceptorRegistry registry) {
        // 当前对象的类加载器
        ClassLoader classLoader = target.getClass().getClassLoader();
        // 获取此对象实现的所有接口
        Class<?>[] interfaces = target.getClass().getInterfaces();
        // 利用DynamicProxyInvocationHandler类来实现InvocationHandler
        InvocationHandler handler = new DynamicProxyInvocationHandler(target, registry);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}