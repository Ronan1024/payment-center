package com.baosight.payment.channel.pojo.dao;


import lombok.Data;

import java.util.List;

@Data
public class TongLianPayResultNotifyDTO {

    /**
     * 交易类型
     */
    private String trxcode;

    /**
     * 收银宝 APPID
     */
    private String appid;

    /**
     * 收银宝商户号
     */
    private String cusid;

    /**
     * 请求时间戳
     * 格式：yyyyMMddHHmmss
     */
    private String timestamp;

    /**
     * 随机字符串
     */
    private String randomstr;

    /**
     * 签名
     */
    private String sign;

    /**
     * 业务流水号
     * 商户订单号
     */
    private String bizseq;

    /**
     * 交易状态
     * SUCCESS / FAIL（以通联文档为准）
     */
    private String trxstatus;

    /**
     * 交易金额
     * 单位：分
     */
    private Long amount;

    /**
     * 通联交易流水号
     */
    private String trxid;

    /**
     * 原交易流水号
     * 撤销 / 冲正交易时返回
     */
    private String srctrxid;

    /**
     * 交易请求日期
     * 格式：yyyyMMdd
     */
    private String trxday;

    /**
     * 交易完成时间
     * 格式：yyyyMMddHHmmss
     */
    private String paytime;

    /**
     * 终端编号
     */
    private String termid;

    /**
     * 终端批次号
     */
    private String termbatchid;

    /**
     * 终端流水号
     */
    private String traceno;

    /**
     * 商户自定义保留字段
     */
    private String trxreserve;

    /**
     * 借贷标志
     */
    private String accttype;

    /**
     * 发卡行代码
     * accttype=05 时为卡组织代码
     */
    private String bankcode;

    /**
     * 交易账号
     * 部分交易类型返回
     */
    private String acct;

    /**
     * 终端授权码
     */
    private String termauthno;

    /**
     * 终端参考号
     */
    private String termrefnum;

    /**
     * 渠道信息
     * JSON 字符串，不同支付渠道字段不同
     */
    private String chnldata;

    /**
     * 渠道侧交易流水号
     * 如：微信 / 支付宝订单号
     */
    private String chnltrxid;

    /**
     * 手续费
     */
    private String fee;

    /**
     * 分期数
     */
    private String fqnum;

    /**
     * 签名类型
     */
    private String signtype;

    /**
     * 用户账号
     * 支付宝买家号 / 云闪付 userid
     */
    private String logonid;

    /**
     * 原始交易金额
     * 单位：分
     */
    private String initamt;

    /**
     * 结算周期
     */
    private String feecycle;

    /**
     * 营销权益抵扣明细
     * JSON 字符串
     */
    private List<TongLianMktresultDTO> mktresult;
}
