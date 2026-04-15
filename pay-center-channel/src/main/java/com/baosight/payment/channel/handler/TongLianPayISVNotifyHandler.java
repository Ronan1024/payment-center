package com.baosight.payment.channel.handler;

import cn.hutool.core.date.DateUtil;
import com.baosight.common.exception.ServiceException;
import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
import com.baosight.payment.channel.pojo.dao.TongLianOrderResultNotifyDTO;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import com.baosight.payment.channel.service.PayNotifyHandler;
import com.baosight.payment.channel.service.impl.PayNotifyProcessor;
import com.baosight.utils.json.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
        return body.contains("\"reqTraceNum\"");
    }


    /**
     * 处理入参信息，改变商城订单状态，新增渠道日志
     * @param body
     * @param request
     * @return
     */
    @Override
    public String handle(String body, HttpServletRequest request) {
        //1.记录渠道日志
        TongLianOrderResultNotifyDTO dto = JsonUtil.parse(body, TongLianOrderResultNotifyDTO.class);
        ChannelGatewayLog channelGatewayLog = new ChannelGatewayLog();
        Long startTime = DateUtil.current();//方法执行开始时间
        channelGatewayLog.setOperation(ChannelGatewayLog.Operation.OUT_SIDE.getCode());
        channelGatewayLog.setBizStatus(ChannelGatewayLog.BizStatus.PROCESS.getCode());
        channelGatewayLog.setResParams(body);
        channelGatewayLog.setOutTradeNo(dto.getRespTraceNum());
//        channelGatewayLogManager.saveChannelGatewayLog(channelGatewayLog);
        try {
            //2.更新商城订单状态
        UnifiedPayNotifyDTO payNotifyDTO = new UnifiedPayNotifyDTO();
//        result.setChannel(PayChannel.ALLINPAY_ISV);
//        result.setStatus(dto.getResult());
//        result.setOrderNo(dto.getReqTraceNum());
//        result.setThirdOrderNo(dto.getRespTraceNum());
//        result.setFinishTime(parseDateTime(dto.getFinishTime()));
//        result.setRawBody(body);
            Boolean process = payNotifyProcessor.process(payNotifyDTO);
            if (!process) {
                throw new ServiceException("商城订单信息更新失败");
            }
        }catch (Exception e) {
            channelGatewayLog.setErrorMsg(e.getMessage());
        }finally {
            //3.更新渠道日志
            Long cost = DateUtil.current() - startTime;
            channelGatewayLog.setCostTime(Math.toIntExact(cost));
//            channelGatewayLogManager.updateById(channelGatewayLog);
        }
        return "success";  // 通联统一要求返回 success
    }
}
