package com.baosight.payment.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.system.dao.entity.SystemClientChannelPermission;
import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;

import java.util.List;

/**
* @author longjiangran
* @description 针对表【system_mch_channel_permission(服务商通道权限)】的数据库操作Service
* @createDate 2026-03-06 10:16:32
*/
public interface SystemClientChannelPermissionService extends IService<SystemClientChannelPermission> {

    /**
     * 添加商户通道权限
     * @param mchChannelPermission 商户通道权限请求参数
     */
    Boolean saveMhChannelPermission(MchChannelPermissionReqDTO mchChannelPermission);

    /**
     * 获取商户已有的支付渠道权限
     *
     * @param type  当前商户类型
     * @param mchId 当前商户id
     */
    List<String> mchChannelPermission(Integer type, Long mchId);
}
