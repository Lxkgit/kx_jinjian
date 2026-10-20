package com.kx.system.service.impl;


import com.kx.system.domain.GridGrassrootsInfo2;
import com.kx.system.mapper.GridGrassrootsInfoMapper;
import com.kx.system.service.GridGrassrootsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GridGrassrootsInfoServiceImpl implements GridGrassrootsInfoService {

    @Autowired
    private GridGrassrootsInfoMapper gridGrassrootsInfoMapper;

    @Override
    public GridGrassrootsInfo2 findById(Long id) {
        return gridGrassrootsInfoMapper.findById(id);
    }

    @Override
    public List<GridGrassrootsInfo2> findByGridCode(String gridCode) {
        return gridGrassrootsInfoMapper.findByGridCode(gridCode);
    }

    @Override
    public List<GridGrassrootsInfo2> findAll() {
        return gridGrassrootsInfoMapper.findAll();
    }

    @Override
    public int insert(GridGrassrootsInfo2 gridGrassrootsInfo) {
        gridGrassrootsInfo.setCreateTime(LocalDateTime.now());
        gridGrassrootsInfo.setUpdateTime(LocalDateTime.now());
        return gridGrassrootsInfoMapper.insert(gridGrassrootsInfo);
    }

    @Override
    public int update(GridGrassrootsInfo2 gridGrassrootsInfo) {
        gridGrassrootsInfo.setUpdateTime(LocalDateTime.now());
        return gridGrassrootsInfoMapper.update(gridGrassrootsInfo);
    }

    @Override
    public int deleteById(Long id) {
        return gridGrassrootsInfoMapper.deleteById(id);
    }
}