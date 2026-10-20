package com.kx.system.service;


import com.kx.system.domain.GridGrassrootsInfo2;

import java.util.List;

public interface GridGrassrootsInfoService {
    GridGrassrootsInfo2 findById(Long id);
    List<GridGrassrootsInfo2> findByGridCode(String gridCode);
    List<GridGrassrootsInfo2> findAll();
    int insert(GridGrassrootsInfo2 gridGrassrootsInfo);
    int update(GridGrassrootsInfo2 gridGrassrootsInfo);
    int deleteById(Long id);
}