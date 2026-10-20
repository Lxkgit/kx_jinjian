package com.kx.system.task.indicator;

import com.kx.common.core.utils.SpringUtils;
import com.kx.system.domain.IndicatorSyncOperator;
import com.kx.system.mapper.*;
import com.kx.system.util.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.kx.system.domain.enums.FtpSyncFileEnum.*;

@Component
public class IndicatorSyncService {
    private static final Logger log = LoggerFactory.getLogger(IndicatorSyncService.class);

    @Resource
    private PaViewDepositModuleManagerMapper paViewDepositModuleManagerMapper;
    @Resource
    private PaViewDepositModuleManagerGridMapper paViewDepositModuleManagerGridMapper;
    @Resource
    private PaViewDepositModuleOrgMapper paViewDepositModuleOrgMapper;
    @Resource
    private PaViewDepositModuleOrgGridMapper paViewDepositModuleOrgGridMapper;
    @Resource
    private PaViewGridOverviewManagerMapper paViewGridOverviewManagerMapper;
    @Resource
    private PaViewGridOverviewManagerGridMapper paViewGridOverviewManagerGridMapper;
    @Resource
    private PaViewGridOverviewOrgMapper paViewGridOverviewOrgMapper;
    @Resource
    private PaViewGridOverviewOrgGridMapper paViewGridOverviewOrgGridMapper;
    @Resource
    private PaViewLoanManagerMapper paViewLoanManagerMapper;
    @Resource
    private PaViewLoanManagerGridMapper paViewLoanManagerGridMapper;
    @Resource
    private PaViewLoanOrgMapper paViewLoanOrgMapper;
    @Resource
    private PaViewLoanOrgGridMapper paViewLoanOrgGridMapper;

    List<IndicatorSyncOperator> operators = new ArrayList<>();

    @PostConstruct
    private void init() throws NoSuchMethodException {
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_DEPOSIT_MODULE_MANAGER).mapper(paViewDepositModuleManagerMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_DEPOSIT_MODULE_MANAGER_GRID).mapper(paViewDepositModuleManagerGridMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_DEPOSIT_MODULE_ORG).mapper(paViewDepositModuleOrgMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_DEPOSIT_MODULE_ORG_GRID).mapper(paViewDepositModuleOrgGridMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_GRID_OVERVIEW_MANAGER).mapper(paViewGridOverviewManagerMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_GRID_OVERVIEW_MANAGER_GRID).mapper(paViewGridOverviewManagerGridMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_GRID_OVERVIEW_ORG).mapper(paViewGridOverviewOrgMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_GRID_OVERVIEW_ORG_GRID).mapper(paViewGridOverviewOrgGridMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_LOAN_MANAGER).mapper(paViewLoanManagerMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_LOAN_MANAGER_GRID).mapper(paViewLoanManagerGridMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_LOAN_ORG).mapper(paViewLoanOrgMapper).build());
        operators.add(IndicatorSyncOperator.builder().syncFile(PA_VIEW_LOAN_ORG_GRID).mapper(paViewLoanOrgGridMapper).build());
    }

    public void syncIndicator(Date date) {
        for (IndicatorSyncOperator operator : operators) {
            SpringUtils.getAopProxy(this).syncIndicator(operator, date);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void syncIndicator(IndicatorSyncOperator operator, Date date) {
        FtpUtil.streamBatchFtpCsvFileToBean(operator.getSyncFile(), date, 50, () -> {
            if (operator.getSyncFile().isFullData()) {
                try {
                    operator.getTruncateMethod().invoke(operator.getMapper());
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        }, list -> {
            try {
                operator.getBatchInsertMethod().invoke(operator.getMapper(), list);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
