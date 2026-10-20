package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 部门表 sys_dept
 *
 * @author kangxu
 */
@Data
public class DeptList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部门ID */
    private Long deptId;

    /** 部门名称 */
    private String deptName;
}
