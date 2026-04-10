package com.baosight.payment.channel.handler.channelflow.allin;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.access.tl.model.TongLianClient;
import com.baosight.payment.api.MchChannelConfigApi;
import com.baosight.payment.api.PlatformConfigurationApi;
import com.baosight.payment.channel.handler.channelflow.IChannelFlowOption;
import com.baosight.payment.channel.handler.config.allin.AllInPayMchConfig;
import com.baosight.payment.channel.utils.AllInPayClient;
import com.baosight.payment.dao.resp.AllInRespDTO;
import com.baosight.payment.enums.TongLianInterfaceCode;
import com.baosight.utils.json.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

import static com.baosight.payment.channel.enums.ChannelCode.ALLIN_PAY;

/**
 * 绑定收银宝
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BindAllInSyb implements IChannelFlowOption {

    private final PlatformConfigurationApi platformConfigurationApi;
    private final MchChannelConfigApi mchChannelConfigApi;

    /**
     * 渠道编号
     *
     */
    @Override
    public String channelCode() {
        return ALLIN_PAY.getCode();
    }

    /**
     * 执行类型 绑定收银宝
     */
    @Override
    public String stepType() {
        return "BIND-ALL-IN-SYB";
    }

    /**
     * 执行名称
     */
    @Override
    public String name() {
        return "绑定收银宝";
    }


    /**
     * 会员绑定收银宝商户
     *
     * @param reqTraceNum     请求流水号 要求唯一
     * @param signNum         商户会员编号
     * @param sybMerchantCode 收银宝商户号
     */
    public static TongLianClient.SendBuild memberBindSyb(Long reqTraceNum, String signNum, String sybMerchantCode) {
        String transCode = TongLianInterfaceCode.BIND_SYB.getCode();
        Map<String, String> map = new HashMap<>(4);
        map.put("reqTraceNum", String.valueOf(reqTraceNum));
        map.put("signNum", signNum);
        map.put("opType", "set");
        map.put("memberRole", "收单商户");
        map.put("sybMerchantCode", sybMerchantCode);
        return new TongLianClient.SendBuild(reqTraceNum, transCode, JsonUtil.toJson(map));
    }


    /**
     * 构建绑定收银宝参数信息
     *
     * @param signNum         商户会员编号
     * @param sybMerchantCode 收银宝商户号
     */
    private Map<String, String> buildParam(String signNum, String sybMerchantCode) {
        Map<String, String> map = new HashMap<>(5);
        map.put("reqTraceNum", "");
        map.put("signNum", signNum);
        map.put("opType", "set");
        map.put("memberRole", "收单商户");
        map.put("sybMerchantCode", sybMerchantCode);
        return map;
    }


    /**
     * 执行
     *
     * @param mchId
     * @param mchType
     * @param param
     */
    @Override
    public ExecuteResult execute(Long mchId, Integer mchType, String param) {
        ExecuteResult result = new ExecuteResult();
        try {
            // 获取通联通用配置
            AllInRespDTO allInRespDTO = platformConfigurationApi.allInPayConfig();
            // 获取商户通联配置信息
            Map<String, String> allInPayMchConfigMap = mchChannelConfigApi.mchChannelConfig(mchId, ALLIN_PAY.getCode());
            if (CollectionUtils.isEmpty(allInPayMchConfigMap)) {
                throw new ServiceException("未获取到商户配置信息");
            }
            AllInPayMchConfig parse = JsonUtil.parse(JsonUtil.toJson(allInPayMchConfigMap), AllInPayMchConfig.class);
            AllInPayClient allInPayClient = new AllInPayClient();
            allInPayClient.init(allInRespDTO.getPublicKey(), allInRespDTO.getMemberRequestUrl(), allInRespDTO.getVersion());
            Map<String, String> buildParam = buildParam(String.valueOf(mchId), parse.getCusid());
            allInPayClient.setRequestParams(buildParam);
            AllInPayClient.Response response = allInPayClient.sendRequest("1024");
            log.info("通联通绑卡请求结果：{}", response);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
