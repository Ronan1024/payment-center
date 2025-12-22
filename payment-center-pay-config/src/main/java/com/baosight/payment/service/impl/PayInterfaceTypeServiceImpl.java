package com.baosight.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.utils.utils.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baosight.database.core.page.PageResponse;
import com.baosight.database.core.page.PageUtil;
import com.baosight.payment.convert.PayInterfaceTypeConvert;
import com.baosight.payment.mapper.PayInterfaceTypeMapper;
import com.baosight.payment.pojo.dto.PayInterfaceTypeDTO;
import com.baosight.payment.pojo.dto.PayInterfaceTypePageDTO;
import com.baosight.payment.pojo.entity.PayInterfaceType;
import com.baosight.payment.pojo.vo.PayInterfaceTypeVO;
import com.baosight.payment.service.PayInterfaceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 接口类型配置表 服务实现类
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2025-12-16
 */
@Service
@RequiredArgsConstructor
public class PayInterfaceTypeServiceImpl extends ServiceImpl<PayInterfaceTypeMapper, PayInterfaceType> implements PayInterfaceTypeService {

    private final PayInterfaceTypeMapper payInterfaceTypeMapper;

    /**
     * 获取接口类型分页列表
     * @param pageDTO
     * @return
     */
    @Override
    public PageResponse<PayInterfaceTypeVO> payInterfaceTypePage(PayInterfaceTypePageDTO pageDTO) {
        PageUtil<PayInterfaceTypeVO> pageUtil = new PageUtil<>(pageDTO);
        PageResponse<PayInterfaceTypeVO> build = pageUtil.builder(payInterfaceTypeMapper.page(pageUtil.Page(), pageDTO)).build();
        return build;
    }


    /**
     * 新增接口分类类型
     * @param payInterfaceTypeDTO
     * @return
     */
    @Override
    public Boolean savePayInterfaceType(PayInterfaceTypeDTO payInterfaceTypeDTO) {
        PayInterfaceType payInterfaceType = PayInterfaceTypeConvert.INSTANCE.toEntity(payInterfaceTypeDTO);
        Long count = payInterfaceTypeMapper.selectCount(new LambdaQueryWrapper<PayInterfaceType>()
                .eq(PayInterfaceType::getInterfaceTypeCode, payInterfaceType.getInterfaceTypeCode()));
        Assert.isTrue(count > 0, "接口类型code已存在");
        return payInterfaceTypeMapper.insert(payInterfaceType) > 0;
    }


    /**
     * 修改接口分类类型
     * @param payInterfaceTypeDTO
     * @return
     */
    @Override
    public Boolean updatePayInterfaceType(PayInterfaceTypeDTO payInterfaceTypeDTO) {
        PayInterfaceType payInterfaceType = payInterfaceTypeMapper.selectById(payInterfaceTypeDTO.getId());
        Assert.isNull(payInterfaceType,"接口类型不存在");
        Long count = payInterfaceTypeMapper.selectCount(new LambdaQueryWrapper<PayInterfaceType>()
                .eq(PayInterfaceType::getInterfaceTypeCode, payInterfaceType.getInterfaceTypeCode())
                .ne(PayInterfaceType::getId, payInterfaceType.getId()));
        Assert.isTrue(count > 0, "接口类型code已存在");
        BeanUtil.copyProperties(payInterfaceTypeDTO, payInterfaceType);
        return payInterfaceTypeMapper.updateById(payInterfaceType) > 0;
    }


    /**
     *删除接口分类类型
     * @param id
     * @return
     */
    @Override
    public Boolean deletePayInterfaceType(Long id) {
        PayInterfaceType payInterfaceType = payInterfaceTypeMapper.selectById(id);
        Assert.isNull(payInterfaceType,"接口类型不存在");
        return payInterfaceTypeMapper.deleteById(payInterfaceType) > 0;
    }


    /**
     *
     * @param id
     * @return
     */
    @Override
    public PayInterfaceTypeVO getInfoById(Long id) {
        PayInterfaceType payInterfaceType = payInterfaceTypeMapper.selectById(id);
        Assert.isNull(payInterfaceType,"接口类型不存在");
        PayInterfaceTypeVO payInterfaceTypeVO = PayInterfaceTypeConvert.INSTANCE.toVO(payInterfaceType);
        return payInterfaceTypeVO;
    }
}
