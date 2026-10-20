package com.kx.system.domain.customer;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class CustomerParam extends BaseEntity {
    private String customerName;
    private String gridCode;
    private List<String> types;

    //当前机构权限编码集合
    private List<String> orgCodes;

    //当前权限客户经理编码集合
    private List<String> managerCodes;
}
