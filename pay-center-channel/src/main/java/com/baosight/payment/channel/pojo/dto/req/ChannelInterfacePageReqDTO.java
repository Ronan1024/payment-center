package com.baosight.payment.channel.pojo.dto.req;

import com.baosight.database.core.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道接口分页查询条件。
 *
 * <p>
 * 用于运营端渠道接口管理页面的筛选查询。分页列表只返回表格展示所需字段，
 * 详情信息通过单独接口查询，避免列表接口承载过多配置字段。
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelInterfacePageReqDTO extends PageRequest {

    /**
     * 接口编码 / 名称关键字
     */
    private String keyword;

    /**
     * 渠道编码
     */
    private String channelCode;

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
     * 使用范围，当前对应签约模式
     */
    private String modeCode;

    /**
     * 平台状态
     */
    private Integer status;
}
