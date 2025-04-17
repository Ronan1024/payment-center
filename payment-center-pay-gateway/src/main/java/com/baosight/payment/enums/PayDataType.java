package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;


public enum PayDataType implements IBaseEnum<String> {

    /**
     * 跳转链接的方式
     */
    PAY_URL("PAY_URL", "跳转链接的方式"),

    /**
     * 表单提交
     */
    FORM("FORM", "表单提交"),
    /**
     * 微信app参数
     */
    WX_APP("WX_APP", "微信app参数"),
//    String ALI_APP = "aliapp";  //支付宝app参数
//    String YSF_APP = "ysfapp";  //云闪付app参数

    /**
     * 二维码URL
     */
    CODE_URL("CODE_URL", "二维码URL"),
    /**
     * 二维码图片显示URL
     */
    CODE_IMG_URL("CODE_IMG_URL", "二维码图片显示URL"),
    /**
     * 无参数
     */
    NONE("NONE", "无参数");;

    PayDataType(String code, String msg) {
        initEnum(code, msg);
    }
}
