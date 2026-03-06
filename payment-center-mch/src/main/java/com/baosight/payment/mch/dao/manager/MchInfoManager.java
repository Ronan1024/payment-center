package com.baosight.payment.mch.dao.manager;

import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.mch.dao.entity.PayMchInfo;
import com.baosight.payment.mch.dao.mapper.PayMchInfoMapper;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
@Manager
public class MchInfoManager extends BaseManagerImpl<PayMchInfoMapper, PayMchInfo> {
}
