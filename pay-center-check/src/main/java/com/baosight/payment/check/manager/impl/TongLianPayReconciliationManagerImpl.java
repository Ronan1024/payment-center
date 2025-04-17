package com.baosight.payment.check.manager.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.access.tl.model.TongLianClient;
import com.baosight.payment.access.tl.model.TongLianIsvConfigDAO;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.check.enums.ChannelBillHandlerState;
import com.baosight.payment.check.manager.TongLianPayReconciliationManager;
import com.baosight.payment.check.mapper.ChannelBillFileMapper;
import com.baosight.payment.check.mapper.ChannelBillMapper;
import com.baosight.payment.check.pojo.entity.ChannelBill;
import com.baosight.payment.check.pojo.entity.ChannelBillFile;
import com.baosight.payment.enums.PayInterfaceCode;
import com.baosight.payment.enums.PayWayCode;
import com.baosight.payment.enums.TradingType;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.utils.utils.Assert;
import com.baosight.web.properties.ProjectInfo;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/4
 */
@Slf4j
@Manager
@RequiredArgsConstructor
public class TongLianPayReconciliationManagerImpl implements TongLianPayReconciliationManager {

    @Resource
    private ProjectInfo projectInfo;

    private final MchAppConfigApi mchAppConfigApi;
    private final ChannelBillFileMapper channelBillFileMapper;
    private final ChannelBillMapper channelBillMapper;

    /**
     * 获取通联商户配置信息
     */
    @Override
    public List<MchInterfaceConfigVO> tlPayMchConfigList() {
        return mchAppConfigApi.isvConfig(PayInterfaceCode.TONG_LIAN_PAY.getCode());
    }

    /**
     * 开始处理支付渠道账单
     *
     * @param mchInterfaceConfig 商户接口配置
     * @param date               获取账单时间
     */
    @Override
    public Boolean handlerPayAgencyBill(MchInterfaceConfigVO mchInterfaceConfig, String date) {
        TongLianIsvConfigDAO tlIsvConfig = TongLianIsvConfigDAO.parse(mchInterfaceConfig.getConfig());
        Assert.isNull(tlIsvConfig, "当前服务商[" + mchInterfaceConfig.getMchId() + "]配置为空");
        String billFileCode = mchInterfaceConfig.getInterfaceCode() + "_" + mchInterfaceConfig.getMchId() + "_" + date;
        Map<String, Object> map = new HashMap<>();
        map.put("fileDate", date);
        map.put("appId", tlIsvConfig.getAppId());
        map.put("fileType", 1);
        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "4002", map);
        // TODO 记录调用信息
        TongLianClient tongLianClient = new TongLianClient(tlIsvConfig);
        String url;
        if (projectInfo.hasDev()) {
            url = "http://116.228.64.55:28082/yst-service-api/file/download";
        } else {
            url = "https://ibsapi.allinpay.com/yst-service-api/file/download";
        }

        ChannelBillFile selectOne = channelBillFileMapper.selectOne(new LambdaQueryWrapper<ChannelBillFile>()
                .eq(ChannelBillFile::getChannelBillCode, billFileCode));
        if (!ObjectUtils.isEmpty(selectOne)) {
            return Boolean.FALSE;
        }
        long nextedId = SnowflakeIdUtil.nextId();
        ChannelBillFile channelBillFile = new ChannelBillFile();
        // 创建批次号
        channelBillFile.setId(nextedId);
        channelBillFile.setChannelBillCode(billFileCode);
        channelBillFile.setParseState(Boolean.FALSE);
        channelBillFile.setChannelCode(mchInterfaceConfig.getInterfaceCode());
        channelBillFile.setChannelId(mchInterfaceConfig.getInterfaceId());
        channelBillFile.setBillDate(date);
        // 使用字符流逐行读取内容
        try {
            List<String> billFile = tongLianClient.download(sendBuild, url, tlIsvConfig.getAppId() + "1" + date);
            billFile.stream().filter(e -> !e.contains("DEAL202504020001298X467INR")).map(e -> {
                ChannelBill channelBill = new ChannelBill();
                parse(channelBill, e, date, mchInterfaceConfig);
                channelBill.setBillFileId(nextedId);
                channelBill.setBillFileCode(billFileCode);
                channelBill.setBillState(ChannelBillHandlerState.PENDING.getCode());
                channelBill.setChannelId(mchInterfaceConfig.getInterfaceId());
                return channelBill;
            }).forEach(e -> {
                int insert = channelBillMapper.insert(e);
                Assert.isFalse(insert > 0, "通联账单保存失败");
            });

            channelBillFile.setParseState(Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            channelBillFile.setParseError(e.getMessage());
            log.info("通联账单解析失败通联账单号: {}", channelBillFile.getChannelBillCode());
        } finally {
            channelBillFileMapper.insert(channelBillFile);
        }
        return Boolean.TRUE;
    }

    // 匹配管道符
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\|");

    /**
     * 解析通联账单信息
     *
     * @param tongLianBillLine 通联账单信息
     */
    private void parse(ChannelBill channelBill, String tongLianBillLine, String date, MchInterfaceConfigVO mchInterfaceConfig) {
        //通联订单号|订单类型|交易金额(单位:分)|平台抽佣金额(单位:分)|交易时间|商户订单号|原商户订单号|原通联订单号|支付模式|扩展参数|结算金额|预留字段1|预留字段2|预留字段3|渠道金额|渠道流水号|渠道手续费(单位:分)|渠道交易类型|卡号|卡类别|渠道商户号|门店名称|终端号|
        String[] split = SPLIT_PATTERN.split(tongLianBillLine);
        channelBill.setChannelCode(mchInterfaceConfig.getInterfaceCode());
        channelBill.setChannelId(mchInterfaceConfig.getInterfaceId());
        channelBill.setBillDate(date);
        // 通联订单号
        channelBill.setChannelOrderId(split[0]);
        //订单类型
        TradingType apply = orderTypeFunction.apply(split[1]);
        String orderType = ObjectUtils.isEmpty(apply) ? "empty" : apply.getCode();
        // TODO 需要根据具体的订单类型逻辑进行处理
        channelBill.setTradingState(2);
        channelBill.setTradeType(orderType);
        //交易金额(单位:分)
        channelBill.setTradingAmount(Long.valueOf(split[2]));
        //平台抽佣金额(单位:分)

        //交易时间 yyyyMMddHHmmss
        channelBill.setTradingTime(DateUtil.parse(split[4], DatePattern.PURE_DATETIME_PATTERN));

        //商户订单号
        channelBill.setOrderId(Long.valueOf(split[5]));
        //原商户订单号 针对退款订单/提现退票订单，返回原商户订单号；单订单担保确认、单会员担保确认、批量分账的订单，返回原商户订单号；
        //原通联订单号 针对退款订单/提现退票订单，返回原通联订单号；单订单担保确认、单会员担保确认、批量分账的订单，返回原商户订单号；
        //(9) 支付模式：订单的支付模式，对应【支付模式】
        PayWayCode payWayCode = payType.apply(split[8]);
        channelBill.setPayWay(payWayCode.getCode());
        //(10) 扩展参数：订单申请上送的“扩展参数-extendParams”信息，原样透传返回给商户；
        channelBill.setMeteDate(split[9]);
        //(11) 结算金额：通过【消费申请】的交易，该笔交易的结算金额；
        channelBill.setSettlementAmount(Long.valueOf(split[10]));
        //(12) 订单子类型
        //(13) 预留字段1：目前默认为空，以便后续扩展；
        //(14) 预留字段2：目前默认为空，以便后续扩展;
        //(15) 渠道金额：单位:分，从支付渠道对账文件获取的支付渠道金额，仅透传信息，如果为空，该字段默认显示为0，而不是空串。
//        channelBill.setTradingAmount(Long.valueOf(split[14]));
//        channelBill.setUpstreamChannelAmount(Long.valueOf(split[14]));
        //(16) 渠道流水号：从支付渠道对账文件获取的渠道流水号，仅透传信息，如果为空，该字段默认显示为0，而不是空串。
//        channelBill.setUpstreamChannelOrderId(String.valueOf(split[15]));
        //(17) 渠道手续费：单位:分，从支付渠道对账文件获取的支付交易手续费，仅透传信息，如果为空，该字段默认显示为0，而不是空串。
        channelBill.setTradingFee(Long.valueOf(split[16]));

        //(18) 渠道交易类型：透传渠道实际交易类型，针对收银宝入金交易返回；

        //(19) 卡号：透传渠道返回的用户交易使用的卡号信息，其中银行卡返回卡号带掩码格式，微信支付返回用户openid，支付宝支付返回用户userid；
        channelBill.setUserNo(String.valueOf(split[18]));
        //(20) 卡类别：透传渠道返回的用户交易使用的卡种；
        //(21) 渠道商户号：本交易在请求渠道流水中的渠道商户号，即实际交易的单商户/子商户；
        channelBill.setChannelMchNo(split[20]);
        //(22) 门店名称：收银宝对账单中的“门店名称”；
        //(23) 终端号：收银宝对账单中的“终端号”。

        channelBill.setSourceDate(tongLianBillLine);
    }

    private final Function<String, TradingType> orderTypeFunction = e -> switch (e) {
        // TODO 通联未处理 订单类型"2089", "2297", "2080", "2084", "2094"
        case "2085" -> TradingType.CONSUMPTION;
        case "2294" -> TradingType.REFUND;
        case "2290" -> TradingType.WITHDRAWAL;
        case "4020" -> TradingType.SETTLEMENT;
        case "2298" -> TradingType.ERROR;
        default -> null;
    };


    /**
     * 支付模式
     */
    private final Function<String, PayWayCode> payType = e -> switch (e) {
        case "SCAN_WEIXIN" -> PayWayCode.TONG_LIAN_WX_SCAN;
        case "WECHATPAY_MINIPROGRAM", "WECHAT_PRECONSUME" -> PayWayCode.TONG_LIAN_WX_MINI_PROGRAM;
        default -> null;
    };

}
