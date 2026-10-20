package com.kx.system.task;

import com.kx.system.mapper.ManagerTransferReferIndexMapper;
import com.kx.system.util.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.kx.system.domain.enums.FtpSyncFileEnum.MAANAGER_TRANSFER_REFER;

/**
 * 通过率收息率同步任务
 */
//@Component
public class ManagerTransferReferJob extends BaseJob {
    private static final Logger log = LoggerFactory.getLogger(ManagerTransferReferJob.class);

    @Resource
    private ManagerTransferReferIndexMapper managerTransferReferIndexMapper;

//    @Value("${data-runtime.ManagerTransferReferJob:-}")
    private String cron;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date date) {
        //清空当天数据
        FtpUtil.streamBatchFtpCsvFileToBean(MAANAGER_TRANSFER_REFER, date, 50,
                () -> managerTransferReferIndexMapper.truncate(),
                list -> managerTransferReferIndexMapper.batchInsert((List) list));
    }

    @Override
    public String getJobDesc() {
        return "通过率收息率同步任务";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
