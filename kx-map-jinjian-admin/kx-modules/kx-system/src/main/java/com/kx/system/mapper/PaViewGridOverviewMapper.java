package com.kx.system.mapper;

import com.kx.system.domain.small.PaViewGridOverview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小微视图-网格概况
 * </p>
 */
public interface PaViewGridOverviewMapper {
    void truncate();

    void batchInsert(@Param("views") List<PaViewGridOverview> items);

    PaViewGridOverview selectByCode(@Param("gridCode") String gridCode);

    PaViewGridOverview groupByCodes(@Param("gridCodes") List<String> gridCodes);
}
