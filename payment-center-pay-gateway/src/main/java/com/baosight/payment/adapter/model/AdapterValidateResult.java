package com.baosight.payment.adapter.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * adapterKey 校验结果。
 */
@Data
@Builder
public class AdapterValidateResult {
    /**
     * 是否校验通过。
     */
    private boolean valid;
    /**
     * 被校验的适配器唯一键。
     */
    private String adapterKey;
    /**
     * 未通过时的错误原因列表。
     */
    private List<String> errors;

    /**
     * 构造通过结果。
     */
    public static AdapterValidateResult success(String adapterKey) {
        return AdapterValidateResult.builder()
                .valid(true)
                .adapterKey(adapterKey)
                .errors(new ArrayList<>())
                .build();
    }

    /**
     * 构造失败结果。
     */
    public static AdapterValidateResult fail(String adapterKey, List<String> errors) {
        return AdapterValidateResult.builder()
                .valid(false)
                .adapterKey(adapterKey)
                .errors(errors)
                .build();
    }
}
