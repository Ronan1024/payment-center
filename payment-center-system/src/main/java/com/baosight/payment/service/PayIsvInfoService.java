package com.baosight.payment.service;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.pojo.dto.IsvPageDTO;
import com.baosight.payment.pojo.entity.PayIsvInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.pojo.vo.PayIsvInfoVO;
import com.baosight.payment.pojo.vo.PayIsvPageVO;

/**
* @author longjiangran
* @description 针对表【pay_isv_info(支付服务商信息表)】的数据库操作Service
* @createDate 2025-02-06 11:09:36
*/
public interface PayIsvInfoService extends IService<PayIsvInfo> {

    /**
     * 获取服务商列表
     * @param isvPageDTO 服务商列表请求参数
     * @return 服务商列表信息
     */
    PageResponse<PayIsvPageVO> isvPage(IsvPageDTO isvPageDTO);

    /**
     * 获取服务商信息详情
     * @param id 服务商id
     * @return 服务商详情
     */
    PayIsvInfoVO info(Long id);
}
