package com.baosight.payment.channel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道接口能力实体。
 *
 * <p>
 * 对应渠道接口能力表，用于沉淀渠道处理器暴露出来的具体业务接口能力。
 * 一条记录描述一个接口编码在指定能力域、动作、品牌、场景下的平台管理状态和运行约束。
 * </p>
 *
 * @TableName channel_interface
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "channel_interface")
public class ChannelInterface extends BasePO {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 接口编码
     */
    private String interfaceCode;


    /**
     * 接口唯一标识
     */
    private String interfaceKey;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 渠道编码
     */
    private String channelCode;

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
     * 接口状态，见 InterfaceStatusEnum
     */
    private Integer status;

    /**
     * 描述
     */
    private String remark;


    /**
     * 签约模式
     */
    private String modeCode;

    /**
     * 处理器
     */
    private String handlerKey;
}

