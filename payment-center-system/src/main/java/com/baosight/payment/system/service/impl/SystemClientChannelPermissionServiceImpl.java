package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.system.dao.entity.SystemClientChannelPermission;
import com.baosight.payment.system.dao.manager.PayInterFaceDefineManager;
import com.baosight.payment.system.dao.manager.SystemClientChannelPermissionManager;
import com.baosight.payment.system.dao.mapper.SystemClientChannelPermissionMapper;
import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.service.SystemClientChannelPermissionService;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.baosight.payment.system.error.PayInterfaceError.PAY_INTERFACE_NOT_EXIST;

/**
 * @author longjiangran
 * @description 针对表【system_mch_channel_permission(服务商通道权限)】的数据库操作Service实现
 * @createDate 2026-03-06 10:16:32
 */
@Service
@RequiredArgsConstructor
public class SystemClientChannelPermissionServiceImpl extends ServiceImpl<SystemClientChannelPermissionMapper, SystemClientChannelPermission>
        implements SystemClientChannelPermissionService {

    private final SystemClientChannelPermissionManager systemMchChannelPermissionManager;
    private final PayInterFaceDefineManager payInterFaceDefineManager;

    /**
     * 添加商户通道权限
     *
     * @param mchChannelPermission 商户通道权限请求参数
     */
    @Override
    public Boolean saveMhChannelPermission(MchChannelPermissionReqDTO mchChannelPermission) {
        List<PayInterfaceDefine> interfaceDefineList = payInterFaceDefineManager.lambdaQuery()
                .in(PayInterfaceDefine::getId, mchChannelPermission.getChannelId())
                .eq(PayInterfaceDefine::getEnable, Boolean.TRUE).list();

        Assert.isFalse(interfaceDefineList.size() == mchChannelPermission.getChannelId().size(), ApiException.supplier(PAY_INTERFACE_NOT_EXIST));

        List<SystemClientChannelPermission> mchChannelPermissions = interfaceDefineList.stream().map(e -> {
            SystemClientChannelPermission systemMchChannelPermission = new SystemClientChannelPermission();
            systemMchChannelPermission.setChannelCode(e.getCode());
            systemMchChannelPermission.setChannelDefineId(e.getId());
            systemMchChannelPermission.setClientId(mchChannelPermission.getClientId());
            systemMchChannelPermission.setClientType(mchChannelPermission.getClientType());
            return systemMchChannelPermission;
        }).toList();
        // 获取用户已有的支付配置进行移除


        return systemMchChannelPermissionManager.saveChannelPermission(mchChannelPermissions, mchChannelPermission.getClientId());
    }

    /**
     * 获取商户已有的支付渠道权限
     *
     * @param type  当前商户类型
     * @param clientId 当前商户id
     */
    @Override
    public List<String> mchChannelPermission(Integer type, Long clientId) {
        List<SystemClientChannelPermission> permissions = systemMchChannelPermissionManager.lambdaQuery()
                .eq(SystemClientChannelPermission::getClientId, clientId)
                .eq(SystemClientChannelPermission::getClientType, type).list();

        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptyList();
        }
        return permissions.stream().map(SystemClientChannelPermission::getChannelDefineId).map(String::valueOf).toList();
    }
}




