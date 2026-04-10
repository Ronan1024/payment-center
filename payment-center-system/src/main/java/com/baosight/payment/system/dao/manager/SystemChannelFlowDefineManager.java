package com.baosight.payment.system.dao.manager;

import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.system.dao.entity.SystemChannelFlowDefine;
import com.baosight.payment.system.dao.mapper.SystemChannelFlowDefineMapper;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/26
 */
@Manager
@RequiredArgsConstructor
public class SystemChannelFlowDefineManager extends BaseManagerImpl<SystemChannelFlowDefineMapper, SystemChannelFlowDefine> {
}
