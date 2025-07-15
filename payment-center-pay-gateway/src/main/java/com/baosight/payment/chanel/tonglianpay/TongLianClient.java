package com.baosight.payment.chanel.tonglianpay;

import com.baosight.payment.access.tl.model.TongLianIsvConfigDAO;
import com.baosight.payment.utils.OkHttp;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.utils.Assert;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class TongLianClient {
    private static final String YYYY_MM_DD = "yyyyMMdd";
    private static final String HH_MM_SS = "HHmmss";
    private TongLianIsvConfigDAO config;
    private final PrivateKey privateKey;
    private final PublicKey tlPublicKey;
    // TODO 待处理url管理
//    public static final String URL = "http://116.228.64.55:28082/yst-service-api/tx/handle";

    public TongLianClient(TongLianIsvConfigDAO config) {
        this.config = config;
        this.privateKey = DemoSM2Util.privKeySM2FromBase64Str(config.getPrivateKeyStr());
        this.tlPublicKey = DemoSM2Util.pubKeySM2FromBase64Str(config.getAllinPayPublicKeyStr());
    }


    public Response sendRequest(SendBuild sendBuild, String url) {
        JsonNode readTree = JsonUtil.readTree(JsonUtil.toJson(sendBuild.getParams()));
        Request request = new Request();
        request.setAppId(this.config.getAppId());
        request.setSpAppId(this.config.getSpAppId());
        request.setTransCode(sendBuild.getTransCode());
        request.setFormat("json");
        request.setCharset("UTF-8");
        request.setTransDate(new SimpleDateFormat(YYYY_MM_DD).format(new Date()));
        request.setTransTime(new SimpleDateFormat(HH_MM_SS).format(new Date()));
        request.setVersion("1.0");
        request.setBizData(readTree.toString());
        String signedValue = DemoSM2Util.jsonMapToStr(JsonUtil.toMap(JsonUtil.toJson(request)));
        String sign = DemoSM2Util.sign(this.privateKey, signedValue);
        request.setSignType("SM3withSM2");
        request.setSign(sign);
        String requestJson = JsonUtil.readTree(JsonUtil.toJson(request)).toString();
        log.info("通联接口 request_code:{}  param:{}  待签名源串:{}", sendBuild.requestId, requestJson, signedValue);
        log.info("请求url：{}", url);
        String result = OkHttp.postJson(url, requestJson);
        verify(result);
        assert result != null;
        Response response = new Response();
        JsonNode jsonNode = JsonUtil.readTree(result);
        if ("00000".equals(jsonNode.get("code").asText())) {
            JsonNode bizData = JsonUtil.readTree(jsonNode.get("bizData").asText());
            List<String> successCode = Arrays.asList("66666", "00000");
            if (successCode.contains(bizData.get("respCode").asText())) {
                response.setSuccess(Boolean.TRUE);
            } else {
                log.error("通联接口调用失败 request_code:{}   response:{}", sendBuild.requestId, bizData);
                response.setSuccess(Boolean.FALSE);
                response.setErrorMsg(bizData.get("respMsg").asText());
                response.setRespCode(bizData.get("respCode").asText());
            }
            response.setResult(bizData);
        } else {
            response.setSuccess(Boolean.FALSE);
            response.setResult(jsonNode);
        }
        return response;
    }


    private void verify(String result) {
        Map<String, Object> map = JsonUtil.toMap(result);
        String sign = String.valueOf(map.remove("sign"));
        String signType = String.valueOf(map.remove("signType"));
        String srcSignMsg = DemoSM2Util.jsonMapToStr(map);
        try {
            Assert.isFalse(DemoSM2Util.verify(this.tlPublicKey, srcSignMsg, sign), "响应报文验签失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Request {
        private String appId;
        private String spAppId;
        private String transCode;
        private String transDate;
        private String transTime;
        private String format;
        private String charset;
        private String signType;
        private String sign;
        private String version;
        private String bizData;
    }

    @Data
    public static class Response {
        private Boolean success;
        private String respCode;
        private JsonNode result;
        private String errorMsg;

        public Boolean success() {
            return success;
        }

        public JsonNode get(String param) {
            return result.get(param);
        }
    }

    @Data
    @AllArgsConstructor
    public static class SendBuild {
        private Long requestId;
        private String transCode;

        private Map<String, Object> params;
    }
}
