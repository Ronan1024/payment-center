package com.baosight.payment.accounting.task;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baosight.payment.accounting.manager.TongLianPayReconciliationManager;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 通联每日对账下载对账单创建对账批次
 * 对账批次号: 支付接口_商户号_时间
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/3
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TongLianPayReconciliationTaskService {
    private final TongLianPayReconciliationManager tongLianPayReconciliationManager;

    /**
     * 下载通联账单
     */
    @XxlJob("pay-tl-pay-bill-file")
    public void handlerPayAgencyBill() {
        String param = XxlJobHelper.getJobParam();
        String format;
        Date date = new Date();
        if (StringUtils.hasText(param)) {
            format = param;
        } else {
            DateTime dateTime = DateUtil.offsetDay(date, -1);
            format = DateUtil.format(dateTime, DatePattern.PURE_DATE_PATTERN);
        }

        log.info("开始执行通联对账任务:{}", format);
        List<MchInterfaceConfigVO> mchInterfaceConfigVOList = tongLianPayReconciliationManager.tlPayMchConfigList();
        if (CollectionUtils.isEmpty(mchInterfaceConfigVOList)) {
            log.info("开始执行通联对账任务-未获取到通联配置信息:{}", format);
            return;
        }
        // 开始执行对账任务 当前商户数量不会太大进行循环下载对账文件并进行解析
        mchInterfaceConfigVOList.forEach(e -> {
            tongLianPayReconciliationManager.handlerPayAgencyBill(e, format);
        });
    }

}
