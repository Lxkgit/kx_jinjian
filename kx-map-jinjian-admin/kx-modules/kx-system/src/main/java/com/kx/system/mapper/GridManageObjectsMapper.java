package com.kx.system.mapper;


import com.kx.system.domain.grid.message.GridManageObjects;
import com.kx.system.domain.GridManageObjects2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GridManageObjectsMapper {

    void insertManageObjects(GridManageObjects manageObjects);

    void deleteManageObjectsById(@Param("id") Integer id);

    void updateManageObjectsById(GridManageObjects manageObjects);

    GridManageObjects selectManageObjectsByGridCode(@Param("gridCode") Integer gridCode);

    Integer insert(GridManageObjects2 gridManageObjects);

    Integer deleteById(@Param("id") long id);

    Integer updateById(GridManageObjects2 gridManageObjects);

    List<GridManageObjects2> selectByGridCode(@Param("gridCode") String gridCode);

    List<GridManageObjects2> allGridManageObjects();
}