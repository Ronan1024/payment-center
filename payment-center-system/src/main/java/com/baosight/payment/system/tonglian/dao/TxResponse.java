package com.baosight.payment.system.tonglian.dao;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应对象
 *
 * @author gejunqing
 * @version 1.0
 * @date 2024/1/11
 */
@Data
public class TxResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    /**
     * 应用id
     */
    private String appId;
    private String spAppId;
    private String transCode;
    private String transDate;
    private String transTime;
    private String format;
    private String charset;
    private String signType;
    private String sign;
    private String version;
    private String code;
    private String msg;
    private String bizData;
}
