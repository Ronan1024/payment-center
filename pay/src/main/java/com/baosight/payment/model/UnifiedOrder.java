package com.baosight.payment.model;

import java.util.Date;

/**
 * 统一下单请求体
 */
public class UnifiedOrder {
    /**
     * 商户号
     */
    private Long mchId;
    /**
     * 请求流水号
     */
    private String requestId;
    /**
     * 应用id
     */
    private Long appId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 支付金额 单位分
     */
    private Long totalFee;

    /**
     * 签名
     */
    private String sign;

    /**
     * 签名类型
     */
    private String signType;

    /**
     * 接口版本
     */
    private String version;

    /**
     * 通知接口
     */
    private String notifyUrl;


    /**
     * 跳转通知地址
     **/
    private String returnUrl;

    /**
     * 支付方式
     */
    private String wayCode;

    /**
     * 支付方式币种
     */
    private String currency;

    /**
     * 商品标题
     */
    private String subject;

    /**
     * 商品描述信息
     */
    private String body;

    /**
     * 商户扩展参数
     */
    private String extParam;

    /**
     * 订单失效时间
     */
    private Integer expiredTime;

    /**
     * 分账模式
     */
    private Integer divisionMode;

    public Long getMchId() {
        return mchId;
    }

    public void setMchId(Long mchId) {
        this.mchId = mchId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getWayCode() {
        return wayCode;
    }

    public void setWayCode(String wayCode) {
        this.wayCode = wayCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getExtParam() {
        return extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    public Integer getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getDivisionMode() {
        return divisionMode;
    }

    public void setDivisionMode(Integer divisionMode) {
        this.divisionMode = divisionMode;
    }
}
