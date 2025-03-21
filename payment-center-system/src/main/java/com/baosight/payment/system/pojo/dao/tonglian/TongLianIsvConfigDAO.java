package com.baosight.payment.system.pojo.dao.tonglian;

import lombok.Data;

@Data
public class TongLianIsvConfigDAO {
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
}
