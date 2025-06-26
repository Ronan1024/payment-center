package com.baosight.payment.pojo.dto;

import com.baosight.database.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CallbackHandlerLogDTO extends PageRequest {
    /**
     * 支付机构
     */
    private Integer payingAgency;

    /**
     * 支付类型
     */
    private Integer payType;

    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 上游机构流水号
     */
    private String trxId;

    /**
     * 接口代码
     */
    private String interfaceCode;

    /**
     * 是否处理成功
     */
    private Boolean hasHandler;

}
