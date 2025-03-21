package com.baosight.payment.mch.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: payment-center
 * @description: 商户类型相应体
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Data
@AllArgsConstructor
public class MchTypeVO {
    /**
     * 类型code
     */
    private Integer code;

    /**
     * 类型名称
     */
    private String name;
}
