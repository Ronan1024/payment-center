package com.baosight.payment.system.dao.manager;

import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.system.dao.entity.SystemPlatformConfiguration;
import com.baosight.payment.system.dao.mapper.SystemPlatformConfigurationMapper;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/10
 */
@Manager
@RequiredArgsConstructor
public class SystemPlatformConfigurationManager extends BaseManagerImpl<SystemPlatformConfigurationMapper, SystemPlatformConfiguration> {
}
