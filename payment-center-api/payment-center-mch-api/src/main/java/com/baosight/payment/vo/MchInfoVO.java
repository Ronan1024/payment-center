package com.baosight.payment.vo;

import lombok.Data;

/**
 * @author L.J.Rab
 */
@Data
public class MchInfoVO {
    private Long id;
    /**
     * 服务商名称
     */
    private String mchName;

    /**
     * 服务商简称
     */
    private String mchShortName;


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

    /**
     * 商户类型
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 服务商id
     */
    private Long isvId;
    /**
     *商户号
     */
    private String mchNo;
}
