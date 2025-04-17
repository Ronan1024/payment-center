package com.baosight.payment.controller;

import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.chanel.tonglianpay.TongLianClient;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.utils.json.JsonUtil;
import com.baosight.web.properties.ProjectInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final MchAppConfigApi mchAppConfigApi;
    private final ProjectInfo projectInfo;

//    /**
//     * 消费申请
//     */
//    @GetMapping("/pay")
//    public Object pay() {
//        TongLianConfigVO config = mchAppConfigApi.tongLianConfig(1894259806383198208L);
//
////        // 获取支付配置
////        Map<String, String> payMode = new HashMap<>(1);
////        scanWx.put("SCAN_WEIXIN", "{\"limitPay\":\"no_credit\"}");
////        payMode.put("SCAN_WEIXIN", JsonUtil.toJson(scanWx));
////        ScanWeiXin scanWeiXin = new ScanWeiXin(StrFormatter.format("{\"vspCusid\":\"{}\"}", config.getSignNum()));
//        Map<String, Object> payModel = new HashMap<>();
//
//        Map<String, Object> scanWx = new HashMap<>();
////        scanWx.put("limitPay", "no_credit");
//        scanWx.put("vspCusid", config.getSignNum());
//        payModel.put("SCAN_WEIXIN", scanWx);
//
//        Map<String, Object> map = new HashMap<>();
//        // 用户id
//        Long userId = SnowflakeIdUtil.nextId();
//        map.put("signNum", "1898329970450374656");
//        // 商户订单号
//        Long orderId = SnowflakeIdUtil.nextId();
//        map.put("reqTraceNum", "1898330070526468096");
//        log.info("订单id:{}, 用户id：{}", orderId, userId);
//        //支付金额
//        Long payAmount = 10 * 100L;
//        //营销金额
//        Long promotionAmount = 0L;
//        //订单金额=支付金额+营销金额
//        Long orderAmount = payAmount + promotionAmount;
//        map.put("orderAmount", orderAmount);
//        map.put("payAmount", payAmount);
//        map.put("promotionAmount", promotionAmount);
//        map.put("payMode", payModel);
//        map.put("respUrl", "https://syxx.xin3plat.com/app-api//payment/notice/pay/tl/1/1/1/1");
//        map.put("receiverSignNum", 1894259806383198208L);
//        map.put("goodsName", "测试商品");
//        // 获取用户信息
//        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "2085", JsonUtil.toJson(map));
//        TongLianClient tongLianClient = new TongLianClient(config);
//        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, TongLianClient.URL);
//        return response.getResult();
//        if (projectInfo.hasDev()) {
//            return JsonUtil.readTree(response.get("chnlFrontParamInfo").asText()).get("chnlPayInfo").asText();
//        }
//        if (Boolean.FALSE.equals(response.success())) {
//            log.info("交易失败：{}", response.getResult());
//        } else {
//            JsonNode jsonNode = JsonUtil.readTree(response.get("chnlFrontParamInfo").asText());
//            return jsonNode.get("chnlPayInfo").asText();
//        }
//    }

    /**
     * 退款申请
     */
    @GetMapping("/refund/{orderId}/{amount}/{userId}")
    public Object refund(@PathVariable String orderId, @PathVariable Long amount, @PathVariable String userId) {
        TongLianIsvAndMchConfigDAO mchConfig = mchAppConfigApi.tongLianIsvAndMchConfig(1894259806383198208L, "tl_pay", 1901964437317865473L);

        Map<String, Object> map = new HashMap<>();
        // 退款订单号
        Long id = SnowflakeIdUtil.nextId();
        map.put("reqTraceNum", id);
        //原消费订单
        map.put("orgRespTraceNum", orderId);

        // 退款总金额 单位：分。
        //
        //【消费申请】退款，
        //
        //当上送“渠道退款金额”时，该字段与“渠道退款金额”+”营销退款金额“一致。
        //
        //当不上送“渠道退款金额”时，该字段与“资金确认退款金额”或”营销退款金额“的最大值保持一致
        Long promotionAmount = 0L;
        Long refundAmount = amount;
        // 营销金额单位：分
        //
        //1、不能超过退款总金额
        //
        //2、不能超过原订单营销金额
        //
        //3、支持部分退款
//        map.put("promotionAmount", promotionAmount);
        //订单退款详情
        Map<String, Object> sepRefundInfo = new HashMap<>();
        BigDecimal bigDecimal = new BigDecimal(10);
        BigDecimal divide = new BigDecimal(120).divide(new BigDecimal(10000), 3, RoundingMode.HALF_UP);
        BigDecimal multiply = bigDecimal.multiply(divide);
        long longValue = multiply.multiply(new BigDecimal(100)).longValue();
        Long orderAmount = refundAmount + promotionAmount;
        map.put("orderAmount", orderAmount);

//        sepRefundInfo.put("orderAmount", orderAmount);
        // 需要配置平台抽拥比例  不能大于签约时的平台抽拥比例

//        sepRefundInfo.put("couponAmount", 12);
        sepRefundInfo.put("cnlRefundAmount", orderAmount);
//         下单付款用户id
        sepRefundInfo.put("signNum", 1894259806383198208L);
        // 如果未分账则只需上送 cnlRefundAmount 字段即可
        // TODO 处理退款通知问题
        map.put("sepRefundInfo", JsonUtil.toJson(sepRefundInfo));
        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "2294", map);
        TongLianClient tongLianClient = new TongLianClient(mchConfig.isvConfig());
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, "http://116.228.64.55:28082/yst-service-api/tx/handle");
        return response.getResult();
    }

//    /**
//     * 订单状态查询
//     */
//    @GetMapping("/query_order_status")
//    public Object queryOrderStatus() {
//        TongLianConfigVO config = mchAppConfigApi.tongLianConfig(1894259806383198208L);
//        Map<String, Object> map = new HashMap<>();
//        map.put("respTraceNum", "20250308191103208501135259");
//        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "3001", map);
//        TongLianClient tongLianClient = new TongLianClient(config);
//        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, "http://116.228.64.55:28082/yst-service-api/tq/handle");
//        return response.getResult();
//    }

    /**
     * 订单详情查询
     */
    @GetMapping("/info")
    public Object orderInfo() {
        TongLianIsvAndMchConfigDAO mchConfig = mchAppConfigApi.tongLianIsvAndMchConfig(1894259806383198208L, "tl_pay", 1901964437317865473L);

        Map<String, Object> map = new HashMap<>();
        map.put("respTraceNum", "20250328170251208501017785");
        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "3002", map);
        TongLianClient tongLianClient = new TongLianClient(mchConfig.isvConfig());
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, "http://116.228.64.55:28082/yst-service-api/tq/handle");
        return response.getResult();
    }
//
//    /**
//     * 批量分账
//     */
//    @GetMapping("/sub_account")
//    public Object subAccount() {
//        // 分账人信息
//        TongLianConfigVO config = mchAppConfigApi.tongLianConfig(1894259806383198208L);
//        Map<String, Object> map = new HashMap<>();
//        // 批次号
//        map.put("batchNo", "1898663521655201792");
//        //分账笔数  最多支持5000笔
//        map.put("totalCount", "1");
//        // 将回盘处理结果通知到该地址
//        map.put("respUrl", "https://syxx.xin3plat.com/app-api//payment/notice/pay/tl/2/2/2/2");
//        //批量分账】文件，
//        //
//        //文件的byte数组进行base64加密后的字符串
//        SubAccountInfo subAccountInfo = new SubAccountInfo();
//        //商户订单号
//        //
//        //全局唯一不可重复
//        subAccountInfo.setReqTraceNum("898330070526468096");
//        BigDecimal bigDecimal = new BigDecimal(10);
//        BigDecimal divide = new BigDecimal(120).divide(new BigDecimal(10000), 3, RoundingMode.HALF_UP);
//        BigDecimal multiply = bigDecimal.multiply(divide);
//        long longValue = multiply.multiply(new BigDecimal(100)).longValue();
//        //平台抽佣金额
//        subAccountInfo.setCouponAmount(longValue);
//        //原收款的通联订单号
//        subAccountInfo.setOrgRespTraceNum("20250308191103208501135259");
//        List<String> list = Stream.of(subAccountInfo).map(SubAccountInfo::toString).toList();
//        String string = writeListToByteString(list);
//        log.info("分账文件数据: {}", string);
//        map.put("file", string);
//        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "2092", map);
//        TongLianClient tongLianClient = new TongLianClient(config);
//        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, "http://116.228.64.55:28082/yst-service-api/tx/handle");
//        return response.getResult();
//    }
//
//    /**
//     * 批量分账文件查询
//     */
//    @GetMapping("sub_account_file_query")
//    public Object subAccountFileQuery() {
//        TongLianConfigVO config = mchAppConfigApi.tongLianConfig(1894259806383198208L);
//        Map<String, Object> map = new HashMap<>();
//        map.put("batchNo", "1898663521655201792");
//        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "3003", map);
//        TongLianClient tongLianClient = new TongLianClient(config);
//        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, "http://116.228.64.55:28082/yst-service-api/tq/handle");
//        return response.getResult();
//    }
//
//    /**
//     * 批量下载回盘文件
//     */
//    @GetMapping("/sub_account_file_download")
//    public Object subAccountFileDownload() {
//        TongLianConfigVO config = mchAppConfigApi.tongLianConfig(1894259806383198208L);
//        Map<String, Object> map = new HashMap<>();
//        map.put("batchNo", "1898663521655201792");
//        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "4005", map);
//        TongLianClient tongLianClient = new TongLianClient(config);
//        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, "http://116.228.64.55:28082/yst-service-api/tq/handle");
//        return response.getResult();
//    }
//
//
//    /**
//     * 提现申请
//     */
//    public Object reportPrice() {
//        TongLianConfigVO config = mchAppConfigApi.tongLianConfig(1894259806383198208L);
//        Map<String, Object> map = new HashMap<>();
//        //商户会员编号 支持个人会员、企业会员、平台。
//        //
//        //若平台，上送固定值：#yunBizUserId_B2C#
////        map.put("signNum", );
////        //商户订单号  全局唯一，不可重复
////        //
////        //不可包含“|”字符
////        map.put("reqTraceNum", "");
////        map.put("acctType", "8");
////        //支付账户号
////        //
////        //提现账户类型为 支付账户时必填
////        map.put("payAcctNo", "");
////        //订单金额
////        //
////        //单位：分
////        map.put("orderAmount", );
////        //平台抽佣金额
////        //
////        //内扣。
////        //
////        //单位：分。
////        //
////        //如订单金额为100，平台抽佣金额为2，实际到账金额为98，平台收入为2
////        map.put("couponAmount", );
////        //后台通知地址
////        map.put("respUrl", );
////        //入账账户类型
////        //
////        //1：银行账户
////        map.put("receiveAcctType", );
////        //银行卡号
////        //
////        //“入账账户类型”为“1：银行账户”时，需上送会员绑定的“银行卡号”；
////        map.put("acctNum", );
////        //提现方式
////        //
////        //D0：D+0到账
////        //
////        //注:目前仅支持“D0”
////        map.put("withdrawType", );
////        //摘要
////        //
////        //不可包含“|”及换行符+，空格，/，?，%，#，&，=等特殊字符，最多50个字符
////        map.put("summary", );
////        //
////        //扩展信息
////        //
////        //原样返回
////        map.put("extendParams", );
//        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "2290", map);
//        TongLianClient tongLianClient = new TongLianClient(config);
//        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, "http://116.228.64.55:28082/yst-service-api/tx/handle");
//        return response.getResult();
//    }
//
//    @GetMapping("/id")
//    public Long id() {
//        return SnowflakeIdUtil.nextId();
//    }
//

//
//
//    public String writeListToByteString(List<String> lines) {
//        // 使用 ByteArrayOutputStream 来写入数据
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            for (String line : lines) {
//                // 写入字符串
//                outputStream.write(line.getBytes(StandardCharsets.UTF_8));
//                // 写入换行符
//                outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
//            }
//            // 获取字节数组
//            byte[] byteArray = outputStream.toByteArray();
//            return Base64.getEncoder().encodeToString(byteArray);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
//    }
//
//    public static void main(String[] args) throws IOException {
//        write();
//    }
//
//    public static void write() throws IOException {
//        byte[] decode = Base64.getDecoder().decode("ODk4MzMwMDcwNTI2NDY4MDk2fDIwMjUwMzA5MTcxNTUwMjA5MjAwMzc1Mjk1fDIwMjUwMzA4MTkxMTAzMjA4NTAxMTM1MjU5fDk5N3wxMnx8MjAyNTAzMDkxNzE1NTF8MXzkuqTmmJPmiJDlip8K");
//        FileOutputStream fos = new FileOutputStream("test.txt");
//        fos.write(decode);
//    }
}
