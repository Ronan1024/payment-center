package com.baosight.payment.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.system.pojo.dto.PayInterfaceTypeDTO;
import com.baosight.payment.system.pojo.dto.PayInterfaceTypePageDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceType;
import com.baosight.payment.system.pojo.vo.PayInterfaceTypeVO;

import java.util.List;

/**
 * <p>
 * 接口类型配置表 服务类
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2025-12-16
 */
public interface PayInterfaceTypeService extends IService<PayInterfaceType> {

    /**
     * 获取接口类型分页列表
     * @return
     */
    PageResponse<PayInterfaceTypeVO> payInterfaceTypePage(PayInterfaceTypePageDTO pageDTO);


    /**
     * 新增接口分类类型
     * @return
     */
    Boolean savePayInterfaceType(PayInterfaceTypeDTO payInterfaceTypeDTO);


    /**
     * 修改接口分类类型
     * @return
     */
    Boolean updatePayInterfaceType(PayInterfaceTypeDTO payInterfaceTypeDTO);


    /**
     * 删除接口分类类型
     * @return
     */
    Boolean deletePayInterfaceType(Long id);


    /**
     * 根据ID获取接口分类类型详情
     * @return
     */
    PayInterfaceTypeVO getInfoById(Long id);

    /**
     * 获取接口类型列表(全部数据)
     * @return
     */
    List<PayInterfaceTypeVO> payInterfaceTypeList();
}
