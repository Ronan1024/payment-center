package com.baosight.payment.notify.mapper;

import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.notify.pojo.vo.PayMchNotifyRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_notify_record(商户通知记录表)】的数据库操作Mapper
 * @createDate 2025-03-20 16:37:35
 * @Entity com.baosight.payment.system.notify.pojo.entity.PayMchNotifyRecord
 */
@Mapper
public interface PayMchNotifyRecordMapper extends BaseMapper<PayMchNotifyRecord> {

    /**
     * 获取可进行通知的记录信息
     *
     * @param now 当前时间
     */
    List<PayMchNotifyRecordVO> getRecordList(@Param("now") Date now);
}




