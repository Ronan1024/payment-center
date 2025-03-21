package com.baosight.payment.isv.vo;

import lombok.Data;

/**
 * @program: payment-center
 * @description: 服务商信息
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Data
public class IsvInfoVO {
    private Long id;
    /**
     * 服务商名称
     */
    private String name;

    /**
     * 服务商简称
     */
    private String shortName;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactTel;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 状态
     */
    private Integer state;
}
