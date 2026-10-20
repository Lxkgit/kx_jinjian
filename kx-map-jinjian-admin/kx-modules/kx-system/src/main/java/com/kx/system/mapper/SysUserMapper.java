package com.kx.system.mapper;

import com.kx.system.api.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author kangxu
 */
public interface SysUserMapper {
    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUserList(SysUser sysUser);

    List<Long> selectESUserIdList(SysUser sysUser);
    /**
     * 根据条件分页查询已配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    public int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public SysUser checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);

    /**
     * 获取所在部门角色
     *
     * @param deptId
     * @param roleId
     * @return
     */
    SysUser selectJudgeRole(@Param("deptId") Long deptId, @Param("roleId") Long roleId);

    List<SysUser> selectUserByManagerNum(String managerNum);
    SysUser selectUserByManagerNum2(@Param("managerNum") String managerNum);
    List<SysUser> selectUserByManagerNumList(@Param("managerNumList") List<String> managerNumList);
    /**
     * 备份表
     *
     * @param date
     */
    void createBackupTable(@Param("date") String date);

    /**
     * 批量新增用户
     *
     * @param batchList
     */
    void batchInsert(@Param("batchList") List<SysUser> batchList);


    /**
     * 查询部门下所有用户
     *
     * @param deptId
     * @return
     */
    List<SysUser> listDeptUsers(@Param("deptId") Long deptId);

    /** 查询指定部门本级及下辖的有效账号，支持关键字过滤。 */
    List<SysUser> selectEnabledUsersByDeptScopes(@Param("rootDeptIds") List<Long> rootDeptIds,
                                                 @Param("keyword") String keyword);

    /** 查询指定部门范围内的有效账号。 */
    List<SysUser> selectEnabledUsersInDeptScopes(@Param("userNames") List<String> userNames,
                                                 @Param("rootDeptIds") List<Long> rootDeptIds);

}
