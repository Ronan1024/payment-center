package com.baosight.payment.channel.pojo.dto.resp;

import com.baosight.payment.channel.enums.CapabilityGroup;
import lombok.Data;

import java.util.List;

/**
 * 渠道处理器信息响应。
 */
@Data
public class ChannelHandlerListRespDTO {

    /**
     * 处理器名称，默认取处理器类简单名。
     */
    private String handlerName;

    /**
     * 处理器编号，默认取 Spring Bean 名称。
     */
    private String handlerNo;

    /**
     * 类对象信息，记录处理器完整类名。
     */
    private String classInfo;

    /**
     * 渠道编码。
     */
    private String channelCode;

    /**
     * 签约模式编码。
     */
    private String modeCode;

    /**
     * 签约模式名称。
     */
    private String modeName;

    /**
     * 处理器能力列表。
     */
    private List<CapabilityGroup> capabilities;
}
