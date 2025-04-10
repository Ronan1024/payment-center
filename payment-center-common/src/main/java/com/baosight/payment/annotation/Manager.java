package com.baosight.payment.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 通用逻辑管理层  及 中间件处理层
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/26
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Manager {

    @AliasFor(annotation = Component.class)
    String value() default "";
}

