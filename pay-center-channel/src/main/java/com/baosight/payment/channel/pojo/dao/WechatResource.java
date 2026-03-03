package com.baosight.payment.channel.pojo.dao;


import lombok.Data;

@Data
public class WechatResource {
    /**
     * 原始数据类型
     * 示例：transaction
     */
    private String originalType;

    /**
     * 加密算法
     * 示例：AEAD_AES_256_GCM
     */
    private String algorithm;

    /**
     * Base64 编码后的密文
     */
    private String ciphertext;

    /**
     * 附加数据
     */
    private String associatedData;

    /**
     * 随机串
     */
    private String nonce;
}
