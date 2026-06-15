package com.baosight.payment.model;

import lombok.Data;

/**
 * 签约流程
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/15
 */
@Data
public class SignFlow {

    /**
     * 步骤编号
     */
    private String stepCode;


    /**
     * 步骤名称
     */
    private String stepName;

    /**
     * 步骤类型
     */
    private Integer type;


    /**
     * 接口编号
     */
    private String interfaceCode;

    /**
     * 前端组件 KEY
     */
    private String componentKey;


    /**
     * 失败处理
     */
    private Integer failHandler;

    /**
     * 流程排序
     */
    private Integer sort;
}
