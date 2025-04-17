package com.baosight.payment.check.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对账批次记录
 * @author longjiangran
 * @TableName check_batch_record
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="check_batch_record")
public class CheckBatchRecord extends BasePO {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 对账批次号由支付接口-商户号-时间组成
     */
    private String checkBatchCode;

    /**
     * 支付接口id
     */
    private String interfaceCode;

    /**
     * 支付接口code
     */
    private Long interfaceId;

    /**
     * 渠道商户号
     */
    private String channelMchNo;

    /**
     * 批次状态
     */
    private Integer state;

    /**
     * 解析状态
     */
    private Boolean releaseState;

    /**
     * 解析异常
     */
    private String releaseErr;

    /**
     * 渠道总金额
     */
    private Long channelTotalAmount;

    /**
     * 渠道交易总数
     */
    private Long channelTotalCount;

    /**
     * 渠道总手续费
     */
    private Long channelTotalFee;

    /**
     * 渠道总退款金额
     */
    private Long channelTotalRefundAmount;

    /**
     * 渠道退款总数量
     */
    private Long channelTotalRefundCount;

    /**
     * 差错总单数
     */
    private Long diffCount;

    /**
     * 渠道对账文件存放地址
     */
    private String orgBillFilePath;

    /**
     * 平台交易总金额
     */
    private Long totalAmount;

    /**
     * 平台交易总数量
     */
    private Long totalCount;

    /**
     * 平台总手续费
     */
    private Long totalFee;

    /**
     * 平台退款总金额
     */
    private Long totalRefundAmount;

    /**
     * 平台退款总书
     */
    private Long totalRefundCount;

    /**
     * 待处差错订单数量
     */
    private Long unHandleDiffCount;

    /**
     * 账单时间
     */
    private String billDate;
}