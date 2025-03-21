package com.baosight.payment.system.pojo.dto;

import lombok.Data;

/**
 * @program: payment-center
 * @description: 服务商接口列表查询请求体
 * @author: L.J.Ran
 * @create: 2025/3/17$
 **/
@Data
public class PayInterfaceListDTO {
    /**
     * 接口名称
     */
    private String name;
}
