package com.baosight.payment.system.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.payment.enums.PlatformConfigEnum;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统平台配置
 *
 * @TableName system_platform_configuration
 */
@TableName(value = "system_platform_configuration")
@Data
public class SystemPlatformConfiguration {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 配置类型
     */
    private String configType;

    /**
     * 配置参数值
     */
    private String configValue;

    /**
     * 更新人
     */
    private Long updateBy;


    private static final Map<String, Class<?>>  CONFIG_MAP = new HashMap<>();
    static {
        CONFIG_MAP.put(PlatformConfigEnum.ALL_IN.name(), AllIn.class);
    }

    public static Class<?> getConfiguration(String configType) {
        return CONFIG_MAP.get(configType);
    }


    /**
     * 通联配置
     */
    @Data
    public static class AllIn {
        /**
         * 请求URL
         */
        private String requestUrl;
        /**
         * 通用公钥
         */
        private String publicKey;

        /**
         * 基础回调URL
         */
        private String baseCallUrl;
    }
}