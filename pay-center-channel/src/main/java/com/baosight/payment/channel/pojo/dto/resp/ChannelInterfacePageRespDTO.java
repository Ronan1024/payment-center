package com.baosight.payment.channel.pojo.dto.resp;

import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * 渠道接口分页响应。
 *
 * <p>
 * 该对象服务于运营端列表页，只包含表格展示、筛选回显和状态操作所需的轻量字段。
 * 详情页字段请使用 {@link ChannelInterfaceInfoRespDTO}。
 * </p>
 */
@Data
public class ChannelInterfacePageRespDTO {

    /**
     * 渠道接口 ID
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 接口编码
     */
    private String interfaceCode;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 使用范围，当前对应签约模式
     */
    private String modeCode;

    /**
     * 渠道处理器
     */
    private String handlerKey;

    /**
     * 能力域
     */
    private String capabilityType;

    /**
     * 能力动作
     */
    private String actionCode;

    /**
     * 支付品牌
     */
    private String payBrand;

    /**
     * 支付场景
     */
    private String payScene;

    /**
     * 是否支持回调
     */
    private Integer supportNotify;

    /**
     * 是否支持测试
     */
    private Integer supportTest;

    /**
     * 平台状态
     */
    private Integer status;
}
