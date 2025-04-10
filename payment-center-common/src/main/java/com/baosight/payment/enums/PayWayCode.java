package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PayWayCode implements IBaseEnum<String> {

    /**
     * 通联码牌
     */
    TONG_LIAN_QR_PLATE("tongLianQrPlate", "通联码牌", 1),
    /**
     * 通联微信正扫
     */
    TONG_LIAN_WX_SCAN("tongLianWxScan", "通联微信正扫", 2),

    /**
     * 通联微信小程序支付
     */
    TONG_LIAN_WX_MINI_PROGRAM("tongLianWxMiniProgram", "通联微信小程序", 4),

    /**
     * 特殊支付方式( 通过二维码跳转到收银台完成支付， 已集成获取用户ID的实现。 )
     */
    QR_CASHIER("QR_CASHIER", "通过二维码跳转到收银台完成支付", 3),
    ;


    private final Integer wayCode;

    PayWayCode(String code, String name, Integer wayCode) {
        initEnum(code, name);
        this.wayCode = wayCode;
    }


    public static PayWayCode payWayCode(Integer wayCode) {
        return Arrays.stream(PayWayCode.values()).filter(e -> e.getWayCode().equals(wayCode)).findFirst().orElse(null);
    }
}
