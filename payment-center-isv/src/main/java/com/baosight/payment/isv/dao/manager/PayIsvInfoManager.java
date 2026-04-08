package com.baosight.payment.isv.dao.manager;

import com.baosight.database.core.annotation.Manager;
import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.isv.mapper.PayIsvInfoMapper;
import com.baosight.payment.isv.pojo.entity.PayIsvInfo;
import com.baosight.payment.mapper.PayEnterpriseInfoMapper;
import com.baosight.payment.pojo.entity.PayEnterpriseInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/8
 */
@Manager
@RequiredArgsConstructor
public class PayIsvInfoManager extends BaseManagerImpl<PayIsvInfoMapper, PayIsvInfo> {

    private final PayIsvInfoMapper payIsvInfoMapper;
    private final PayEnterpriseInfoMapper payEnterpriseInfoMapper;


    /**
     * 创建服务商信息
     *
     * @param payIsvInfo        服务商信息
     * @param payEnterpriseInfo 服务商企业信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean createIsv(PayIsvInfo payIsvInfo, PayEnterpriseInfo payEnterpriseInfo) {
        payEnterpriseInfoMapper.insert(payEnterpriseInfo);
        payIsvInfo.setEnterpriseInfoId(payEnterpriseInfo.getId());
        payIsvInfoMapper.insert(payIsvInfo);
        return Boolean.TRUE;
    }
}
