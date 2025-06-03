package com.baosight.payment.settlement.api;

import com.baosight.payment.settlement.dto.MchAccountDTO;
import com.baosight.payment.settlement.vo.MchAccountRecordVO;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/21
 */
public interface MchAccountApi {

    /**
     * 修改商家账户余额
     */
    Boolean changeMchAccount(List<MchAccountDTO> mchAccountDTOList);


    MchAccountRecordVO getMchAccountInfo(Long recordId);
}
