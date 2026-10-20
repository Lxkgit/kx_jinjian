package com.kx.system.api.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class ServiceTypeExcel {

    /**
     * 网点服务
     */
    @ExcelProperty("网点服务")
    private String serviceTypeName;

    /**
     * 编码
     */
    @ExcelProperty("编码")
    private String serviceTypeCode;

}
