package com.baosight.payment.channel.pojo.dao;

import com.baosight.payment.channel.enums.CallbackHandleStatus;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.enums.ChannelCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Map;

/**
 * 统一渠道回调结果。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Data
@Accessors(chain = true)
public class UnifiedPayNotifyDTO {

    /**
     * 支付渠道编号。
     */
    private ChannelCode channelCode;

    /**
     * 回调事件类型。
     */
    private ChannelEventType eventType;

    /**
     * 统一业务状态。
     */
    private CallbackHandleStatus handleStatus;

    /**
     * 系统业务主键
     */
    private Long bizId;

    /**
     * 渠道流水
     */
    private String channelSeqNo;

    /**
     * 渠道订单号。
     */
    private String channelOrderNo;

    /**
     * 原渠道订单号，退款等关联原交易场景使用。
     */
    private String originChannelOrderNo;

    /**
     * 渠道商户号。
     */
    private String channelMchNo;

    /**
     * 渠道用户标识。
     */
    private String channelUser;

    /**
     * 完成时间。
     */
    private Date finishTime;

    /**
     * 渠道错误码。
     */
    private String errCode;

    /**
     * 渠道错误描述。
     */
    private String errMsg;

    /**
     * 原始回调内容。
     */
    private String rawBody;

    /**
     * 外部系统交易号
     */
    private String outTradeNo;

    /**
     * 扩展信息。
     */
    private Map<String, Object> extra;

    /**
     * 判断渠道回调结果是否明确成功。
     *
     * @return true 表示明确成功
     */
    public boolean success() {
        return CallbackHandleStatus.SUCCESS.equals(handleStatus);
    }

    /**
     * 判断渠道回调结果是否明确失败。
     *
     * @return true 表示明确失败
     */
    public boolean fail() {
        return CallbackHandleStatus.FAIL.equals(handleStatus);
    }

    /**
     * 判断渠道回调结果是否仍在处理中。
     *
     * @return true 表示仍在处理中
     */
    public boolean processing() {
        return CallbackHandleStatus.PROCESSING.equals(handleStatus);
    }
}
