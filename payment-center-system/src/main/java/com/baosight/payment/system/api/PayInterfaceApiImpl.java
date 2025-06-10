package com.baosight.payment.system.api;

import com.baosight.payment.api.PayInterfaceApi;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.vo.IsvInterfaceConfigVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.payment.vo.PayInterfaceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayInterfaceApiImpl implements PayInterfaceApi {

    private final PayInterfaceConfigService payInterfaceConfigService;

    @Override
    public List<PayInterfaceVO> interfaceDefineAll(List<Long> interfaceId) {
        return List.of();
    }

    /**
     * 根据接口code 以及 渠道用户信息获取接口配置信息
     *
     * @param interfaceCode  接口编号
     * @param mchChannelUser 渠道用户信息
     */
    @Override
    public MchInterfaceConfigVO mchInterfaceConfig(String interfaceCode, String mchChannelUser) {
        return payInterfaceConfigService.mchInterfaceConfig(interfaceCode, mchChannelUser);
    }

    /**
     * 根据接口code 以及 商户渠道用户信息获取接口配置信息
     *
     * @param interfaceCode  接口编号
     * @param mchChannelUser 渠道用户信息
     */
    @Override
    public IsvInterfaceConfigVO isvInterfaceConfig(String interfaceCode, String mchChannelUser) {
        return payInterfaceConfigService.isvInterfaceConfig(interfaceCode, mchChannelUser);
    }


}
