package com.kx.system.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class PaViewDeptMemberVo {
    /**
     * 机构Id/用户Id
     */
    private String id;

    /**
     * 机构名称/用户名称
     */
    private String name;

    /**
     * 类型
     * 0-用户
     * 1-机构
     */
    private Integer type;

    /**
     * 机构编号/客户经理号
     */
    private String code;

    /**
     * 上级机构ID
     */
    private String parentId;

    /**
     * 机构管辖的网格ID列表
     */
    private Set<String> addrTypes;

    /**
     * 子机构列表/机构下用户列表
     */
    private List<PaViewDeptMemberVo> children = new ArrayList<>();
}
