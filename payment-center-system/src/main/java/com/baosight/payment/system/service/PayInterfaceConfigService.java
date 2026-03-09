package com.baosight.payment.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.pojo.dto.PayInterfaceConfigDTO;
import com.baosight.payment.system.pojo.dto.PayInterfaceDefineBindDTO;
import com.baosight.payment.system.pojo.dto.req.ClientChannelConfigReqDTO;
import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientChannelConfigRespDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientChannelRespDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigDynamicVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.vo.IsvInterfaceConfigVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @author longjiangran
 * @description 针对表【pay_interface_config(支付接口配置)】的数据库操作Service
 * @createDate 2025-01-20 15:28:23
 */
public interface PayInterfaceConfigService extends IService<PayInterfaceConfig> {
    /**
     * 支付设置
     *
     * @param payInterfaceConfigDynamicVO
     * @return
     */
    Boolean setPaymentConfig(PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO);

    /**
     * 获取服务商签约的支付商户
     *
     * @param isvId
     */
    List<PayInterfaceConfigDynamicVO> getIsvPayInterfaceConfigs(Long isvId);

    /**
     * 更新
     *
     * @param payInterfaceConfigDynamicVO
     * @return
     */
    Boolean updatePayInterfaceConfig4Mch(PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO);

    /**
     * 更新
     *
     * @param payInterfaceConfigDynamicVO
     * @return
     */
    Boolean updatePayInterfaceConfig4Isv(PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO);

    /**
     * 获取指定用户的支付配置信息
     *
     * @param payClientType 支付客户端类型
     * @param clientInfoId  支付客户信息id
     * @return 客户支付配置
     */
    List<PayInterfaceConfig> getPayConfiguration(PayClientType payClientType, Long clientInfoId);
//
//    /**
//     * 获取指定用户的支付配置信息
//     *
//     * @param mchId 支付客户端类型
//     * @return 客户支付配置
//     */
//    List<PayInterfaceConfig> getPayConfiguration(Long mchId);
//

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


    PayInterfaceConfigVO getConfigInfo(Long mchId, PayingAgency payingAgency);

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
//
//    /**
//     * 获取用户指定接口配置信息
//     *
//     * @param mchId       商户id
//     * @param interfaceId 接口id
//     */
////    List<DynamicForm> getDynamicForm(Long mchId, Long interfaceId);

    /**
     * 获取支付配置列表
     *
     * @param isvId 服务商id
     */
    List<PayInterfaceConfigListVO> getIsvInterfaceConfigList(Long isvId);

    /**
     * 获取指定商户签约的指定支付机构的接口ID
     *
     * @param mchId        商户id
     * @param payingAgency 支付机构信息
     */
    Long getMchInterfaceIdByPayingAgency(Long mchId, PayingAgency payingAgency);

    /**
     * 获取指定客户端配置信息
     *
     * @param clientId     客户端id
     * @param payingAgency 支付机构信息
     * @param interfaceId  支付接口id
     */
    PayInterfaceConfigVO getConfigInfo(Long clientId, PayingAgency payingAgency, Long interfaceId);
//
//    /**
//     * 获取服务商接口配置信息
//     *
//     * @param isvId 服务商id
//     */
//    PayInterfaceConfig getIsvInterfaceConfig(Long isvId);
//
//
//    /**
//     * 根据账号类型与信息id 获取支付接口配置列表
//     *
//     * @param accountType 账号类型
//     * @param infoId      详情id
//     */
//    List<PayInterfaceConfig> payInterfaceConfigByTypeAndInfoId(Integer accountType, Long infoId);

    /**
     * 获取指定商户
     *
     * @param mchId
     * @return
     */
    PayInterfaceConfigDynamicVO getMchInterfaceConfigList(Long mchId);

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
//
//    /**
//     * 处理当前用户是否需要执行后续操作
//     *
//     * @param interfaceId 接口id
//     * @param mchId       商户id
//     */
//    List<String> trailingOption(Long interfaceId, Long mchId);
//

    /**
     * 获取通联服务商与商家配置
     *
     * @param mchId       商家id
     * @param interfaceId 接口id
     */
    TongLianIsvAndMchConfigDAO getTongLianIsvAndMchConfig(Long mchId, Long interfaceId);


    /**
     * 获取服务商 支付配置列表
     *
     * @param isvId                  服务商id
     * @param payInterfaceDefineList 支付接口定义列表
     * @param clientType             客户端类型
     */
    List<PayInterfaceConfigListVO> getIsvInterfaceConfigList(Long isvId, List<PayInterfaceDefineListVO> payInterfaceDefineList, PayClientType clientType);

    /**
     * 根据接口code 以及 商户渠道用户信息获取接口配置信息
     *
     * @param interfaceCode  接口编号
     * @param mchChannelUser 渠道用户信息
     */
    IsvInterfaceConfigVO isvInterfaceConfig(String interfaceCode, String mchChannelUser);

    /**
     * 获取商户签约的支付配置列表
     *
     * @param mchId
     * @return
     */
    List<PayInterfaceConfigDynamicVO> getMchPayInterfaceConfigs(Long mchId);

    /**
     * 为商户增加支付配置
     *
     * @param payInterfaceDefineBindDTO
     * @return
     */
    Boolean addPayInterfaceConfig(PayInterfaceDefineBindDTO payInterfaceDefineBindDTO);

    /**
     * 特约商户绑定服务商
     *
     * @param isvId
     * @return
     */
    Boolean bindIsv(Long mchId, Long isvId);

    /**
     * 保存商户支付渠道权限
     *
     * @param mchChannelPermission 商户支付渠道权限
     */
    Boolean saveMchChannel(MchChannelPermissionReqDTO mchChannelPermission);

    /**
     * 获取商户已授权的支付渠道
     *
     * @param type  商户类型
     * @param mchId 商户ID
     */
    List<String> getMchChannel(Integer type, Long mchId);

    /**
     * 获取当前商户渠道配置列表
     *
     * @param clientId 商户id
     */
    List<ClientChannelRespDTO> mchChannelList(Long clientId);

    /**
     * 保存商户渠道配置信息
     * @param channelConfigReq 渠道配置信息请求参数
     */
    Boolean saveClientChannelConfig(ClientChannelConfigReqDTO channelConfigReq);

    /**
     * 获取客户端支付渠道配置信息
     * @param channelId 支付渠道id
     * @param clientId 客户端id
     * @param type 客户端类型
     */
    ClientChannelConfigRespDTO clientChannelConfigInfo(Long channelId, Long clientId, Integer type);
}
