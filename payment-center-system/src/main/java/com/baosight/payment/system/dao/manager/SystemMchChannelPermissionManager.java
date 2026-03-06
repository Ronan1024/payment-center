package com.baosight.payment.system.dao.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.system.dao.entity.SystemMchChannelPermission;
import com.baosight.payment.system.dao.mapper.SystemMchChannelPermissionMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/6
 */
@Manager
public class SystemMchChannelPermissionManager extends BaseManagerImpl<SystemMchChannelPermissionMapper, SystemMchChannelPermission> {

    /**
     * 添加商户与支付通道关联
     * @param mchChannelPermissions 商户关联信息
     * @param mchId 商户id
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveChannelPermission(List<SystemMchChannelPermission> mchChannelPermissions, Long mchId) {
         remove(new LambdaQueryWrapper<SystemMchChannelPermission>()
                .eq(SystemMchChannelPermission::getMchId, mchId));
        return saveBatch(mchChannelPermissions);

    }
}
