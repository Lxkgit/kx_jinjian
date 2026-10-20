package com.kx.system.task.indicator;

import com.kx.system.domain.small.PaViewGridOverview;
import com.kx.system.mapper.PaViewGridOverviewOrgGridMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 网格概览模块指标汇总
 */
@Component
public class GridOverviewSummaryService extends SummaryService<PaViewGridOverview> {

    @Resource
    private PaViewGridOverviewOrgGridMapper paViewGridOverviewOrgGridMapper;

    @Override
    protected void batchInsert(List<PaViewGridOverview> lists) {
        paViewGridOverviewOrgGridMapper.batchInsert(lists);
    }

    @Override
    protected List<PaViewGridOverview> listAllIndicators() {
        return paViewGridOverviewOrgGridMapper.list(new PaViewGridOverview());
//        return paViewGridOverviewOrgGridMapper.listGridLevel4();
    }

    @Override
    boolean statisticed() {
        return paViewGridOverviewOrgGridMapper.statisticed();
    }


    @Override
    protected String getIndicatorCustomerType(PaViewGridOverview view) {
        return view.getCustomerType();
    }

    @Override
    protected String getIndicatorGridCode(PaViewGridOverview view) {
        return view.getGridCode();
    }

    @Override
    protected String getIndicatorOrgCode(PaViewGridOverview view) {
        return view.getOrgCode();
    }

    @Override
    protected PaViewGridOverview statisticSub(List<PaViewGridOverview> subLists, String orgNum, String gridCode, String customerType) {
        //此处作汇总逻辑
        PaViewGridOverview result = new PaViewGridOverview();
        result.setOrgCode(orgNum);
        result.setGridCode(gridCode);
        result.setCustomerType(customerType);

        result.setFtp(sumStringByField(subLists, PaViewGridOverview::getFtp, true));
        result.setCustomerTotalNum(sumIntegerByField(subLists, PaViewGridOverview::getCustomerTotalNum));
        result.setPerformanceCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getPerformanceCustomerNum));
        result.setPerformanceInsideCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getPerformanceInsideCustomerNum));
        result.setPerformanceInsideCustomerRatio(percentStr(result.getPerformanceInsideCustomerNum(), result.getPerformanceCustomerNum()));
        result.setPerformanceOutsideCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getPerformanceOutsideCustomerNum));
        result.setPerformanceOutsideCustomerRatio(percentStr(result.getPerformanceOutsideCustomerNum(), result.getPerformanceCustomerNum()));
        result.setCreditCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getCreditCustomerNum));
        result.setCreditInsideCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getCreditInsideCustomerNum));
        result.setCreditInsideCustomerRatio(percentStr(result.getCreditInsideCustomerNum(), result.getCreditCustomerNum()));
        result.setCreditOutsideCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getCreditOutsideCustomerNum));
        result.setCreditOutsideCustomerRatio(percentStr(result.getCreditOutsideCustomerNum(), result.getCreditCustomerNum()));
        result.setAcquireCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getAcquireCustomerNum));
        result.setAcquireInsideCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getAcquireInsideCustomerNum));
        result.setAcquireInsideCustomerRatio(percentStr(result.getAcquireInsideCustomerNum(), result.getAcquireCustomerNum()));
        result.setAcquireOutsideCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getAcquireOutsideCustomerNum));
        result.setAcquireOutsideCustomerRatio(percentStr(result.getAcquireOutsideCustomerNum(), result.getAcquireCustomerNum()));
        result.setValueCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getValueCustomerNum));
        result.setValueInsideCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getValueInsideCustomerNum));
        result.setValueInsideCustomerRatio(percentStr(result.getValueInsideCustomerNum(), result.getValueCustomerNum()));
        result.setValueOutsideCustomerNum(sumIntegerByField(subLists, PaViewGridOverview::getValueOutsideCustomerNum));
        result.setValueOutsideCustomerRatio(percentStr(result.getValueOutsideCustomerNum(), result.getValueCustomerNum()));
        return result;
    }
}
