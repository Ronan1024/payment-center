package com.baosight.payment.chanel.tonglianpay.payway;

import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.chanel.IPaymentService;
import com.baosight.payment.chanel.tonglianpay.TongLianClient;
import com.baosight.payment.constant.PayWay;
import com.baosight.payment.dao.TongLianConfigVO;
import com.baosight.payment.model.UnifiedOrder;
import com.baosight.payment.pojo.dao.MchAppConfigInfoDAO;
import com.baosight.payment.pojo.entity.PayOrder;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.utils.StrFormatter;
import com.baosight.web.exception.ApiException;
import com.baosight.web.properties.ProjectInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.util.MapUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 通联微信正扫
 */
@Slf4j
@RequiredArgsConstructor
@Component(PayWay.TONG_LIAN_ALI_SCAN)
public class WxScan implements IPaymentService {
    private final MchAppConfigApi mchAppConfigApi;
    private final ProjectInfo projectInfo;

    /**
     * 获取到接口code
     **/
    @Override
    public Long getPayInterfaceCode() {
        return 0L;
    }

    /**
     * 调起支付接口并响应数据
     *
     * @param unifiedOrder     申请支付请求体
     * @param mchAppConfigInfo
     */
    @Override
    public Object pay(UnifiedOrder unifiedOrder, MchAppConfigInfoDAO mchAppConfigInfo) {
        TongLianConfigVO config = mchAppConfigApi.tongLianConfig(unifiedOrder.getMchId());

        // 获取支付配置
//        Map<String, String> payMode = new HashMap<>(1);
//        scanWx.put("SCAN_WEIXIN", "{\"limitPay\":\"no_credit\"}");
//        payMode.put("SCAN_WEIXIN", JsonUtil.toJson(scanWx));
//        ScanWeiXin scanWeiXin = new ScanWeiXin(StrFormatter.format("{\"vspCusid\":\"{}\"}", config.getSignNum()));
        Map<String, Object> payModel = new HashMap<>();

        Map<String, Object> scanWx = new HashMap<>();
//        scanWx.put("limitPay", "no_credit");
        scanWx.put("vspCusid", "66053305137015X");
        payModel.put("SCAN_WEIXIN", scanWx);

        Map<String, Object> map = new HashMap<>();
        map.put("signNum", String.valueOf(SnowflakeIdUtil.nextId()));
        map.put("reqTraceNum", unifiedOrder.getOutTradeNo());
        map.put("orderAmount", String.valueOf(unifiedOrder.getTotalFee()));
        map.put("payAmount", String.valueOf(unifiedOrder.getTotalFee()));
        map.put("payMode", payModel);
        map.put("respUrl", "https://www.baidu.com");
        map.put("receiverSignNum", String.valueOf(unifiedOrder.getMchId()));
        map.put("goodsName", "sss");

        // 获取用户信息
        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "2085", map);
        TongLianClient tongLianClient = new TongLianClient(config);
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, TongLianClient.URL);
        if (projectInfo.hasDev()) {
            return JsonUtil.readTree(response.get("chnlFrontParamInfo").asText()).get("chnlPayInfo").asText();
        }
        if (Boolean.FALSE.equals(response.success())) {
            log.info("交易失败：{}", response.getResult());
        } else {
            JsonNode jsonNode = JsonUtil.readTree(response.get("chnlFrontParamInfo").asText());
            return jsonNode.get("chnlPayInfo").asText();
        }
        return null;
    }

    /**
     * 前置检查如参数等信息是否符合要求， 返回错误信息或直接抛出异常即可
     *
     * @param unifiedOrder
     * @param payOrder
     */
    @Override
    public ApiException preCheck(UnifiedOrder unifiedOrder, PayOrder payOrder) {
        return null;
    }

    /**
     * 自定义支付订单号， 若返回空则使用系统生成订单号
     *
     * @param unifiedOrder
     * @param payOrder
     * @param mchInfoVO
     */
    @Override
    public String customPayOrderId(UnifiedOrder unifiedOrder, PayOrder payOrder, MchInfoVO mchInfoVO) {
        return "";
    }

    /**
     * 是否支持当前支付方式
     *
     * @param patWayCode 支付方式code
     */
    @Override
    public boolean isSupport(String patWayCode) {
        return false;
    }

    @Data
    @AllArgsConstructor
    static class ScanWeiXin {
        @JsonProperty("SCAN_WEIXIN")
        private String scanWeiXin;
    }
}
