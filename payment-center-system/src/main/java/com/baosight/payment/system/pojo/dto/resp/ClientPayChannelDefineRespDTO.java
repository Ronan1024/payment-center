package com.baosight.payment.system.pojo.dto.resp;

import com.baosight.payment.system.utils.DynamicFormUtil;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/10
 */
@Data
public class ClientPayChannelDefineRespDTO {

    /**
     * id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 参数配置信息
     */
    private List<DynamicFormUtil.DynamicForm> dynamicForm;


    /**
     * 后续操作预留字段
     */
    public String remark;
}
