package com.kx.system.api.domain.shop.dto;

import lombok.Data;

@Data
public class GridInfoDto {

    private String gridCode;

    private String gridName;

    private String gridAncestorCodes;

    private String branchCode;

    private String branchName;

    private String branchAncestors;

    private Integer type;

    private String deptClassify;

    private String orgDeptId;

    private String orgDeptCode;

    private String orgDeptName;

    private String orgDeptAreaCode;

    private String orgDeptAreaAncestorCodes;



}
