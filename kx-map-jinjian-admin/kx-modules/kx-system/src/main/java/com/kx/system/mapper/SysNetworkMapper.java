package com.kx.system.mapper;

import com.kx.system.domain.model.SysNetwork;
import com.kx.system.domain.vo.KpiTargetVO;
import com.kx.system.domain.vo.SysNetworkVo;
import com.kx.system.domain.vo.TaskSelectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构管理 数据层
 *
 * @author ruoyi
 */
@Mapper
public interface SysNetworkMapper
{
    /**
     * 查询机构管理数据
     *
     * @param network 机构信息
     * @return 机构信息集合
     */
    public List<SysNetwork> selectNetworkList(SysNetwork network);

    /**
     * 根据角色ID查询机构树信息
     *
     * @param roleId 角色ID
     * @return 选中机构列表
     */
    public List<Integer> selectNetworkListByRoleId(Long roleId);

    /**
     * 根据机构ID查询信息
     *
     * @param networkId 机构ID
     * @return 机构信息
     */
    public SysNetwork selectNetworkById(Long networkId);

    /**
     * 根据ID查询所有子机构
     *
     * @param networkId 机构ID
     * @return 机构列表
     */
    public List<SysNetwork> selectChildrenNetworkById(Long networkId);

    /**
     * 根据ID查询所有子机构（正常状态）
     *
     * @param networkId 机构ID
     * @return 机构点数
     */
    public int selectNormalChildrenNetworkById(Long networkId);

    /**
     * 是否存在子节点
     *
     * @param networkId 机构ID
     * @return 结果
     */
    public int hasChildByNetworkId(Long networkId);

    /**
     * 查询机构是否存在用户
     *
     * @param networkId 机构ID
     * @return 机构
     */
    public int checkNetworkExistUser(Long networkId);

    /**
     * 校验机构名称是否唯一
     *
     * @param networkName 机构名称
     * @param parentId 父机构ID
     * @return 结果
     */
    public SysNetwork checkNetworkNameUnique(@Param("networkName") String networkName, @Param("parentId") Long parentId);

    /**
     * 新增机构信息
     *
     * @param network 机构信息
     * @return 结果
     */
    public int insertSysNetwork(SysNetwork network);

    /**
     * 修改机构信息
     *
     * @param network 机构信息
     * @return 结果
     */
    public int updateNetwork(SysNetwork network);

    /**
     * 修改所在机构的父级机构状态
     *
     * @param network 机构
     */
    public void updateNetworkStatus(SysNetwork network);

    /**
     * 修改子元素关系
     *
     * @param networks 子元素
     * @return 结果
     */
    public int updateNetworkChildren(@Param("networks") List<SysNetwork> networks);

    /**
     * 删除机构管理信息
     *
     * @param networkId 机构ID
     * @return 结果
     */
    public int deleteNetworkById(Long networkId);

    /**
     * 查询机构id
     *
     * @param networkCode 机构ID
     * @return 结果
     */
    public long selectNetworkIdByNetworkCode(String networkCode);

    SysNetwork checkNetworkCodeUnique(String networkCode);

    List<String> selectChildrenListById(Long networkId);

    SysNetwork selectNetworkByNetworkCode(@Param("networkCode") String networkCode);

    SysNetwork selectNetWorkByCode(String netWorkCode);

    void updateManageAreaTime(Long belongNetworkId);

    List<String> selectNetworkCodeById(long netWorkId);

    List<SysNetwork> selectChildrenAndOwnNetworkById(Long networkId);

    List<SysNetwork> selectByNetWorkCode(String netWorkCode);

    SysNetwork queryByNetWorkCode(String netWorkCode);

    List<SysNetwork> queryChildrenByNetWorkId(Long networkId);

   String selectAncestorsByNetworkId(Long networkId);

    List<SysNetworkVo> networkList();

    List<SysNetwork> queryByNetWorkName(String headOffice);

    List<Long> selectChildrenIdListById(Long networkId);

    List<SysNetwork> queryAllNetWorkData();

    List<String> queryNetWorkCodes(Long networkId);

    List<TaskSelectVo> queryNetworkNames(@Param("keyword") String keyword);

    List<SysNetwork> selectSelfAndChildrenNetworkById(Long networkId);

    /**
     * 查询二级机构
     * @param networkName
     * @return
     */
    List<SysNetwork> selectSecondNetworkListByName(@Param("networkName") String networkName);

    List<Long> listSonIds(@Param("networkIds") List<Long> networkIds);

    /**
     *  获取总行所有
     * @param
     * @return
     */
    List<String> selectNetworkCodeByParentId(long parentId);

    List<SysNetwork> selectByTaskIdAndName(@Param("taskId") Long taskId, @Param("name") String name);

    List<SysNetwork> selectByParentIdAndName(@Param("parentId") Long parentId, @Param("name") String name);

    List<String> selectNetworkCodesByTaskId(@Param("taskId") Long taskId);

    List<String> selectNetworkCodesByNetworkIds(@Param("networkIdList") List<Long> networkIdList);

    List<String> selectNetworkCodeByParentCode(@Param("networkCode") String networkCode);

    List<SysNetwork> selectByNetworkdIds(@Param("networkIdList") List<String> networkIdList);

    List<KpiTargetVO> selectKpiTargetByNetworkIds(@Param("networkIdList") List<String> networkIdList);

    String selectNetworkTreeByUserId(String userId);

    SysNetwork selectByNetworkId(@Param("networkId") Long networkId);

    SysNetwork selectByNetworkName(@Param("networkName") String networkName);

    List<SysNetwork> selectAllSysNetwork();
}
