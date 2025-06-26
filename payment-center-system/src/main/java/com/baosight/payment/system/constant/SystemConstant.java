package com.baosight.payment.system.constant;

import com.baosight.utils.utils.StrFormatter;

/**
 * @author L.J.Ran
 */
public class SystemConstant {

    private SystemConstant(){
        throw new IllegalStateException("Utility class");
    }

    private static final String BASE = "payment:system:";

    /**
     * 通联绑定手机号返回信息
     */
    private static final String TONG_LIAN_BIND_PHONE_RESP = BASE + "tong_lian:sign_num_{}:{}:phone_{}";


    /**
     * 获取通联手机号申请
     * @param signNum 商户号
     * @param phone 手机号
     * @param hasBind 是否为绑定操作
     */
    public static String getTongLianPhoneResp(String signNum, String phone, Boolean hasBind) {
        if (Boolean.TRUE.equals(hasBind)) {
            return StrFormatter.format(TONG_LIAN_BIND_PHONE_RESP, signNum, "bind", phone);
        } else {
            return StrFormatter.format(TONG_LIAN_BIND_PHONE_RESP, signNum, "unbind", phone);
        }
    }
}
