package com.baosight.payment.controller;

import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.api.PayInterfaceApi;
import com.baosight.payment.enums.PayState;
import com.baosight.payment.enums.PayWayCode;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.error.NoticeError;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.order.api.dto.CrCreateOrderDTO;
import com.baosight.payment.pojo.entity.CallbackHandlerLog;
import com.baosight.payment.pojo.entity.TonglianReturn;
import com.baosight.payment.service.CallbackHandlerLogService;
import com.baosight.payment.service.TonglianReturnService;
import com.baosight.payment.utils.SybUtil;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.utils.Assert;
import com.baosight.utils.utils.ObjectUtils;
import com.baosight.web.annotation.IgnoreHandlerResponse;
import com.baosight.web.exception.ApiException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class ChannelNoticeController {

    private final TonglianReturnService tonglianReturnService;
    @Resource
    private CallbackHandlerLogService callbackHandlerLogService;

    @Resource
    private PayInterfaceApi payInterfaceApi;

    @Resource
    private OrderApi orderApi;

    @Resource
    private MchInfoApi mchInfoApi;

    // TODO 根据接口code与收银宝号获取支付配置
    // TODO 根据收银宝号获取商家信息
    // TODO 创建订单 进行数据回调

    /**
     * @return
     */
    @IgnoreHandlerResponse
    @GetMapping("/{payingAgency}/{payType}")
    public String tongLianGet(@PathVariable("payingAgency") Integer payingAgency,
                              @PathVariable("payType") Integer payType,
                              HttpServletRequest request) {
        Map<String, String> params = getParams(request);
        String data = JsonUtil.toJson(params);
        log.info("收到通联回调支付企业: {} 支付类型: {}, 请求数据{}", payingAgency, payType, data);
        TonglianReturn tonglianReturn = new TonglianReturn();
        tonglianReturn.setResult(data);
        tonglianReturnService.save(tonglianReturn);
        return "success";
    }

    /**
     * @return
     */
    @IgnoreHandlerResponse
    @PostMapping("/{interfaceCode}/{payingAgency}/{payType}")
    public String tongLianPost(@PathVariable("payingAgency") Integer payingAgency,
                               @PathVariable("payType") Integer payType,
                               @PathVariable("interfaceCode") String interfaceCode,
                               HttpServletRequest request) {

        Map<String, String> params = getParams(request);
        String data = JsonUtil.toJson(params);
        JsonNode jsonNode = JsonUtil.readTree(data);
        CallbackHandlerLog callbackHandlerLog = new CallbackHandlerLog();
        try {
            // 支付流水号
            String trxId = jsonNode.get("trxid").asText();

            // 收银宝商户号
            String cusid = jsonNode.get("cusid").asText();
            CallbackHandlerLog info = callbackHandlerLogService.getInfo(payingAgency, payType, trxId, interfaceCode);
            if (ObjectUtils.isEmpty(info)) {
                MchInterfaceConfigVO mchInterfaceConfig = payInterfaceApi.mchInterfaceConfig(interfaceCode, cusid);
                // 未处理当前请求或是处理失败 再一次处理请求
                callbackHandlerLog.setTrxId(trxId);
                callbackHandlerLog.setPayType(payType);
                callbackHandlerLog.setPayingAgency(payingAgency);
                callbackHandlerLog.setCallbackContext(data);
                callbackHandlerLog.setInterfaceCode(interfaceCode);
                callbackHandlerLog.setMchNo(mchInterfaceConfig.getMchNo());
                // 预先保存
                callbackHandlerLogService.save(callbackHandlerLog);
                // 根据支付接口以及通联收银宝账号获取接口信息
                // 获取商户信息
                MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchInterfaceConfig.getMchId());
                // 获取公钥
                String appPubKey = mchInterfaceConfig.getConfig().get("rsaPublicKey");
                //请求数据验签解密使用公钥
                String signType = params.get("signtype");
                boolean isSign = SybUtil.validSign(params, appPubKey, signType);
                PayingAgency payingAgencyType = PayingAgency.byAgencyCode(payingAgency);
                Assert.isFalse(isSign, ApiException.supplier(NoticeError.PARAMETER_CHECK_ERROR, payingAgencyType.getMsg()));
                // 开始创建订单
                CrCreateOrderDTO crCreateOrderDTO = new CrCreateOrderDTO();
                crCreateOrderDTO.setChannelOrderNo(trxId);
                crCreateOrderDTO.setTradeUser(jsonNode.get("acct").asText());
                crCreateOrderDTO.setMchChannelUser(mchInterfaceConfig.getThirdCode());
                crCreateOrderDTO.setCreateTime(new Date(jsonNode.get("timestamp").asLong()));
                crCreateOrderDTO.setHasDivision(Boolean.FALSE);
                crCreateOrderDTO.setOutTradeNo(jsonNode.get("chnltrxid").asText());
                crCreateOrderDTO.setMchChannelUser(cusid);
                crCreateOrderDTO.setFinishTime(new Date(jsonNode.get("paytime").asLong()));
                crCreateOrderDTO.setMchId(mchInfoVO.getId());
                crCreateOrderDTO.setMchNo(mchInfoVO.getMchNo());
                crCreateOrderDTO.setIsvId(mchInfoVO.getIsvId());
                crCreateOrderDTO.setMchType(mchInfoVO.getType());
                crCreateOrderDTO.setInterfaceCode(interfaceCode);
                crCreateOrderDTO.setMchName(mchInfoVO.getMchName());
                PayWayCode payWayCode = PayWayCode.payWayCode(payType);
                crCreateOrderDTO.setWayCode(payWayCode.getCode());
                crCreateOrderDTO.setMchFeeRate(mchInterfaceConfig.getMchFeeRate());
                crCreateOrderDTO.setTotalAmount(jsonNode.get("amount").asLong());
                // TODO 应用信息待处理
                crCreateOrderDTO.setState(PayState.findState(jsonNode.get("trxstatus").asText()).getCode());
                Boolean result = orderApi.qCrCreateOrder(crCreateOrderDTO);
                callbackHandlerLogService.updateHandlerState(result, null, callbackHandlerLog.getId());
            }
            // 接受到推送通知,首先验签
        } catch (Exception e) {
            e.printStackTrace();
            String error = e.toString();
            log.info("回调执行失败:{}", error);
            callbackHandlerLogService.updateHandlerState(Boolean.FALSE, error, callbackHandlerLog.getId());
        }
        return "success";
    }

    private TreeMap<String, String> getParams(HttpServletRequest request) {
        TreeMap<String, String> map = new TreeMap<>();
        Map<String, String[]> reqMap = request.getParameterMap();
        reqMap.forEach((k, v) -> map.put(k, v[0]));
        return map;
    }

    public static void main(String[] args) {
        String json = "{\"acct\":\"oa8Al5Ob7k9mJLuQjhhx7nqoB7jU\",\"accttype\":\"02\",\"amount\":\"10\",\"appid\":\"00010398\",\"bankcode\":\"GDB_CREDIT\",\"chnltrxid\":\"4200002715202503171611419655\",\"cusid\":\"990521082996000\",\"fee\":\"0\",\"feecycle\":\"2\",\"initamt\":\"10\",\"paytime\":\"20250317093754\",\"randomstr\":\"170469\",\"sign\":\"auvkBd2WcSWyvRE18pyZJEQcN24wQ2C3qwwK70LK+OYGY2JhvfvrkH3tD2oMYJMey0FroixsEIRMK9YQw/w0ssIJ7NNgUCQef9UQuYaxZeeBMIVnWuSueRq6Na6aaMetoMggmD2iQpewBxmNh7cyW6Vtp04z5plDbpXTy1xIuno=\",\"signtype\":\"RSA\",\"termauthno\":\"GDB_CREDIT\",\"termid\":\"110BQZK9R5DU\",\"termrefnum\":\"4200002715202503171611419655\",\"timestamp\":\"20250317093754\",\"traceno\":\"0\",\"trxcode\":\"VSP501\",\"trxday\":\"20250317\",\"trxid\":\"250317120421092468\",\"trxstatus\":\"0000\"}";
        AtomicReference<String> param = new AtomicReference<>("?");
        Map<String, Object> map = JsonUtil.toMap(json);
        map.forEach((k, v) -> param.set(param + k + "=" + v.toString() + "&"));
        System.out.println(param);
    }

}
