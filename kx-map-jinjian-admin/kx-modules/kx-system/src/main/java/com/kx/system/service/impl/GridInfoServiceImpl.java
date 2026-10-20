package com.kx.system.service.impl;

import com.kx.common.core.domain.system.grid.*;
import com.kx.system.mapper.*;
import com.kx.system.service.GridInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author lxk
 * @CreateTime 2025-07-17
 */

@Service
public class GridInfoServiceImpl implements GridInfoService {

    @Resource
    private GridInfoMapper gridInfoMapper;

    @Resource
    private GridInfoManageObjectsMapper gridInfoManageObjectsMapper;

    @Resource
    private GridInfoCommunitySupervisorMapper gridInfoCommunitySupervisorMapper;

    @Resource
    private GridInfoGrassrootsInfoMapper gridInfoGrassrootsInfoMapper;

    @Resource
    private GridInfoPoliceMapper gridInfoPoliceMapper;

    @Override
    public List<GridInfo> getGridInfoList(GridInfo gridInfo) {
        return gridInfoMapper.selectGridInfoList(gridInfo);
    }

    @Override
    public void importGridInfo() {
    }

    @Override
    public void exportGridInfo() {
    }

    @Override
    public GridInfo getGridInfoById(Long id) {
        return null;
    }

    @Override
    public void updateGridInfo(GridInfo gridInfo) {
        gridInfoMapper.updateGridInfoByGridCode(gridInfo);
    }

    @Override
    public void updateGridInfoBranch(GridInfo gridInfo) {
        gridInfoMapper.updateGridInfoByGridCode(gridInfo);
    }

    @Override
    public void insertManageObjects(GridInfoManageObjects gridInfoManageObjects) {
        gridInfoManageObjectsMapper.insertGridInfoManageObjects(gridInfoManageObjects);
    }

    @Override
    public void deleteManageObjects(Long id) {
        gridInfoManageObjectsMapper.deleteGridInfoManageObjectsById(id);
    }

    @Override
    public void updateManageObjects(GridInfoManageObjects gridInfoManageObjects) {
        gridInfoManageObjectsMapper.updateGridInfoManageObjects(gridInfoManageObjects);
    }

    @Override
    public List<GridInfoManageObjects> selectGridInfoManageObjectsList(GridInfoManageObjects gridInfoManageObjects) {
        return gridInfoManageObjectsMapper.selectGridInfoManageObjectsList(gridInfoManageObjects);
    }

    @Override
    public void insertCommunitySupervisor(GridInfoCommunitySupervisor gridInfoCommunitySupervisor) {
        gridInfoCommunitySupervisorMapper.insertGridInfoCommunitySupervisor(gridInfoCommunitySupervisor);
    }

    @Override
    public void deleteCommunitySupervisor(Long id) {
        gridInfoCommunitySupervisorMapper.deleteGridInfoCommunitySupervisorById(id);
    }

    @Override
    public void updateCommunitySupervisor(GridInfoCommunitySupervisor gridInfoCommunitySupervisor) {
        gridInfoCommunitySupervisorMapper.updateGridInfoCommunitySupervisor(gridInfoCommunitySupervisor);
    }

    @Override
    public List<GridInfoCommunitySupervisor> selectGridInfoCommunitySupervisorList(GridInfoCommunitySupervisor gridInfoCommunitySupervisor) {
        return gridInfoCommunitySupervisorMapper.selectGridInfoCommunitySupervisorList(gridInfoCommunitySupervisor);
    }

    @Override
    public void insertGrassrootsInfo(GridInfoGrassrootsInfo gridInfoGrassrootsInfo) {
        gridInfoGrassrootsInfoMapper.insertGridInfoGrassrootsInfo(gridInfoGrassrootsInfo);
    }

    @Override
    public void deleteGrassrootsInfo(Long id) {
        gridInfoGrassrootsInfoMapper.deleteGridInfoGrassrootsInfoById(id);
    }

    @Override
    public void updateGrassrootsInfo(GridInfoGrassrootsInfo gridInfoGrassrootsInfo) {
        gridInfoGrassrootsInfoMapper.updateGridInfoGrassrootsInfo(gridInfoGrassrootsInfo);
    }

    @Override
    public List<GridInfoGrassrootsInfo> selectGridInfoGrassrootsInfoList(GridInfoGrassrootsInfo gridInfoGrassrootsInfo) {
        return gridInfoGrassrootsInfoMapper.selectGridInfoGrassrootsInfoList(gridInfoGrassrootsInfo);
    }

    @Override
    public void insertGridInfoPolice(GridInfoPolice gridInfoPolice) {
        gridInfoPoliceMapper.insertGridInfoPolice(gridInfoPolice);
    }

    @Override
    public void deleteGridInfoPoliceById(Long id) {
        gridInfoPoliceMapper.deleteGridInfoPoliceById(id);
    }

    @Override
    public void updateGridInfoPolice(GridInfoPolice gridInfoPolice) {
        gridInfoPoliceMapper.updateGridInfoPolice(gridInfoPolice);
    }

    @Override
    public List<GridInfoPolice> selectGridInfoPoliceList(GridInfoPolice gridInfoPolice) {
        return gridInfoPoliceMapper.selectGridInfoPoliceList(gridInfoPolice);
    }
}
