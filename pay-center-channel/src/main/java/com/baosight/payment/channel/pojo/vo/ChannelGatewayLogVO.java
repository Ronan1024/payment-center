package com.baosight.payment.channel.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 渠道网关出入站日志
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2026-01-29
 */
@Data
public class ChannelGatewayLogVO {

    /**
     * ID
     */
    private Long id;

    /**
     * 操作类型 1 出站(请求) 2 入站(回调/通知)
     */
    private Boolean operation;

    /**
     * 系统流水号/请求编号
     */
    private Long requestNo;

    /**
     * 外部系统交易号
     */
    private String outTradeNo;

    /**
     * 支付机构编号
     */
    private String instCode;

    /**
     * 支付渠道编号
     */
    private String channelCode;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMsg;

    /**
     * 耗时(毫秒)
     */
    private Integer costTime;

    /**
     * 出站参数
     */
    private String reqParams;

    /**
     * 入站参数
     */
    private String resParams;

    /**
     * 业务处理状态 1 创建  2 处理中 3 成功 4 失败
     */
    private Boolean bizStatus;

    /**
     * 渠道接口id
     */
    private Long channelInterfaceId;


}
