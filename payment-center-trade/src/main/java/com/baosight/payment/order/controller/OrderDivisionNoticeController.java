package com.baosight.payment.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.access.tl.model.TongLianClient;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.enums.DivisionState;
import com.baosight.payment.order.mapper.OrderDivisionBatchMapper;
import com.baosight.payment.order.pojo.entity.OrderDivisionBatch;
import com.baosight.utils.json.JsonUtil;
import com.baosight.web.properties.ProjectInfo;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class OrderDivisionNoticeController {

    private final OrderDivisionBatchMapper orderDivisionBatchMapper;
    private final MchAppConfigApi mchAppConfigApi;
    private final ProjectInfo projectInfo;

    /**
     * 异步回调入口
     **/
    @RequestMapping(value = {"/api/division/notify"})
    public ResponseEntity doNotify(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String body = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        log.info("收到分账回调信息===================================================================,{}", body);
        log.info("收到分账回调信息===================================================================,{}", request.getParameterMap());
        JsonNode jsonNode = JsonUtil.readTree(body);
        //{\"batchNo\":\"1912781053302722562\",\"submitTime\":\"2025-04-17 16:11:56\",\"fileStatus\":\"1\",\"respMsg\":\"交易成功\",\"respCode\":\"00000\"}
        JsonNode bizData = JsonUtil.readTree(jsonNode.get("bizData").asText());
        String batchNo = bizData.get("batchNo").asText();
        OrderDivisionBatch orderDivisionBatch = orderDivisionBatchMapper.selectOne(new LambdaQueryWrapper<OrderDivisionBatch>()
                .eq(OrderDivisionBatch::getChannelBatchId, batchNo));
        if (!ObjectUtils.isEmpty(orderDivisionBatch)) {
            if (bizData.get("respCode").asText().equals("00000")){
                if (bizData.get("fileStatus").asText().equals("1")) {
                    // 处理成功
                    orderDivisionBatch.setDivisionState(DivisionState.CHANNEL_HANDLER_SUCCESS.code());
                } else if (bizData.get("fileStatus").asText().equals("2")) {
                    orderDivisionBatch.setDivisionState(DivisionState.CHANNEL_HANDLER_FAILURE.code());
                } else {
                    orderDivisionBatch.setDivisionState(DivisionState.DIVISION_CHANNEL_PROCESSING.code());
                }
            }else {
                orderDivisionBatch.setDivisionState(DivisionState.CHANNEL_HANDLER_FAILURE.code());
            }
            orderDivisionBatch.setChannelHandlerResult(bizData.toString());
            orderDivisionBatchMapper.updateById(orderDivisionBatch);
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("/division/find/{isvId}/{batchNo}")
    public Object divisionFind(@PathVariable Long isvId, @PathVariable String batchNo) {
        TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfigDAO = mchAppConfigApi.tongLianIsvAndMchConfig(null, "tl_pay", isvId);
        Map<String, Object> map = new HashMap<>();
        map.put("batchNo", batchNo);
        TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "3003", map);
        TongLianClient tongLianClient = new TongLianClient(tongLianIsvAndMchConfigDAO.isvConfig());
        String url;
        if (projectInfo.hasDev()) {
            url = "http://116.228.64.55:28082/yst-service-api/tq/handle";
        } else {
            url = "https://ibsapi.allinpay.com/yst-service-api/tq/handle";
        }
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, url);
        return response.getResult();
    }

}
