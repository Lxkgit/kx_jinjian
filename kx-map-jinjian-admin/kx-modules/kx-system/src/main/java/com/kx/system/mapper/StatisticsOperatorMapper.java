package com.kx.system.mapper;

import java.util.List;

import com.kx.system.domain.small.PaViewGridOverview;
import org.apache.ibatis.annotations.Param;

import com.kx.system.domain.StatisticsValue;

/**
 * @author 王滨琦
 * @description 统计操作通用Mapper
 * @createDate 2024-04-18 16:14:02
 * @Entity com.kx.system.domain.StatisticsValue
 */
public interface StatisticsOperatorMapper {

    List<StatisticsValue> countByColumn(@Param("tableName") String tableName,
                                        @Param("groupByColumn") String groupByColumn);

    List<StatisticsValue> sumByColumn(@Param("tableName") String tableName, @Param("sumColumn") String sumColumn,
                                      @Param("groupByColumn") String groupByColumn);

    List<PaViewGridOverview> selectOverview(@Param("tableName") String tableName);

    void update(@Param("tableName") String tableName, @Param("o") PaViewGridOverview paViewGridOverview);

    void batchInsert(@Param("tableName") String tableName, @Param("os") List<PaViewGridOverview> os);
}
