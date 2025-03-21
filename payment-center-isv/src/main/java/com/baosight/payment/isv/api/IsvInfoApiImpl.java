package com.baosight.payment.isv.api;

import com.baosight.payment.isv.convert.PayIsvInfoConvert;
import com.baosight.payment.isv.pojo.entity.PayIsvInfo;
import com.baosight.payment.isv.service.PayIsvInfoService;
import com.baosight.payment.isv.vo.IsvInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: payment-center
 * @description: 服务商信息接口impl
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Service
@RequiredArgsConstructor
public class IsvInfoApiImpl implements IsvInfoApi {
    private final PayIsvInfoService payIsvInfoService;

    /**
     * 服务商信息列表
     *
     * @param isvIdList 服务商id列表
     */
    @Override
    public List<IsvInfoVO> isvInfoList(List<Long> isvIdList) {
        if (CollectionUtils.isEmpty(isvIdList)) {
            return new ArrayList<>();
        }
        List<PayIsvInfo> payIsvInfoList = payIsvInfoService.infoByIdList(isvIdList);
        return payIsvInfoList.stream().map(PayIsvInfoConvert.INSTANCE::toIsvInfoVO).toList();
    }

    /**
     * 获取服务商信息
     *
     * @param isvId 服务商id
     */
    @Override
    public IsvInfoVO isvInfoById(Long isvId) {
        PayIsvInfo info = payIsvInfoService.infoById(isvId);
        return PayIsvInfoConvert.INSTANCE.toIsvInfoVO(info);
    }

}
