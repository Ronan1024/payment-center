package com.baosight.payment.system.mapper;

import com.baosight.payment.system.pojo.entity.PayMchPassage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【pay_mch_passage(商户支付通道表)】的数据库操作Mapper
* @createDate 2025-03-19 20:46:12
* @Entity com.baosight.payment.system.pojo.entity.PayMchPassage
*/
@Mapper
public interface PayMchPassageMapper extends BaseMapper<PayMchPassage> {

}




