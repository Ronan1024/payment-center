package com.baosight.payment.check.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道账单文件
 * @author L.J.Ran
 * @TableName channel_bill_file
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="channel_bill_file")
public class ChannelBillFile extends BasePO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账单日期
     */
    private String billDate;

    /**
     * 解析状态
     */
    private Boolean parseState;

    /**
     * 渠道账单code 渠道-日期组成
     */
    private String channelBillCode;

    private String parseError;

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 渠道code
     */
    private String channelCode;
}