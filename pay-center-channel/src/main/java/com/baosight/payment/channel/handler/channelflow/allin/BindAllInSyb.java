package com.baosight.payment.channel.handler.channelflow.allin;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.api.MchChannelConfigApi;
import com.baosight.payment.api.PlatformConfigurationApi;
import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
import com.baosight.payment.channel.dao.manager.ChannelGatewayLogManager;
import com.baosight.payment.channel.handler.channelflow.IChannelFlowOption;
import com.baosight.payment.channel.handler.config.allin.AllInPayMchConfig;
import com.baosight.payment.channel.pojo.dao.ChannelGatewayLogDAO;
import com.baosight.payment.channel.utils.AllInPayClient;
import com.baosight.payment.dao.resp.AllInRespDTO;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.utils.json.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static com.baosight.payment.channel.dao.entity.ChannelGatewayLog.Operation.OUT_SIDE;
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
    private final IsvConfigApi isvConfigApi;
    private final WebClient webClient;
    private final ChannelGatewayLogManager channelGatewayLogManager;

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

//
//    /**
//     * 会员绑定收银宝商户
//     *
//     * @param reqTraceNum     请求流水号 要求唯一
//     * @param signNum         商户会员编号
//     * @param sybMerchantCode 收银宝商户号
//     */
//    public static TongLianClient.SendBuild memberBindSyb(Long reqTraceNum, String signNum, String sybMerchantCode) {
//        String transCode = TongLianInterfaceCode.BIND_SYB.getCode();
//        Map<String, String> map = new HashMap<>(4);
//        map.put("reqTraceNum", String.valueOf(reqTraceNum));
//        map.put("signNum", signNum);
//        map.put("opType", "set");
//        map.put("memberRole", "收单商户");
//        map.put("sybMerchantCode", sybMerchantCode);
//        return new TongLianClient.SendBuild(reqTraceNum, transCode, JsonUtil.toJson(map));
//    }


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
     * @param clientId   客户端id
     * @param clientType 客户端类型
     * @param param      执行参数
     */
    @Override
    public ExecuteResult execute(Long clientId, Integer clientType, String param) {
        ExecuteResult result = new ExecuteResult();
        ChannelGatewayLogDAO log = new ChannelGatewayLogDAO();
        log.setOperation(OUT_SIDE.getCode())
                .setBizType(stepType())
                .setBizTypeName(name())
                .setClientType(clientType)
                .setClientId(clientId)
                .setInstCode(PayingAgency.ALL_IN.code())
                .setChannelCode(channelCode())
                .setBizStatus(ChannelGatewayLog.BizStatus.CREATE.getCode());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // 获取通联通用配置
            AllInRespDTO allInRespDTO = platformConfigurationApi.allInPayConfig();
            // 获取商户通联配置信息
            Map<String, String> allInPayMchConfigMap = mchChannelConfigApi.mchChannelConfig(clientId, ALLIN_PAY.getCode());
            if (CollectionUtils.isEmpty(allInPayMchConfigMap)) {
                throw new ServiceException("未获取到商户配置信息");
            }
            // TODO 获取商户服务商通联配置信息

            AllInPayMchConfig parse = JsonUtil.parse(JsonUtil.toJson(allInPayMchConfigMap), AllInPayMchConfig.class);
            AllInPayClient allInPayClient = AllInPayClient.init(allInRespDTO.getPublicKey(), allInRespDTO.getAppId(), allInRespDTO.getMemberRequestUrl(), allInRespDTO.getVersion());
            allInPayClient.privateKey(parse.getSignNum());
            allInPayClient.webClient(webClient);
            Map<String, String> buildParam = buildParam(String.valueOf(clientId), parse.getCusid());
            allInPayClient.setRequestParams(buildParam);
            AllInPayClient.Response response = allInPayClient.sendRequest("1024");
            log.setReqParams(response.getRequest())
                    .setOutTradeNo(response.getRespCode())
                    .setReqUrl(response.getUrl())
                    .setRequestNo(response.getRequestNo())
                    .setResParams(String.valueOf(response.getResult()))
                    .setResCode(response.getRespCode());

            if (Boolean.TRUE.equals(response.getSuccess())) {
                result.setSuccess(true);
                log.setBizStatus(ChannelGatewayLog.BizStatus.SUCCESS.getCode());
            } else {
                result.setSuccess(false);
                log.setBizStatus(ChannelGatewayLog.BizStatus.SUCCESS.getCode());
                log.setErrorCode(response.getRespCode());
                log.setErrorMsg(response.getErrorMsg());
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.setBizStatus(ChannelGatewayLog.BizStatus.FAIL.getCode());
            log.setErrorMsg(e.getMessage());
        } finally {
            stopWatch.stop();
            log.setCostTime(stopWatch.getTotalTimeMillis());
            channelGatewayLogManager.saveChannelGatewayLog(log);
        }
        return result;
    }
}
