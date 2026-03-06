package com.baosight.payment.system.service;

import com.baosight.payment.system.dao.entity.SystemMchChannelPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;

/**
* @author longjiangran
* @description 针对表【system_mch_channel_permission(服务商通道权限)】的数据库操作Service
* @createDate 2026-03-06 10:16:32
*/
public interface SystemMchChannelPermissionService extends IService<SystemMchChannelPermission> {

    /**
     * 添加商户通道权限
     * @param mchChannelPermission 商户通道权限请求参数
     */
    Boolean saveMhChannelPermission(MchChannelPermissionReqDTO mchChannelPermission);
}
