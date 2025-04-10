package com.baosight.payment.system.service.app;

import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigListVO;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
public interface PayInterfaceConfigAppService {

    /**
     * 获取支付配置列表
     *
     * @param isvId      服务商id
     * @param clientType 客户端类型
     */
    List<PayInterfaceConfigListVO> getIsvInterfaceConfigList(Long isvId, PayClientType clientType);
}
