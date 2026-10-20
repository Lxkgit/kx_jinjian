package com.kx.system.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cash_service_points")
@ApiModel(value = "现金服务点信息表对象", description = "现金服务点信息表")
@HeadRowHeight(25)
public class CashServicePointsVo {

  @ApiModelProperty(value = "序号")
  private long id;

  @ApiModelProperty(value = "现金服务点责任银行")
  @ExcelProperty("现金服务点责任银行")
  @HeadFontStyle(color = 2)
  private String serveBank;

  @ApiModelProperty(value = "现金服务点责任网点")
  @ExcelProperty("现金服务点责任网点")
  @HeadFontStyle(color = 2)
  private String serveAnchor;

  @ApiModelProperty(value = "现金服务点责任网点地址")
  @ExcelProperty("现金服务点责任网点地址")
  @HeadFontStyle(color = 2)
  private String serveAnchorAddr;

  @ApiModelProperty(value = "服务类别")
  @ExcelProperty("服务类别")
  @HeadFontStyle(color = 2)
  private String serviceType;

  @ApiModelProperty(value = "服务点名称")
  @ExcelProperty("服务点名称")
  @HeadFontStyle(color = 2)
  private String servePointName;

  @ApiModelProperty(value = "服务点地址")
  @ExcelProperty("服务点地址")
  @HeadFontStyle(color = 2)
  private String servePointAddr;

  @ApiModelProperty(value = "选点位置")
  @ExcelProperty("选点位置")
  @HeadFontStyle(color = 2)
  private String chosenPosition;

  @ApiModelProperty(value = "现金服务点责任人")
  @ExcelProperty("现金服务点责任人")
  @HeadFontStyle(color = 2)
  private String dutyName;

  @ApiModelProperty(value = "联系电话")
  @ExcelProperty("联系电话")
  @HeadFontStyle(color = 2)
  private String dutyPhone;

  @ApiModelProperty(value = "建设模式（自建/合建）")
  @ExcelProperty("建设模式")
  @HeadFontStyle(color = 2)
  private String constructingMode;

  @ApiModelProperty(value = "推进情况（1.洽谈中；2.建设中；3.运行中）")
  @ExcelProperty("推进情况")
  @HeadFontStyle(color = 2)
  private long constructingCase;

  @ApiModelProperty(value = "计划启用时间")
  @ExcelProperty("计划启用时间")
  @HeadFontStyle(color = 2)
  private String startTime;

  @ApiModelProperty(value = "服务模式")
  @ExcelProperty("服务模式")
  @HeadFontStyle(color = 2)
  private String serviceMode;

  @ApiModelProperty(value = "是否布放小面额现金自助兑换设备")
  @ExcelProperty("是否布放小面额现金自助兑换设备")
  @HeadFontStyle(color = 2)
  private String publishingDevice;

  @ApiModelProperty(value = "所属网格编码")
  @ExcelProperty("所属网格编码")
  @HeadFontStyle(color = 2)
  private String gridCode;

  @ApiModelProperty(value = "所属网格名称")
  @ExcelProperty("所属网格名称")
  @HeadFontStyle(color = 2)
  private String gridName;

  @ApiModelProperty(value = "记录创建时间")
  private LocalDateTime createdTime;

  @ApiModelProperty(value = "记录最后一次修改时间")
  private LocalDateTime updatedTime;

}
