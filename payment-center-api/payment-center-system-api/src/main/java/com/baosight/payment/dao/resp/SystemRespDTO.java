package com.baosight.payment.dao.resp;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/17
 */
@Data
public class SystemRespDTO {

    /**
     * 回调通知地址
     */
    private String notifyUrl;
}
