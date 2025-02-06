package com.baosight.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.service.PayInterfaceConfigService;
import com.baosight.payment.mapper.PayInterfaceConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_interface_config(支付接口配置)】的数据库操作Service实现
 * @createDate 2025-01-20 15:28:23
 */
@Service
@RequiredArgsConstructor
public class PayInterfaceConfigServiceImpl extends ServiceImpl<PayInterfaceConfigMapper, PayInterfaceConfig> implements PayInterfaceConfigService {
    private final PayInterfaceConfigMapper payInterfaceConfigMapper;

    /**
     * 获取指定用户的支付配置信息
     *
     * @param payClientType 支付客户端类型
     * @param clientInfoId  支付客户信息id
     * @return 客户支付配置
     */
    @Override
    public List<PayInterfaceConfig> getPayConfiguration(PayClientType payClientType, Long clientInfoId) {
        return payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientType, payClientType.getCode())
                .eq(PayInterfaceConfig::getClientId, clientInfoId)
        );
    }

    /**
     * 获取服务商支付配置信息
     *
     * @param payClientType 支付客户端类型
     * @param id            服务商id
     * @param interfaceId   支付接口ID
     * @return 支付接口配置信息
     */
    @Override
    public PayInterfaceConfigVO getIsvConfigInfo(PayClientType payClientType, Long id, Long interfaceId) {
        return null;
    }
}




