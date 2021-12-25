/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.converter;

/**
 * POJOConverter
 *
 * @author liyunjun
 * @date 2021/12/25 21:35
 */
public interface POJOConverter<S, T> {

    T convert(S source);

    S reconvert(T target);
}