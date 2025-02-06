package com.baosight.payment.service;

import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.pojo.entity.PayInterfaceConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.pojo.vo.PayInterfaceConfigVO;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_interface_config(支付接口配置)】的数据库操作Service
 * @createDate 2025-01-20 15:28:23
 */
public interface PayInterfaceConfigService extends IService<PayInterfaceConfig> {

    /**
     * 获取指定用户的支付配置信息
     *
     * @param payClientType 支付客户端类型
     * @param clientInfoId  支付客户信息id
     * @return 客户支付配置
     */
    List<PayInterfaceConfig> getPayConfiguration(PayClientType payClientType, Long clientInfoId);

    /**
     * 获取服务商支付配置信息
     *
     * @param payClientType 支付客户端类型
     * @param id            服务商id
     * @param interfaceId   支付接口ID
     * @return 支付接口配置信息
     */
    PayInterfaceConfigVO getIsvConfigInfo(PayClientType payClientType, Long id, Long interfaceId);
}
