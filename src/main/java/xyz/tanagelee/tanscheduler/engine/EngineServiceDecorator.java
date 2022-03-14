/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tanagelee.tanscheduler.core.DynamicProxyFactory;
import xyz.tanagelee.tanscheduler.core.DynamicProxyFactoryImpl;
import xyz.tanagelee.tanscheduler.core.EngineInterceptorRegistry;
import xyz.tanagelee.tanscheduler.core.interceptors.OptLogInterceptor;
import xyz.tanagelee.tanscheduler.engine.service.EngineService;
import xyz.tanagelee.tanscheduler.engine.service.impl.EngineServiceImpl;

/**
 * EngineServiceDecorator
 *
 * @author liyunjun
 * @date 2022/3/11 15:24
 */
@Component
public class EngineServiceDecorator extends AbstractDecorator {
    @Autowired
    private EngineService engineService = new EngineServiceImpl();

    public EngineServiceDecorator() {
        EngineInterceptorRegistry registry = new EngineInterceptorRegistry();
        registry.addInterceptor(new OptLogInterceptor());

        DynamicProxyFactory dynamicProxyFactory = new DynamicProxyFactoryImpl();
        EngineService proxy = dynamicProxyFactory.createProxy(engineService, registry);
        super.setEngineService(proxy);
    }
}