package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @program: payment-center
 * @description: 交易状态
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Getter
public enum PayState implements IBaseEnum<Integer> {
    /**
     * 交易成功
     */
    SUCCESS(PayOrderState.SUCCESS.code(), "交易成功", List.of("0000")),
    ;
    private final List<String> channelPayState;

    PayState(Integer code, String msg, List<String> channelPayState) {
        initEnum(code, msg);
        this.channelPayState = channelPayState;
    }


    public static PayState findState(String channelPayState) {
        return Arrays.stream(PayState.values()).filter(e -> e.getChannelPayState().contains(channelPayState)).findFirst().orElse(null);
    }
}
