package com.kx.system.mapper;

import com.kx.system.domain.grid.GridCruxInfo;
import java.util.List;
/**
 * <p>
 * 网格关键信息维护表 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
public interface GridCruxInfoMapper {

    List<GridCruxInfo> list(GridCruxInfo gridCruxInfo);

    GridCruxInfo getById(Long id);

    Long insertGridCruxInfo(GridCruxInfo gridCruxInfo);

    Long updateGridCruxInfo(GridCruxInfo gridCruxInfo);

    void deleteById(Long id);
}
