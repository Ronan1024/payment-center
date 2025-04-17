package com.baosight.payment.chanel;

import com.baosight.payment.pojo.dao.ParseChannelParamDAO;
import com.baosight.payment.pojo.vo.OrderChannelHandlerResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

/*
 * 渠道侧的支付订单通知解析实现 【分为同步跳转（doReturn）和异步回调(doNotify) 】
 *
 */
public interface IChannelNoticeService {

    /**
     * 通知类型
     **/
    enum NoticeTypeEnum {
        //同步跳转
        DO_RETURN,
        //异步回调
        DO_NOTIFY
    }

    String getNotifyParam(HttpServletRequest request);

    /**
     * 获取到接口code
     **/
    String getIfCode();

    /**
     * 解析参数： 订单号 和 请求参数
     * 异常需要自行捕捉，并返回null , 表示已响应数据。
     **/
    ParseChannelParamDAO parseParams(String notifyParam, Long urlOrderId, NoticeTypeEnum noticeTypeEnum);

    /**
     * 返回需要更新的订单状态 和响应数据
     **/
    OrderChannelHandlerResult doNotice(HttpServletRequest request, ParseChannelParamDAO params);

    /**
     * 数据库订单 状态更新异常 (仅异步通知使用)
     **/
    default ResponseEntity doNotifyOrderStateUpdateFail(HttpServletRequest request) {
        return textResp("update status error");
    }

    /**
     * 数据库订单数据不存在  (仅异步通知使用)
     **/
    ResponseEntity doNotifyOrderNotExists(HttpServletRequest request);


    /**
     * 文本类型的响应数据
     **/
    default ResponseEntity textResp(String text) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity(text, httpHeaders, HttpStatus.OK);
    }

    /**
     * json类型的响应数据
     **/
    default ResponseEntity jsonResp(Object body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(body, httpHeaders, HttpStatus.OK);
    }

    // TODO 待处理聚合回调问题
    default String getBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
