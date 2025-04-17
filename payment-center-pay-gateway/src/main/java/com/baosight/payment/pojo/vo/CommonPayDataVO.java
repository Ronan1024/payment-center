package com.baosight.payment.pojo.vo;

import lombok.Data;


/**
 * 通用支付数据返回
 */
@Data
public class CommonPayDataVO {
    /**
     * 跳转地址
     **/
    private String payUrl;

    /**
     * 二维码地址
     **/
    private String codeUrl;

    /**
     * 二维码图片地址
     **/
    private String codeImgUrl;

    /**
     * 表单内容
     **/
    private String formContent;
}
