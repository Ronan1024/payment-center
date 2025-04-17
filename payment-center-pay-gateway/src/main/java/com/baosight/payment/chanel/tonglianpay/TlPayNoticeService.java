package com.baosight.payment.chanel.tonglianpay;

import com.baosight.payment.chanel.IChannelNoticeService;
import com.baosight.payment.enums.*;
import com.baosight.payment.pojo.dao.ParseChannelParamDAO;
import com.baosight.payment.pojo.vo.OrderChannelHandlerResult;
import com.baosight.utils.json.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @program: payment-center
 * @description: 通联支付回调
 * @author: L.J.Ran
 * @create: 2025/3/28
 */
@Component(value = "tLPayNotice")
public class TlPayNoticeService implements IChannelNoticeService {
    private static final Logger log = LoggerFactory.getLogger(TlPayNoticeService.class);

    @Override
    public String getNotifyParam(HttpServletRequest request) {
        String body = null;
        try {
            body = body(request);
            log.info("获取到通联回调信息：{}", body);
        } catch (IOException e) {
            log.info("解析通联回调信息异常.....");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return body;
    }

    /**
     * 获取到接口code
     **/
    @Override
    public String getIfCode() {
        return "";
    }

    /**
     * 解析参数： 订单号 和 请求参数
     * 异常需要自行捕捉，并返回null , 表示已响应数据。
     *
     * @param notifyParam
     * @param urlOrderId
     * @param noticeTypeEnum
     */
    @Override
    public ParseChannelParamDAO parseParams(String notifyParam, Long urlOrderId, NoticeTypeEnum noticeTypeEnum) {
        // TODO 回调待处理完善
        try {
            ParseChannelParamDAO parseChannelParamDAO = new ParseChannelParamDAO();

            JsonNode notifyParamJsonNode = JsonUtil.readTree(notifyParam);
            JsonNode jsonNode = JsonUtil.readTree(notifyParamJsonNode.get("bizData").asText());
            //订单状态
            String result = jsonNode.get("result").asText();
            if (result.equals("2")) {
                // 错误信息
                String respMsg = jsonNode.get("respMsg").asText();
                parseChannelParamDAO.setErrMsg(respMsg);
            }
            // 商户订单号（支付订单）
            Long reqTraceNum = jsonNode.get("reqTraceNum").asLong();
            // 通联订单号
            String respTraceNum = jsonNode.get("respTraceNum").asText();
            //订单支付完成时间
            String finishTime = jsonNode.get("finishTime").asText();
            //商户会员编号-付款人
            String signNum = jsonNode.get("signNum").asText();
            //商户会员编号-收款人
            Long receiverSignNum = jsonNode.get("receiverSignNum").asLong();
            parseChannelParamDAO.setChannelState(result);
            parseChannelParamDAO.setOrderId(reqTraceNum);
            parseChannelParamDAO.setChannelOrderId(respTraceNum);
            parseChannelParamDAO.setMchId(receiverSignNum);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            parseChannelParamDAO.setFinishTime(simpleDateFormat.parse(finishTime));
            parseChannelParamDAO.setPayingAgency(PayingAgency.TONG_LIAN.getAgencyCode());
            if (jsonNode.has("channelParamInfo")) {
                JsonNode channelParamInfo = JsonUtil.readTree(jsonNode.get("channelParamInfo").asText());
                parseChannelParamDAO.setPayAgencyChannelOrder(channelParamInfo.get("chnlTrxid").asText());
                parseChannelParamDAO.setChannelUserId(channelParamInfo.get("payAcctNo").asText());
                if (channelParamInfo.get("chnlTransCode").asText().equals("VSP681")) {
                    parseChannelParamDAO.setTradingMode(TradingMode.WECHAT_PRE_CONSUMPTION.getCode());
                }
                if (channelParamInfo.get("chnlTransCode").asText().equals("VSP683")) {
                    parseChannelParamDAO.setTradingMode(TradingMode.WECHAT_ORDER_COMPLETED.getCode());
                }
            }

            if (notifyParamJsonNode.get("transCode").equals("2085")) {
                // 消费
                parseChannelParamDAO.setTradingType(TradingType.CONSUMPTION.getCode());
            }

            return parseChannelParamDAO;
        } catch (Exception e) {
            // TODO 异常待处理
        }
        return null;
    }

    /**
     * 返回需要更新的订单状态 和响应数据
     *
     * @param request
     * @param params
     */
    @Override
    public OrderChannelHandlerResult doNotice(HttpServletRequest request, ParseChannelParamDAO params) {
        OrderChannelHandlerResult result = new OrderChannelHandlerResult();
        // 默认支付中
        result.setChannelState(ChannelState.PROCESSING.getCode());
        result.setChannelErrCode(params.getErrCode());
        result.setChannelErrMsg(params.getErrMsg());
        if (params.getChannelState().equals("1")) {
            result.setChannelState(ChannelState.SUCCESS.getCode());
            if (params.getTradingMode().equals(TradingMode.WECHAT_PRE_CONSUMPTION.getCode())) {
                result.setPayOrderState(PayOrderState.PRE_CONSUMPTION.getCode());
            } else {
                result.setPayOrderState(PayOrderState.SUCCESS.getCode());
            }
        } else if (params.getChannelState().equals("2")) {
            result.setChannelState(ChannelState.FAIL.getCode());
            result.setPayOrderState(PayOrderState.FAIL.getCode());
            result.setChannelErrMsg(params.getErrMsg());
        } else {
            result.setChannelState(ChannelState.PROCESSING.getCode());
            result.setPayOrderState(PayOrderState.PAYING.getCode());
        }
        result.setChannelOrderNo(params.getChannelOrderId());
        result.setResponseEntity(textResp("success"));
        result.setTradingModel(params.getTradingMode());
        result.setTradingType(params.getTradingType());
        return result;
    }

    /**
     * 数据库订单数据不存在  (仅异步通知使用)
     *
     * @param request
     */
    @Override
    public ResponseEntity doNotifyOrderNotExists(HttpServletRequest request) {
        return null;
    }

    private String body(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
