package com.baosight.payment.exception;

import com.baosight.payment.enums.ChannelState;
import com.baosight.payment.pojo.vo.ChannelHandlerResult;
import lombok.Getter;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
@Getter
public class ChannelHandlerException extends RuntimeException {
    private ChannelHandlerResult channelHandlerResult;


    public ChannelHandlerException(ChannelHandlerResult channelHandlerResult) {
        super(channelHandlerResult != null ? channelHandlerResult.getChannelErrMsg() : null);
        this.channelHandlerResult = channelHandlerResult;
    }


    /**
     * 未知状态
     **/
    public static ChannelHandlerException unknown(String channelErrMsg) {
        ChannelHandlerResult result = new ChannelHandlerResult();
        result.setChannelErrMsg(channelErrMsg);
        result.setChannelState(ChannelState.UNKNOWN.getCode());


        return new ChannelHandlerException(result);
    }


    /**
     * 系统异常
     *
     */
    public static ChannelHandlerException system(String msg) {
        ChannelHandlerResult result = new ChannelHandlerResult();
        result.setChannelState(ChannelState.SYSTEM_ERROR.getCode());
        result.setChannelErrMsg(msg);
        return new ChannelHandlerException(result);
    }
}
