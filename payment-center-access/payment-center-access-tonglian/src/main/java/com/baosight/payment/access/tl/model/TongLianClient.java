package com.baosight.payment.access.tl.model;

import com.baosight.payment.access.tl.utils.DemoSM2Util;
import com.baosight.payment.access.tl.utils.OkHttp;
import com.baosight.utils.exception.AbstractException;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.utils.Assert;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TongLianClient {
    private static final String YYYY_MM_DD = "yyyyMMdd";
    private static final String HH_MM_SS = "HHmmss";
    private final TongLianIsvConfigDAO config;
    private final PrivateKey privateKey;
    private final PublicKey tlPublicKey;
//    public static final String URL = "http://116.228.64.55:28082/yst-service-api/tx/handle";

    public TongLianClient(TongLianIsvConfigDAO config) {
        this.config = config;
        this.privateKey = DemoSM2Util.privKeySM2FromBase64Str(config.getPrivateKeyStr());
        this.tlPublicKey = DemoSM2Util.pubKeySM2FromBase64Str(config.getAllinPayPublicKeyStr());
    }

    public TongLianClient(String appId, String privateKey, String allinPayPublicKey) {
        TongLianIsvConfigDAO tongLianIsvConfigDAO = new TongLianIsvConfigDAO();
        tongLianIsvConfigDAO.setAppId(appId);
        this.config = tongLianIsvConfigDAO;
        this.privateKey = DemoSM2Util.privKeySM2FromBase64Str(privateKey);
        this.tlPublicKey = DemoSM2Util.pubKeySM2FromBase64Str(allinPayPublicKey);
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
        request.setBizData(String.valueOf(readTree));
        String signedValue = DemoSM2Util.jsonMapToStr(JsonUtil.toMap(JsonUtil.toJson(request)));
        String sign = DemoSM2Util.sign(this.privateKey, signedValue);
        request.setSignType("SM3withSM2");
        request.setSign(sign);
        String requestJson = JsonUtil.readTree(JsonUtil.toJson(request)).toString();
        String result = OkHttp.postJson(url, requestJson);
        verify(result);
        assert result != null;
        Response response = new Response();
        JsonNode jsonNode = JsonUtil.readTree(result);
        if (jsonNode.get("code").asText().equals("00000")) {
            JsonNode bizData = JsonUtil.readTree(jsonNode.get("bizData").asText());
            List<String> successCode = Arrays.asList("66666", "00000");
            if (successCode.contains(bizData.get("respCode").asText())) {
                response.setSuccess(Boolean.TRUE);
            } else {
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

    public List<String> download(SendBuild sendBuild, String url, String srcMsg) {
        // 加签开始
        String sign = DemoSM2Util.sign(privateKey, srcMsg);
        // 加签结束
        sendBuild.params.put("sign", sign);
        try (InputStream inputStream = OkHttp.postForm(url, sendBuild.getParams()).byteStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines().skip(1).toList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AbstractException("通联文件下载失败");
        }

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
    public static class SendBuild {
        private Long requestId;
        private String transCode;

        private Map<String, Object> params;
        private String paramsStr;

        public SendBuild(Long requestId, String transCode, Map<String, Object> params) {
            this.params = params;
            this.transCode = transCode;
            this.requestId = requestId;
            this.paramsStr = JsonUtil.toJson(params);
        }

        public SendBuild(Long requestId, String transCode, String params) {
            this.params = JsonUtil.toMap(params);
            this.transCode = transCode;
            this.requestId = requestId;
            this.paramsStr = params;
        }
    }
}
