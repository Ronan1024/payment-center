package com.baosight.payment.channel.pojo.dto.req;

import lombok.Data;

/**
 * 渠道处理器查询条件。
 */
@Data
public class ChannelHandlerQueryReqDTO {


    /**
     * 处理器编号
     */
    private String handlerNo;


    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 签约模式编码
     */
    private String modeCode;

}
