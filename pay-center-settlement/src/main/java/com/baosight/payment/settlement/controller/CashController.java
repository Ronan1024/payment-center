package com.baosight.payment.settlement.controller;

import cn.hutool.core.math.Money;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.access.tl.model.TongLianClient;
import com.baosight.payment.access.tl.model.TongLianIsvConfigDAO;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.enums.NotifyType;
import com.baosight.payment.enums.PayInterfaceCode;
import com.baosight.payment.notify.api.NotifyApi;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.pojo.entity.CallbackHandlerLog;
import com.baosight.payment.service.CallbackHandlerLogService;
import com.baosight.payment.settlement.enums.AccountType;
import com.baosight.payment.settlement.enums.AppleState;
import com.baosight.payment.settlement.error.AccountError;
import com.baosight.payment.settlement.manager.MchAccountManager;
import com.baosight.payment.settlement.manager.MchAccountRecordManager;
import com.baosight.payment.settlement.pojo.dto.AppleCashDTO;
import com.baosight.payment.settlement.pojo.entity.MchAccount;
import com.baosight.payment.settlement.pojo.entity.MchAccountRecord;
import com.baosight.payment.system.tonglian.utils.DemoSM2Util;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.utils.Assert;
import com.baosight.web.annotation.IgnoreHandlerResponse;
import com.baosight.web.exception.ApiException;
import com.baosight.web.properties.ProjectInfo;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 提现申请接口
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/21
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cash")
public class CashController {

    private final ProjectInfo projectInfo;
    private final MchAppConfigApi mchAppConfigApi;
    private final MchInfoApi mchInfoApi;
    private final CallbackHandlerLogService callbackHandlerLogService;
    private final NotifyApi notifyApi;

    @Value("${pay.notifyUrl}")
    private String notifyUrl;

    private final MchAccountManager mchAccountManager;
    private final MchAccountRecordManager mchAccountRecordManager;
    private static final ConcurrentHashMap<String, String> APPLY_MAP = new ConcurrentHashMap<>();

    @PostMapping("/apply")
    public void applyCash(@RequestBody AppleCashDTO appleCashDTO) {
        MchInfoVO mchInfoVO = mchInfoApi.mchInfoBuMchNO(appleCashDTO.getMchNo());
        Assert.isNull(mchInfoVO, ApiException.supplier(AccountError.MCH_ACCOUNT_NOT_FOUND));
        MchAccount mchAccount = mchAccountManager.getOne(new LambdaQueryWrapper<MchAccount>()
                .eq(MchAccount::getMchId, mchInfoVO.getId()));
        Assert.isNull(mchAccount, ApiException.supplier(AccountError.MCH_ACCOUNT_NOT_FOUND));

        String key = mchInfoVO.getId() + "-apply";
        Assert.isTrue(APPLY_MAP.containsKey(key), ApiException.supplier(AccountError.MCH_WITHDRAWAL_PROCESSING));
        APPLY_MAP.put(key, "apply");
        Money money = new Money(appleCashDTO.getAmount()).divide(100);
        MchAccountRecord mchAccountRecord = new MchAccountRecord();
        mchAccountRecord.setAmount(money.getAmount());
        mchAccountRecord.setType(AccountType.WITHDRAW.getCode());
        mchAccountRecord.setCreateTime(new Date());
        mchAccountRecord.setMchId(mchInfoVO.getId());
        mchAccountRecord.setNotifyUrl(appleCashDTO.getNotifyUrl());
        mchAccountRecord.setApplyOrderId(appleCashDTO.getApplyOrderId());
        try {
            Money balance = new Money(mchAccount.getBalance());
            Assert.isTrue(balance.compareTo(money) < 0, ApiException.supplier(AccountError.ACCOUNT_BALANCE_INSUFFICIENT));
            TongLianIsvAndMchConfigDAO mchConfig = mchAppConfigApi.tongLianIsvAndMchConfig(mchInfoVO.getId(), PayInterfaceCode.TONG_LIAN_PAY.getCode(), mchInfoVO.getIsvId());
            TongLianIsvConfigDAO isvConfig = mchConfig.isvConfig();
            Map<String, Object> map = new HashMap<>();
            map.put("signNum", mchInfoVO.getId());
            String nexted = SnowflakeIdUtil.nextIdStr();
            map.put("reqTraceNum", nexted);
            map.put("acctType", "8");
            map.put("orderAmount", appleCashDTO.getAmount());
            JsonNode mchBankCardNo = mchAppConfigApi.getMchBankCardNo(mchInfoVO.getId());
            String bankCardNo = mchBankCardNo.get("bankCardNo").asText();
            String acctNum = DemoSM2Util.decryptEcb(isvConfig.getSecretKey(), bankCardNo);
            map.put("respUrl", notifyUrl + "/api/cash/notify");
            map.put("receiveAcctType", "1");
            map.put("acctNum", acctNum);
            map.put("withdrawType", "D0");
            String url;
            if (projectInfo.hasDev()) {
                url = "http://116.228.64.55:28082/yst-service-api/tx/handle";
            } else {
                url = "https://ibsapi.allinpay.com/yst-service-api/tx/handle";
            }
            TongLianClient client = new TongLianClient(isvConfig);
            TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "2290", map);
            TongLianClient.Response response = client.sendRequest(sendBuild, url);
            if (Boolean.TRUE.equals(response.getSuccess())) {
                mchAccountRecord.setApplyState(AppleState.PROCESSING.getCode());
                mchAccount.setBalance(balance.subtract(money).getAmount());
                Money add = new Money(mchAccount.getAccountFrozen()).add(money);
                mchAccount.setAccountFrozen(add.getAmount());
                mchAccountManager.updateById(mchAccount);
            } else {
                mchAccountRecord.setApplyState(AppleState.FAILURE.getCode());
                mchAccountRecord.setApplyMsg(response.getResult().asText());
                throw new ApiException(response.getErrorMsg(), response.getErrorMsg());
            }
        } catch (Exception e) {
            mchAccountRecord.setApplyMsg(e.getMessage());
            e.printStackTrace();
            throw new ApiException("1000", e.getMessage());
        } finally {
            mchAccountRecordManager.save(mchAccountRecord);
            APPLY_MAP.remove(key);
        }

    }


    @IgnoreHandlerResponse
    @PostMapping("/notify")
    public String applyNotify(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String collect = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        log.info("收到提现回调信息:{}", collect);
        JsonNode jsonNode = JsonUtil.readTree(collect);
        JsonNode bizData = JsonUtil.readTree(jsonNode.get("bizData").asText());
        CallbackHandlerLog callbackHandlerLog = new CallbackHandlerLog();
        long mchId = bizData.get("signNum").asLong();
        String key = mchId + "-apply-notify";
        try {
            Assert.isTrue(APPLY_MAP.containsKey(key), ApiException.supplier(AccountError.MCH_WITHDRAWAL_PROCESSING));
            APPLY_MAP.put(key, "apply-notify");
            callbackHandlerLog.setHasHandler(Boolean.FALSE);
            callbackHandlerLog.setCallbackContext(collect);
            callbackHandlerLog.setInterfaceCode("提现");
            callbackHandlerLog.setHasHandler(Boolean.TRUE);
            String respTraceNum = bizData.get("respTraceNum").asText();
            callbackHandlerLog.setTrxId(respTraceNum);
            MchAccountRecord accountRecord = mchAccountRecordManager.getOne(new LambdaQueryWrapper<MchAccountRecord>()
                    .eq(MchAccountRecord::getApplyOrderId, respTraceNum)
                    .eq(MchAccountRecord::getApplyState, AppleState.PROCESSING.getCode())
            );
            if ("1".equals(bizData.get("result").asText())) {
                // 成功
                Assert.isNull(accountRecord, ApiException.supplier(AccountError.WITHDRAWAL_RECORD_NOT_FOUND));
                MchAccount mchAccount = mchAccountManager.getOne(new LambdaQueryWrapper<MchAccount>()
                        .eq(MchAccount::getMchId, mchId));
                Money divide = new Money(bizData.get("orderAmount").asText()).divide(100);
                Money money = new Money(mchAccount.getAccountFrozen());
                Assert.isTrue(money.compareTo(divide) < 0, ApiException.supplier(AccountError.FREEZE_AMOUNT_INSUFFICIENT));
                mchAccount.setAccountFrozen(money.subtract(divide).getAmount());
                boolean update = mchAccountManager.updateById(mchAccount);
                accountRecord.setApplyState(AppleState.SUCCESS.getCode());
                mchAccountRecordManager.updateById(accountRecord);
                if (update) {
                    //通知下级系统
                    PayOrderNotifyDTO payOrderNotifyDTO = new PayOrderNotifyDTO();
                    payOrderNotifyDTO.setMchId(mchId);
                    payOrderNotifyDTO.setOrderId(accountRecord.getId());
                    payOrderNotifyDTO.setOrderType(NotifyType.WITHDRAW_SUCCESS.getCode());
                    payOrderNotifyDTO.setNotifyUrl(accountRecord.getNotifyUrl());
                    notifyApi.payOrderNotify(payOrderNotifyDTO);
                }
            } else {
                accountRecord.setApplyState(AppleState.FAILURE.getCode());
                accountRecord.setApplyMsg(bizData.get("respMsg").asText());
                mchAccountRecordManager.updateById(accountRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callbackHandlerLog.setHandlerError(JsonUtil.toJson(e.getMessage()));
        } finally {
            APPLY_MAP.remove(key);
            callbackHandlerLogService.save(callbackHandlerLog);
        }

        return "success";
    }


    /**
     * 获取商户信息
     */
    @GetMapping("/mch_info/{mchId}/{type}")
    public Object mchInfo(@PathVariable("mchId") Long mchId, @PathVariable String type) {
        return mchAppConfigApi.getMchBankCardNo(mchId);
    }

}
