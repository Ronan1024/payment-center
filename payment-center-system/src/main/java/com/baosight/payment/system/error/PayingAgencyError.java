package com.baosight.payment.system.error;

import com.baosight.utils.enums.IBaseEnum;


public enum PayingAgencyError implements IBaseEnum<String> {
    /**
     * 支付机构异常
     */
    PAYING_AGENCY_ERROR("40001", "{}未完成支付配置");

    PayingAgencyError(String code, String msg) {
        initEnum(code, msg);
    }
}
