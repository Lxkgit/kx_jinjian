package com.kx.system.task;

import com.kx.system.domain.enums.FtpSyncFileEnum;
import com.kx.system.mapper.SysTeamMapper;
import com.kx.system.util.FtpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class SysTeamSyncJob extends BaseJob {

    @Resource
    private SysTeamMapper sysTeamMapper;

//    @Value("${data-runtime.SysTeamSyncJob:-}")
    private String cron;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date date) {
        FtpUtil.streamBatchFtpCsvFileToBean(FtpSyncFileEnum.SYS_TEAM, date, 50,
                () -> sysTeamMapper.trancateSysTeams(),
                list -> sysTeamMapper.batchInsertSysTeams((List) list));
        FtpUtil.streamBatchFtpCsvFileToBean(FtpSyncFileEnum.SYS_TEAM_MEMBER, date, 50,
                () -> sysTeamMapper.trancateSysTeamMembers(),
                list -> sysTeamMapper.batchInsertSysTeamMembers((List) list));
    }

    @Override
    public String getJobDesc() {
        return "团队成员跑批";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
