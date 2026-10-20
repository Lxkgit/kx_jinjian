package com.kx.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode
public class VillageExport {


    /** 服务点名称 */
    @ApiModelProperty(value = "金融服务站名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty( "金融服务站名称")
    private String servePointName;

    /** 服务点地址 */
    @ApiModelProperty(value = "金融服务站地址")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty("金融服务站地址")
    private String servePointAddr;

    /** 现金服务点责任人 */
    @ApiModelProperty(value = "金融服务站站长")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty("金融服务站站长")
    private String dutyName;

    /** 联系电话 */
    @ApiModelProperty(value = "站长联系电话")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty("站长联系电话")
    private String dutyPhone;

    /** 所属网格名称 */
    @ApiModelProperty(value = "所在网格")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty("所在网格")
    private String gridName;

}
