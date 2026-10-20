package com.kx.system.mapper;

import com.kx.system.domain.grid.message.GridCommunitySupervisors;
import com.kx.system.domain.GridCommunitySupervisors2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GridCommunitySupervisorsMapper {

    void insertCommunitySupervisors(GridCommunitySupervisors gridCommunitySupervisors);

    void deleteCommunitySupervisorsById(@Param("id") Long id);

    void updateCommunitySupervisors(GridCommunitySupervisors gridCommunitySupervisors);

    GridCommunitySupervisors selectCommunitySupervisorsByGridCode(@Param("gridCode") String gridCode);

    int insert(GridCommunitySupervisors2 supervisor);
    int update(GridCommunitySupervisors2 supervisor);
    int delete(@Param("id") Long id);
    List<GridCommunitySupervisors2> selectByGridCode(@Param("gridCode") String gridCode);
    List<GridCommunitySupervisors2>selectAll();
}