/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.enums;

import java.util.Objects;

/**
 * EnumUtils
 *
 * @author liyunjun
 * @date 2021/12/25 21:38
 */
public class EnumUtils {
    public static <E extends Enum<E>, T> E getEnum(T value, Class<E> enumClazz) {
        if (enumClazz.isEnum() && isImplements(enumClazz, Valuable.class) && null != value) {
            E[] enums = enumClazz.getEnumConstants();
            if (null != enums && enums.length > 0) {
                for (E e : enums) {
                    @SuppressWarnings("unchecked")
                    Valuable<T> valuable = (Valuable<T>) e;
                    if (Objects.equals(value, valuable.getValue())) {
                        return e;
                    }
                }
            }
        }
        return null;
    }

    public static <E extends Enum<E>, T> E getEnum(T value, Class<E> enumClazz, E defaultValue) {
        E e = getEnum(value, enumClazz);
        return null != e ? e : defaultValue;
    }

    private static boolean isImplements(Class<?> clazz, Class<?> interfaceType) {
        Class<?>[] interfaces = clazz.getInterfaces();
        if (null != interfaces && interfaces.length > 0) {
            for (Class<?> type : interfaces) {
                if (type == interfaceType) {
                    return true;
                }
            }
        }
        return false;
    }
}