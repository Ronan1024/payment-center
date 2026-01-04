package com.baosight.payment.chanel.ums.utils;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.utils.OkHttp;
import com.baosight.utils.json.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/12/31
 */
@Slf4j
public class UmsClient {


    private final SendBuild sendBuild;

    public UmsClient(SendBuild sendBuild) {
        this.sendBuild = sendBuild;
    }


    public Response sendRequest(String url) {
        Map<String, String> params = sendBuild.params;
        params.put("version", "20191031");
        params.put("requestTimestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("signType", "SHA256");
        params.put("mid", sendBuild.mchId);
        params.put("tid", sendBuild.tid);
        // 代签名的字符串
        StringBuilder sb = new StringBuilder();
        // 组织请求参数
        StringBuilder req = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (StringUtils.hasText(entry.getValue())) {
                if (sb.isEmpty()) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue());
                    req.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                } else {
                    sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                    req.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                }
            }

        }

        String sign = sm3(sb.toString());

        log.info("UMS 待签名的字符串是：{}", sb);
        log.info("UMS sign的值是：{}", sign);
        req.append("&sign=").append(URLEncoder.encode(sign, StandardCharsets.UTF_8));
        String response = OkHttp.get(url + req);
        try {
            checkSign(response);
        } catch (JsonProcessingException e) {
            throw new ServiceException("签名验证失败", e);
        }
        assert response != null;
        JsonNode jsonNode = JsonUtil.readTree(response);


        Response result = new Response();
        String errCode = jsonNode.get("errCode").asText();
        String errMsg = jsonNode.get("errMsg").asText();
        result.setResult(jsonNode);
        if (!"SUCCESS".equals(errCode)) {
            result.setSuccess(Boolean.FALSE);
        } else {
            result.setSuccess(Boolean.TRUE);
            log.info("UMS 接口调用失败 errorCode: {}， errorMsg: {}, response:{}", errCode, errMsg, response);
        }

        return result;
    }


    /**
     * 验证签名
     *
     * @param data
     */
    public void checkSign(String data) throws JsonProcessingException {
        TreeMap<String, String> treeMap = JsonUtil.getInstance().readValue(data, new TypeReference<>() {
        });

        String sign = treeMap.get("sign");
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            if (entry.getKey().equals("sign")) {
                continue;
            }
            if (builder.isEmpty()) {
                builder.append(entry.getKey()).append("=").append(entry.getValue());
            } else {
                builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        String localMac = sm3(builder.toString());
        System.out.println("本地计算的mac:" + localMac);

        if (!sign.equalsIgnoreCase(localMac)) {
            log.error("签名验证失败：sign {}, localSign{}, data: {}", sign, localMac, data);
            throw new ServiceException("签名验证失败");
        }
    }

    private String sm3(String data) {
        byte[] messages = (data + sendBuild.signKey).getBytes(StandardCharsets.UTF_8);
        SM3Digest digest = new SM3Digest();
        digest.update(messages, 0, messages.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return Hex.toHexString(hash);
    }

    @Data
    public static class Response {
        private Boolean success;
        private String respCode;
        private JsonNode result;
        private UmsResponseModel umsResponseModel;
        private String errorMsg;

        public Boolean success() {
            return success;
        }

        public JsonNode get(String param) {
            return result.get(param);
        }
    }


    @Data
    public static class Request {
        /**
         * 版本号
         */
        private String version;

        /**
         * 报文请求时间 格式yyyy-MM-dd HH:mm:ss
         */
        private String requestTimestamp;
    }

    @Data
    public static class UmsResponseModel {
        /**
         * 版本号
         */
        private String version;
        /**
         * 平台错误码
         */
        private String errCode;
        /**
         * 平台错误描述
         */
        private String errMsg;
        /**
         * 时间戳
         */
        private String responseTimestamp;
        /**
         * 平台流水号
         */
        private String seqId;
        /**
         * 清分
         */
        private String settleRefId;
        /**
         * 交易状态
         */
        private String status;
        /**
         * 第三方订单号
         */
        private String targetOrderId;
        /**
         * 目标平台代码
         */
        private String targetSys;
        /**
         * 目标平台的状态
         */
        private String targetStatus;
        /**
         * 小程序支付用的请求报文，带有签名信息
         */
        private String miniPayRequest;
        /**
         * 消息类型
         */
        private String msgType;
        /**
         * 商户订单号
         */
        private String merOrderId;
        /**
         * 商户号
         */
        private String mid;
        /**
         * 终端号
         */
        private String tid;
        /**
         * 操作员工号
         */
        private String employeeNo;
        /**
         * 商户附加数据
         */
        private String attachedData;
        /**
         * 订单原始金额
         */
        private String originalAmount;
        /**
         * 支付总金额
         */
        private String totalAmount;
        /**
         * 平台商户分账金额
         */
        private String platformAmount;
        /**
         * 分账明细
         */
        private String subOrders;
        /**
         * 签名算法
         */
        private String signType;
        /**
         * 付款人openid
         */
        private String subOpenId;
        /**
         * 商户微信appId
         */
        private String subAppId;
        /**
         * 扩展字段1
         */
        private String extend1;
        /**
         * 扩展字段2
         */
        private String extend2;
        /**
         * 扩展字段3
         */
        private String extend3;
        /**
         * 扩展字段4
         */
        private String extend4;
        /**
         * 签名
         */
        private String sign;
    }


    @Data
    @AllArgsConstructor
    public static class SendBuild {
        /**
         * 商户号
         */
        private final String mchId;

        /**
         * 终端号
         */
        private final String tid;

        /**
         * 业务类型
         */
        private String instMid;

        private String version;

        /**
         * 签名密钥
         */
        private String signKey;

        /**
         * 请求参数
         */
        private TreeMap<String, String> params;
    }
}
