package com.kx.system.task;

import com.kx.common.core.utils.SpringUtils;
import com.kx.system.api.domain.SysDept;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.JobCodeRelation;
import com.kx.system.domain.customer.CustomerLabel;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.enums.FtpSyncFileEnum;
import com.kx.system.mapper.*;
import com.kx.system.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
@Slf4j
public class CustomerUpdateJob extends BaseJob {
    @Resource
    private CustomInfoSyncJob customInfoSyncJob;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private DeptLocationInfoMapper deptLocationInfoMapper;

    @Resource
    private JobCodeRelationMapper jobCodeRelationMapper;

    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;

    @Resource
    private CustomerLabelMapper customerLabelMapper;

//    @Value("${data-runtime.CustomerUpdateJob}")
    private String cron;


    @Override
    protected void execute(Date date){
        //待使用参数
        List<SysDept> depts = sysDeptMapper.selectAll();
        Map<String, String> deptMap = depts.stream().collect(Collectors.toMap(SysDept::getOrgNum, SysDept::getDeptName));
        Map<String, Long> deptIdCodeMap = depts.stream().collect(Collectors.toMap(SysDept::getOrgNum, SysDept::getDeptId));

        List<JobCodeRelation> jobCodeRelations = jobCodeRelationMapper.listAll();
        Map<String, String> jobCode = jobCodeRelations.stream().collect(Collectors.toMap(JobCodeRelation::getJobCode, JobCodeRelation::getJobName));

        //设置批次
        int size = 500;
        //待更新绩效列表
        List<PerformanceCustomerInfo> newPerformanceCustomerInfos = new ArrayList<>();
        //待更新授信列表
        List<CreditCustomerInfo> newCreditCustomerInfos = new ArrayList<>();
        //修改后绩效客户标签关联
        List<CustomerLabel> newPerLabels = new ArrayList<>();
        //修改后授信客户标签关联
        List<CustomerLabel> newCreLabels = new ArrayList<>();
        FtpUtil.getFileInputStream(FtpSyncFileEnum.CUSTOMER_FILE_PATH, date, inputStream -> {
            Date timeFlag = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String now = sdf.format(timeFlag);
            log.info("==========" + now + "客户数据开始更新==========");
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            CSVParser parser = null;
            try {
                parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withDelimiter('|').withQuote(null));
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (CSVRecord record : parser) {
                String flag = record.get(51);

                //修改
                if ("1".equals(flag)) {
                    if ("1".equals(record.get(49))) {
                        //待更新绩效客户构建
                        PerformanceCustomerInfo newPerformance = customInfoSyncJob.createPerformance(sdf, deptMap, deptIdCodeMap, jobCode, record, newPerLabels);
                        newPerformance.setUpdateTime(new Date());
                        newPerformanceCustomerInfos.add(newPerformance);
                        //到达预定批次大小，执行更新，删除原系统标签，清空绩效客户list
                        if(newPerformanceCustomerInfos.size() == size){
                            dealPerinfo(newPerformanceCustomerInfos, newPerLabels);
                            newPerformanceCustomerInfos.clear();
                            newPerLabels.clear();
                        }
                    }
                    if ("1".equals(record.get(48))) {
                        //待更新授信客户构建
                        CreditCustomerInfo newCredit = customInfoSyncJob.createCredit(sdf, deptMap, deptIdCodeMap, jobCode, record, newCreLabels);
                        newCredit.setUpdateTime(new Date());
                        newCreditCustomerInfos.add(newCredit);
                        //到达预定批次大小，执行更新，删除原系统标签，清空授信客户list
                        if(newCreditCustomerInfos.size() == size){
                            dealCreInfo(newCreditCustomerInfos, newCreLabels);
                            newCreditCustomerInfos.clear();
                            newCreLabels.clear();
                        }
                    }
                }
            }
            //处理未达到批次大小的剩余客户
            dealPerinfo(newPerformanceCustomerInfos, newPerLabels);
            dealCreInfo(newCreditCustomerInfos, newCreLabels);

            //更新绩效客户标签关联表客户id
            customerLabelMapper.updatePerCustomerIdByNmAndType();
            //更新授信客户标签关联表客户id
            customerLabelMapper.updateCreCustomerIdByNmAndType();

            log.info("==========" + now + "客户数据更新结束==========");
        });
    }

    /**
     * 批量更新授信客户信息，删除原系统标签，添加新系统标签
     * @param newCreditCustomerInfos
     * @param newCreLabels
     */
    private void dealCreInfo(List<CreditCustomerInfo> newCreditCustomerInfos, List<CustomerLabel> newCreLabels) {
        for (CreditCustomerInfo newCreditCustomerInfo : newCreditCustomerInfos) {
            creditCustomerInfoMapper.updateFtpCredit(newCreditCustomerInfo);
        }
        List<String> creNms = newCreditCustomerInfos.stream().map(CreditCustomerInfo::getCustomerNm).collect(Collectors.toList());
        customerLabelMapper.deleteByNmsAndType(creNms, "2");
        customerLabelMapper.insertBatch(newCreLabels);
    }

    /**
     * 批量更新绩效客户信息，删除原系统标签，添加新系统标签
     * @param newPerformanceCustomerInfos
     * @param newPerLabels
     */
    private void dealPerinfo(List<PerformanceCustomerInfo> newPerformanceCustomerInfos, List<CustomerLabel> newPerLabels) {
        for (PerformanceCustomerInfo newPerformanceCustomerInfo : newPerformanceCustomerInfos) {
            performanceCustomerInfoMapper.updateFtpPerformance(newPerformanceCustomerInfo);
        }
        List<String> perNms = newPerformanceCustomerInfos.stream().map(PerformanceCustomerInfo::getCustomerNm).collect(Collectors.toList());
        customerLabelMapper.deleteByNmsAndType(perNms, "1");
        customerLabelMapper.insertBatch(newPerLabels);
    }

    @Override
    public String getJobDesc() {
        return "待更新客户跑批任务";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
