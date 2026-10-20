package com.kx.system.util;

import com.kx.system.domain.enums.FtpSyncFileEnum;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CsvReadUtil {

    public static <T> List<T> readCsv(InputStream is, FtpSyncFileEnum syncFile) {
        CsvToBeanBuilder builder = new CsvToBeanBuilder(new InputStreamReader(is));
        if (syncFile.isIncludeTitle()) {
            builder.withSkipLines(1);
        }
        CsvToBean<T> reader = builder.withType(syncFile.getBeanType())
                .withSeparator(syncFile.getSeparator())
                .build();
        return reader.parse();
    }

    public static <T> void streamBatchCsvFileToBean(InputStream is, FtpSyncFileEnum syncFile, int batchSize, Consumer<List<T>> consumer) {
        CsvToBeanBuilder builder = new CsvToBeanBuilder(new InputStreamReader(is));
        if (syncFile.isIncludeTitle()) {
            builder.withSkipLines(1);
        }
        CsvToBean<T> reader = builder.withType(syncFile.getBeanType())
                .withSeparator(syncFile.getSeparator())
                .build();
        List<T> tempList = new ArrayList<>();
        reader.stream().forEach(t -> {
            tempList.add(t);
            if (tempList.size() == batchSize) {
                consumer.accept(tempList);
                tempList.clear();
            }
        });
        if (!CollectionUtils.isEmpty(tempList)) {
            consumer.accept(tempList);
        }
    }

}
