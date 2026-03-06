package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.system.dao.entity.SystemMchChannelPermission;
import com.baosight.payment.system.dao.manager.PayInterFaceDefineManager;
import com.baosight.payment.system.dao.manager.SystemMchChannelPermissionManager;
import com.baosight.payment.system.dao.mapper.SystemMchChannelPermissionMapper;
import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.service.SystemMchChannelPermissionService;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.baosight.payment.system.error.PayInterfaceError.PAY_INTERFACE_NOT_EXIST;

/**
 * @author longjiangran
 * @description 针对表【system_mch_channel_permission(服务商通道权限)】的数据库操作Service实现
 * @createDate 2026-03-06 10:16:32
 */
@Service
@RequiredArgsConstructor
public class SystemMchChannelPermissionServiceImpl extends ServiceImpl<SystemMchChannelPermissionMapper, SystemMchChannelPermission>
        implements SystemMchChannelPermissionService {

    private final SystemMchChannelPermissionManager systemMchChannelPermissionManager;
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


        List<SystemMchChannelPermission> mchChannelPermissions = interfaceDefineList.stream().map(e -> {
            SystemMchChannelPermission systemMchChannelPermission = new SystemMchChannelPermission();
            systemMchChannelPermission.setChannelCode(e.getCode());
            systemMchChannelPermission.setChannelDefineId(e.getId());
            systemMchChannelPermission.setMchId(mchChannelPermission.getMchId());
            systemMchChannelPermission.setMchType(mchChannelPermission.getType());
            return systemMchChannelPermission;
        }).toList();
         return systemMchChannelPermissionManager.saveChannelPermission(mchChannelPermissions, mchChannelPermission.getMchId());
    }
}




