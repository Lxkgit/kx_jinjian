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

public class ExportGrid {
    @ExcelProperty( "网格编码")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String  code;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网格名称")
    private String  name;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网格所在镇街")
    private String  zjName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网格所在县域")
    private String  xyName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网格类型")
    private String  ruralGridType;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "是否重点网格")
    private String  isCash;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "银行网格长")
    private String  gridMainManagerName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网格长联系电话")
    private String  gridMainManagerPhone;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "责任网点")
    private String  dutyNetworkName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "网格内普通网点")
    private String  ordinaryNetwork;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"重点管理对象", "重点管理对象名称"})
    private String  importName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"重点管理对象", "重点管理对象地址"})
    private String  importAddress;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"网格中现金服务点", "服务点名称"})
    private String  cServePointName ;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"网格中现金服务点", "服务点地址"})
    private String  cServePointAddr;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"网格中金融服务站", "金融服务站名称"})
    private String  vServePointName ;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"网格中金融服务站", "金融服务站地址"})
    private String  vServePointaddr;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"社会网格员", "网格联系员"})
    private String  supervisorName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"社会网格员", "联系电话"})
    private String  supervisorPhone;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"网格内派出所信息", "派出所名称"})
    private String  policeName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"网格内派出所信息", "派出所地址"})
    private String  policeAddress;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"网格内派出所信息", "反假联系人"})
    private String  unFakerName;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"网格内派出所信息", "反假联系人电话"})
    private String  unFakerMobile;
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( {"网格内派出所信息", "反假联系人固定电话"})
    private String  unFakerPhone;

}
