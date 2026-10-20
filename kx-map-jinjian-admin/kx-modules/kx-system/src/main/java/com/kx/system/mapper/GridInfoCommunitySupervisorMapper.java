package com.kx.system.mapper;

import com.kx.common.core.domain.system.grid.GridInfoCommunitySupervisor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxk
 * @description
 * @date 2025/07/28
 */

public interface GridInfoCommunitySupervisorMapper {

    void insertGridInfoCommunitySupervisor(GridInfoCommunitySupervisor gridInfoCommunitySupervisor);

    void deleteGridInfoCommunitySupervisorById(@Param("id") Long id);

    void updateGridInfoCommunitySupervisor(GridInfoCommunitySupervisor gridInfoCommunitySupervisor);

    List<GridInfoCommunitySupervisor> selectGridInfoCommunitySupervisorList(GridInfoCommunitySupervisor gridInfoCommunitySupervisor);

}
