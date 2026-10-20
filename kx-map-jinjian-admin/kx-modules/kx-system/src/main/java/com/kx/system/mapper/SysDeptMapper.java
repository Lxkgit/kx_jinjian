package com.kx.system.mapper;

import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysDeptExcel;
import com.kx.system.domain.grid.GridMessageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理 数据层
 *
 * @author kangxu
 */
@Mapper
public interface SysDeptMapper {
    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<SysDept> selectDeptList(SysDept dept);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId            角色ID
     * @param deptCheckStrictly 部门树选择项是否关联显示
     * @return 选中部门列表
     */
    List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    SysDept selectDeptById(Long deptId);

    /**
     * 根据部门编码集合查询部门id信息
     *
     * @param orgList
     * @return
     */
    List<Long> selectDeptIdByCode(@Param("orgList") List<String> orgList);

    /**
     * 根据机构号查询机构
     *
     * @param orgCodes
     * @return
     */
    List<SysDept> listDeptByCode(@Param("orgCodes") List<String> orgCodes);

    /**
     * 根据机构号查询机构
     *
     * @param orgCode
     * @return
     */
    SysDept selectDeptByCode(@Param("orgCode") String orgCode);

    /**
     * 根据机构id集合查询所有下辖机构
     *
     * @param
     * @return
     */
    List<SysDept> selectDeptByIds(@Param("ids") List<Long> ids);

    /** 按ID精确查询启用部门。 */
    List<SysDept> selectEnabledDeptByIds(@Param("ids") List<Long> ids);


    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int checkDeptExistUser(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int insertDept(SysDept dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int updateDept(SysDept dept);

    /**
     * 修改所在部门正常状态
     *
     * @param deptIds 部门ID组
     */
    void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int deleteDeptById(Long deptId);

    List<SysDept> selectAll();

    String selectDeptNameById(@Param("creditManagerBranchBank") String creditManagerBranchBank);

    List<String> selectSelfAndParent(Long deptId);

    //根据用户角色id集合查询自定义权限机构编码集
    List<String> getSelfDefinition(@Param("roleIdList") List<Long> roleIdList);

    /**
     * 按照时间创建备份表
     * 达梦数据库 CREATE TABLE LIKE 会同步复制数据
     *
     * @param date
     */
    void createBackupTable(@Param("date") String date);

    /**
     * 清空部门数据
     */
    void truncateTable();

    /**
     * 批量插入
     *
     * @param batchList
     * @return
     */
    int batchInsert(@Param("batchList") List<SysDept> batchList);

    /**
     * 查询部门的直接子节点
     *
     * @param deptId
     * @return
     */
    List<SysDept> listDirectChildrenDept(@Param("deptId") Long deptId);

    /** 查询根部门或指定部门的直属下级，供通用懒加载树复用。 */
    List<SysDept> selectLazyDeptOptions(@Param("parentId") Long parentId);

    /**
     * 根据机构号查询父级部门
     * @param orgNum
     * @return
     */
    SysDept selectParentByNum(String orgNum);

    List<SysDept> selectByAreaCode(@Param("sysDept") SysDept sysDept);


    List<Long> selectDeptIdByCodes(@Param("orgCodes") List<String> orgCodes);

    List<SysDeptExcel> selectAllByRenHang();

    void UpdateBatchByDeptName(@Param("cachedDataList") List<SysDeptExcel> cachedDataList);

    String selectDotTapy(String anchorName);

    void updateDotTapy(@Param("anchorName") String anchorName,@Param("dotType")  String dotType);

    List<String> getDeptClassify();

    List<SysDept> getDeptInfoByGridCode(@Param("orgNum") String orgNum);
}
