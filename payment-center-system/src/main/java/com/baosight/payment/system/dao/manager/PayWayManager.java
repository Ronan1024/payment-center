package com.baosight.payment.system.dao.manager;

import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.system.mapper.PayWayMapper;
import com.baosight.payment.system.pojo.entity.PayWay;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
@Manager
public class PayWayManager extends BaseManagerImpl<PayWayMapper, PayWay> {
}
