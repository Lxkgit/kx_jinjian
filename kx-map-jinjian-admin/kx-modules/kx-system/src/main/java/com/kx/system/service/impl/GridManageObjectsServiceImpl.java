package com.kx.system.service.impl;


import com.kx.system.domain.GridManageObjects2;
import com.kx.system.mapper.GridManageObjectsMapper;
import com.kx.system.service.GridManageObjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GridManageObjectsServiceImpl implements GridManageObjectsService {

    @Autowired
    private GridManageObjectsMapper gridManageObjectsMapper;

    @Override
    public void addGridManageObjects(GridManageObjects2 gridManageObjects) {
        gridManageObjects.setCreateTime(LocalDateTime.now());
        gridManageObjects.setUpdateTime(LocalDateTime.now());
        gridManageObjectsMapper.insert(gridManageObjects);
    }

    @Override
    public void deleteGridManageObjectsById(long id) {
        gridManageObjectsMapper.deleteById(id);
    }


    @Override
    public List<GridManageObjects2> getGridManageObjectsByGridCode(String gridCode) {
        return gridManageObjectsMapper.selectByGridCode(gridCode);
    }

    @Override
    public void updateGridManageObjectsByGridCode(GridManageObjects2 gridManageObjects) {
        gridManageObjects.setUpdateTime(LocalDateTime.now());
        gridManageObjectsMapper.updateById(gridManageObjects);
    }

    @Override
    public List<GridManageObjects2> allGridManageObjects() {
        return gridManageObjectsMapper.allGridManageObjects();
    }
}