package com.kx.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SyncAddrBelongRelationVo {
    @ExcelProperty("机构ID")
    private String orgId;

    @ExcelProperty("town_code")
    private String townCode;

    @ExcelProperty("网格管理员工号")
    private String managerId;
}
