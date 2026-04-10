package com.baosight.payment.dao.resp;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
@Data
public class AllInRespDTO {
    /**
     * 会员类请求url
     */
    private String memberRequestUrl;

    /**
     * 通用公钥
     */
    private String publicKey;

    /**
     * 基础回调URL
     */
    private String baseCallUrl;

    /**
     * 版本号
     */
    private String version;
}
