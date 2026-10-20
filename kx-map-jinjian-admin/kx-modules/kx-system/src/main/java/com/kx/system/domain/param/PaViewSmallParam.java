package com.kx.system.domain.param;

import lombok.Data;

import java.util.List;


@Data
public class PaViewSmallParam {
    //客户类型 1-绩效客户 2-授信客户 4-乡贷客户
    private String customerType;
    //当前登陆人查询网格编码
    private String gridCode;
    //机构编码
    private String orgCodes;
    //客户经理编码
    private List<String> managerCodes;
    //分解后的街道级网格编码集
    private List<String> gridCodes;
}
