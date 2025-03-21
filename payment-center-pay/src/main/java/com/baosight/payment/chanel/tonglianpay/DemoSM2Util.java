package com.baosight.payment.chanel.tonglianpay;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gejunqing
 * @version 1.0
 * @date 2024/1/11
 */
public class DemoSM2Util {
    /**
     * 算法常量:SM3withSM2
     */
    public static final String ALGORITHM_SM3SM2_BCPROV = "SM3withSM2";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String jsonMapToStr(Map<String, Object> map) {
        String[] keys = map.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder raw = new StringBuilder();
        for (String key : keys) {
            if (StringUtils.hasText(String.valueOf(map.get(key)))) {
                raw.append(key).append("=").append(map.get(key)).append("&");
            }
        }

        if (!raw.isEmpty()) {
            raw.deleteCharAt(raw.length() - 1);
        }
        return raw.toString();
    }

    /**
     * 从字符串读取私钥-目前支持PKCS8(keystr为BASE64格式)
     */
    public static PrivateKey privKeySM2FromBase64Str(String keystr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(keystr)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从字符串读取RSA公钥(keystr为BASE64格式)
     */
    public static PublicKey pubKeySM2FromBase64Str(String keystr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(keystr)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sign(PrivateKey privateKey, String text) {
        try {

            Signature signature = Signature.getInstance(ALGORITHM_SM3SM2_BCPROV, "BC");
            signature.initSign(privateKey);
            byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
            signature.update(plainText);
            byte[] signatureValue = signature.sign();
            return Base64.toBase64String(signatureValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(PublicKey publicKey, String text, String sign) throws Exception {
        if (isEmpty(sign)) {
            return false;
        }
        Signature signature = Signature.getInstance(ALGORITHM_SM3SM2_BCPROV, "BC");
        signature.initVerify(publicKey);
        signature.update(text.getBytes(StandardCharsets.UTF_8));
        byte[] signed = Base64.decode(sign);
        return signature.verify(signed);
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || "".equals(str.trim());
    }
}
