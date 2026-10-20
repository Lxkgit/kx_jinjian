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

public class GridManageObjects2 {

  @ApiModelProperty(value = "序号")
  private long id;
  @ApiModelProperty(value = "网格编码")
  private String gridCode;
  @ApiModelProperty(value = "重点处理对象")
  private String importName;
  @ApiModelProperty(value = "重点处理对象地址")
  private String importAddress;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;



}
