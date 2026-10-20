package com.kx.system.task;

import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.SysBatchTaskRecord;
import com.kx.system.mapper.SysBatchTaskRecordMapper;
import com.kx.system.util.AopProxyHolder;
import com.kx.system.util.IPInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public abstract class BaseJob extends AopProxyHolder<BaseJob> implements SchedulingConfigurer, Runnable {
    private static final Logger log = LoggerFactory.getLogger(BaseJob.class);

//    @Value("${data-runtime.server-ip:}")
    private String executServerIp;

    @Resource
    private SysBatchTaskRecordMapper sysBatchTaskRecordMapper;

    private static final Map<String, BaseJob> REGISTER = new HashMap<>();

    private static final List<Map<String, String>> DETAILS = new ArrayList<>();

    private CronTrigger trigger;

    @PostConstruct
    private final void register() {
        if (cronValidate()) {
            trigger = new CronTrigger(getCron());
        }
        synchronized (REGISTER) {
            REGISTER.put(getJobName(), this);
            Map<String, String> jobDetail = new HashMap<>();
            jobDetail.put("jobName", this.getJobName());
            jobDetail.put("cron", this.getCron());
            jobDetail.put("jobDesc", this.getJobDesc());
            DETAILS.add(jobDetail);
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (cronValidate()) {
            taskRegistrar.addTriggerTask(this, triggerContext -> trigger.nextExecutionTime(triggerContext));
        }
    }

    private boolean cronValidate() {
        return !StringUtils.isEmpty(getCron()) && !"-".equalsIgnoreCase(getCron());
    }

    public abstract String getCron();

    @Override
    public void run() {
        String serverIp = IPInfoUtils.getServerIp();
        if (!executServerIp.equalsIgnoreCase(serverIp)) {
            log.info("跑批任务指定ip:{},本机ip:{},跑批忽略", executServerIp, serverIp);
            return;
        }
        executeTask(getPreDate(), true);
    }

    public void executeTask() {
        executeTask(getPreDate());
    }

    public void executeTask(Date date) {
        executeTask(date, false);
    }

    protected void executeTask(Date date, Boolean autoExecute) {
        synchronized (this) {
            String serverIp = IPInfoUtils.getServerIp();
            SysBatchTaskRecord batchTaskRecord = new SysBatchTaskRecord();
            batchTaskRecord.setBeginTime(new Date());
            batchTaskRecord.setTaskName(getJobName());
            batchTaskRecord.setAutoExecute(autoExecute);
            batchTaskRecord.setParams(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date));
            batchTaskRecord.setIp(serverIp);

            log.info(getJobName() + "跑批开始");
            try {
                getAopProxy().execute(date);
                batchTaskRecord.setStatus(true);
                batchTaskRecord.setLog("跑批成功");
            } catch (Exception e) {
                log.error(getJobName() + "跑批异常", e);
                batchTaskRecord.setStatus(false);
                batchTaskRecord.setLog(e.getMessage());
            } finally {
                batchTaskRecord.setEndTime(new Date());
                batchTaskRecord.setExecuteTime((batchTaskRecord.getEndTime().getTime() - batchTaskRecord.getBeginTime().getTime()) / 1000);
                sysBatchTaskRecordMapper.insert(batchTaskRecord);
            }
        }
    }

    protected abstract void execute(Date date);

    protected Date getPreDate() {
        //获取前一天的日期
        LocalDate previousDay = LocalDate.now().minus(1, ChronoUnit.DAYS);
        Date date = java.sql.Date.from(previousDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    protected abstract String getJobDesc();

    protected String getJobName() {
        return this.getClass().getSimpleName();
    }

    public static Map<String, BaseJob> getRegister() {
        return REGISTER;
    }

    public static List<Map<String, String>> getDetails() {
        return DETAILS;
    }
}
