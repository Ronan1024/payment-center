package com.baosight.payment.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

/*
 * String 工具类
 *
 */
public class StringUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "") + Thread.currentThread().getId();
    }

    public static String getUUID(int endAt) {
        return getUUID().substring(0, endAt);
    }

    /**
     * 是否 http 或 https连接
     **/
    public static boolean isAvailableUrl(String url) {

        if (StringUtils.isEmpty(url)) {
            return false;
        }

        return url.startsWith("http://") || url.startsWith("https://");
    }



}
