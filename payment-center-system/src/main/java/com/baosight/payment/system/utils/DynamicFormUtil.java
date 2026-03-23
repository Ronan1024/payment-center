package com.baosight.payment.system.utils;

import com.baosight.payment.system.pojo.validation.InsertChannelConfigGroup;
import com.baosight.payment.system.pojo.validation.InsertChannelDefineGroup;
import com.baosight.web.core.exception.ApiException;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import static com.baosight.payment.system.error.PayInterfaceConfigError.FORM_FIELD_CANNOT_BE_EMPTY;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
public class DynamicFormUtil {


    /**
     * 验证表单是否为空
     *
     * @param sourceDynamicForm 原始定义的表单
     * @param verifyDynamicForm 需要进行校验的表单信息
     * @throws IllegalArgumentException 如果表单必填字段为空
     */
    public static void validateDynamicForm(List<DynamicForm> sourceDynamicForm, Map<String, String> verifyDynamicForm) {
        if (sourceDynamicForm == null || sourceDynamicForm.isEmpty()) {
            throw new IllegalArgumentException("Source dynamic form list cannot be null or empty");
        }
        if (CollectionUtils.isEmpty(verifyDynamicForm)) {
            throw new IllegalArgumentException("Verify dynamic form list cannot be null or empty");
        }
        for (DynamicForm sourceForm : sourceDynamicForm) {
            if (Boolean.TRUE.equals(sourceForm.getRequired())) {
                if (!verifyDynamicForm.containsKey(sourceForm.getName())) {
                    throw new ApiException(FORM_FIELD_CANNOT_BE_EMPTY, sourceForm.getName());
                }
                String formValue = verifyDynamicForm.get(sourceForm.getName());
                if (!StringUtils.hasText(formValue)) {
                    throw new ApiException(FORM_FIELD_CANNOT_BE_EMPTY, sourceForm.getName());
                }
            }
        }

    }


    /**
     * 动态表单
     */
    @Data
    public static class DynamicForm {
        /**
         * 表单名
         */
        @NotBlank(message = "字段名不能为空", groups = {InsertChannelDefineGroup.class, InsertChannelConfigGroup.class})
        private String name;


        /**
         * 排序
         */
        private Integer sort;

        /**
         * 表单值
         */
        @NotBlank(message = "参数值不能为空", groups = {InsertChannelConfigGroup.class})
        private String value;

        /**
         * 字段类型
         */
        @NotBlank(message = "字段类型不能为空", groups = {InsertChannelDefineGroup.class})
        private String type;

        /**
         * 是否必须
         */
        @NotBlank(message = "是否必填不能为空", groups = {InsertChannelDefineGroup.class})
        private Boolean required;

        /**
         * 字段描述
         */
        private String desc;
    }

}
