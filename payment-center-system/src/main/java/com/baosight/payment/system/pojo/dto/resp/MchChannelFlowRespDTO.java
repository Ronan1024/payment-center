package com.baosight.payment.system.pojo.dto.resp;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
@Data
public class MchChannelFlowRespDTO {

    /**
     * 渠道流程名称
     */
    private String name;


    /**
     * 当前执行id
     */
    private Integer index;
    /**
     * 类型
     */
    private String stepType;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 处理时间
     */
    private Date handleTime;
    /**
     * 处理结果
     */
    private String handleResult;

}
