//package com.baosight.payment.channel.handler.channelflow.allin;
//
//import com.baosight.common.exception.ServiceException;
//import com.baosight.payment.api.MchChannelConfigApi;
//import com.baosight.payment.api.MchInfoApi;
//import com.baosight.payment.api.PlatformConfigurationApi;
//import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
//import com.baosight.payment.channel.dao.manager.ChannelGatewayLogManager;
//import com.baosight.payment.channel.enums.ChannelEventType;
//import com.baosight.payment.channel.handler.channelflow.IChannelFlowOption;
//import com.baosight.payment.channel.handler.config.allin.AllInPayIsvConfig;
//import com.baosight.payment.channel.handler.config.allin.AllInPayMchConfig;
//import com.baosight.payment.channel.pojo.dao.ChannelGatewayLogDAO;
//import com.baosight.payment.channel.utils.AllInPayClient;
//import com.baosight.payment.dao.resp.AllInRespDTO;
//import com.baosight.payment.enums.PayingAgency;
//import com.baosight.payment.vo.MchInfoVO;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StopWatch;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.baosight.payment.channel.dao.entity.ChannelGatewayLog.Operation.OUT_SIDE;
//
///**
// * 绑定收银宝
// *
// * @program: payment-center
// * @description:
// * @author: L.J.Ran
// * @create: 2026/3/27
// */
//@Slf4j
//@Component
//public class BindAllInSyb extends AllInAbstractHandler implements IChannelFlowOption {
//
//    @Resource
//    private MchInfoApi mchInfoApi;
//    @Resource
//    private WebClient webClient;
//    @Resource
//    private ChannelGatewayLogManager channelGatewayLogManager;
//
//    protected BindAllInSyb(PlatformConfigurationApi platformConfigurationApi, MchChannelConfigApi mchChannelConfigApi) {
//        super(platformConfigurationApi, mchChannelConfigApi);
//    }
//
//    /**
//     * 渠道编号
//     *
//     */
//    @Override
//    public ChannelCode channelCode() {
//        return ALLIN_PAY;
//    }
//
//    /**
//     * 执行类型 绑定收银宝
//     */
//    @Override
//    public ChannelEventType eventType() {
//        return ChannelEventType.BIND_ALL_IN_SYB;
//    }
//
//
//    /**
//     * 构建绑定收银宝参数信息
//     *
//     * @param signNum         商户会员编号
//     * @param sybMerchantCode 收银宝商户号
//     */
//    private Map<String, String> buildParam(String signNum, String sybMerchantCode) {
//        Map<String, String> map = new HashMap<>(5);
//        map.put("signNum", signNum);
//        map.put("opType", "set");
//        map.put("memberRole", "收单商户");
//        map.put("sybMerchantCode", sybMerchantCode);
//        return map;
//    }
//
//
//    /**
//     * 执行
//     *
//     * @param clientId   客户端id
//     * @param clientType 客户端类型
//     * @param param      执行参数
//     */
//    @Override
//    public ExecuteResult execute(Long clientId, Integer clientType, String param) {
//        ExecuteResult result = new ExecuteResult();
//        ChannelGatewayLogDAO log = channelGatewayLog(clientId, clientType, OUT_SIDE, PayingAgency.ALL_IN);
//        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(clientId);
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        try {
//            AllInRespDTO allInRespDTO = allInPayConfig();
//            if (ObjectUtils.isEmpty(mchInfoVO)) {
//                throw new ServiceException("商户信息不存在");
//            }
//
//            AllInPayMchConfig allInPayMchConfig = allInPayMchConfig(clientId);
//            AllInPayIsvConfig allInPayIsvConfig = allInPayIsvConfig(mchInfoVO.getIsvId());
//
//            //调用逻辑处理
//            AllInPayClient allInPayClient = AllInPayClient.init(allInRespDTO.getPublicKey(), allInPayIsvConfig.getAppId(), allInRespDTO.getMemberRequestUrl(), allInRespDTO.getVersion());
//            allInPayClient.privateKey(allInPayMchConfig.getSignNum());
//            allInPayClient.webClient(webClient);
//            Map<String, String> buildParam = buildParam(String.valueOf(clientId), allInPayMchConfig.getCusid());
//            allInPayClient.setRequestParams(buildParam);
//            AllInPayClient.Response response = allInPayClient.sendRequest("1024");
//
//            handlerResponse(response, log, result);
//        } catch (Exception e) {
//            result.setSuccess(false);
//            result.setErrorMsg(e.getMessage());
//            log.setBizStatus(ChannelGatewayLog.BizStatus.FAIL.getCode()).setErrorMsg(e.getMessage());
//        } finally {
//            stopWatch.stop();
//            log.setCostTime(stopWatch.getTotalTimeMillis());
//            channelGatewayLogManager.saveChannelGatewayLog(log);
//        }
//        return result;
//    }
//}
