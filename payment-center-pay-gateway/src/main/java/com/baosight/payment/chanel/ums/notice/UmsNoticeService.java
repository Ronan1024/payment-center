package com.baosight.payment.chanel.ums.notice;

import com.baosight.payment.chanel.IChannelNoticeService;
import com.baosight.payment.pojo.dao.ParseChannelParamDAO;
import com.baosight.payment.pojo.vo.OrderChannelHandlerResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/12/31
 */
@Component("UMSNotice")
public class UmsNoticeService implements IChannelNoticeService {

    /**
     * 获取通知参数
     */
    @Override
    public String getNotifyParam(HttpServletRequest request) {
        return "";
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
