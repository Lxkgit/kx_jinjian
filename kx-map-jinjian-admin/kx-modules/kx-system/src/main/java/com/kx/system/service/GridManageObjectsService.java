package com.kx.system.service;


import com.kx.system.domain.GridManageObjects2;

import java.util.List;

public interface GridManageObjectsService {

    void addGridManageObjects(GridManageObjects2 gridManageObjects);

    void deleteGridManageObjectsById(long id);

    List<GridManageObjects2> getGridManageObjectsByGridCode(String gridCode);

    void updateGridManageObjectsByGridCode(GridManageObjects2 gridManageObjects);

    List<GridManageObjects2> allGridManageObjects();
}