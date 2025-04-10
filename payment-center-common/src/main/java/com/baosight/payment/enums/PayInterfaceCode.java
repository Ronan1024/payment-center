package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;
import lombok.Getter;

/**
 * @program: payment-center
 * @description: 支付接口code
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
@Getter
public enum PayInterfaceCode implements IBaseEnum<String> {
    /**
     * 通联码牌
     */
    TONG_LIAN_QR("tl_qr", "通联码牌","tLQR"),
    /**
     * 通联支付
     */
    TONG_LIAN_PAY("tl_pay", "通联支付", "tLPay"),
    ;

    private final String codeName;

    PayInterfaceCode(String code, String msg, String codeName) {
        initEnum(code, msg);
        this.codeName = codeName;
    }
}
