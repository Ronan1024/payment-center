package com.baosight.payment.isv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.isv.pojo.dto.CreateIsvDTO;
import com.baosight.payment.isv.pojo.dto.IsvPageDTO;
import com.baosight.payment.isv.pojo.entity.PayIsvInfo;
import com.baosight.payment.isv.pojo.vo.PayIsvInfoVO;
import com.baosight.payment.isv.pojo.vo.PayIsvPageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_isv_info(服务商信息表)】的数据库操作Service
 * @createDate 2025-03-15 19:50:10
 */
public interface PayIsvInfoService extends IService<PayIsvInfo> {

    /**
     * 获取服务商列表
     *
     * @param isvPageDTO 服务商列表请求参数
     * @return 服务商列表信息
     */
    PageResponse<PayIsvPageVO> isvPage(IsvPageDTO isvPageDTO);

    /**
     * 获取服务商信息详情
     *
     * @param id 服务商id
     * @return 服务商详情
     */
    @Transactional
    PayIsvInfoVO info(Long id);

    /**
     * 新增服务商信息
     *
     * @param createIsvDTO 服务商请求信息
     * @param clientType 创建客户端类型
     */
    Boolean createIsv(CreateIsvDTO createIsvDTO, PayClientType clientType);

    /**
     * 禁用｜启用 服务商
     *
     * @param isvId 服务商id
     */
    Boolean enable(Long isvId);

    /**
     * 获取商户信息
     *
     * @param id id
     * @return boolean
     */
    PayIsvInfo infoById(Long id);

    /**
     * 根据id 列表获取服务商信息
     */
    List<PayIsvInfo> infoByIdList(List<Long> isvIdList);

    /**
     * 获取所有服务商列表
     */
    List<PayIsvPageVO> isvList();

    /**
     * 根据租户ID获取服务商信息
     * @param id
     * @return
     */
    PayIsvInfoVO tenantIsvInfo(Long id);
}
