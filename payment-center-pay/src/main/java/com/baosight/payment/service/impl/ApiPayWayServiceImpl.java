package com.baosight.payment.service.impl;

import com.baosight.payment.api.PayWayApi;
import com.baosight.payment.model.PayWayModel;
import com.baosight.payment.service.ApiPayWayService;
import com.baosight.payment.vo.ApiPayWayVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiPayWayServiceImpl implements ApiPayWayService {
    private final PayWayApi payWayApi;

    /**
     * 获取支付方式列表
     *
     * @param client 获取客户端id
     * @param appId  应用id
     * @param mchId  商户id
     */
    @Override
    public List<PayWayModel> paywayList(Integer client, Long appId, Long mchId) {
        List<ApiPayWayVO> wayVOList = payWayApi.payWayList(client, mchId, appId);
        if (CollectionUtils.isEmpty(wayVOList)) {
            return new ArrayList<>();
        }
        return wayVOList.stream().map(e -> {
            PayWayModel payWayModel = new PayWayModel();
            payWayModel.setId(String.valueOf(e.getId()));
            payWayModel.setPayName(e.getName());
            return payWayModel;
        }).toList();
    }
}
