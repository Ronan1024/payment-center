package com.baosight.payment.channel.handler;

import cn.hutool.core.date.DateUtil;
import com.baosight.payment.channel.enums.CallbackHandleStatus;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.pojo.dao.TongLianOrderResultNotifyDTO;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import com.baosight.payment.channel.service.PayNotifyHandler;
import com.baosight.payment.channel.service.impl.PayNotifyProcessor;
import com.baosight.payment.enums.ChannelCode;
import com.baosight.utils.json.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(30)
public class TongLianPayISVNotifyHandler implements PayNotifyHandler {

    @Autowired
    private PayNotifyProcessor payNotifyProcessor;

//    @Autowired
//    private ChannelGatewayLogManager channelGatewayLogManager;


    /**
     * 判断是否为通联订单结果通知
     * @param body
     * @param request
     * @return
     */
    @Override
    public boolean support(String body, HttpServletRequest request) {
        return body != null && (body.contains("\"reqTraceNum\"")
                || body.contains("\"orgReqTraceNum\"")
                || body.contains("\"signNum\"")
                || body.contains("\"agreement\"")
                || body.contains("\"protocol\"")
                || body.contains("\"phone\""));
    }


    /**
     * 处理入参信息，改变商城订单状态，新增渠道日志
     * @param body
     * @param request
     * @return
     */
    @Override
    public String handle(String body, HttpServletRequest request) {
        TongLianOrderResultNotifyDTO dto = JsonUtil.parse(body, TongLianOrderResultNotifyDTO.class);
        UnifiedPayNotifyDTO payNotifyDTO = new UnifiedPayNotifyDTO()
                .setChannelCode(ChannelCode.ALLIN_PAY)
                .setEventType(resolveEventType(body, dto))
                .setHandleStatus(resolveStatus(dto.getResult(), dto.getTransferResult()))
                .setChannelOrderNo(dto.getRespTraceNum())
                .setOriginChannelOrderNo(dto.getOrgRespTraceNum())
                .setChannelUser(dto.getSignNum())
                .setFinishTime(StringUtils.hasText(dto.getFinishTime()) ? DateUtil.parse(dto.getFinishTime()) : null)
                .setExtra(dto.getChannelParamInfo())
                .setErrMsg(dto.getRespMsg())
                .setRawBody(body);
        payNotifyProcessor.process(payNotifyDTO);
        return "success";  // 通联统一要求返回 success
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
