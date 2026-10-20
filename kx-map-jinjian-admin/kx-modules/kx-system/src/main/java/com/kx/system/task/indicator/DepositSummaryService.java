package com.kx.system.task.indicator;

import com.kx.system.domain.small.PaViewDepositModule;
import com.kx.system.mapper.PaViewDepositModuleOrgGridMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 贷款模块指标汇总
 */
@Component
public class DepositSummaryService extends SummaryService<PaViewDepositModule> {

    @Resource
    private PaViewDepositModuleOrgGridMapper paViewDepositModuleOrgGridMapper;

    @Override
    protected void batchInsert(List<PaViewDepositModule> lists) {
        paViewDepositModuleOrgGridMapper.batchInsert(lists);
    }

    @Override
    protected List<PaViewDepositModule> listAllIndicators() {
        return paViewDepositModuleOrgGridMapper.list(new PaViewDepositModule());
        //只需要按照4级网格汇总
//        return paViewDepositModuleOrgGridMapper.listGridLevel4();
    }

    @Override
    boolean statisticed() {
        return paViewDepositModuleOrgGridMapper.statisticed();
    }

    @Override
    protected String getIndicatorCustomerType(PaViewDepositModule view) {
        return view.getCustomerType();
    }

    @Override
    protected String getIndicatorGridCode(PaViewDepositModule view) {
        return view.getGridCode();
    }

    @Override
    protected String getIndicatorOrgCode(PaViewDepositModule view) {
        return view.getOrgCode();
    }

    @Override
    protected PaViewDepositModule statisticSub(List<PaViewDepositModule> subLists, String orgNum, String gridCode, String customerType) {
        //此处作汇总逻辑
        PaViewDepositModule result = new PaViewDepositModule();
        result.setOrgCode(orgNum);
        result.setGridCode(gridCode);
        result.setCustomerType(customerType);
        result.setSaveFtp(sumStringByField(subLists, PaViewDepositModule::getSaveFtp, true));
        result.setSaveBalance(sumStringByField(subLists, PaViewDepositModule::getSaveBalance, false));
        result.setSaveBalanceLastMonth(sumStringByField(subLists, PaViewDepositModule::getSaveBalanceLastMonth, false));
        result.setSaveBalanceBeginYear(sumStringByField(subLists, PaViewDepositModule::getSaveBalanceBeginYear, false));
        result.setSaveDayAvg(sumStringByField(subLists, PaViewDepositModule::getSaveDayAvg, false));
        result.setSaveDayAvgLastMonth(sumStringByField(subLists, PaViewDepositModule::getSaveDayAvgLastMonth, false));
        result.setSaveDayAvgBeginYear(sumStringByField(subLists, PaViewDepositModule::getSaveDayAvgBeginYear, false));
        result.setDepositSaveBalance(sumStringByField(subLists, PaViewDepositModule::getDepositSaveBalance, false));
        result.setDepositSaveBalanceLastMonth(sumStringByField(subLists, PaViewDepositModule::getDepositSaveBalanceLastMonth, false));
        result.setDepositSaveBalanceBeginYear(sumStringByField(subLists, PaViewDepositModule::getDepositSaveBalanceBeginYear, false));
        result.setDepositSaveDayAvg(sumStringByField(subLists, PaViewDepositModule::getDepositSaveDayAvg, false));
        result.setDepositSaveDayAvgLastMonth(sumStringByField(subLists, PaViewDepositModule::getDepositSaveDayAvgLastMonth, false));
        result.setDepositSaveDayAvgBeginYear(sumStringByField(subLists, PaViewDepositModule::getDepositSaveDayAvgBeginYear, false));
        return result;
    }
}
