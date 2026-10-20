package com.kx.system.service;

import com.kx.common.core.domain.system.grid.*;

import java.util.List;

/**
 * @author lxk
 * @description
 * @date 2025/07/17
 */

public interface GridInfoService {


    List<GridInfo> getGridInfoList(GridInfo gridInfo);

    void importGridInfo();

    void exportGridInfo();

    GridInfo getGridInfoById(Long id);

    void updateGridInfo(GridInfo gridInfo);

    void updateGridInfoBranch(GridInfo gridInfo);

    /**
     * 新增网格重点监管对象
     */
    void insertManageObjects(GridInfoManageObjects gridInfoManageObjects);

    /**
     * 删除网格重点监管对象
     */
    void deleteManageObjects(Long id);

    /**
     * 修改网格重点监管对象
     */
    void updateManageObjects(GridInfoManageObjects gridInfoManageObjects);

    /**
     * 根据网格编码获取网格重点监管对象
     */
    List<GridInfoManageObjects> selectGridInfoManageObjectsList(GridInfoManageObjects gridInfoManageObjects);

    /**
     * 新增社会网格员
     */
    void insertCommunitySupervisor(GridInfoCommunitySupervisor gridInfoCommunitySupervisor);

    /**
     * 删除社会网格员
     *
     * @param id
     */
    void deleteCommunitySupervisor(Long id);

    /**
     * 修改社会网格员
     */
    void updateCommunitySupervisor(GridInfoCommunitySupervisor gridInfoCommunitySupervisor);

    /**
     * 根据网格编码查询社会网格员
     *
     * @param gridInfoCommunitySupervisor
     */
    List<GridInfoCommunitySupervisor> selectGridInfoCommunitySupervisorList(GridInfoCommunitySupervisor gridInfoCommunitySupervisor);


    /**
     * @return
     */
    void insertGrassrootsInfo(GridInfoGrassrootsInfo gridInfoGrassrootsInfo);

    /**
     * 删除基层组织
     */
    void deleteGrassrootsInfo(Long id);

    /**
     * 修改基层组织
     */
    void updateGrassrootsInfo(GridInfoGrassrootsInfo gridInfoGrassrootsInfo);

    /**
     * 根据网格编码查询基层组织
     */
    List<GridInfoGrassrootsInfo> selectGridInfoGrassrootsInfoList(GridInfoGrassrootsInfo gridInfoGrassrootsInfo);

    void insertGridInfoPolice(GridInfoPolice gridInfoPolice);

    /**
     * 删除派出所信息
     */
    void deleteGridInfoPoliceById(Long id);

    /**
     * 修改派出所信息
     */
    void updateGridInfoPolice(GridInfoPolice gridInfoPolice);

    /**
     * 根据网格编码查询派出所信息
     */
    List<GridInfoPolice> selectGridInfoPoliceList(GridInfoPolice gridInfoPolice);
}
