package com.baosight.payment.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 回调处理记录表
 *
 * @author L.J.Ran
 * @TableName callback_handler_log
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "callback_handler_log")
public class CallbackHandlerLog  extends BasePO {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     *
     */
    private Boolean hasHandler;

    /**
     * 处理异常信息
     */
    private String handlerError;

    /**
     * 支付机构
     */
    private Integer payingAgency;

    /**
     * 支付类型
     */
    private Integer payType;

    /**
     * 回调内容
     */
    private String callbackContext;
    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 上游机构流水号
     */
    private String trxId;

    /**
     * 接口代码
     */
    private String interfaceCode;
}