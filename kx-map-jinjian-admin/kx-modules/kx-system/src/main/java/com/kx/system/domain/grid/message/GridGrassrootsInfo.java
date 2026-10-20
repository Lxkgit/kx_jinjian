package com.kx.system.domain.grid.message;

import lombok.Data;

import java.util.Date;

@Data
public class GridGrassrootsInfo {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 网格编码
     */
    private String gridCode;

    /**
     * 基层组织名称
     */
    private String organizationName;

    /**
     * 基层组织类型
     */
    private Integer organizationType;

    /**
     * 联系人姓名
     */
    private String contactsName;

    /**
     * 联系人电话
     */
    private String contactsPhone;

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

//  @ApiModelProperty("序号")
//  private Long id;
//  @ApiModelProperty("基层联系员名称")
//  private String contactName;
//  @ApiModelProperty("基层联系员电话")
//  private String contactPhone;
//  @ApiModelProperty("网格编码")
//  private String gridCode;
//  @ApiModelProperty("创建时间")
//  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//  private LocalDateTime createTime;
//  @ApiModelProperty("修改时间")
//  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//  private LocalDateTime updateTime;
//  @ApiModelProperty("基层组织名称")
//  private String grassroots;
//  @ApiModelProperty("基层组织类型")
//  private String grassrootsType;


}
