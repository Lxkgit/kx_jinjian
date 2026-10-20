package com.kx.system.task.indicator;

import com.kx.system.domain.small.PaViewLoan;
import com.kx.system.mapper.PaViewLoanOrgGridMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 贷款模块指标汇总
 */
@Component
public class LoanSummaryService extends SummaryService<PaViewLoan> {

    @Resource
    private PaViewLoanOrgGridMapper paViewLoanOrgGridMapper;

    @Override
    protected void batchInsert(List<PaViewLoan> lists) {
        paViewLoanOrgGridMapper.batchInsert(lists);
    }

    @Override
    protected List<PaViewLoan> listAllIndicators() {
        return paViewLoanOrgGridMapper.list(new PaViewLoan());
//        return paViewLoanOrgGridMapper.listGridLevel4();
    }

    @Override
    boolean statisticed() {
        return paViewLoanOrgGridMapper.statisticed();
    }

    @Override
    protected String getIndicatorCustomerType(PaViewLoan view) {
        return view.getCustomerType();
    }

    @Override
    protected String getIndicatorGridCode(PaViewLoan view) {
        return view.getGridCode();
    }

    @Override
    protected String getIndicatorOrgCode(PaViewLoan view) {
        return view.getOrgCode();
    }

    @Override
    protected PaViewLoan statisticSub(List<PaViewLoan> subLists, String orgNum, String gridCode, String customerType) {
        //此处作汇总逻辑
        PaViewLoan result = new PaViewLoan();
        result.setOrgCode(orgNum);
        result.setGridCode(gridCode);
        result.setCustomerType(customerType);

        result.setLoanFtp(sumStringByField(subLists, PaViewLoan::getLoanFtp, true));
        result.setGrantCreditNum(sumIntegerByField(subLists, PaViewLoan::getGrantCreditNum));
        result.setGrantCreditCompareMonthNum(sumIntegerByField(subLists, PaViewLoan::getGrantCreditCompareMonthNum));
        result.setGrantCreditCompareYearNum(sumIntegerByField(subLists, PaViewLoan::getGrantCreditCompareYearNum));
        result.setGrantCreditGridInsideNum(sumIntegerByField(subLists, PaViewLoan::getGrantCreditGridInsideNum));
        result.setGrantCreditInsideCompareMonthNum(sumIntegerByField(subLists, PaViewLoan::getGrantCreditInsideCompareMonthNum));
        result.setGrantCreditInsideCompareYearNum(sumIntegerByField(subLists, PaViewLoan::getGrantCreditInsideCompareYearNum));
        result.setGrantCreditGridOutsideNum(sumIntegerByField(subLists, PaViewLoan::getGrantCreditGridOutsideNum));
        result.setGrantCreditOutsideCompareMonthNum(sumIntegerByField(subLists, PaViewLoan::getGrantCreditOutsideCompareMonthNum));
        result.setGrantCreditOutsideCompareYearNum(sumIntegerByField(subLists, PaViewLoan::getGrantCreditOutsideCompareYearNum));
        result.setUseCreditNum(sumIntegerByField(subLists, PaViewLoan::getUseCreditNum));
        result.setUseCreditCompareMonthNum(sumIntegerByField(subLists, PaViewLoan::getUseCreditCompareMonthNum));
        result.setUseCreditCompareYearNum(sumIntegerByField(subLists, PaViewLoan::getUseCreditCompareYearNum));
        result.setUseCreditGridInsideNum(sumIntegerByField(subLists, PaViewLoan::getUseCreditGridInsideNum));
        result.setUseCreditInsideCompareMonthNum(sumIntegerByField(subLists, PaViewLoan::getUseCreditInsideCompareMonthNum));
        result.setUseCreditInsideCompareYearNum(sumIntegerByField(subLists, PaViewLoan::getUseCreditInsideCompareYearNum));
        result.setUseCreditGridOutsideNum(sumIntegerByField(subLists, PaViewLoan::getUseCreditGridOutsideNum));
        result.setUseCreditOutsideCompareMonthNum(sumIntegerByField(subLists, PaViewLoan::getUseCreditOutsideCompareMonthNum));
        result.setUseCreditOutsideCompareYearNum(sumIntegerByField(subLists, PaViewLoan::getUseCreditOutsideCompareYearNum));
        result.setGrantCreditBal(sumStringByField(subLists, PaViewLoan::getGrantCreditBal, false));
        result.setGrantCreditCompareMonthBal(sumStringByField(subLists, PaViewLoan::getGrantCreditCompareMonthBal, false));
        result.setGrantCreditCompareYearBal(sumStringByField(subLists, PaViewLoan::getGrantCreditCompareYearBal, false));
        result.setGrantCreditGridInsideBal(sumStringByField(subLists, PaViewLoan::getGrantCreditGridInsideBal, false));
        result.setGrantCreditInsideCompareMonthBal(sumStringByField(subLists, PaViewLoan::getGrantCreditInsideCompareMonthBal, false));
        result.setGrantCreditInsideCompareYearBal(sumStringByField(subLists, PaViewLoan::getGrantCreditInsideCompareYearBal, false));
        result.setGrantCreditGridOutsideBal(sumStringByField(subLists, PaViewLoan::getGrantCreditGridOutsideBal, false));
        result.setGrantCreditOutsideCompareMonthBal(sumStringByField(subLists, PaViewLoan::getGrantCreditOutsideCompareMonthBal, false));
        result.setGrantCreditOutsideCompareYearBal(sumStringByField(subLists, PaViewLoan::getGrantCreditOutsideCompareYearBal, false));
        result.setUseCreditBal(sumStringByField(subLists, PaViewLoan::getUseCreditBal, false));
        result.setUseCreditCompareMonthBal(sumStringByField(subLists, PaViewLoan::getUseCreditCompareMonthBal, false));
        result.setUseCreditCompareYearBal(sumStringByField(subLists, PaViewLoan::getUseCreditCompareYearBal, false));
        result.setUseCreditGridInsideBal(sumStringByField(subLists, PaViewLoan::getUseCreditGridInsideBal, false));
        result.setUseCreditInsideCompareMonthBal(sumStringByField(subLists, PaViewLoan::getUseCreditInsideCompareMonthBal, false));
        result.setUseCreditInsideCompareYearBal(sumStringByField(subLists, PaViewLoan::getUseCreditInsideCompareYearBal, false));
        result.setUseCreditGridOutsideBal(sumStringByField(subLists, PaViewLoan::getUseCreditGridOutsideBal, false));
        result.setUseCreditOutsideCompareMonthBal(sumStringByField(subLists, PaViewLoan::getUseCreditOutsideCompareMonthBal, false));
        result.setUseCreditOutsideCompareYearBal(sumStringByField(subLists, PaViewLoan::getUseCreditOutsideCompareYearBal, false));
        result.setTotalBadBal(sumStringByField(subLists, PaViewLoan::getTotalBadBal, false));
        result.setTotalBadCompareMonthBal(sumStringByField(subLists, PaViewLoan::getTotalBadCompareMonthBal, false));
        result.setTotalBadCompareYearBal(sumStringByField(subLists, PaViewLoan::getTotalBadCompareYearBal, false));
        result.setTotalOverdueBal(sumStringByField(subLists, PaViewLoan::getTotalOverdueBal, false));
        result.setTotalOverdueCompareMonthBal(sumStringByField(subLists, PaViewLoan::getTotalOverdueCompareMonthBal, false));
        result.setTotalOverdueCompareYearBal(sumStringByField(subLists, PaViewLoan::getTotalOverdueCompareYearBal, false));
        result.setCreditNum(sumIntegerByField(subLists, PaViewLoan::getCreditNum));
        result.setCreditBal(sumStringByField(subLists, PaViewLoan::getCreditBal, false));
        result.setCreditBadBal(sumStringByField(subLists, PaViewLoan::getCreditBadBal, false));
        result.setCreditOverdueBal(sumStringByField(subLists, PaViewLoan::getCreditOverdueBal, false));
        result.setEnsureNum(sumIntegerByField(subLists, PaViewLoan::getEnsureNum));
        result.setEnsureBal(sumStringByField(subLists, PaViewLoan::getEnsureBal, false));
        result.setEnsureBadBal(sumStringByField(subLists, PaViewLoan::getEnsureBadBal, false));
        result.setEnsureOverdueBal(sumStringByField(subLists, PaViewLoan::getEnsureOverdueBal, false));
        result.setMortgageNum(sumIntegerByField(subLists, PaViewLoan::getMortgageNum));
        result.setMortgageBal(sumStringByField(subLists, PaViewLoan::getMortgageBal, false));
        result.setMortgageBadBal(sumStringByField(subLists, PaViewLoan::getMortgageBadBal, false));
        result.setMortgageOverdueBal(sumStringByField(subLists, PaViewLoan::getMortgageOverdueBal, false));
        result.setPledgeNum(sumIntegerByField(subLists, PaViewLoan::getPledgeNum));
        result.setPledgeBal(sumStringByField(subLists, PaViewLoan::getPledgeBal, false));
        result.setPledgeBadBal(sumStringByField(subLists, PaViewLoan::getPledgeBadBal, false));
        result.setPledgeOverdueBal(sumStringByField(subLists, PaViewLoan::getPledgeOverdueBal, false));
        //百分比统计
        result.setTotalBadRatio(percentStr(result.getTotalBadBal(), result.getUseCreditBal()));
        result.setTotalBadGridInsideRatio(result.getTotalBadRatio());
        result.setTotalBadGridOutsideRatio("0.00");
        result.setTotalOverdueRatio(percentStr(result.getTotalOverdueBal(), result.getUseCreditBal()));
        result.setTotalOverdueGridInsideRatio(result.getTotalOverdueRatio());
        result.setTotalOverdueGridOutsideRatio("0.00");
        result.setTotalInterestRatio(null);
        result.setTotalInterestGridInsideRatio(null);
        result.setTotalInterestGridOutsideRatio(null);
        result.setCreditBadRatio(percentStr(result.getCreditBadBal(), result.getCreditBal()));
        result.setCreditOverdueRatio(percentStr(result.getCreditOverdueBal(), result.getCreditBal()));
        result.setEnsureBadRatio(percentStr(result.getEnsureBadBal(), result.getEnsureBal()));
        result.setEnsureOverdueRatio(percentStr(result.getEnsureOverdueBal(), result.getEnsureBal()));
        result.setMortgageBadRatio(percentStr(result.getMortgageBadBal(), result.getMortgageBal()));
        result.setMortgageOverdueRatio(percentStr(result.getMortgageOverdueBal(), result.getMortgageBal()));
        result.setPledgeBadRatio(percentStr(result.getPledgeBadBal(), result.getPledgeBal()));
        result.setPledgeOverdueRatio(percentStr(result.getPledgeOverdueBal(), result.getPledgeBal()));
        result.setGrantCreditInsideNumRatio(percentStr(result.getGrantCreditGridInsideNum(), result.getGrantCreditNum()));
        result.setGrantCreditOutsideNumRatio(percentStr(result.getGrantCreditGridOutsideNum(), result.getGrantCreditNum()));
        result.setUseCreditInsideNumRatio(percentStr(result.getUseCreditGridInsideNum(), result.getUseCreditNum()));
        result.setUseCreditOutsideNumRatio(percentStr(result.getUseCreditGridOutsideNum(), result.getUseCreditNum()));
        result.setGrantCreditInsideBalRatio(percentStr(result.getGrantCreditGridInsideBal(), result.getGrantCreditBal()));
        result.setGrantCreditOutsideBalRatio(percentStr(result.getGrantCreditGridOutsideBal(), result.getGrantCreditBal()));
        result.setUseCreditInsideBalRatio(percentStr(result.getUseCreditGridInsideBal(), result.getUseCreditBal()));
        result.setUseCreditOutsideBalRatio(percentStr(result.getUseCreditGridOutsideBal(), result.getUseCreditBal()));
        return result;
    }
}
