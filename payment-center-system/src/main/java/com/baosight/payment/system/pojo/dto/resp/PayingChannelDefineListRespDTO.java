package com.baosight.payment.system.pojo.dto.resp;

import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * 支付通道列表信息
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/6
 */
@Data
public class PayingChannelDefineListRespDTO {

    /**
     * 通道定义Id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 通道名称
     */
    private String name;


    /**
     * 通道编号
     */
    private String code;


}
