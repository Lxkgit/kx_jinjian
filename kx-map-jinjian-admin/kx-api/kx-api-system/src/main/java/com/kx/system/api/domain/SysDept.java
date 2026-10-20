package com.kx.system.api.domain;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门表 sys_dept
 *
 * @author kangxu
 */
@Data
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 30, message = "部门名称长度不能超过30个字符")
    private String deptName;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 机构编号
     */
    private String orgNum;

    /**
     * 父级机构编号
     */
    private String parentOrgNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    @Size(max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    /**
     * 邮箱
     */
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 部门状态:0正常,1停用
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 父部门名称
     */
    private String parentName;

    /**
     * 子部门
     */
    private List<SysDept> children = new ArrayList<>();

    /** 懒加载部门树使用：是否存在可用的直属下级。 */
    private Boolean hasChildren;
    /** 经度 */
    private String lng;

    /** 纬度 */
    private String lat;
    private String areaCode;
    private String ancestorCodes;
    private String address;
    private String bankAgencyName;
    private String dutyCode;
    private String dutyName;
    private String dutyPhone;
    private String dutyTelephone;
    private int dotType;

    private List<String> orgNumList;
}
