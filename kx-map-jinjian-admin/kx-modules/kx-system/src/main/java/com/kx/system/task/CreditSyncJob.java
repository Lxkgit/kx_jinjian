package com.kx.system.task;

import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.CreditRecord;
import com.kx.system.domain.enums.FtpSyncFileEnum;
import com.kx.system.mapper.CreditRecordMapper;
import com.kx.system.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 授信记录跑批任务
 */
//@Component
@Slf4j
public class CreditSyncJob extends BaseJob {
    @Resource
    private CreditRecordMapper creditRecordMapper;

//    @Value("${data-runtime.CreditSyncJob:-}")
    private String cron;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date fileDate) {
        Date timeFlag = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String now = sdf.format(timeFlag);
        log.info("==========" + now + "开卡记录数据跑批开始==========");
        //产品id与名称关系映射
        HashMap<String, String> creditProduct = new HashMap<>();
        //信贷记录
        List<CreditRecord> creditRecords = new ArrayList<>();
        //读取信贷产品名称文件
        FtpUtil.getFileInputStream(FtpSyncFileEnum.CREDIT_PRODUCT_FILE_PATH, fileDate, is -> {
            InputStreamReader fileReader = new InputStreamReader(is);
            CSVParser parser = null;
            try {
                parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withDelimiter('|'));
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessValidationException("文件解析失败");
            }
            for (CSVRecord record : parser) {
                creditProduct.put(record.get(0), record.get(1));
            }
        });
        //读取信贷产品
        FtpUtil.getFileInputStream(FtpSyncFileEnum.CREDIT_FILE_PATH, fileDate, is -> {
            InputStreamReader fileReader = new InputStreamReader(is);
            CSVParser parser = null;
            try {
                parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withDelimiter('|'));
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessValidationException("文件解析失败");
            }
            for (CSVRecord record : parser) {
                CreditRecord creditRecord = new CreditRecord();
                creditRecord.setCustomerNm(record.get(0));
                creditRecord.setContractNum(record.get(1));
                String productId = record.get(2);
                if (StringUtils.isNotEmpty(productId)) {
                    creditRecord.setProductName(creditProduct.get(productId));
                }
                String date = record.get(3);
                if (StringUtils.isNotEmpty(date)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date creditTime = null;
                    try {
                        creditTime = format.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    creditRecord.setCreateTime(creditTime);
                }
                if (StringUtils.isNotEmpty(record.get(4))) {
                    Date expireDate = null;
                    SimpleDateFormat expireFormat = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        expireDate = expireFormat.parse(record.get(4));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    creditRecord.setExpireDate(expireDate);
                }
                creditRecord.setCreditAmount(BigDecimal.valueOf(Double.valueOf(record.get(5))));
                creditRecord.setLoanAmount(BigDecimal.valueOf(Double.valueOf(record.get(6))));
                creditRecord.setManagerNum(record.get(7));
                creditRecord.setManager(record.get(8));
                creditRecord.setEtlDate(new Date());
                creditRecords.add(creditRecord);
            }
        });
        //清空原数据
        creditRecordMapper.clear();
        int size = 500;
        List<List<CreditRecord>> records = splitCreditGroup(creditRecords, size);
        for (List<CreditRecord> record : records) {
            creditRecordMapper.insertBatch(record);
        }
    }

    @Override
    public String getJobDesc() {
        return "授信记录跑批任务";
    }

    @Override
    public String getCron() {
        return cron;
    }

    private List<List<CreditRecord>> splitCreditGroup(List<CreditRecord> creditRecords, int size) {
        List<List<CreditRecord>> groupList = new ArrayList<>();
        for (int i = 0; i < creditRecords.size(); i += size) {
            int endIndex = Math.min(i + size, creditRecords.size());
            groupList.add(creditRecords.subList(i, endIndex));
        }
        return groupList;
    }
}
