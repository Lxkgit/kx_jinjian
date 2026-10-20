package com.kx.system.mapper;

import com.kx.system.domain.GridCommunityAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GridCommunityAdminMapper {
    int insert(GridCommunityAdmin admin);
    int update(GridCommunityAdmin admin);
    int delete(@Param("id")  Long id);
    List<GridCommunityAdmin> selectByGridCode(@Param("gridCode") String gridCode);
    List<GridCommunityAdmin>selectAll();
}