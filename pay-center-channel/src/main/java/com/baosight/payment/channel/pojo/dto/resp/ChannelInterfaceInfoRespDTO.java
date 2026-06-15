package com.baosight.payment.channel.pojo.dto.resp;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道接口详情响应。
 *
 * <p>
 * 该对象用于详情页展示渠道接口的完整配置，包括能力维度、渠道处理器、金额范围、
 * 回调/测试支持情况和维护备注等信息。
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelInterfaceInfoRespDTO extends BasePO {

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
     * 支付产品
     */
    private String payProduct;


    /**
     * 是否支持回调
     */
    private Integer supportNotify;

    /**
     * 是否支持测试
     */
    private Integer supportTest;

    /**
     * 最小金额，单位分
     */
    private Long minAmount;

    /**
     * 最大金额，单位分
     */
    private Long maxAmount;

    /**
     * 平台状态
     */
    private Integer status;


    /**
     * 处理器
     */
    private String handlerKey;

    /**
     * 备注
     */
    private String remark;
}
