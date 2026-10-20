package com.kx.system.mapper;


import com.kx.system.domain.GridGrassrootsInfo2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GridGrassrootsInfoMapper {


    GridGrassrootsInfo2 findById(@Param("id") Long id);

    List<GridGrassrootsInfo2> findByGridCode(@Param("gridCode") String gridCode);

    List<GridGrassrootsInfo2> findAll();

    int insert(GridGrassrootsInfo2 gridGrassrootsInfo);

    int update(GridGrassrootsInfo2 gridGrassrootsInfo);

    int deleteById(@Param("id") Long id);
}