package com.kx.system.domain;

import com.kx.system.domain.enums.StatisticOperatorType;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class StatisticsOperator {
    private String tableName;
    private StatisticOperatorType operatorType;
    /**
     * 分组列表，按照每个分组字段各统计一次
     */
    private List<String> groupByColumn;
    private String statisticColumn;
    private Method resultSetMethod;
    private Class<?> resultType;

    public static StatisticsOperatorBuilder builder() {
        return new StatisticsOperatorBuilder();
    }

    public static class StatisticsOperatorBuilder {
        private StatisticsOperator o;

        public StatisticsOperatorBuilder() {
            this.o = new StatisticsOperator();
        }

        public StatisticsOperatorBuilder tableName(String tableName) {
            o.setTableName(tableName);
            return this;
        }

        public StatisticsOperatorBuilder operatorType(StatisticOperatorType operatorType) {
            o.setOperatorType(operatorType);
            return this;
        }

        public StatisticsOperatorBuilder groupByColumn(List<String> groupByColumn) {
            o.setGroupByColumn(groupByColumn);
            return this;
        }

        public StatisticsOperatorBuilder statisticColumn(String statisticColumn) {
            o.setStatisticColumn(statisticColumn);
            return this;
        }

        public StatisticsOperatorBuilder result(Class type, String methodName, Class paramType) throws NoSuchMethodException {
            o.setResultSetMethod(type.getMethod(methodName, paramType));
            o.setResultType(paramType);
            return this;
        }

        public StatisticsOperator build() {
            return o;
        }
    }
}
