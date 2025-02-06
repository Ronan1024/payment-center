package com.baosight.payment.pojo.vo;

import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayInterfaceConfigVO extends BasePO {
    private Long id;

    /**
     * 客户端类型
     */
    private Integer clientType;

    /**
     * 客户端id 如服务商、商家等id
     */
    private Long clientId;

    /**
     * 支付接口id
     */
    private Long interfaceId;

    /**
     * 支付接口参数
     */
    private String interfaceParams;

    /**
     * 支付接口费率
     */
    private Long interfaceRate;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 备注信息
     */
    private String remark;

    private Long createBy;

    private Long updateBy;

}
