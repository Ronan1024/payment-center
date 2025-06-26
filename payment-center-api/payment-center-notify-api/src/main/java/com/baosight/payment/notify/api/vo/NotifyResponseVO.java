package com.baosight.payment.notify.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/20
 */
@Data
public class NotifyResponseVO {
    /**
     * 序号
     */
    private Integer index;

    /**
     * 时间
     */
    private Date time;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 返回结果
     */
    private String resResult;

    /**
     * 通知地址url
     */
    private String notifyUrl;
}
