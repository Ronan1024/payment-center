package com.baosight.payment.notify.pojo.vo;

import com.baosight.payment.notify.pojo.dao.NotifyResponseDAO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/20
 */
@Data
public class OrderNotifyRecordVO {
    /**
     * 商户通知记录ID
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 订单id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long orderId;

    /**
     * 订单类型:1-支付,2-退款
     */
    private Integer orderType;

    /**
     * 商户订单号
     */
    private String mchOrderNo;

    /**
     * 通知地址
     */
    private String notifyUrl;


    /**
     * 通知次数
     */
    private Integer notifyCount;

    /**
     * 最大通知次数, 默认7次
     */
    private Integer notifyCountLimit;

    /**
     * 通知状态,1-通知中,2-通知成功,3-通知失败
     */
    private Integer state;

    /**
     * 最后一次通知时间
     */
    private Date lastNotifyTime;

    /**
     * 通知类型
     */
    private Integer notifyType;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 通知记录
     */
    private List<NotifyResponseDAO> notifyResponseList;


}
