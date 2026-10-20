package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashServicePointsDTO {

  @ApiModelProperty(value = "序号")
  private long id;

  @ApiModelProperty(value = "现金服务点责任银行")
  private String serveBank;

  @ApiModelProperty(value = "现金服务点责任网点")
  private String serveAnchor;

  @ApiModelProperty(value = "现金服务点责任网点地址")
  private String serveAnchorAddr;

  @ApiModelProperty(value = "服务类别")
  private String serviceType;

  @ApiModelProperty(value = "服务点名称")
  private String servePointName;

  @ApiModelProperty(value = "服务点地址")
  private String servePointAddr;

  @ApiModelProperty(value = "选点位置")
  private String chosenPosition;

  @ApiModelProperty(value = "现金服务点责任人")
  private String dutyName;

  @ApiModelProperty(value = "联系电话")
  private String dutyPhone;

  @ApiModelProperty(value = "建设模式（自建/合建）")
  private String constructingMode;

  @ApiModelProperty(value = "推进情况（1.洽谈中；2.建设中；3.运行中）")
  private long constructingCase;

  @ApiModelProperty(value = "计划启用时间")
  private String startTime;

  @ApiModelProperty(value = "服务模式")
  private String serviceMode;

  @ApiModelProperty(value = "是否布放小面额现金自助兑换设备")
  private String publishingDevice;

  @ApiModelProperty(value = "所属网格编码集合")
  private List<String> belongGridCodeList;

  @ApiModelProperty(value = "所属网格名称")
  private String gridName;

  @ApiModelProperty(value = "记录创建时间")
  private LocalDateTime createdTime;

  @ApiModelProperty(value = "记录最后一次修改时间")
  private LocalDateTime updatedTime;

  private Integer pageNum;

  private Integer pageSize;

}