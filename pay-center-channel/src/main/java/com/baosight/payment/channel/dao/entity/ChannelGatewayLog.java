package com.baosight.payment.channel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.common.enums.IBaseEnum;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 渠道网关出入站日志
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2026-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="channel_gateway_log")
public class ChannelGatewayLog extends BasePO {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 操作类型 1 出站(请求) 2 入站(回调/通知)
     */
    private Integer operation;

    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 系统流水号/请求编号
     */
    private Long requestNo;

    /**
     * 操作客户端类型
     */
    private String clientType;

    /**
     * 操作客户端编号
     */
    private Long clientId;


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
    private Integer bizStatus;

    /**
     * 渠道接口id
     */
    private Long channelInterfaceId;


    /**
     * 出站url
     */
    private String reqUrl;



    public enum Operation implements IBaseEnum<Integer> {

        /**
         * 入站
         */
        IN_SIDE(1,"入站"),

        /**
         * 出站
         */
        OUT_SIDE(2,"出站");


        Operation(Integer code, String msg) {
            initEnum(code, msg);
        }

    }


    public enum BizStatus implements IBaseEnum<Integer> {

        /**
         * 创建
         */
        CREATE(1,"创建"),

        /**
         * 处理中
         */
        PROCESS(2,"处理中"),

        /**
         *成功
         */
        SUCCESS(3,"成功"),

        /**
         *失败
         */
        FAIL(4,"失败");


        BizStatus(Integer code, String msg) {
            initEnum(code, msg);
        }

    }


}
