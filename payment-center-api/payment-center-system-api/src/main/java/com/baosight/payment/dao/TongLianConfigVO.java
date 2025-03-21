package com.baosight.payment.dao;

import lombok.Data;

@Data
public class TongLianConfigVO {
    /**
     * 应用号
     */
    private String appId;

    private String spAppId;
    /**
     * 应用私钥
     */
    private String privateKeyStr;

    /**
     * 通联公钥
     */
    private String allinPayPublicKeyStr;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 收银宝商户号
     */
    private String signNum;

    /**
     * 商户名称
     */
    private String signName;
}
