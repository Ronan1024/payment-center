package com.baosight.payment.chanel.ums.notice;

import com.baosight.payment.chanel.IChannelRefundNoticeService;
import com.baosight.payment.pojo.dao.ParseChannelParamDAO;
import com.baosight.payment.pojo.vo.RefundOrderChannelHandlerResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/12/31
 */
@Component("UMSRefundNotice")
public class UmsRefundNoticeService implements IChannelRefundNoticeService
{
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
        return "";
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
        return null;
    }

    /**
     * 返回需要更新的订单状态 和响应数据
     *
     * @param request
     * @param parseParams
     */
    @Override
    public RefundOrderChannelHandlerResult doNotice(HttpServletRequest request, ParseChannelParamDAO parseParams) {
        return null;
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
