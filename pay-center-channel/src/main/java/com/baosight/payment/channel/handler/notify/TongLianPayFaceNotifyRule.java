package com.baosight.payment.channel.handler.notify;

import cn.hutool.core.date.DateUtil;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.enums.CallbackHandleStatus;
import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.pojo.dao.TongLianPayResultNotifyDTO;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import com.baosight.utils.json.JsonUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 通联收银宝回调识别与解析规则。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Component
@Order(20)
public class TongLianPayFaceNotifyRule implements IChannelNotifyRule {

    /**
     * 渠道编号
     */
    @Override
    public ChannelCode channelCode() {
        return ChannelCode.ALLIN_PAY;
    }

    /**
     * 支持的回调事件类型。
     *
     * @return 回调事件类型
     */
    @Override
    public ChannelEventType eventType() {
        // TODO  处理通联收银宝事件类型
        return null;
    }

    /**
     * 判断是否为通联收银宝回调。
     *
     * @param request 渠道回调入站请求
     * @param key
     * @return true 表示通联收银宝回调
     */
    @Override
    public boolean support(ChannelNotifyRequest request, String key) {
        return request.getBody() != null && request.getBody().contains("bizseq");
    }

    /**
     * 解析通联收银宝回调为统一结果。
     *
     * @param request 渠道回调入站请求
     * @return 统一渠道回调结果
     */
    @Override
    public UnifiedPayNotifyDTO parse(ChannelNotifyRequest request) {
        TongLianPayResultNotifyDTO dto = JsonUtil.parse(request.getBody(), TongLianPayResultNotifyDTO.class);
        return new UnifiedPayNotifyDTO()
                .setChannelCode(channelCode())
                .setEventType(resolveEventType(dto))
                .setHandleStatus(resolveStatus(dto.getTrxstatus()))
                .setChannelOrderNo(StringUtils.hasText(dto.getChnltrxid()) ? dto.getChnltrxid() : dto.getTrxid())
                .setOriginChannelOrderNo(dto.getSrctrxid())
                .setChannelMchNo(dto.getCusid())
                .setChannelUser(dto.getLogonid())
                .setFinishTime(StringUtils.hasText(dto.getPaytime()) ? DateUtil.parse(dto.getPaytime(), "yyyyMMddHHmmss") : null)
                .setRawBody(request.getBody());
    }

    /**
     * 获取通联成功响应内容。
     *
     * @return 通联成功响应内容
     */
    @Override
    public String successResponse() {
        return "success";
    }

    /**
     * 执行处理
     *
     * @param dto 统一渠道回调结果
     * @return true 表示处理成功
     */
    @Override
    public Boolean process(UnifiedPayNotifyDTO dto) {
        return null;
    }

    private ChannelEventType resolveEventType(TongLianPayResultNotifyDTO dto) {
        if (StringUtils.hasText(dto.getSrctrxid()) || containsIgnoreCase(dto.getTrxcode(), "REFUND")) {
            return ChannelEventType.REFUND;
        }
        return ChannelEventType.PAY_ORDER;
    }

    private CallbackHandleStatus resolveStatus(String status) {
        if (containsIgnoreCase(status, "SUCCESS")) {
            return CallbackHandleStatus.SUCCESS;
        }
        if (containsIgnoreCase(status, "FAIL")) {
            return CallbackHandleStatus.FAIL;
        }
        return CallbackHandleStatus.PROCESSING;
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toUpperCase().contains(keyword.toUpperCase());
    }
}
