package com.kx.system.mapper;

import com.kx.system.api.domain.SysDeptExcel;
import com.kx.system.domain.GridExport;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.ExportGrid;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.grid.GridMessageInfo2;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 网格基础信息维护表 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
public interface GridMessageInfoMapper {

    List<GridMessageInfo2> list(GridMessageInfo2 gridMessageInfo);

    //新增查询总数
    Long countList(GridMessageInfo2 gridMessageInfo);

    GridMessageInfo2 getById(Long id);

    Long insertGridMessageInfo(GridMessageInfo2 gridMessageInfo);

    Long updateGridMessageInfo(GridMessageInfo2 gridMessageInfo);

    void deleteById(Long id);

    GridMessageInfo2 getByGridCode(@Param("gridCode") String gridCode);

    void UpdateBatchByDeptName(@Param("cachedDataList") List<SysDeptExcel> cachedDataList);

    String getByGridName(String gridName);

    List<AnchorPoint> getGridInfo(String gridCode);

    List<PaAddrInfoType> getAllByGridCode(String gridCode);

    List<GridExport> selectAllGrid(GridMessageInfo2 gridExport);

    List<ExportGrid> selectAllGrids(GridMessageInfo2 gridExport);
}
