package com.kx.system.mapper;

import com.kx.common.core.domain.system.grid.GridInfoManageObjects;

import java.util.List;

/**
 * @author lxk
 * @description 重点监管对象
 * @date 2025/07/28
 */

public interface GridInfoManageObjectsMapper {

    void insertGridInfoManageObjects(GridInfoManageObjects gridInfoManageObjects);

    void deleteGridInfoManageObjectsById(Long id);

    void updateGridInfoManageObjects(GridInfoManageObjects gridInfoManageObjects);

    List<GridInfoManageObjects> selectGridInfoManageObjectsList(GridInfoManageObjects gridInfoManageObjects);
}
