package com.kx.system.mapper;

import com.kx.system.domain.param.PaViewSmallParam;
import com.kx.system.domain.small.PaViewGridOverview;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 小微视图-网格概况-网格、客户经理纬度 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
public interface PaViewGridOverviewManagerGridMapper {

    List<PaViewGridOverview> list(PaViewGridOverview paViewGridOverview);

    PaViewGridOverview getById(Long id);

    Long insertPaViewGridOverviewManagerGrid(PaViewGridOverview paViewGridOverview);

    Long batchInsert(@Param("paViewGridOverviews") List<PaViewGridOverview> paViewGridOverviews);

    void truncate();

    Long updatePaViewGridOverviewManagerGrid(PaViewGridOverview paViewGridOverview);

    void deleteById(Long id);

    PaViewGridOverview getByQuery(PaViewSmallParam paViewSmallParam);

}
