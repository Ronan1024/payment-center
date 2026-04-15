package com.baosight.payment.channel.utils;


import com.baosight.common.exception.ServiceException;
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
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

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
    private static final String BC_PROVIDER = "BC";

    static {
        if (Security.getProvider(BC_PROVIDER) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private final Config config;
    private PrivateKey privateKey;
    private PublicKey allInPublicKey;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 版本号
     */
    private String version;
    /**
     * 请求流水号
     */
    private String requestId;
    /**
     * 请求参数
     */
    private Map<String, String> requestParam;

    /**
     * 请求参数JSON
     */
    private String requestJson;
    /**
     * 接口编号
     */
    private String transCode;
    /**
     * 请求组件信息
     */
    private WebClient webClient;
    /**
     * 签名值
     */
    private String signedValue;

    public AllInPayClient() {
        this.config = new Config();
        this.requestId = SnowflakeIdUtil.nextIdStr();
    }

    public AllInPayClient(Config config) {
        this.config = config;
        this.privateKey = privateKeySM2FromBase64Str(this.config.getPrivateKeyStr());
        this.allInPublicKey = pubKeySM2FromBase64Str(this.config.getAllinPayPublicKeyStr());
    }

    public static AllInPayClient init(String publicKey, String appId, String url, String version) {
        AllInPayClient allInPayClient = new AllInPayClient();
        allInPayClient.config.setAllinPayPublicKeyStr(publicKey);
        allInPayClient.config.setAppId(appId);
        allInPayClient.allInPublicKey = pubKeySM2FromBase64Str(publicKey);
        allInPayClient.url = url;
        allInPayClient.version = version;
        allInPayClient.requestId = SnowflakeIdUtil.nextIdStr();
        return allInPayClient;
    }

    public void webClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void privateKey(String privateKey) {
        this.privateKey = privateKeySM2FromBase64Str(privateKey);
    }


    public void setRequestParams(Map<String, String> requestParam) {
        this.requestParam = requestParam;
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
        this.transCode = transCode;
        request.setAppId(this.config.getAppId());
        request.setSpAppId("");
        request.setTransCode(transCode);
        request.setTransDate(new SimpleDateFormat(YYYY_MM_DD).format(new Date()));
        request.setTransTime(new SimpleDateFormat(HH_MM_SS).format(new Date()));
        request.setVersion(version);
        if (!requestParam.containsKey("reqTraceNum")) {
            requestParam.put("reqTraceNum", requestId);
        }
        request.setBizData(JsonUtil.toJson(requestParam));
        this.signedValue = jsonMapToStr(JsonUtil.toMap(JsonUtil.toJson(request)));
        String sign = sign(this.privateKey, signedValue);
        request.setSign(sign);
        this.requestJson = JsonUtil.toJson(request);
        log.info("通联接口 request_code:{}  param:{}  待签名源串:{}", requestId, requestJson, signedValue);
        log.info("请求url：{}", url);

        if (ObjectUtils.isEmpty(webClient)) {
            throw new ServiceException("请求目标服务器组件未装配");
        }
        // 发送请求
        String result = webClient.post()
                .uri(url)
                .bodyValue(requestJson)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        verify(result);
        assert result != null;
        Response response = new Response();
        response.setRequest(requestJson);
        response.setRequestNo(response.requestNo);
        response.setUrl(url + "?transCode=" + transCode);
        JsonNode jsonNode = JsonUtil.readTree(result);
        if ("00000".equals(jsonNode.get("code").asText())) {
            JsonNode bizData = JsonUtil.readTree(jsonNode.get("bizData").asText());
            String respCode = bizData.get("respCode").asText();
            if (Arrays.asList("00000", "66666", "66667").contains(respCode)) {
                response.setSuccess(Boolean.TRUE);
            } else {
                log.error("通联接口调用失败 request_code:{}   response:{}", requestId, bizData);
                response.setSuccess(Boolean.FALSE);
                response.setErrorMsg(bizData.get("respMsg").asText());
            }
            response.setRespCode(bizData.get("respCode").asText());
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
        /**
         * 请求类型
         */
        private String format = "json";
        private String charset = "UTF-8";
        /**
         * 签名类型
         */
        private String signType = "SM3withSM2";
        /**
         * 签名值
         */
        private String sign;
        private String version;
        private String bizData;
    }

    @Data
    public static class Response {
        /**
         * 请求参数
         */
        private String request;
        /**
         * 是否成功
         */
        private Boolean success;
        /**
         * 机构响应编号
         */
        private String respCode;
        /**
         * 请求流水号
         */
        private Long requestNo;
        /**
         * 结果信息
         */
        private JsonNode result;

        /**
         * 异常信息
         */
        private String errorMsg;

        /**
         * 请求url
         */
        private String url;


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
            KeyFactory keyFactory = KeyFactory.getInstance("EC", BC_PROVIDER);
            byte[] privateKeyBytes = Base64.getDecoder().decode(normalizeKey(keyStr));
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (IllegalArgumentException e) {
            throw new ServiceException("通联私钥格式异常：请提供Base64编码PKCS8私钥");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从字符串读取RSA公钥(keyStr)
     */
    private static PublicKey pubKeySM2FromBase64Str(String keyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC", BC_PROVIDER);
            byte[] publicKeyBytes = Base64.getDecoder().decode(normalizeKey(keyStr));
            return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (IllegalArgumentException e) {
            throw new ServiceException("通联密钥信息异常：请提供Base64编码X509公钥");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
            throw new ServiceException("通联密钥信息异常：" + e.getMessage(), e);
        }
    }

    private static String normalizeKey(String key) {
        return key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
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

    /**
     * 通联签名处理
     *
     * @param privateKey 私钥
     * @param text       待签名文本
     * @return 签名结果
     */
    private String sign(PrivateKey privateKey, String text) {
        try {
            Signature signature = Signature.getInstance("SM3withSM2", "BC");
            if (ObjectUtils.isEmpty(privateKey)) {
                throw new ServiceException("通联密钥信息为空");
            }
            signature.initSign(privateKey);
            byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
            signature.update(plainText);
            byte[] signatureValue = signature.sign();
            return Base64.getEncoder().encodeToString(signatureValue);
        } catch (Exception e) {
            throw new ServiceException("通联签名异常：" + e.getMessage());
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
