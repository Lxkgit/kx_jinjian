package com.kx.system.api.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import lombok.Data;

@Data
public class SysDeptExcel {

    /**
     * 归属银行机构
     */
    @ExcelProperty("银行机构名称(此列不可修改)")
    @ColumnWidth(18)
    private String bankAgencyName;

    /**
     * 网点名称
     */
    @ExcelProperty("网点名称(此列不可修改)")
    @ColumnWidth(30)
    private String deptName;

    /**
     * 网点负责人
     */
    @ExcelProperty("网点负责人")
    @HeadFontStyle(color = 2)
    private String dutyName;

    /**
     * 网点负责人固定电话
     */
    @ExcelProperty("网点负责人固定电话")
    @HeadFontStyle(color = 2)
    private String dutyTelephone;

    /**
     * 网点负责人移动电话
     */
    @ExcelProperty("网点负责人移动电话")
    @HeadFontStyle(color = 2)
    private String dutyPhone;

    /**
     * 网点服务类型
     */
    @ExcelProperty("网点服务\n" +
            "（输入对应服务编码，多个服务以“,”相隔）")
    @HeadFontStyle(color = 2)
    @ColumnWidth(35)
    private String serviceType;

}
