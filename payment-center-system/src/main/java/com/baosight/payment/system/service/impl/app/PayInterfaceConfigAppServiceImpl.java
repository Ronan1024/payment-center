package com.baosight.payment.system.service.impl.app;

import com.baosight.payment.annotation.ApplicationService;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayInterfaceDefineService;
import com.baosight.payment.system.service.app.PayInterfaceConfigAppService;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
@ApplicationService
@RequiredArgsConstructor
public class PayInterfaceConfigAppServiceImpl implements PayInterfaceConfigAppService {
    private final PayInterfaceDefineService payInterfaceDefineService;
    private final PayInterfaceConfigService payInterfaceConfigService;

    /**
     * 获取支付配置列表
     *
     * @param isvId      服务商id
     * @param clientType 客户端类型
     */
    @Override
    public List<PayInterfaceConfigListVO> getIsvInterfaceConfigList(Long isvId, PayClientType clientType) {
        List<PayInterfaceDefineListVO> payInterfaceDefineList = payInterfaceDefineService.getPayInterfaceDefineList(clientType);
        return payInterfaceConfigService.getIsvInterfaceConfigList(isvId, payInterfaceDefineList, clientType);
    }
}
