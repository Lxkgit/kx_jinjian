package com.kx.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class AnchorComplaintEvents {

  @ApiModelProperty("事件id")
  private long id;
  @ApiModelProperty("网点序号")
  private String anchorId;
  @ApiModelProperty("网点名称")
  private String anchorName;
  @ApiModelProperty("投诉来源: 0 ='外来投诉事件' 1 = '网点上报事件")
  private String complaintSource;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty("投诉时间")
  private LocalDateTime complaintTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty("更新时间")
  private LocalDateTime updateTime;
  @ApiModelProperty("线索来源: 0='投诉' 1='舆情'")
  private String leadSource;
  @ApiModelProperty("投诉人")
  private String complainant;
  @ApiModelProperty("投诉人电话")
  private String complainantPhone;
  @ApiModelProperty("所属行业")
  private String industry;
  @ApiModelProperty("发生场景")
  private String scene;
  @ApiModelProperty("是否为拒收人民现金行为: 0=否 1=是")
  private String isCashRejection;
  @ApiModelProperty("具体是由")
  private String specificReason;
  @ApiModelProperty("处置情况")
  private String disposalSituation;
  @ApiModelProperty("网点联络员")
  private String branchLiaison;
  @ApiModelProperty("网点联络员电话")
  private String branchLiaisonPhone;
  @ApiModelProperty("上级负责人名称")
  private String superiorsName;
  @ApiModelProperty("上级负责人电话")
  private String superiorPhone;


  }
