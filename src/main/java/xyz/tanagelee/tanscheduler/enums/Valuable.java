/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.enums;

/**
 * Valuable
 *
 * @author liyunjun
 * @date 2021/12/25 18:58
 */
public interface Valuable<T> {

    T getValue();

    String getDescription();
}