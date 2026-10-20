package com.kx.system.mapper;

import com.kx.common.core.domain.system.grid.GridInfoGrassrootsInfo;

import java.util.List;

/**
 * @author lxk
 * @description
 * @date 2025/07/29
 */

public interface GridInfoGrassrootsInfoMapper {

    void insertGridInfoGrassrootsInfo(GridInfoGrassrootsInfo gridInfoGrassrootsInfo);

    void deleteGridInfoGrassrootsInfoById(Long id);

    void updateGridInfoGrassrootsInfo(GridInfoGrassrootsInfo gridInfoGrassrootsInfo);

    List<GridInfoGrassrootsInfo> selectGridInfoGrassrootsInfoList(GridInfoGrassrootsInfo gridInfoGrassrootsInfo);

}
