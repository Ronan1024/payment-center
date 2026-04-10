package com.baosight.payment.channel.utils;

//import com.baosight.payment.system.utils.OkHttp;

import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.text.CharSequenceUtil;
import com.baosight.utils.utils.Assert;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Accessors(chain = true)
public class AllInPayClient {
    private static final String YYYY_MM_DD = "yyyyMMdd";
    private static final String HH_MM_SS = "HHmmss";
    private Config config;
    private PrivateKey privateKey;
    private PublicKey allInPublicKey;
    private String url;
    private String version;
    private String requestId;
    private Map<String, String> requestParam;
    private String transCode;

//    public static final String URL = "http://116.228.64.55:28082/yst-service-api/tm/handle";

    public AllInPayClient() {
        this.config = new Config();
        this.requestId = SnowflakeIdUtil.nextIdStr();
    }

    public AllInPayClient(Config config) {
        this.config = config;
        this.privateKey = privateKeySM2FromBase64Str(this.config.getPrivateKeyStr());
        this.allInPublicKey = pubKeySM2FromBase64Str(this.config.getAllinPayPublicKeyStr());
    }

    public AllInPayClient init(String publicKey, String url, String version) {
        this.config.setAllinPayPublicKeyStr(publicKey);
        this.allInPublicKey = pubKeySM2FromBase64Str(publicKey);
        this.url = url;
        this.version = version;
        return this;
    }

    public AllInPayClient setRequestParams(Map<String, String> requestParam) {
        this.requestParam = requestParam;
        return this;
    }


//
//    public AllInPayClient(String appId, String privateKey, String allinPayPublicKey) {
//        TongLianIsvConfigDAO tongLianIsvConfigDAO = new TongLianIsvConfigDAO();
//        tongLianIsvConfigDAO.setAppId(appId);
//        this.config = tongLianIsvConfigDAO;
//        this.privateKey = com.baosight.payment.access.tl.utils.DemoSM2Util.privKeySM2FromBase64Str(privateKey);
//        this.tlPublicKey = com.baosight.payment.access.tl.utils.DemoSM2Util.pubKeySM2FromBase64Str(allinPayPublicKey);
//    }


    public Response sendRequest(String transCode) {
        Request request = new Request();
        request.setAppId(this.config.getAppId());
        request.setSpAppId("");
        request.setTransCode(transCode);
        request.setFormat("json");
        request.setCharset("UTF-8");
        request.setTransDate(new SimpleDateFormat(YYYY_MM_DD).format(new Date()));
        request.setTransTime(new SimpleDateFormat(HH_MM_SS).format(new Date()));
        request.setVersion("1.0");
        if (!requestParam.containsKey("reqTraceNum")) {
            requestParam.put("reqTraceNum", requestId);
        }
        request.setBizData(JsonUtil.toJson(requestParam));
        String signedValue = jsonMapToStr(JsonUtil.toMap(JsonUtil.toJson(request)));
        String sign = sign(this.privateKey, signedValue);
        request.setSignType("SM3withSM2");
        request.setSign(sign);
        String requestJson = JsonUtil.toJson(request);
        log.info("通联接口 request_code:{}  param:{}  待签名源串:{}", requestId, requestJson, signedValue);
        log.info("请求url：{}", url);
//        String result = OkHttp.postJson(url, requestJson);
        String result = "";
        verify(result);
        assert result != null;
        Response response = new Response();
        JsonNode jsonNode = JsonUtil.readTree(result);
        if (jsonNode.get("code").asText().equals("00000")) {
            JsonNode bizData = JsonUtil.readTree(jsonNode.get("bizData").asText());
            String respCode = bizData.get("respCode").asText();

            if (Arrays.asList("00000", "66666", "66667").contains(respCode)) {
                response.setSuccess(Boolean.TRUE);
            } else {
                log.error("通联接口调用失败 request_code:{}   response:{}", requestId, bizData);
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
        String srcSignMsg = jsonMapToStr(map);
        try {
            Assert.isFalse(verify(this.allInPublicKey, srcSignMsg, sign), "响应报文验签失败");
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

        private String params;

    }

    @Data
    public class Config {
        /**
         * 通联应用id
         */
        private String appId;
        /**
         * 通联私钥
         */
        private String privateKeyStr;

        /**
         * 通联公钥
         */
        private String allinPayPublicKeyStr;

        /**
         * 版本号
         */
        private String version;
    }


    /**
     * 从字符串读取私钥-目前支持PKCS8(keyStr为BASE64格式)
     */
    private static PrivateKey privateKeySM2FromBase64Str(String keyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(keyStr)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从字符串读取RSA公钥(keyStr)
     */
    private static PublicKey pubKeySM2FromBase64Str(String keyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(keyStr)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static String jsonMapToStr(Map<String, Object> map) {
        String[] keys = map.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder raw = new StringBuilder();
        for (String key : keys) {
            if (!CharSequenceUtil.isEmpty(String.valueOf(map.get(key)))) {
                raw.append(key).append("=").append(map.get(key)).append("&");
            }
        }

        if (!raw.isEmpty()) {
            raw.deleteCharAt(raw.length() - 1);
        }
        return raw.toString();
    }

    private static String sign(PrivateKey privateKey, String text) {
        try {
            Signature signature = Signature.getInstance("SM3withSM2", "BC");
            signature.initSign(privateKey);
            byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
            signature.update(plainText);
            byte[] signatureValue = signature.sign();
            return Base64.getEncoder().encodeToString(signatureValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean verify(PublicKey publicKey, String text, String sign) throws Exception {
        if (!StringUtils.hasText(sign)) {
            return false;
        }
        Signature signature = Signature.getInstance("SM3withSM2", "BC");
        signature.initVerify(publicKey);
        signature.update(text.getBytes(StandardCharsets.UTF_8));
        return signature.verify(Base64.getDecoder().decode(sign));
    }
}
