package com.baosight.payment.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class CallbackHandlerLogVO extends BasePO {

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    private Boolean hasHandler;

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
}
