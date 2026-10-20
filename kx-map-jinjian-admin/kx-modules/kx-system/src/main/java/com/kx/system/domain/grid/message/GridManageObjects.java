package com.kx.system.domain.grid.message;

import lombok.Data;

import java.util.Date;

@Data
public class GridManageObjects {

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 网格编码
     */
    private String gridCode;

    /**
     * 重点管理对象名称
     */
    private String managerName;

    /**
     * 重点管理对象类型（1-5级）
     */
    private Integer managerType;

    /**
     * 重点管理对象地址
     */
    private String managerAddr;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建人账号
     */
    private Long createBy;

    /**
     * 最近修改时间
     */
    private Date updateTime;

    /**
     * 修改人名称
     */
    private String updateName;

    /**
     * 修改人账号
     */
    private Long updateBy;

//  @ApiModelProperty(value = "序号")
//  private long id;
//  @ApiModelProperty(value = "网格编码")
//  private String gridCode;
//  @ApiModelProperty(value = "重点处理对象")
//  private String importName;
//  @ApiModelProperty(value = "重点处理对象地址")
//  private String importAddress;
//  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//  private LocalDateTime createTime;
//  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//  private LocalDateTime updateTime;


}
