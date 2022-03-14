/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core;

import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EngineInterceptorRegistry
 *
 * @author liyunjun
 * @date 2022/3/11 15:30
 */
public class EngineInterceptorRegistry {
    private final List<EngineInterceptorRegistration> registrations = new ArrayList();

    public EngineInterceptorRegistration addInterceptor(EngineHandlerInterceptor interceptor) {
        EngineInterceptorRegistration registration = new EngineInterceptorRegistration(interceptor);
        this.registrations.add(registration);
        return registration;
    }

    protected List<EngineHandlerInterceptor> getInterceptors() {
        return this.registrations.stream().sorted(INTERCEPTOR_ORDER_COMPARATOR).map(EngineInterceptorRegistration::getInterceptor)
                .collect(Collectors.toList());
    }

    private static final Comparator<Object> INTERCEPTOR_ORDER_COMPARATOR = OrderComparator.INSTANCE.withSourceProvider(object -> {
        if (object instanceof EngineInterceptorRegistration) {
            return (Ordered) ((EngineInterceptorRegistration) object)::getOrder;
        }
        return null;
    });
}