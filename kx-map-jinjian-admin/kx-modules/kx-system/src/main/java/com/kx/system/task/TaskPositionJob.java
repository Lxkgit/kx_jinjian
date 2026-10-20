package com.kx.system.task;

import com.kx.system.mapper.PaTaskPositionMapper;
import com.kx.system.util.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.kx.system.domain.enums.FtpSyncFileEnum.PA_TASK_POSITION;

/**
 * 时空轨迹同步任务
 */
//@Component
public class TaskPositionJob extends BaseJob {
    private static final Logger log = LoggerFactory.getLogger(TaskPositionJob.class);

    @Resource
    private PaTaskPositionMapper paTaskPositionMapper;

//    @Value("${data-runtime.TaskPositionJob:-}")
    private String cron;

    @Transactional(rollbackFor = Exception.class)
    @Override
    protected void execute(Date date) {
        FtpUtil.streamBatchFtpCsvFileToBean(PA_TASK_POSITION, date, 50,
                () -> paTaskPositionMapper.deleteByDate(date),
                list -> paTaskPositionMapper.batchInsert((List) list));
    }

    @Override
    public String getJobDesc() {
        return "时空轨迹同步任务";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
