package com.baosight.payment.system.validator;

import com.baosight.payment.system.enums.FieldType;
import com.baosight.payment.system.enums.ValidationType;
import com.baosight.payment.system.pojo.dto.dynamicform.FormData;
import com.baosight.payment.system.pojo.dto.dynamicform.ValidationResult;
import com.baosight.payment.system.pojo.entity.dynamicform.FieldDefinition;
import com.baosight.payment.system.pojo.entity.dynamicform.FormSchema;
import com.baosight.payment.system.pojo.entity.dynamicform.ValidationRule;
import com.baosight.utils.json.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 表单验证器
 * 负责验证表单数据是否符合表单模式定义的规则
 *
 * @author Dynamic Form Tool
 */
@Slf4j
@Component
public class FormValidator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * 验证表单数据
     *
     * @param formData        表单数据
     * @param formSchema      表单模式
     * @param fieldDefinitions 字段定义列表
     * @param validationRules  验证规则列表（按字段定义ID分组）
     * @return 验证结果
     */
    public ValidationResult validate(FormData formData, FormSchema formSchema,
                                      List<FieldDefinition> fieldDefinitions,
                                      Map<Long, List<ValidationRule>> validationRules) {
        ValidationResult result = ValidationResult.success();

        if (formData == null || fieldDefinitions == null || fieldDefinitions.isEmpty()) {
            return result;
        }

        for (FieldDefinition fieldDef : fieldDefinitions) {
            validateField(formData, fieldDef, validationRules.get(fieldDef.getId()), result);
        }

        return result;
    }

    /**
     * 验证单个字段
     */
    private void validateField(FormData formData, FieldDefinition fieldDef,
                                List<ValidationRule> rules, ValidationResult result) {
        String fieldName = fieldDef.getFieldName();
        Object fieldValue = formData.getFieldValue(fieldName);

        // 验证必填字段
        if (Boolean.TRUE.equals(fieldDef.getRequired())) {
            if (!validateRequired(fieldValue)) {
                result.addError(fieldName, String.format("字段 %s 为必填项", fieldDef.getLabel() != null ? fieldDef.getLabel() : fieldName));
                return; // 必填验证失败，不再进行其他验证
            }
        }

        // 如果字段值为空且非必填，跳过其他验证
        if (isEmpty(fieldValue)) {
            return;
        }

        // 验证字段类型
        if (!validateFieldType(fieldValue, fieldDef.getFieldType(), fieldName, result)) {
            return; // 类型验证失败，不再进行其他验证
        }

        // 应用验证规则
        if (rules != null && !rules.isEmpty()) {
            for (ValidationRule rule : rules) {
                applyValidationRule(fieldValue, fieldDef, rule, result);
            }
        }
    }

    /**
     * 验证必填字段
     */
    private boolean validateRequired(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            return StringUtils.hasText((String) value);
        }
        return true;
    }

    /**
     * 检查值是否为空
     */
    private boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return !StringUtils.hasText((String) value);
        }
        return false;
    }

    /**
     * 验证字段类型
     */
    private boolean validateFieldType(Object value, String fieldTypeCode, String fieldName, ValidationResult result) {
        try {
            FieldType fieldType = FieldType.fromCode(fieldTypeCode);

            switch (fieldType) {
                case TEXT:
                    if (!(value instanceof String)) {
                        result.addError(fieldName, String.format("字段 %s 必须是文本类型", fieldName));
                        return false;
                    }
                    break;

                case NUMBER:
                    if (!isNumeric(value)) {
                        result.addError(fieldName, String.format("字段 %s 必须是数字类型", fieldName));
                        return false;
                    }
                    break;

                case DATE:
                    if (!isValidDate(value)) {
                        result.addError(fieldName, String.format("字段 %s 必须是有效的日期格式 (yyyy-MM-dd)", fieldName));
                        return false;
                    }
                    break;

                case BOOLEAN:
                    if (!(value instanceof Boolean)) {
                        result.addError(fieldName, String.format("字段 %s 必须是布尔类型", fieldName));
                        return false;
                    }
                    break;

                case RADIO:
                case CHECKBOX:
                case FILE:
                    // 这些类型的验证较为灵活，暂不做严格类型检查
                    break;

                default:
                    log.warn("Unknown field type: {}", fieldTypeCode);
            }
        } catch (IllegalArgumentException e) {
            log.error("Invalid field type code: {}", fieldTypeCode, e);
            result.addError(fieldName, String.format("字段 %s 的类型配置无效", fieldName));
            return false;
        }

        return true;
    }

    /**
     * 应用验证规则
     */
    private void applyValidationRule(Object value, FieldDefinition fieldDef,
                                      ValidationRule rule, ValidationResult result) {
        String fieldName = fieldDef.getFieldName();
        String ruleTypeCode = rule.getRuleType();

        try {
            ValidationType validationType = ValidationType.fromCode(ruleTypeCode);
            Map<String, Object> ruleParams = parseRuleParams(rule.getRuleParams());

            switch (validationType) {
                case REQUIRED:
                    // 必填验证已在前面处理
                    break;

                case MIN_LENGTH:
                    validateMinLength(value, fieldName, ruleParams, rule.getErrorMessage(), result);
                    break;

                case MAX_LENGTH:
                    validateMaxLength(value, fieldName, ruleParams, rule.getErrorMessage(), result);
                    break;

                case REGEX:
                    validateRegex(value, fieldName, ruleParams, rule.getErrorMessage(), result);
                    break;

                case MIN_VALUE:
                    validateMinValue(value, fieldName, ruleParams, rule.getErrorMessage(), result);
                    break;

                case MAX_VALUE:
                    validateMaxValue(value, fieldName, ruleParams, rule.getErrorMessage(), result);
                    break;

                case MIN_DATE:
                    validateMinDate(value, fieldName, ruleParams, rule.getErrorMessage(), result);
                    break;

                case MAX_DATE:
                    validateMaxDate(value, fieldName, ruleParams, rule.getErrorMessage(), result);
                    break;

                default:
                    log.warn("Unknown validation type: {}", ruleTypeCode);
            }
        } catch (IllegalArgumentException e) {
            log.error("Invalid validation type code: {}", ruleTypeCode, e);
        }
    }

    /**
     * 验证最小长度
     */
    private void validateMinLength(Object value, String fieldName, Map<String, Object> params,
                                     String errorMessage, ValidationResult result) {
        if (!(value instanceof String)) {
            return;
        }

        String strValue = (String) value;
        Integer minLength = getIntParam(params, "minLength");

        if (minLength != null && strValue.length() < minLength) {
            String message = StringUtils.hasText(errorMessage)
                    ? errorMessage
                    : String.format("字段 %s 的长度不能小于 %d", fieldName, minLength);
            result.addError(fieldName, message);
        }
    }

    /**
     * 验证最大长度
     */
    private void validateMaxLength(Object value, String fieldName, Map<String, Object> params,
                                     String errorMessage, ValidationResult result) {
        if (!(value instanceof String)) {
            return;
        }

        String strValue = (String) value;
        Integer maxLength = getIntParam(params, "maxLength");

        if (maxLength != null && strValue.length() > maxLength) {
            String message = StringUtils.hasText(errorMessage)
                    ? errorMessage
                    : String.format("字段 %s 的长度不能大于 %d", fieldName, maxLength);
            result.addError(fieldName, message);
        }
    }

    /**
     * 验证正则表达式
     */
    private void validateRegex(Object value, String fieldName, Map<String, Object> params,
                                String errorMessage, ValidationResult result) {
        if (!(value instanceof String)) {
            return;
        }

        String strValue = (String) value;
        String pattern = getStringParam(params, "pattern");

        if (pattern != null) {
            try {
                if (!Pattern.matches(pattern, strValue)) {
                    String message = StringUtils.hasText(errorMessage)
                            ? errorMessage
                            : String.format("字段 %s 的格式不正确", fieldName);
                    result.addError(fieldName, message);
                }
            } catch (PatternSyntaxException e) {
                log.error("Invalid regex pattern: {}", pattern, e);
                result.addError(fieldName, String.format("字段 %s 的正则表达式配置无效", fieldName));
            }
        }
    }

    /**
     * 验证最小值
     */
    private void validateMinValue(Object value, String fieldName, Map<String, Object> params,
                                    String errorMessage, ValidationResult result) {
        if (!isNumeric(value)) {
            return;
        }

        BigDecimal numValue = toBigDecimal(value);
        BigDecimal minValue = getBigDecimalParam(params, "minValue");

        if (minValue != null && numValue.compareTo(minValue) < 0) {
            String message = StringUtils.hasText(errorMessage)
                    ? errorMessage
                    : String.format("字段 %s 的值不能小于 %s", fieldName, minValue);
            result.addError(fieldName, message);
        }
    }

    /**
     * 验证最大值
     */
    private void validateMaxValue(Object value, String fieldName, Map<String, Object> params,
                                    String errorMessage, ValidationResult result) {
        if (!isNumeric(value)) {
            return;
        }

        BigDecimal numValue = toBigDecimal(value);
        BigDecimal maxValue = getBigDecimalParam(params, "maxValue");

        if (maxValue != null && numValue.compareTo(maxValue) > 0) {
            String message = StringUtils.hasText(errorMessage)
                    ? errorMessage
                    : String.format("字段 %s 的值不能大于 %s", fieldName, maxValue);
            result.addError(fieldName, message);
        }
    }

    /**
     * 验证最小日期
     */
    private void validateMinDate(Object value, String fieldName, Map<String, Object> params,
                                   String errorMessage, ValidationResult result) {
        LocalDate date = parseDate(value);
        if (date == null) {
            return;
        }

        String minDateStr = getStringParam(params, "minDate");
        if (minDateStr != null) {
            LocalDate minDate = parseDate(minDateStr);
            if (minDate != null && date.isBefore(minDate)) {
                String message = StringUtils.hasText(errorMessage)
                        ? errorMessage
                        : String.format("字段 %s 的日期不能早于 %s", fieldName, minDateStr);
                result.addError(fieldName, message);
            }
        }
    }

    /**
     * 验证最大日期
     */
    private void validateMaxDate(Object value, String fieldName, Map<String, Object> params,
                                   String errorMessage, ValidationResult result) {
        LocalDate date = parseDate(value);
        if (date == null) {
            return;
        }

        String maxDateStr = getStringParam(params, "maxDate");
        if (maxDateStr != null) {
            LocalDate maxDate = parseDate(maxDateStr);
            if (maxDate != null && date.isAfter(maxDate)) {
                String message = StringUtils.hasText(errorMessage)
                        ? errorMessage
                        : String.format("字段 %s 的日期不能晚于 %s", fieldName, maxDateStr);
                result.addError(fieldName, message);
            }
        }
    }

    // ========== 辅助方法 ==========

    /**
     * 解析规则参数
     */
    private Map<String, Object> parseRuleParams(String ruleParams) {
        if (!StringUtils.hasText(ruleParams)) {
            return Map.of();
        }

        try {
            return JsonUtil.parse(ruleParams, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            log.error("Failed to parse rule params: {}", ruleParams, e);
            return Map.of();
        }
    }

    /**
     * 检查是否为数字类型
     */
    private boolean isNumeric(Object value) {
        if (value instanceof Number) {
            return true;
        }
        if (value instanceof String) {
            try {
                new BigDecimal((String) value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 转换为 BigDecimal
     */
    private BigDecimal toBigDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }
        if (value instanceof String) {
            try {
                return new BigDecimal((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 检查是否为有效日期
     */
    private boolean isValidDate(Object value) {
        return parseDate(value) != null;
    }

    /**
     * 解析日期
     */
    private LocalDate parseDate(Object value) {
        if (value instanceof LocalDate) {
            return (LocalDate) value;
        }
        if (value instanceof String) {
            try {
                return LocalDate.parse((String) value, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取整数参数
     */
    private Integer getIntParam(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取字符串参数
     */
    private String getStringParam(Map<String, Object> params, String key) {
        Object value = params.get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * 获取 BigDecimal 参数
     */
    private BigDecimal getBigDecimalParam(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }
        if (value instanceof String) {
            try {
                return new BigDecimal((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
