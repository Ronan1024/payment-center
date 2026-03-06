package com.baosight.payment.mch.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.mch.dao.entity.PayMchInfo;
import com.baosight.payment.mch.pojo.dto.MchPageDTO;
import com.baosight.payment.mch.pojo.vo.PayMchListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_info(支付服务商信息表)】的数据库操作Mapper
 * @createDate 2025-03-17 13:13:10
 * @Entity com.baosight.payment.pojo.entity.PayMchInfo
 */
@Mapper
public interface PayMchInfoMapper extends BaseMapper<PayMchInfo> {


    /**
     * 获取商户列表
     *
     * @param page    page
     * @param mchPage 商户列表请求体
     */
    IPage<PayMchListVO> page(@Param("page") Page<PayMchListVO> page, @Param("mchPage") MchPageDTO mchPage);

}




