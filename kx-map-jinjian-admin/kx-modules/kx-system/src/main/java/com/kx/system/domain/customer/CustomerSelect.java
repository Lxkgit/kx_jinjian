package com.kx.system.domain.customer;

import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomerSelect extends BaseEntity implements Serializable {
    //筛选类型 1-客户类型筛选 2-客群类型筛选
    private String type;

    //客户类型 1-绩效客户 2-授信客户 3-建档客户 4-收单客户 5-不含乡贷客户
    private List<String> typeList;

    //授信客户风险类型 1-正常类 2-逾欠类 3-不良类
    private List<String> riskTypeList;

    //授信客户担保方式 1-保证 2-信用 3-抵押 4-质押
    private List<String> guaranteeTypeList;

    //授信客户用途 1-经营 2-消费
    private List<String> purposeTypeList;

    //授信客户乡村振兴贷类型  1-用信客户 2-签约未用信客户
    private List<String> villageLoanTypeList;

    //当前网格编码
    private String gridCode;

    //当前网格下辖编码集
    private List<String> gridChildCodes;

    //当前机构权限编码集合
    private List<String> orgCodes;

    //当前机构权限编码集合
    private List<Long> deptIds;


    //当前权限客户经理编码集合
    private List<String> managerCodes;

    //客群id
    private List<String> groupIdList;

    //客群类型 1-绩效客户 2-授信客户 3-建档客户
    private String groupCategory;


    //Es查询 权限控制
    private JSONObject queryEsRole;

    private List<String> belongOrgGrid;

    private String boundary;
    private Integer size;
    private Long userId;
}
