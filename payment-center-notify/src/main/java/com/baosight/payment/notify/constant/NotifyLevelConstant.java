package com.baosight.payment.notify.constant;

import cn.hutool.core.date.DateUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/9
 */
public class NotifyLevelConstant {
    /**
     * 1秒
     */
    public static final int ONE_SECOND = 1;

    /**
     * 30秒
     */
    public static final int THIRTY_SECOND = 30;

    /**
     * 1 分钟
     */
    public static final int ONE_MINUTE = 1 * 60;

    /**
     * 2分钟
     */
    public static final int TWO_MINUTE = 2 * 60;

    /**
     * 5 分钟
     */
    public static final int FIVE_MINUTE = 5 * 60;

    static final List<Integer> NOTIFY_LEVEL = Arrays.asList(ONE_SECOND, THIRTY_SECOND, THIRTY_SECOND, ONE_MINUTE, ONE_MINUTE, TWO_MINUTE, FIVE_MINUTE);

    public static Date getNotifyTime(Date date, int count) {
        count = count + 1 > NOTIFY_LEVEL.size() ? 0 : count - 1;
        Integer integer = NOTIFY_LEVEL.get(count);
        return DateUtil.offsetSecond(date, integer);
    }
}
