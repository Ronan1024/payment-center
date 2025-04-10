package com.baosight.payment.system.manager;

import com.baosight.payment.access.tl.model.TongLianIsvConfigDAO;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.dao.TongLianMchConfigDAO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayTongLianRelevance;
import com.baosight.payment.system.pojo.entity.PayWay;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/26
 */

public interface PayInterfaceConfigManager {

    /**
     * 保存支付接口配置
     *
     * @param payInterfaceConfig 支付接口保存配置类
     * @param payWayList         支付方式列表
     */
    @Transactional(rollbackFor = Exception.class)
    int saveInterfaceConfig(PayInterfaceConfig payInterfaceConfig, List<PayWay> payWayList);

    /**
     * 获取通联关联信息
     */
    PayTongLianRelevance tongLianRelevance(Long mchId);

    /**
     * 获取通联支付配置
     *
     * @param mchId         商户id
     * @param interfaceCode 接口编号
     * @param isvId         服务商id
     */
    TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfig(Long mchId, String interfaceCode, Long isvId);

    /**
     * 获取通联服务商配置
     */
    TongLianIsvConfigDAO tongLianIsvConfig(Long isvId, String interfaceCode, Long interfaceId);

    /**
     * 获得通联商家配置
     */
    TongLianMchConfigDAO tongLianMchConfig(Long mchId, String interfaceCode, Long interfaceId);

    /**
     * 获取商户指定支付接口配置信息
     * @param interfaceCode 接口code
     */
    List<MchInterfaceConfigVO> mchConfig(String interfaceCode);

    /**
     * 获取下级子商户的配置信息
     *
     * @param isvId         服务商id
     * @param interfaceCode 支付接口编号
     */
    List<MchInterfaceConfigVO> mchConfig(Long isvId, String interfaceCode);
}
