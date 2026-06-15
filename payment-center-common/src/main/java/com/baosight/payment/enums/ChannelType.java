package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */


public enum ChannelType implements IBaseEnum<Integer> {

    /**
     * 官方支付渠道 例如微信、支付宝
     */
    OFFICIAL(1, "官方"),

    /**
     * 银行
     */
    BANK(2, "银行"),

    /**
     *  三方收单机构，例如通联、拉卡拉
     */
    THIRD_PARTY(3, "第三方"),

    /**
     * 测试
     */
    MOCK(4, "测试");

    ChannelType(Integer code, String msg) {
        initEnum(code, msg);
    }
}
