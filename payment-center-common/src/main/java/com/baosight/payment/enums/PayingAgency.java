package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付机构code
 * @author L.J.Ran
 */
@Getter
public enum PayingAgency implements IBaseEnum<String> {
    /**
     * 通联
     */
    TONG_LIAN("TONG_LIAN", "通联支付", 1);

    private final Integer agencyCode;

    PayingAgency(String code, String msg, Integer agencyCode) {
        initEnum(code, msg);
        this.agencyCode = agencyCode;
    }

    public static PayingAgency byAgencyCode(Integer agencyCode){
        return Arrays.stream(PayingAgency.values()).filter(e -> e.getAgencyCode().equals(agencyCode)).findFirst().orElse(null);
    }

    public static PayingAgency getByCode(String code){
        return Arrays.stream(PayingAgency.values()).filter(e -> e.code().equals(code)).findFirst().orElse(null);
    }
}
