package com.baosight.payment.system.dao.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.system.dao.entity.SystemClientChannelPermission;
import com.baosight.payment.system.dao.mapper.SystemClientChannelPermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/6
 */
@Manager
@RequiredArgsConstructor
public class SystemClientChannelPermissionManager extends BaseManagerImpl<SystemClientChannelPermissionMapper, SystemClientChannelPermission> {

    private final SystemClientChannelPermissionMapper systemMchChannelPermissionMapper;

    /**
     * 添加商户与支付通道关联
     *
     * @param mchChannelPermissions 商户关联信息
     * @param clientId                 商户id
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveChannelPermission(List<SystemClientChannelPermission> mchChannelPermissions, Long clientId) {

        systemMchChannelPermissionMapper.delete(new LambdaQueryWrapper<SystemClientChannelPermission>()
                .eq(SystemClientChannelPermission::getClientId, clientId));

        return systemMchChannelPermissionMapper.insert(mchChannelPermissions).size() == mchChannelPermissions.size();
    }
}
