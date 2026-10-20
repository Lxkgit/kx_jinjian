package com.kx.system.task;

import com.kx.system.task.indicator.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class IndicatorSyncJob extends BaseJob {
    @Resource
    private IndicatorSyncService indicatorSyncService;

    @Resource
    private LoanSummaryService loanSummaryService;
    @Resource
    private DepositSummaryService depositStatisticService;
    @Resource
    private GridOverviewSummaryService gridOverviewSummaryService;

    @Resource
    private GridInfoStatisticService gridInfoStatisticService;
    @Resource
    private FilingCustomerIndicatorSyncService filingCustomerIndicatorSyncService;

//    @Value("${data-runtime.IndicatorSyncJob:-}")
    private String cron;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date date) {
        syncFromFtp(date);
        summaryByOrg();
        statisticGridInfo();
        filingGridStatistic();
    }

    public void syncFromFtp(Date date) {
        indicatorSyncService.syncIndicator(date);
    }

    /**
     * 机构-网格维度 按照机构层级汇总
     */
    public void summaryByOrg() {
        //贷款
        loanSummaryService.summaryByOrg();
        //存款
        depositStatisticService.summaryByOrg();
        //网格
        gridOverviewSummaryService.summaryByOrg();
    }

    /**
     * 统计网格内资源（子网格、户数、商圈等..）
     */
    public void statisticGridInfo() {
        gridInfoStatisticService.syncPaViewGridStatistic();
    }

    /**
     * 统计建档客户信息
     */
    public void filingGridStatistic() {
        filingCustomerIndicatorSyncService.syncFilingGridStatistic();
    }

    @Override
    public String getJobDesc() {
        return "同步首页指标信息";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
