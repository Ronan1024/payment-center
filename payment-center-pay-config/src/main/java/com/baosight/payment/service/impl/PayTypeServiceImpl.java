package com.baosight.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.mapper.PayTypeMapper;
import com.baosight.payment.pojo.entity.PayType;
import com.baosight.payment.service.PayTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付类型表 服务实现类
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2025-12-18
 */
@Service
public class PayTypeServiceImpl extends ServiceImpl<PayTypeMapper, PayType> implements PayTypeService {

}
