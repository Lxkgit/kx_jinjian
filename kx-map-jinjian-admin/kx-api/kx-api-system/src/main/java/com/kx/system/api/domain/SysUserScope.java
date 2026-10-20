package com.kx.system.api.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysUserScope {

    // 当前登录用户权限范围 1-全部范围 2-自定义范围 3-当前部门范围 4-本级以及下辖部门范围 5-当前用户范围
    private String scope;

    // 权限客户经理经理号 仅限于5级 当前用户范围 带回参数
    private String managerCode;

    // 权限机构编码集  2、3、4级范围-带回参数 1级范围全部-无需带回参数
    private List<String> orgCodeList;

    // 自定义角色id集
    private List<Long> roleIdList;
}
