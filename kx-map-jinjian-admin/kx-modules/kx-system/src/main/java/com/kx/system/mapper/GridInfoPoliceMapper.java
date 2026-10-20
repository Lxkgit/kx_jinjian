package com.kx.system.mapper;

import com.kx.common.core.domain.system.grid.GridInfoPolice;

import java.util.List;

/**
 * @author lxk
 * @description
 * @date 2025/07/29
 */

public interface GridInfoPoliceMapper {

    void insertGridInfoPolice(GridInfoPolice gridInfoPolice);

    void deleteGridInfoPoliceById(Long id);

    void updateGridInfoPolice(GridInfoPolice gridInfoPolice);

    List<GridInfoPolice> selectGridInfoPoliceList(GridInfoPolice gridInfoPolice);

}
