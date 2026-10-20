package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysRole;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 辖区信息对象 pa_manage_area
 *
 * @author gary
 * @date 2021-12-07
 */
@Data
public class PaManageArea extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 辖区名称 */
    private String areaName;

    /** 辖区编码 */

    private String areaCode;

    /** 所属客户经理 */

    private String belongManageId;

    /** 所属网点id */

    private Long belongNetworkId;
    private String belongNetworkName;

    /** 0-存在 2-删除 */
    private String delFlag;




    //-----------------------------
    private String manageName;
    String jobNumber;
    List<PaAddrInfo> manageAreaList;
    String manageAreaName;
    Integer myAreaClientSum;
    Integer myManageClientSum;
    Integer increMyManageClientSumThisMonth;
    Integer increMyAreaClientSumThisMonth;
    Integer increNotMyAreaClientSumThisMonth;
    //_---------------------------------
    private Long userId;

    /** 部门ID */
    private Long deptId;

    /** 用户账号 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 用户邮箱 */
    private String email;

    /** 手机号码 */
    private String phonenumber;

    /** 用户性别 */
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    private Long networkId;

    /** 盐加密 */
    private String salt;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */

    /** 最后登陆IP */
    private String loginIp;

    /** 最后登陆时间 */
    private Date loginDate;

    /** 部门对象 */
    private SysDept dept;

    /** 角色对象 */
    private List<SysRole> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 角色组 */
    private Long roleId;

    /** 岗位组 */
    private Long[] postIds;
    private Long postId;

    private String basePic;


    List<Long> networkIds;

}
