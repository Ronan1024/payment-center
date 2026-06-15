package com.baosight.payment.channel.handler;

import cn.hutool.core.date.DateUtil;
import com.baosight.payment.channel.enums.CallbackHandleStatus;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.pojo.dao.TongLianPayResultNotifyDTO;
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
@Order(20)
public class TongLianPayFaceNotifyHandler implements PayNotifyHandler {


    @Autowired
    private PayNotifyProcessor payNotifyProcessor;

//    @Autowired
//    private ChannelGatewayLogManager channelGatewayLogManager;


    /**
     * 判断是否为通联当面付
     * @param body
     * @param request
     * @return
     */
    @Override
    public boolean support(String body, HttpServletRequest request) {
        // 可能同时判断 bizseq 或 trxstatus
        return body != null && body.contains("bizseq");
    }


    /**
     * 处理入参信息，改变商城订单状态，新增渠道日志
     * @param body
     * @param request
     * @return
     */
    @Override
    public String handle(String body, HttpServletRequest request) {
        TongLianPayResultNotifyDTO dto = JsonUtil.parse(body, TongLianPayResultNotifyDTO.class);
        UnifiedPayNotifyDTO result = new UnifiedPayNotifyDTO()
                .setChannelCode(ChannelCode.ALLIN_PAY)
                .setEventType(resolveEventType(dto))
                .setHandleStatus(resolveStatus(dto.getTrxstatus()))
                // TODO 待处理
//                .setBizOrderNo(dto.getBizseq())
                .setChannelOrderNo(StringUtils.hasText(dto.getChnltrxid()) ? dto.getChnltrxid() : dto.getTrxid())
                .setOriginChannelOrderNo(dto.getSrctrxid())
                .setChannelMchNo(dto.getCusid())
                .setChannelUser(dto.getLogonid())
                .setFinishTime(StringUtils.hasText(dto.getPaytime()) ? DateUtil.parse(dto.getPaytime(), "yyyyMMddHHmmss") : null)
                .setRawBody(body);
        payNotifyProcessor.process(result);
        return "success";
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
