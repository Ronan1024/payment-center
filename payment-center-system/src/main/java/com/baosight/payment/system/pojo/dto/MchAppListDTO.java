package com.baosight.payment.system.pojo.dto;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/19
 */
@Data
public class MchAppListDTO {

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用编号
     */
    private String appCode;

    /**
     * 状态
     */
    private Integer state;

}
