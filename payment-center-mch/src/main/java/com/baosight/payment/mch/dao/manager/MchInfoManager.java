package com.baosight.payment.mch.dao.manager;

import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.mapper.PayEnterpriseInfoMapper;
import com.baosight.payment.mch.dao.entity.PayMchInfo;
import com.baosight.payment.mch.dao.mapper.PayMchInfoMapper;
import com.baosight.payment.mch.mapper.PayBankAccountInfoMapper;
import com.baosight.payment.mch.pojo.entity.PayBankAccountInfo;
import com.baosight.payment.pojo.entity.PayEnterpriseInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
@Manager
@RequiredArgsConstructor
public class MchInfoManager extends BaseManagerImpl<PayMchInfoMapper, PayMchInfo> {

    private final PayEnterpriseInfoMapper payEnterpriseInfoMapper;

    private final PayBankAccountInfoMapper payBankAccountInfoMapper;

    private final PayMchInfoMapper payMchInfoMapper;

    /**
     * 创建商户信息
     * @param payMchInfo
     * @param payEnterpriseInfo
     * @param payBankAccountInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean createMchInfo(PayMchInfo payMchInfo, PayEnterpriseInfo payEnterpriseInfo, PayBankAccountInfo payBankAccountInfo) {
        payBankAccountInfoMapper.insert(payBankAccountInfo);
        payEnterpriseInfoMapper.insert(payEnterpriseInfo);

        payMchInfo.setBankAccountInfoId(payBankAccountInfo.getId());
        payMchInfo.setEnterpriseInfoId(payEnterpriseInfo.getId());
        payMchInfoMapper.insert(payMchInfo);
        return Boolean.TRUE;
    }
}
