package com.baosight.payment.channel.handler.channelflow.allin;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.api.MchChannelConfigApi;
import com.baosight.payment.api.PlatformConfigurationApi;
import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
import com.baosight.payment.channel.handler.channelflow.ChannelFlowAbstractHandler;
import com.baosight.payment.channel.handler.config.allin.AllInPayIsvConfig;
import com.baosight.payment.channel.handler.config.allin.AllInPayMchConfig;
import com.baosight.payment.channel.pojo.dao.ChannelGatewayLogDAO;
import com.baosight.payment.channel.utils.AllInPayClient;
import com.baosight.payment.dao.resp.AllInRespDTO;
import com.baosight.utils.json.JsonUtil;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import static com.baosight.payment.enums.ChannelCode.ALLIN_PAY;


/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/17
 */
public abstract class AllInAbstractHandler extends ChannelFlowAbstractHandler {

    protected final MchChannelConfigApi mchChannelConfigApi;

    protected AllInAbstractHandler(PlatformConfigurationApi platformConfigurationApi, MchChannelConfigApi mchChannelConfigApi) {
        super(platformConfigurationApi);
        this.mchChannelConfigApi = mchChannelConfigApi;
    }

    /**
     * 获取通联配置信息
     */
    protected AllInRespDTO allInPayConfig() {
        return platformConfigurationApi.allInPayConfig();
    }

    /**
     * 获取通联商户配置信息
     *
     * @param clientId 商户id
     */
    protected AllInPayMchConfig allInPayMchConfig(Long clientId) {
        Map<String, String> allInPayMchConfigMap = mchChannelConfigApi.mchChannelConfig(clientId, ALLIN_PAY.code());
        if (CollectionUtils.isEmpty(allInPayMchConfigMap)) {
            throw new ServiceException("未获取到商户配置信息");
        }

        return JsonUtil.parse(JsonUtil.toJson(allInPayMchConfigMap), AllInPayMchConfig.class);
    }

    /**
     * 获取通联商户服务商配置信息
     *
     * @param clientId 服务商商户id
     */
    protected AllInPayIsvConfig allInPayIsvConfig(Long clientId) {
        Map<String, String> allInPayIsvConfigMap = mchChannelConfigApi.mchChannelConfig(clientId, ALLIN_PAY.code());
        if (CollectionUtils.isEmpty(allInPayIsvConfigMap)) {
            throw new ServiceException("未获取到商户服务商配置信息");
        }

        return JsonUtil.parse(JsonUtil.toJson(allInPayIsvConfigMap), AllInPayIsvConfig.class);
    }


    /**
     * 处理请求响应信息
     * @param response 通联请求响应信息
     * @param log 渠道网关日志
     * @param result 执行结果
     */
    protected void handlerResponse(AllInPayClient.Response response, ChannelGatewayLogDAO log, ExecuteResult result){
        log.setReqParams(response.getRequest())
                .setReqUrl(response.getUrl())
                .setRequestNo(response.getRequestNo())
                .setResParams(String.valueOf(response.getResult()))
                .setResCode(response.effectiveCode());

        if (response.processing()) {
            result.setSuccess(true);
            result.setResult(String.valueOf(response.getResult()));
            log.setBizStatus(ChannelGatewayLog.BizStatus.PROCESS.getCode());
            return;
        }

        if (Boolean.TRUE.equals(response.getSuccess())) {
            result.setSuccess(true);
            result.setResult(String.valueOf(response.getResult()));
            log.setBizStatus(ChannelGatewayLog.BizStatus.SUCCESS.getCode());
            return;
        }

        result.setSuccess(false);
        result.setErrorMsg(response.effectiveErrorMsg());
        log.setBizStatus(ChannelGatewayLog.BizStatus.FAIL.getCode())
                .setErrorCode(response.effectiveCode())
                .setErrorMsg(response.effectiveErrorMsg());
    }
}
