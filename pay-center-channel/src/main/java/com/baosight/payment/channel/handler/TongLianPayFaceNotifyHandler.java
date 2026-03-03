package com.baosight.payment.channel.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baosight.payment.channel.pojo.dao.TongLianPayResultNotifyDTO;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import com.baosight.payment.channel.pojo.entity.ChannelGatewayLog;
import com.baosight.payment.channel.service.ChannelGatewayLogManager;
import com.baosight.payment.channel.service.PayNotifyHandler;
import com.baosight.payment.channel.service.impl.PayNotifyProcessor;
import com.baosight.utils.json.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialException;
import java.util.Date;

@Component
public class TongLianPayFaceNotifyHandler implements PayNotifyHandler {


    @Autowired
    private PayNotifyProcessor payNotifyProcessor;

    @Autowired
    private ChannelGatewayLogManager channelGatewayLogManager;


    /**
     * 判断是否为通联当面付
     * @param body
     * @param request
     * @return
     */
    @Override
    public boolean support(String body, HttpServletRequest request) {
        // 可能同时判断 bizseq 或 trxstatus
        return body.contains("bizseq");
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
        ChannelGatewayLog channelGatewayLog = new ChannelGatewayLog();
        Long startTime = DateUtil.current();//方法执行开始时间
        channelGatewayLog.setOperation(ChannelGatewayLog.Operation.OUT_SIDE.getCode());
        channelGatewayLog.setBizStatus(ChannelGatewayLog.BizStatus.PROCESS.getCode());
        channelGatewayLog.setResParams(body);
        channelGatewayLog.setOutTradeNo(dto.getTrxid());
        channelGatewayLogManager.saveChannelGatewayLog(channelGatewayLog);
        try {
            //TODO 入站先新增数据状态为处理中
            UnifiedPayNotifyDTO result = new UnifiedPayNotifyDTO();
//        result.setChannel(PayChannel.ALLINPAY_FACE);
//        result.setStatus(dto.getTrxstatus());
//        result.setOrderNo(dto.getBizseq());
//        result.setThirdOrderNo(dto.getTrxid());
//        result.setFinishTime(parseDateTime(dto.getPaytime()));
//        result.setRawBody(body);
            payNotifyProcessor.process(result);

        }catch (Exception e) {
            channelGatewayLog.setErrorMsg(e.getMessage());
        }finally {
            Long cost = DateUtil.current() - startTime;
            channelGatewayLog.setCostTime(Math.toIntExact(cost));
            channelGatewayLogManager.updateById(channelGatewayLog);
        }
        return "success";
    }
}
