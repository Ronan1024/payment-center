package com.baosight.payment.system.enums;

import com.baosight.payment.system.pojo.dao.tonglian.TongLianMemberBasicInfoDAO;
import com.ronan.common.enums.IBaseEnum;
import com.baosight.utils.json.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * @author L.J.Ran
 */

public enum TongLianInfoType implements IBaseEnum<Integer> {
    /**
     * 基本信息
     */
    BASIC_INFO(1, "基本信息"),
    /**
     * 银行账户信息
     */
    BANK_ACCOUNT_INFO(2, "银行账户信息"),
    /**
     * 协议信息
     */
    PROTOCOL_INFO(3, "协议信息"),
    /**
     * 影印件ocr核对信息
     */
    OCR_CHECK_INFO(4, "影印件ocr核对信息"),
    /**
     * 绑定手机号信息
     */
    BIND_PHONE_INFO(5, "绑定手机号信息"),
    /**
     * 支付账户信息
     */
    PAY_ACCOUNT_INFO(6, "支付账户信息"),
    /**
     * 支付账户审核结果详情
     */
    PAY_ACCOUNT_AUDIT_INFO(7, "支付账户审核结果详情"),
    /**
     * 待结算户
     */
    WAIT_SETTLEMENT_ACCOUNT(9, "待结算户");;


    TongLianInfoType(Integer type, String msg) {
        initEnum(type, msg);
    }

    public static <T> T build(JsonNode result, TongLianInfoType tongLianInfoType) {
        if (tongLianInfoType.equals(BASIC_INFO)) {
            return (T) JsonUtil.parse(JsonUtil.toJson(result.get("memberBasicInfo")), TongLianMemberBasicInfoDAO.class);
        }

        return null;
    }
}
