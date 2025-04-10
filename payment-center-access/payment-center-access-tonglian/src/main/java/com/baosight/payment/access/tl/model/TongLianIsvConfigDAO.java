package com.baosight.payment.access.tl.model;

import com.baosight.utils.json.JsonUtil;
import lombok.Data;

import java.util.Map;

/**
 * @author L.J.Ran
 */
@Data
public class TongLianIsvConfigDAO {
    /**
     * 应用号
     */
    private String appId;

    private String spAppId;
    /**
     * 应用私钥
     */
    private String privateKeyStr;

    /**
     * 通联公钥
     */
    private String allinPayPublicKeyStr;

    /**
     * 密钥
     */
    private String secretKey;


    public static TongLianIsvConfigDAO parse(Map<String, String> map) {
        return JsonUtil.parse(JsonUtil.toJson(map), TongLianIsvConfigDAO.class);
    }
}
