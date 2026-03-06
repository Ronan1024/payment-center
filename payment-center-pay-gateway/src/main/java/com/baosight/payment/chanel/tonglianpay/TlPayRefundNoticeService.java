package com.baosight.payment.chanel.tonglianpay;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.chanel.IChannelRefundNoticeService;
import com.baosight.payment.enums.ChannelState;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.enums.RefundOrderState;
import com.baosight.payment.pojo.dao.ParseChannelParamDAO;
import com.baosight.payment.pojo.vo.RefundOrderChannelHandlerResult;
import com.baosight.utils.json.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
@Slf4j
@Component("tLPayRefundNotice")
public class TlPayRefundNoticeService implements IChannelRefundNoticeService {

    /**
     * 获取到接口code
     **/
    @Override
    public String getIfCode() {
        return "";
    }

    /**
     * 获取渠道回调信息
     *
     * @param request 请求信息
     */
    @Override
    public String getNotifyParam(HttpServletRequest request) {
        String body = null;
        try {
            body = getBody(request);
            log.info("获取到通联回调信息：{}", body);
        } catch (Exception e) {
            log.info("解析通联回调信息异常.....");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return body;
    }

    /**
     * 解析参数： 订单号 和 请求参数
     * 异常需要自行捕捉，并返回null , 表示已响应数据。
     *
     * @param notifyParam
     * @param refundOrderId
     */
    @Override
    public ParseChannelParamDAO parseParams(String notifyParam, Long refundOrderId) {

        JsonNode notifyParamJsonNode = JsonUtil.readTree(notifyParam);
        JsonNode jsonNode = JsonUtil.readTree(notifyParamJsonNode.get("bizData").asText());
        ParseChannelParamDAO parseChannelParamDAO = new ParseChannelParamDAO();
        parseChannelParamDAO.setChannelState(jsonNode.get("result").asText());
        parseChannelParamDAO.setErrMsg(jsonNode.get("respMsg").asText());
        parseChannelParamDAO.setOrderId(jsonNode.get("reqTraceNum").asLong());
        parseChannelParamDAO.setChannelOrderId(jsonNode.get("respTraceNum").asText());
        parseChannelParamDAO.setPayingAgency(PayingAgency.ALL_IN.getAgencyCode());
        parseChannelParamDAO.setChannelResult(notifyParam);
        if (jsonNode.has("channelParamInfo")) {
            JsonNode channelParamInfo = JsonUtil.readTree(jsonNode.get("channelParamInfo").asText());
            String chnlTransCode = channelParamInfo.get("chnlTransCode").asText();
            if (chnlTransCode.equals("VSP682") || chnlTransCode.equals("VSP684")) {
                parseChannelParamDAO.setOrderState(RefundOrderState.REFUNDED.code());
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            parseChannelParamDAO.setFinishTime(simpleDateFormat.parse(jsonNode.get("finishTime").asText()));
        } catch (ParseException e) {
            throw new ServiceException(e);
        }
        return parseChannelParamDAO;
    }

    /**
     * 返回需要更新的订单状态 和响应数据
     *
     * @param request
     * @param parseParams
     */
    @Override
    public RefundOrderChannelHandlerResult doNotice(HttpServletRequest request, ParseChannelParamDAO parseParams) {
        RefundOrderChannelHandlerResult refundOrderChannelHandlerResult = new RefundOrderChannelHandlerResult();
        refundOrderChannelHandlerResult.setChannelErrCode(parseParams.getErrCode());
        refundOrderChannelHandlerResult.setChannelErrMsg(parseParams.getErrMsg());
        Integer orderState = ObjectUtils.isEmpty(parseParams.getOrderState()) ? RefundOrderState.REFUNDING.code() : parseParams.getOrderState();
        if (parseParams.getChannelState().equals("1")) {
            orderState = RefundOrderState.REFUNDED.code();
            refundOrderChannelHandlerResult.setChannelState(ChannelState.SUCCESS.getCode());
        } else if (parseParams.getChannelState().equals("2")) {
            orderState = RefundOrderState.REFUND_FAILED.code();
            refundOrderChannelHandlerResult.setChannelState(ChannelState.FAIL.getCode());
        } else {
            refundOrderChannelHandlerResult.setChannelState(ChannelState.PROCESSING.getCode());
        }
        ResponseEntity<String> success = ResponseEntity.ok().body("success");
        refundOrderChannelHandlerResult.setChannelOriginResponse(parseParams.getChannelResult());
        refundOrderChannelHandlerResult.setChannelAttach(parseParams.getChannelResult());
        refundOrderChannelHandlerResult.setResponseEntity(success);
        refundOrderChannelHandlerResult.setChannelOrderNo(parseParams.getChannelOrderId());
        refundOrderChannelHandlerResult.setPayOrderState(orderState);
        return refundOrderChannelHandlerResult;
    }


    /**
     * 数据库订单 状态更新异常 (仅异步通知使用)
     *
     * @param request
     */
    @Override
    public ResponseEntity doNotifyOrderStateUpdateFail(HttpServletRequest request) {
        return null;
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
}
