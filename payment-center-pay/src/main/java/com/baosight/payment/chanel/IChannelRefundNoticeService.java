package com.baosight.payment.chanel;

import com.baosight.payment.pojo.dao.ParseChannelParamDAO;
import com.baosight.payment.pojo.vo.RefundOrderChannelHandlerResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @program: payment-center
 * @description: 渠道退款回调
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
public interface IChannelRefundNoticeService {

    /**
     * 通知类型
     **/
    enum NoticeTypeEnum {
        DO_NOTIFY //异步回调
    }

    /**
     * 获取到接口code
     **/
    String getIfCode();

    /**
     * 获取渠道回调信息
     * @param request 请求信息
     */
    String getNotifyParam(HttpServletRequest request);

    /**
     * 解析参数： 订单号 和 请求参数
     * 异常需要自行捕捉，并返回null , 表示已响应数据。
     **/
    ParseChannelParamDAO parseParams(String notifyParam, Long refundOrderId);

    /**
     * 返回需要更新的订单状态 和响应数据
     **/
    RefundOrderChannelHandlerResult doNotice(HttpServletRequest request, ParseChannelParamDAO parseParams);

    /**
     * 数据库订单 状态更新异常 (仅异步通知使用)
     **/
    ResponseEntity doNotifyOrderStateUpdateFail(HttpServletRequest request);

    /**
     * 数据库订单数据不存在  (仅异步通知使用)
     **/
    ResponseEntity doNotifyOrderNotExists(HttpServletRequest request);

    // TODO 待处理 聚合
    default String getBody(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
