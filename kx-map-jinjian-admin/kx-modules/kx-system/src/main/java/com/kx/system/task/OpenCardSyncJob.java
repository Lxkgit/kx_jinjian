package com.kx.system.task;

import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.OpeningCardRecord;
import com.kx.system.domain.enums.FtpSyncFileEnum;
import com.kx.system.mapper.OpeningCardRecordMapper;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class OpenCardSyncJob extends BaseJob {
    @Resource
    private OpeningCardRecordMapper openingCardRecordMapper;

//    @Value("${data-runtime.OpenCardSyncJob:-}")
    private String cron;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date fileDate) {
        Date timeFlag = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String now = sdf.format(timeFlag);
        log.info("==========" + now + "开卡记录数据跑批开始==========");
        //ftp读取文件
        FtpUtil.getFileInputStream(FtpSyncFileEnum.OPEN_CARD_FILE_PATH, fileDate, () -> openingCardRecordMapper.clear(), is -> {
            InputStreamReader fileReader = new InputStreamReader(is);
            CSVParser parser = null;
            try {
                parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withDelimiter('|'));
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessValidationException("文件解析失败");
            }
            List<OpeningCardRecord> openingCardRecords = new ArrayList<>();
            for (CSVRecord record : parser) {
                OpeningCardRecord openingCardRecord = new OpeningCardRecord();
                openingCardRecord.setCustomNm(record.get(0));
                openingCardRecord.setCardNum(record.get(1));
                if ("1".equals(record.get(2))) {
                    openingCardRecord.setCardType("一类卡");
                } else if ("2".equals(record.get(2))) {
                    openingCardRecord.setCardType("二类卡");
                }
                String date = record.get(3);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (StringUtils.isNotEmpty(date)) {
                    try {
                        Date openDate = format.parse(date);
                        openingCardRecord.setCreateTime(openDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                openingCardRecord.setManagerNum(record.get(4));
                openingCardRecord.setManager(record.get(5));
                openingCardRecord.setEtlDate(new Date());
                openingCardRecords.add(openingCardRecord);
            }
            int size = 500;
            List<List<OpeningCardRecord>> records = splitRecordGroup(openingCardRecords, size);
            for (List<OpeningCardRecord> record : records) {
                openingCardRecordMapper.insertBatch(record);
            }
        });
        log.info("==========" + now + "开卡记录数据跑批结束==========");
    }

    /**
     * 分割集合
     *
     * @param records
     * @param size
     * @return
     */
    private List<List<OpeningCardRecord>> splitRecordGroup(List<OpeningCardRecord> records, int size) {
        List<List<OpeningCardRecord>> groupList = new ArrayList<>();
        for (int i = 0; i < records.size(); i += size) {
            int endIndex = Math.min(i + size, records.size());
            groupList.add(records.subList(i, endIndex));
        }
        return groupList;
    }

    @Override
    public String getJobDesc() {
        return "开卡记录数据跑批";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
