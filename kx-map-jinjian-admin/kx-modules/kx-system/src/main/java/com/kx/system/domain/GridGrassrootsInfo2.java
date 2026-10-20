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
@AllArgsConstructor
@NoArgsConstructor


public class GridGrassrootsInfo2 {

  @ApiModelProperty("序号")
  private Long id;
  @ApiModelProperty("基层联系员名称")
  private String contactName;
  @ApiModelProperty("基层联系员电话")
  private String contactPhone;
  @ApiModelProperty("网格编码")
  private String gridCode;
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;
  @ApiModelProperty("基层组织名称")
  private String grassroots;
  @ApiModelProperty("基层组织类型")
  private String grassrootsType;


}
