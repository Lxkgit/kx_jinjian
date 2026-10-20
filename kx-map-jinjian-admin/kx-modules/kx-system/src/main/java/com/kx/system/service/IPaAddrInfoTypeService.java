package com.kx.system.service;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.domain.shop.dto.GridInfoDto;
import com.kx.system.api.domain.shop.dto.OrgGridInfoDto;
import com.kx.system.api.domain.shop.param.GridInfoParam;
import com.kx.system.api.domain.shop.param.OrgGridInfoParam;
import com.kx.system.domain.PaAddrInfo;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.boundary.BoundaryData;
import com.kx.system.domain.param.SearchAddrInfoUserParam;

import com.kx.system.domain.vo.AreaSplitPointVO;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 网格管理Service
 *
 * @author gary
 * @date 2021-12-02
 */
public interface IPaAddrInfoTypeService {

    /**
     * 初始化网格体系下的网格
     */
    void initAddrInfoType(PaAddrInfoType paAddrInfoType);

    /**
     * 获取网格下级数据-特色网格
     *
     * @return
     */
    List<PaAddrInfoType> getPaAddrInfoTypeList(PaAddrInfoType paAddrInfoType);

    List<PaAddrInfoType> getPaAddrInfoTypeLists(PaAddrInfoType paAddrInfoType);

    /**
     * 获取网格下级数据-特色网格
     *
     * @return
     */
    List<PaAddrInfoType> getPaAddrInfoType(PaAddrInfoType paAddrInfoType);

    /**
     * 获取网格下级数据-基础网格
     *
     * @return
     */
    List<PaAddrInfo> getPaAddrInfoList(PaAddrInfo paAddrInfo);

    /**
     * 获取所有网格管理员
     *
     * @param param
     * @return
     */
    List<SysUser> getPaAddrInfoTypeUser(SearchAddrInfoUserParam param);

    /**
     * 获取网格下级五级网格数据
     *
     * @return
     */
    List<PaAddrInfoType> getAddrInfoTypeBottom(PaAddrInfoType paAddrInfoType);

    /**
     * 边界调整
     */
    int update(PaAddrInfoType paAddrInfoType);

    /**
     * 边界批量调整
     */
    void updateBatch(List<PaAddrInfoType> paAddrInfoTypeList);

    /**
     * 新增网格
     */
    int add(PaAddrInfoType paAddrInfoType);

    /**
     * 删除网格
     */
    void delete(PaAddrInfoType paAddrInfoType);

    /**
     * 新建子网格
     */
    int addChild(List<PaAddrInfoType> paAddrInfoTypeList);

    /**
     * 拆分网格
     */
    void splitGrid(List<PaAddrInfoType> paAddrInfoTypeList);

    /**
     * 切分网格
     */
    void cutGrid(List<PaAddrInfoType> paAddrInfoTypeList);

    /**
     * 合并子网格
     */
    void mergeChildGrid(PaAddrInfoType paAddrInfoType);

    /**
     * 清空子网格
     */
    void cleanChildGrid(PaAddrInfoType paAddrInfoType);

    /**
     * 根据网格编码获取边界数据
     *
     * @param areaCode     网格编码
     * @param jurisdiction 是否下辖 0-本级，1-下级，2-本级及下辖
     * @return
     */
    BoundaryData getCoordinatesByCode(String areaCode, String typeCode, String jurisdiction);

    /**
     * 根据网格编码获取边界数据
     *
     * @param areaCode     网格编码
     * @param jurisdiction 是否下辖 0-本级，1-下级，2-本级及下辖
     * @return
     */
    BoundaryData getBaseCoordinatesByCode(String areaCode, String jurisdiction);

    /**
     * 获取默认的网格体系ID
     *
     * @return 默认网格体系ID
     */
    Long getDefaultAddrInfoTypeId();

    /**
     * 修改网格员
     *
     * @param paAddrInfoType
     */
    AjaxResult updateGridUser(PaAddrInfoType paAddrInfoType);

    /**
     * 批量辖区分配
     *
     * @param paAddrInfoTypeList
     */
    void updateGridDept(List<PaAddrInfoType> paAddrInfoTypeList);

    /**
     * 根据当前网格编码获取本级及下下网格编码
     *
     * @param gridCode
     * @return
     */
    List<PaAddrInfoType> getSelfAndChild(String gridCode);

    /**
     * 获取所有的网格信息
     *
     * @return
     */
    List<PaAddrInfoType> listAllAddrInfoWithOutCoordinates();

    Set<String> getPaAddrInfoTypeListByUser(SysUser user);

    Set<String> getPaAddrInfoTypeListByDeptCode(String deptCode);

    Set<String> getPaAddrInfoTypeListByManagerCode(String managerCode);

    /**
     * 网格去重，如果网格存在父子关系，则过滤掉子网格
     */
    void filterChildPaAddrInfoType(List<PaAddrInfoType> paAddrInfoTypes);

    void reOrgAncestorCodes();

    HashMap<String, String> getMinGridCount(String gridCode);

    void updateGridDepts(List<PaAddrInfoType> paAddrInfoTypeList);

    List<OrgGridInfoDto> getPaAddrInfoTypeByOrgCode(OrgGridInfoParam orgGridInfoParam);

    List<GridInfoDto> getGridInfo(GridInfoParam gridInfoParam);

    GridInfoDto getGridInfoByCode(String gridCode);

    /**
     * 根据行政区划编码查询区域，将每个区域的边界垂直平分为左右两块，返回两块的中心点
     *
     * @param areaCode  行政区划编码
     * @param isCurrent true-本级，false-下辖
     * @return 点位列表，每个区域生成两个点（pointType=1左，pointType=2右）
     */
    List<AreaSplitPointVO> getAreaSplitPoints(String areaCode, Boolean isCurrent);
}
