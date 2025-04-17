package com.baosight.payment.settlement.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.settlement.dto.CreateSettlementRequestDTO;
import com.baosight.payment.settlement.pojo.entity.SettlementRequest;

/**
 * @author longjiangran
 * @description 针对表【settlement_request(结算受理单)】的数据库操作Service
 * @createDate 2025-04-16 10:44:26
 */
public interface SettlementRequestManager extends IService<SettlementRequest> {

    /**
     * 创建结算受理
     * @param createSettlementRequest 结算受理请求
     */
    Boolean createSettlementRequest(CreateSettlementRequestDTO createSettlementRequest);
}
