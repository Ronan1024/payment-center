package com.baosight.payment.channel.service.impl;

import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
import com.baosight.payment.channel.dao.manager.ChannelGatewayLogManager;
import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.error.ChannelError;
import com.baosight.payment.channel.handler.notify.ChannelNotifyRequest;
import com.baosight.payment.channel.handler.notify.ChannelNotifyRoute;
import com.baosight.payment.channel.handler.notify.IChannelNotifyRule;
import com.baosight.payment.channel.pojo.dao.ChannelGatewayLogDAO;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import com.baosight.payment.channel.service.ChannelNotifyService;
import com.baosight.utils.json.JsonUtil;
import com.baosight.web.core.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 渠道回调通知处理服务实现。
 * <p>
 * 负责将渠道入站请求封装为统一请求对象，记录渠道入站日志，
 * 识别具体渠道回调规则，并分发到对应业务处理器执行状态更新。
 * </p>
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Service
@RequiredArgsConstructor
public class ChannelNotifyServiceImpl implements ChannelNotifyService {

    /**
     * 渠道回调识别与解析规则集合。
     */
    private final List<IChannelNotifyRule> notifyRules;

    /**
     * 统一回调业务处理器。
     */
    private final PayNotifyProcessor payNotifyProcessor;

    /**
     * 渠道网关日志管理器。
     */
    private final ChannelGatewayLogManager channelGatewayLogManager;

    /**
     * 处理渠道回调入站请求。
     *
     * @param body    原始请求体
     * @param request HTTP 请求
     * @param channel 渠道
     * @param event   事件类型
     * @param bizId   业务ID
     * @return 渠道要求的响应内容
     */
    @Override
    public String handle(String body, HttpServletRequest request, String channel, String event, Long bizId) {
        StopWatch watch = new StopWatch();
        watch.start();
        ChannelNotifyRequest notifyRequest = buildRequest(body, request);
        ChannelGatewayLogDAO log = buildRawLog(notifyRequest);
        String response = "FAIL";
        boolean save = true;
        try {
            ChannelNotifyRoute route = ChannelNotifyRoute.parse(channel, event);
            IChannelNotifyRule rule = notifyRules.stream().filter(item -> item.support(notifyRequest, route))
                    .findFirst().orElse(null);

            if (rule == null) {
                throw new ApiException(ChannelError.CALLBACK_RULE_NOT_FOUND);
            }
            // 解析请求参数
            UnifiedPayNotifyDTO dto = rule.parse(notifyRequest);
            validateRoute(route, dto);
            dto.setBizId(bizId)
                    .setChannelCode(route.channelCode())
                    .setEventType(route.eventType());

            // 补充日志信息
            log.setBizId(bizId).setBizType(route.eventType().code())
                    .setBizTypeName(route.eventType().desc()).setChannelCode(route.channelCode().code()).setOutTradeNo(dto.getOutTradeNo());

            if (!existsSuccessLog(dto.getOutTradeNo(), route.channelCode(), route.eventType())) {
                Boolean processResult = rule.process(dto);
                log.setBizStatus(Boolean.TRUE.equals(processResult)
                        ? ChannelGatewayLog.BizStatus.SUCCESS.getCode()
                        : ChannelGatewayLog.BizStatus.FAIL.getCode());
                log.setResCode(dto.getErrCode());
                response = rule.successResponse();
            } else {
                save = false;
            }
        } catch (Exception e) {
            log.setBizStatus(ChannelGatewayLog.BizStatus.FAIL.getCode());
            log.setErrorMsg(e.getMessage());
        } finally {
            watch.stop();
            log.setCostTime(watch.getTotalTimeMillis());
            log.setReqParams(JsonUtil.toJson(notifyRequest.getHeaders()));
            if (save) {
                channelGatewayLogManager.saveChannelGatewayLog(log);
            }
        }

        return response;
    }

    private void validateRoute(ChannelNotifyRoute route, UnifiedPayNotifyDTO dto) {
        if (dto == null) {
            throw new ApiException(ChannelError.CALLBACK_RULE_NOT_FOUND);
        }
        if (dto.getChannelCode() != null && !route.channelCode().equals(dto.getChannelCode())) {
            throw new ApiException(ChannelError.CALLBACK_CHANNEL_NOT_MATCH);
        }
        if (dto.getEventType() != null && !route.eventType().equals(dto.getEventType())) {
            throw new ApiException(ChannelError.CALLBACK_EVENT_TYPE_NOT_MATCH);
        }
    }


    /**
     * 构建渠道入站原始日志。
     *
     * @param request 渠道回调入站请求
     * @return 渠道网关日志数据对象
     */
    private ChannelGatewayLogDAO buildRawLog(ChannelNotifyRequest request) {
        return new ChannelGatewayLogDAO()
                .setOperation(ChannelGatewayLog.Operation.IN_SIDE.getCode())
                .setBizStatus(ChannelGatewayLog.BizStatus.PROCESS.getCode())
                .setResUrl(request.getMethod() + " " + request.getRequestUri())
                .setResParams(request.getBody());
    }


    /**
     * 判断当前渠道回调是否已经存在成功处理记录。
     *
     * @param channelCode      渠道编号
     * @param channelEventType 渠道事件类型
     * @param outTradeNo       外部交易号
     * @return true 表示已存在成功处理记录
     */
    private boolean existsSuccessLog(String outTradeNo, ChannelCode channelCode, ChannelEventType channelEventType) {
        return channelGatewayLogManager.lambdaQuery()
                .eq(ChannelGatewayLog::getChannelCode, channelCode.code())
                .eq(ChannelGatewayLog::getBizType, channelEventType.code())
                .eq(ChannelGatewayLog::getOutTradeNo, outTradeNo)
                .eq(ChannelGatewayLog::getBizStatus, ChannelGatewayLog.BizStatus.SUCCESS.getCode())
                .count() > 0;
    }

    /**
     * 构建统一渠道回调入站请求对象。
     *
     * @param body    原始请求体
     * @param request HTTP 请求
     * @return 渠道回调入站请求
     */
    private ChannelNotifyRequest buildRequest(String body, HttpServletRequest request) {
        String requestBody = StringUtils.hasText(body) ? body : JsonUtil.toJson(resolveParams(request));
        return new ChannelNotifyRequest()
                .setBody(requestBody)
                .setMethod(request.getMethod())
                .setRequestUri(request.getRequestURI())
                .setHeaders(resolveHeaders(request))
                .setParams(resolveParams(request));
    }

    /**
     * 解析 HTTP 请求参数。
     *
     * @param request HTTP 请求
     * @return 请求参数键值对
     */
    private Map<String, String> resolveParams(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap == null || parameterMap.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> params = new LinkedHashMap<>();
        parameterMap.forEach((key, value) -> params.put(key, String.join(",", value)));
        return params;
    }


    /**
     * 解析 HTTP 请求头。
     *
     * @param request HTTP 请求
     * @return 请求头键值对，key 统一转换为小写
     */
    private Map<String, String> resolveHeaders(HttpServletRequest request) {
        Map<String, String> headers = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName.toLowerCase(), request.getHeader(headerName));
        }
        return headers;
    }
}
