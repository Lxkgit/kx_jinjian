package com.kx.system.domain;

import com.kx.system.domain.enums.FtpSyncFileEnum;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class IndicatorSyncOperator {
    private FtpSyncFileEnum syncFile;
    private Method batchInsertMethod;
    private Method truncateMethod;
    private Object mapper;

    public static IndicatorSyncOperatorBuilder builder() {
        return new IndicatorSyncOperatorBuilder();
    }

    public static class IndicatorSyncOperatorBuilder {
        private IndicatorSyncOperator indicatorSyncOperator;

        public IndicatorSyncOperatorBuilder() {
            this.indicatorSyncOperator = new IndicatorSyncOperator();
        }

        public IndicatorSyncOperatorBuilder syncFile(FtpSyncFileEnum syncFile) {
            indicatorSyncOperator.setSyncFile(syncFile);
            return this;
        }

        public IndicatorSyncOperatorBuilder mapper(Object mapper) throws NoSuchMethodException {
            indicatorSyncOperator.setMapper(mapper);
            indicatorSyncOperator.setBatchInsertMethod(mapper.getClass().getMethod("batchInsert", List.class));
            if (indicatorSyncOperator.getSyncFile() != null && indicatorSyncOperator.getSyncFile().isFullData()) {
                indicatorSyncOperator.setTruncateMethod(mapper.getClass().getMethod("truncate"));
            }
            return this;
        }

        public IndicatorSyncOperator build() {
            return indicatorSyncOperator;
        }
    }

}
