package com.baosight.payment.system.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import com.ronan.common.enums.IBaseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 渠道配置流程
 *
 * @author L.J.Ran
 * @TableName system_mch_channel_config_flow
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_mch_channel_config_flow")
public class SystemMchChannelConfigFlow extends BasePO {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 当前流程执行编号
     */
    @TableField("`index`")
    private Integer index;

    /**
     * 当前流程列表
     */
    private String flow;

    /**
     * 商户id
     */
    private Long clientId;

    /**
     * 商户类型
     */
    private Integer clientType;

    /**
     * 当前执行状态
     */
    @TableField("`status`")
    private Integer status;

    /**
     * 完整的流程列表
     */
    private String wholeFlow;

    public enum Status implements IBaseEnum<Integer> {
        /**
         * 处理中
         */
        PROCESSING(1, "处理中"),
        /**
         * 失败
         */
        FAILED(2, "失败"),
        /**
         * 成功
         */
        SUCCESS(3, "成功"),
        /**
         * 未开始
         */
        UNPLAYED(4, "未开始")
        ;


        Status(Integer code, String msg) {
            initEnum(code, msg);
        }

    }

    @Data
    public static class WholeFlow {
        private Long id;
        /**
         * 当前执行id
         */
        private Integer index;
        /**
         * 类型
         */
        private String stepType;
        /**
         * 状态
         */
        private Integer status;
        /**
         * 处理时间
         */
        private Date handleTime;
        /**
         * 处理结果
         */
        private String handleResult;

        /**
         * 处理异常结果信息
         */
        private String errorMsg;
    }

}