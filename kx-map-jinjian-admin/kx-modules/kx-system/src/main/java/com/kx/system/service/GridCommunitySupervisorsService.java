package com.kx.system.service;

import com.kx.system.domain.GridCommunitySupervisors2;

import java.util.List;

public interface GridCommunitySupervisorsService {
    int insertSupervisor(GridCommunitySupervisors2 supervisor);
    int updateSupervisor(GridCommunitySupervisors2 supervisor);
    int deleteSupervisor(Long id);
    List<GridCommunitySupervisors2> selectSupervisorByGridCode(String gridCode);
    List<GridCommunitySupervisors2>selectAllSupervisors();
}