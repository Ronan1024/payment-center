//package com.baosight.payment.channel.handler.capability;
//
//import com.baosight.payment.channel.enums.ActionCode;
//import com.baosight.payment.channel.enums.CapabilityGroup;
//import com.baosight.payment.enums.ChannelCode;
//import com.baosight.payment.enums.ModeCode;
//import com.baosight.payment.enums.PayBrand;
//import com.baosight.payment.enums.PayScene;
//import lombok.Builder;
//import lombok.Data;
//
///**
// * @program: payment-center
// * @description:
// * @author: L.J.Ran
// * @create: 2026/6/10
// */
//@Data
//@Builder
//public class CapabilityInfo {
//
//    /**
//     * 接口编号
//     */
//    private String code;
//
//    /**
//     * 能力名称
//     */
//    private String name;
//
//    /**
//     * 能力描述
//     */
//    private String description;
//
//    /**
//     * 行为动作
//     */
//    private ActionCode actionCode;
//
//    /**
//     * 支付场景
//     */
//    private PayScene scene;
//
//    /**
//     * 能力域
//     */
//    private CapabilityGroup capabilityGroup;
//
//    /**
//     * 渠道编号
//     */
//    private ChannelCode channelCode;
//
//
//    /**
//     * 签约模式
//     */
//    private ModeCode mode;
//
//
//    /**
//     * 支付品牌
//     */
//    private PayBrand payBrand;
//
//    /**
//     * 能力接口唯一key
//     */
//    private String key;
//}
