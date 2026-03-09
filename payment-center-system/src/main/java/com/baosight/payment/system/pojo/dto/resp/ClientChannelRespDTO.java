package com.baosight.payment.system.pojo.dto.resp;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientChannelRespDTO extends BasePO {
    /**
     * 渠道配置id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 渠道id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long channelId;

    /**
     *  渠道名称
     */
    private String channelName;

    /**
     * 当前配置状态
     */
    private Boolean enable;

}
