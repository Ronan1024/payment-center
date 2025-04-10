package com.baosight.payment.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.enums.TongLianInterfaceCode;
import com.baosight.payment.system.tonglian.TongLianClient;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @TableName request_interface_record
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "request_interface_record")
public class RequestInterfaceRecord extends BasePO {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 接口号
     */
    private String interfaceId;

    /**
     * 支付机构
     */
    private String payAgency;

    /**
     * 请求参数
     */
    private String request;

    /**
     * 返回结果
     */
    private String response;

    /**
     * 是否成功
     */
    private Boolean success;

    public static RequestInterfaceRecord tongLianInit(String request, TongLianClient.Response response) {
        RequestInterfaceRecord requestInterfaceRecord = new RequestInterfaceRecord();
        requestInterfaceRecord.setRequest(request);
        requestInterfaceRecord.setSuccess(response.getSuccess());
        requestInterfaceRecord.setPayAgency(PayingAgency.TONG_LIAN.getCode());
        requestInterfaceRecord.setInterfaceId(TongLianInterfaceCode.BIND_SYB.getCode());
        requestInterfaceRecord.setResponse(response.getResult().toString());
        return requestInterfaceRecord;
    }
}