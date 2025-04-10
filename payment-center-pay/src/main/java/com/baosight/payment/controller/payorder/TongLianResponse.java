package com.baosight.payment.controller.payorder;

import lombok.Data;

/**
 * @program: payment-center
 * @description: 通联返回
 * @author: L.J.Ran
 * @create: 2025/3/19
 */
@Data
public class TongLianResponse {
    private String retcode;

    private String retmsg;


    public String trxreserve;
    /**
     * 业务流水号
     */
    public String bizseq;
    /**
     * 交易金额
     */
    public long amount;

    /**
     * 收银宝商户号
     */
    private String cusid;
    /**
     * 收银宝APPID
     */
    private String appid;
    /**
     * 随机字符串用于随机加签
     */
    private String randomstr;
    /**
     * 调用时间戳
     */
    private String timestamp;

    /**
     * sign校验码
     */
    private String sign;
    /**
     * 交易类型
     */
    private String trxcode;
}
