package com.baosight.payment.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.pojo.dto.PayInterfaceConfigDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.saas.entity.DynamicForm;

import java.util.List;
import java.util.Map;

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
     * 获取指定用户的支付配置信息
     *
     * @param mchId 支付客户端类型
     * @return 客户支付配置
     */
    List<PayInterfaceConfig> getPayConfiguration(Long mchId);

    /**
     * 获取指定用户的支付配置信息
     *
     * @param mchId 商户id
     * @return 客户支付配置
     */
    Map<Long, PayInterfaceConfig> getPayConfigurationMap(Long mchId);

    /**
     * 获取支付配置信息
     *
     * @param payClientType 支付客户端类型
     * @param id            客户端id
     * @param interfaceId   支付接口ID
     * @return 支付接口配置信息
     */
    PayInterfaceConfigVO getConfigInfo(PayClientType payClientType, Long id, Long interfaceId);

    /**
     * 保存或更新支付配置
     *
     * @param payInterfaceConfigDTO 保存或更新
     * @param payClientType         支付客户端信息
     * @param mchId                 商户id
     * @param hasMch                是否为商户
     */
    Boolean payConfigurationSaveOrUpdate(PayInterfaceConfigDTO payInterfaceConfigDTO, PayClientType payClientType, Long mchId, Boolean hasMch);

    /**
     * 获取指定的客户端下指定支付接口配置
     *
     * @param payClientType 支付客户端类型
     * @param clientId      客户端用户唯一id
     * @param interfaceId   支付接口id
     */
    PayInterfaceConfig byPayInterfaceconfig(PayClientType payClientType, Long clientId, Long interfaceId);

    /**
     * 获取用户指定接口配置信息
     *
     * @param mchId       商户id
     * @param interfaceId 接口id
     */
    List<DynamicForm> getDynamicForm(Long mchId, Long interfaceId);

    /**
     * 获取支付配置列表
     *
     * @param isvId 服务商id
     */
    List<PayInterfaceConfigListVO> getIsvInterfaceConfigList(Long isvId);

    /**
     * 根据指定商户获取指定支付机构配置信息
     *
     * @param mchId        商户id
     * @param payingAgency 支付机构信息
     */
    PayInterfaceConfigVO getConfigInfo(Long mchId, PayingAgency payingAgency);

    /**
     * 获取服务商接口配置信息
     *
     * @param isvId 服务商id
     */
    PayInterfaceConfig getIsvInterfaceConfig(Long isvId);


    /**
     * 根据账号类型与信息id 获取支付接口配置列表
     *
     * @param accountType 账号类型
     * @param infoId      详情id
     */
    List<PayInterfaceConfig> payInterfaceConfigByTypeAndInfoId(Integer accountType, Long infoId);

    List<PayInterfaceConfigListVO> getMchInterfaceConfigList(Long mchId);

    /**
     * 获取商户支付接口配置
     *
     * @param mchId 商户id
     * @param type  商户类型
     */
    List<PayInterfaceConfig> getMchInterfaceConfig(Long mchId, Integer type);

    /**
     * 根据接口code 以及 渠道用户信息获取接口配置信息
     *
     * @param interfaceCode  接口编号
     * @param mchChannelUser 渠道用户信息
     */
    MchInterfaceConfigVO mchInterfaceConfig(String interfaceCode, String mchChannelUser);

}
