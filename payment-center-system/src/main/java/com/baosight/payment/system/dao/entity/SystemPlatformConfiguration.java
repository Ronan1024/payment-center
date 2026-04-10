package com.baosight.payment.system.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.payment.enums.PlatformConfigEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统平台配置
 *
 * @author L.J.Ran
 * @TableName system_platform_configuration
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_platform_configuration")
public class SystemPlatformConfiguration extends BasePO {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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


    private static final Map<String, Class<?>> CONFIG_MAP = new HashMap<>();

    static {
        CONFIG_MAP.put(PlatformConfigEnum.ALL_IN.getCode(), AllIn.class);
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
         * 会员类请求url
         */
        private String memberRequestUrl;

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