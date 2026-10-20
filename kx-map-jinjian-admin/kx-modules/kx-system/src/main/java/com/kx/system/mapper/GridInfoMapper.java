package com.kx.system.mapper;

import com.kx.common.core.domain.system.grid.GridInfo;

import java.util.List;

/**
 * @Description
 * @Author lxk
 * @CreateTime 2025-07-17
 */

public interface GridInfoMapper {

    void insertGridInfo(GridInfo gridInfo);

    void deleteGridInfoByGridCode(String gridCode);

    void updateGridInfoByGridCode(GridInfo gridInfo);

    GridInfo selectGridInfoByGridCode(String gridCode);

    List<GridInfo> selectGridInfoList(GridInfo GridInfo);





}
