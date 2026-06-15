//package com.baosight.payment.channel.handler.channelflow.allin;
//
//import com.baosight.common.exception.ServiceException;
//import com.baosight.payment.api.MchChannelConfigApi;
//import com.baosight.payment.api.MchInfoApi;
//import com.baosight.payment.api.PlatformConfigurationApi;
//import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
//import com.baosight.payment.channel.dao.manager.ChannelGatewayLogManager;
//import com.baosight.payment.channel.enums.ChannelCode;
//import com.baosight.payment.channel.enums.ChannelEventType;
//import com.baosight.payment.channel.handler.channelflow.IChannelFlowOption;
//import com.baosight.payment.channel.handler.config.allin.AllInPayIsvConfig;
//import com.baosight.payment.channel.handler.config.allin.AllInPayMchConfig;
//import com.baosight.payment.channel.pojo.dao.ChannelGatewayLogDAO;
//import com.baosight.payment.channel.utils.AllInPayClient;
//import com.baosight.payment.dao.resp.AllInRespDTO;
//import com.baosight.payment.dao.resp.SystemRespDTO;
//import com.baosight.payment.enums.PayingAgency;
//import com.baosight.payment.vo.MchInfoVO;
//import com.baosight.utils.json.JsonUtil;
//import jakarta.annotation.Resource;
//import lombok.Data;
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
//import static com.baosight.payment.channel.enums.ChannelCode.ALLIN_PAY;
//
///**
// * @program: payment-center
// * @description:
// * @author: L.J.Ran
// * @create: 2026/4/17
// */
//@Slf4j
//@Component
//public class BindAllInPhoneReport extends AllInAbstractHandler implements IChannelFlowOption {
//    @Resource
//    private MchInfoApi mchInfoApi;
//    @Resource
//    private WebClient webClient;
//    @Resource
//    private ChannelGatewayLogManager channelGatewayLogManager;
//
//    protected BindAllInPhoneReport(PlatformConfigurationApi platformConfigurationApi, MchChannelConfigApi mchChannelConfigApi) {
//        super(platformConfigurationApi, mchChannelConfigApi);
//    }
//
//    /**
//     * 渠道编号
//     */
//    @Override
//    public ChannelCode channelCode() {
//        return ALLIN_PAY;
//
//    }
//
//    /**
//     * 执行类型
//     */
//    @Override
//    public ChannelEventType eventType() {
//        return ChannelEventType.BIND_PHONE_REPORT;
//    }
//
//    private Map<String, String> buildParam(Long clientId, String mobile, String domainUrl) {
//        Map<String, String> map = new HashMap<>(5);
//        map.put("signNum", String.valueOf(clientId));
//        map.put("phone", mobile);
//        // 手机号类型 1 法人 2 非法人 系统目前只支持法人的方式进行绑定
//        map.put("phoneType", "1");
//        // 回调地址
//        map.put("notifyUrl", domainUrl);
//        return map;
//    }
//
//    @Data
//    private static class RequestParam {
//        private String phone;
//    }
//
//    /**
//     * 渠道流程事件执行
//     *
//     * @param clientId   客户端ID
//     * @param clientType 客户端类型
//     * @param param      事件执行参数
//     */
//    @Override
//    public ExecuteResult execute(Long clientId, Integer clientType, String param) {
//        ExecuteResult result = new ExecuteResult();
//        ChannelGatewayLogDAO log = channelGatewayLog(clientId, clientType, OUT_SIDE, PayingAgency.ALL_IN);
//        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(clientId);
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        try {
//
//            if (ObjectUtils.isEmpty(mchInfoVO)) {
//                throw new ServiceException("商户信息不存在");
//            }
//
//            // 获取系统配置
//            SystemRespDTO systemedConfig = systemConfig();
//            // 获取通联通用配置
//            AllInRespDTO allInRespDTO = allInPayConfig();
//
//            RequestParam requestParam = JsonUtil.parse(param, RequestParam.class);
//
//
//            AllInPayMchConfig allInPayMchConfig = allInPayMchConfig(clientId);
//            AllInPayIsvConfig allInPayIsvConfig = allInPayIsvConfig(mchInfoVO.getIsvId());
//
//            // 处理调用逻辑
//            AllInPayClient allInPayClient = AllInPayClient.init(allInRespDTO.getPublicKey(), allInPayIsvConfig.getAppId(), allInRespDTO.getMemberRequestUrl(), allInRespDTO.getVersion());
//            allInPayClient.privateKey(allInPayMchConfig.getSignNum());
//            allInPayClient.webClient(webClient);
//            String notifyUrl = systemedConfig.getNotifyUrl()+"/" + channelCode()+"/"+"";
//            Map<String, String> buildParam = buildParam(clientId, requestParam.getPhone(), notifyUrl);
//            allInPayClient.setRequestParams(buildParam);
//            AllInPayClient.Response response = allInPayClient.sendRequest("1030");
//
//            handlerResponse(response, log, result);
//        } catch (Exception e) {
//            result.setSuccess(false).setErrorMsg(e.getMessage());
//            log.setBizStatus(ChannelGatewayLog.BizStatus.FAIL.getCode()).setErrorMsg(e.getMessage());
//        } finally {
//            stopWatch.stop();
//            log.setCostTime(stopWatch.getTotalTimeMillis());
//            channelGatewayLogManager.saveChannelGatewayLog(log);
//
//        }
//        return result;
//    }
//}
