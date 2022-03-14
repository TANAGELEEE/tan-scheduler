/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core;

/**
 * DynamicProxyFactory
 *
 * @author liyunjun
 * @date 2022/3/11 15:33
 */
public interface DynamicProxyFactory {

    <T> T createProxy(T target, EngineInterceptorRegistry registry);
}