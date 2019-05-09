package com.ifast.common.utils;

import java.util.UUID;

/**
 * <pre>
 *
 * </pre>
 * <small> 2019-05-09 16:40 | Aron</small>
 */
public abstract class UUIDUtils {

    public static String get() {
        return get(true);
    }

    public static String get(boolean isPureStr) {
        String sesult = UUID.randomUUID().toString();
        if (isPureStr) {
            sesult = sesult.replaceAll("-", "");
        }
        return sesult;
    }

}
