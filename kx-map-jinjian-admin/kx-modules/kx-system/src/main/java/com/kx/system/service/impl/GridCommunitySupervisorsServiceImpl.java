package com.kx.system.service.impl;

import com.kx.system.domain.GridCommunitySupervisors2;
import com.kx.system.mapper.GridCommunitySupervisorsMapper;
import com.kx.system.service.GridCommunitySupervisorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GridCommunitySupervisorsServiceImpl implements GridCommunitySupervisorsService {

    @Autowired
    private GridCommunitySupervisorsMapper supervisorsMapper;

    @Override
    public int insertSupervisor(GridCommunitySupervisors2 supervisor) {
        supervisor.setCreateTime(LocalDateTime.now());
        supervisor.setUpdateTime(LocalDateTime.now());
        return supervisorsMapper.insert(supervisor);
    }

    @Override
    public int updateSupervisor(GridCommunitySupervisors2 supervisor) {
        supervisor.setUpdateTime(LocalDateTime.now());
        return supervisorsMapper.update(supervisor);
    }

    @Override
    public int deleteSupervisor(Long id) {
        return supervisorsMapper.delete(id);
    }

    @Override
    public List<GridCommunitySupervisors2> selectSupervisorByGridCode(String gridCode) {
        return supervisorsMapper.selectByGridCode(gridCode);
    }

    @Override
    public List<GridCommunitySupervisors2> selectAllSupervisors() {
        return supervisorsMapper.selectAll();
    }
}