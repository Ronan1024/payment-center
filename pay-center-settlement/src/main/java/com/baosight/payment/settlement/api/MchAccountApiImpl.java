package com.baosight.payment.settlement.api;

import cn.hutool.core.math.Money;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.payment.settlement.convert.MchAccountRecordConvert;
import com.baosight.payment.settlement.dto.MchAccountDTO;
import com.baosight.payment.settlement.enums.AppleState;
import com.baosight.payment.settlement.manager.MchAccountManager;
import com.baosight.payment.settlement.manager.MchAccountRecordManager;
import com.baosight.payment.settlement.pojo.entity.MchAccount;
import com.baosight.payment.settlement.pojo.entity.MchAccountRecord;
import com.baosight.payment.settlement.vo.MchAccountRecordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/21
 */
@Component
@RequiredArgsConstructor
public class MchAccountApiImpl implements MchAccountApi {

    private final MchAccountRecordManager mchAccountRecordManager;
    private final MchAccountManager accountManager;


    @Override
    public Boolean changeMchAccount(List<MchAccountDTO> mchAccountDTOList) {
        mchAccountDTOList.forEach(e -> {
            MchAccount mchAccount = accountManager.getOne(new LambdaQueryWrapper<MchAccount>()
                    .eq(MchAccount::getMchId, e.getMchId()));
            if (ObjectUtils.isEmpty(mchAccount)) {
                mchAccount = new MchAccount();
                mchAccount.setMchId(e.getMchId());
                mchAccount.setBalance(new BigDecimal("0"));
                accountManager.save(mchAccount);
            }
            AtomicReference<Money> money = new AtomicReference<>(new Money());
            List<MchAccountRecord> accountRecordList = e.getAmount().stream().map(amount -> {
                MchAccountRecord mchAccountRecord = new MchAccountRecord();
                mchAccountRecord.setMchId(e.getMchId());
                mchAccountRecord.setType(e.getType());
                mchAccountRecord.setCreateTime(new Date());
                mchAccountRecord.setAmount(amount.getAmount());
                mchAccountRecord.setApplyState(AppleState.SUCCESS.getCode());
                return mchAccountRecord;}).toList();
            e.getAmount().forEach(amount -> money.set(money.get().add(amount)));

            mchAccountRecordManager.saveBatch(accountRecordList);
            Money balance = new Money(mchAccount.getBalance());
            mchAccount.setBalance(balance.add(money.get()).getAmount());
            accountManager.updateById(mchAccount);
        });

        return Boolean.TRUE;

    }

    @Override
    public MchAccountRecordVO getMchAccountInfo(Long recordId) {
        MchAccountRecord mchAccountRecord = mchAccountRecordManager.getById(recordId);
        return MchAccountRecordConvert.INSTANCE.toMchAccountRecordVO(mchAccountRecord);
    }
}
