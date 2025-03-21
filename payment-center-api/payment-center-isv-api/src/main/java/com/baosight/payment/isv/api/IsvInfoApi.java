package com.baosight.payment.isv.api;

import com.baosight.payment.isv.vo.IsvInfoVO;

import java.util.List;

/**
 * @program: payment-center
 * @description: 服务商接口查询
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
public interface IsvInfoApi {

    /**
     * 服务商信息列表
     *
     * @param isvIdList 服务商id列表
     */
    List<IsvInfoVO> isvInfoList(List<Long> isvIdList);


    /**
     * 获取服务商信息
     *
     * @param isvId 服务商id
     */
    IsvInfoVO isvInfoById(Long isvId);

}
