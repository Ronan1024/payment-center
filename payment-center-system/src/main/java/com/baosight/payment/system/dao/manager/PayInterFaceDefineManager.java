package com.baosight.payment.system.dao.manager;

import com.baosight.database.core.annotation.Manager;
import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.system.mapper.PayInterfaceDefineMapper;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;

import static com.baosight.payment.system.error.ChannelError.CHANNEL_INFO_NOT_EXIT;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@Manager
public class PayInterFaceDefineManager extends BaseManagerImpl<PayInterfaceDefineMapper, PayInterfaceDefine> {


    /**
     * 获取渠道配置定义信息
     *
     * @param channelId 渠道ID
     */
    public PayInterfaceDefine infoById(Long channelId) {
        PayInterfaceDefine interfaceDefine = getById(channelId);
        Assert.isNull(interfaceDefine, ApiException.supplier(CHANNEL_INFO_NOT_EXIT));
        return interfaceDefine;
    }
}
