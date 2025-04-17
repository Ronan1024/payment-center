package com.baosight.payment.order.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baosight.payment.order.mapper.OrderDivisionBatchMapper;
import com.baosight.payment.order.pojo.entity.OrderDivisionBatch;
import com.baosight.utils.json.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
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
        long batchNo = jsonNode.get("batchNo").asLong();
        orderDivisionBatchMapper.update(new LambdaUpdateWrapper<OrderDivisionBatch>()
                .eq(OrderDivisionBatch::getId, batchNo).set(OrderDivisionBatch::getFailMsg, body));

        return ResponseEntity.ok("success");
    }


}
