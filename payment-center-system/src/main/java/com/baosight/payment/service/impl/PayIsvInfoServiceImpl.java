package com.baosight.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.page.PageResponse;
import com.baosight.database.utils.PageUtil;
import com.baosight.payment.convert.PayIsvInfoConvert;
import com.baosight.payment.pojo.dto.IsvPageDTO;
import com.baosight.payment.pojo.entity.PayIsvInfo;
import com.baosight.payment.pojo.vo.PayIsvInfoVO;
import com.baosight.payment.pojo.vo.PayIsvPageVO;
import com.baosight.payment.service.PayIsvInfoService;
import com.baosight.payment.mapper.PayIsvInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author longjiangran
 * @description 针对表【pay_isv_info(支付服务商信息表)】的数据库操作Service实现
 * @createDate 2025-02-06 11:09:36
 */
@Service
@RequiredArgsConstructor
public class PayIsvInfoServiceImpl extends ServiceImpl<PayIsvInfoMapper, PayIsvInfo> implements PayIsvInfoService {

    private final PayIsvInfoMapper payIsvInfoMapper;

    /**
     * 获取服务商列表
     *
     * @param isvPageDTO 服务商列表请求参数
     * @return 服务商列表信息
     */
    @Override
    public PageResponse<PayIsvPageVO> isvPage(IsvPageDTO isvPageDTO) {
        PageUtil<PayIsvPageVO> pageUtil = new PageUtil<>(isvPageDTO);
        return pageUtil.builder(payIsvInfoMapper.page(pageUtil.Page(), isvPageDTO)).build();
    }

    /**
     * 获取服务商信息详情
     *
     * @param id 服务商id
     * @return 服务商详情
     */
    @Override
    public PayIsvInfoVO info(Long id) {
        PayIsvInfo payIsvInfo = payIsvInfoMapper.selectById(id);
        if (payIsvInfo == null) {
            return null;
        }
        return PayIsvInfoConvert.INSTANCE.toPayIsvInfoVO(payIsvInfo);
    }
}




