package com.baosight.payment.accounting.manager;

import com.baosight.payment.vo.MchInterfaceConfigVO;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/4
 */
public interface TongLianPayReconciliationManager {

    /**
     * 获取通联商户配置信息
     */
    List<MchInterfaceConfigVO> tlPayMchConfigList();

    /**
     * 开始处理支付渠道账单
     *
     * @param mchInterfaceConfig 商户支付接口配置
     */
    Boolean handlerPayAgencyBill(MchInterfaceConfigVO mchInterfaceConfig, String date);
}
