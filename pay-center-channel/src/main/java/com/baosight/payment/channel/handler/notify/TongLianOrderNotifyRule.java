package com.baosight.payment.channel.handler.notify;

import cn.hutool.core.date.DateUtil;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.enums.CallbackHandleStatus;
import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.pojo.dao.TongLianOrderResultNotifyDTO;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import com.baosight.utils.json.JsonUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 通联订单、会员、协议类回调识别与解析规则。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Component
@Order(30)
public class TongLianOrderNotifyRule implements IChannelNotifyRule {
    // TODO   通联订单回调规则待处理
    /**
     * 渠道编号
     */
    @Override
    public ChannelCode channelCode() {
        return null;
    }

    /**
     * 支持的回调事件类型。
     *
     * @return 回调事件类型
     */
    @Override
    public ChannelEventType eventType() {
        return null;
    }

    /**
     * 判断是否为通联订单、会员或协议类回调。
     *
     * @param request 渠道回调入站请求
     * @param key
     * @return true 表示通联订单、会员或协议类回调
     */
    @Override
    public boolean support(ChannelNotifyRequest request, String key) {
        String body = request.getBody();
        return body != null && (body.contains("\"reqTraceNum\"")
                || body.contains("\"orgReqTraceNum\"")
                || body.contains("\"signNum\"")
                || containsIgnoreCase(body, "agreement")
                || containsIgnoreCase(body, "protocol")
                || containsIgnoreCase(body, "phone")
                || containsIgnoreCase(body, "mobile"));
    }

    /**
     * 解析通联订单、会员或协议类回调为统一结果。
     *
     * @param request 渠道回调入站请求
     * @return 统一渠道回调结果
     */
    @Override
    public UnifiedPayNotifyDTO parse(ChannelNotifyRequest request) {
        TongLianOrderResultNotifyDTO dto = JsonUtil.parse(request.getBody(), TongLianOrderResultNotifyDTO.class);
        return new UnifiedPayNotifyDTO()
                .setChannelCode(ChannelCode.ALLIN_PAY)
                .setEventType(resolveEventType(request.getBody(), dto))
                .setHandleStatus(resolveStatus(dto.getResult(), dto.getTransferResult()))
//                .setBizOrderNo(resolveBizOrderNo(dto))
                .setChannelOrderNo(dto.getRespTraceNum())
                .setOriginChannelOrderNo(dto.getOrgRespTraceNum())
                .setChannelUser(dto.getSignNum())
                .setFinishTime(StringUtils.hasText(dto.getFinishTime()) ? DateUtil.parse(dto.getFinishTime()) : null)
                .setExtra(dto.getChannelParamInfo())
                .setErrMsg(dto.getRespMsg())
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

    private ChannelEventType resolveEventType(String body, TongLianOrderResultNotifyDTO dto) {
        if (StringUtils.hasText(dto.getOrgReqTraceNum())
                || StringUtils.hasText(dto.getOrgRespTraceNum())
                || StringUtils.hasText(dto.getTransferResult())) {
            return ChannelEventType.REFUND;
        }
        if (containsIgnoreCase(body, "phone") || containsIgnoreCase(body, "mobile")) {
            return ChannelEventType.MEMBER_PHONE_BIND;
        }
        if (containsIgnoreCase(body, "agreement") || containsIgnoreCase(body, "protocol")) {
            return ChannelEventType.AGREEMENT_SIGN;
        }
        return ChannelEventType.PAY_ORDER;
    }

    private String resolveBizOrderNo(TongLianOrderResultNotifyDTO dto) {
        if (StringUtils.hasText(dto.getReqTraceNum())) {
            return dto.getReqTraceNum();
        }
        return dto.getOrgReqTraceNum();
    }

    private CallbackHandleStatus resolveStatus(String result, String transferResult) {
        if ("1".equals(transferResult) || containsIgnoreCase(result, "SUCCESS")) {
            return CallbackHandleStatus.SUCCESS;
        }
        if ("0".equals(transferResult) || containsIgnoreCase(result, "FAIL")) {
            return CallbackHandleStatus.FAIL;
        }
        return CallbackHandleStatus.PROCESSING;
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toUpperCase().contains(keyword.toUpperCase());
    }
}
