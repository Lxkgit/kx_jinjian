package com.kx.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExportAnchor {
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "银行机构")
    private String bankAgencyName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网点名称")
    private String customerName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网点类型")
    private String dotType;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网点级别")
    private String anchorRanks;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网点负责人")
    private String dutyName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "负责人固定电话")
    private String dutyPhone;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "负责人移动电话")
    private String dutyTelephone;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网点地址")
    private String customerAddress;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网点所在地类型")
    private String ruralGridType;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "所属网格")
    private String belongGridName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "所属网格网格长")
    private String gridMainManagerName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网格长固定电话")
    private String gridMainManagerPhone;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "存取款一体机CRS")
    private String depositMachine;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "现金柜员机")
    private String cashTellerMachine;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "自动取款机ATM数量")
    private String atmMachine;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网点功能")
    private String serviceType;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "管理网格名称")
    private String gridName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "管理网格所在镇街")
    private String zjName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "管理网格所在县域")
    private String xyName;
}
