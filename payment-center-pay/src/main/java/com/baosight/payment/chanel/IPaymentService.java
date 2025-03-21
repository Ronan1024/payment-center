package com.baosight.payment.chanel;

import com.baosight.payment.model.UnifiedOrder;
import com.baosight.payment.pojo.dao.MchAppConfigInfoDAO;
import com.baosight.payment.pojo.entity.PayOrder;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.web.exception.ApiException;

public interface IPaymentService {

    /**
     * 获取到接口code
     **/
    Long getPayInterfaceCode();

    /**
     * 调起支付接口并响应数据
     *
     * @param unifiedOrder     申请支付请求体
     * @param mchAppConfigInfo 商户应用配置信息
     */
    Object pay(UnifiedOrder unifiedOrder, MchAppConfigInfoDAO mchAppConfigInfo);

    /**
     * 前置检查如参数等信息是否符合要求， 返回错误信息或直接抛出异常即可
     */
    ApiException preCheck(UnifiedOrder unifiedOrder, PayOrder payOrder);


    /**
     * 自定义支付订单号， 若返回空则使用系统生成订单号
     */
    String customPayOrderId(UnifiedOrder unifiedOrder, PayOrder payOrder, MchInfoVO mchInfoVO);

    /**
     * 是否支持当前支付方式
     *
     * @param patWayCode 支付方式code
     */
    boolean isSupport(String patWayCode);
}
