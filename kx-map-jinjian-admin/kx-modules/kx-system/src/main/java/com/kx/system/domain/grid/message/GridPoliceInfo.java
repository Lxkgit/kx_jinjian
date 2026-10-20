package com.kx.system.domain.grid.message;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 网格评级信息维护表
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@TableName("grid_rating_info")
//@ApiModel(value="网格评级信息维护表对象", description="网格评级信息维护表")
public class GridPoliceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Long id;

    /**
     * 网格编码
     */
    private String gridCode;

    /**
     * 派出所名称
     */
    private String policeStationName;

    /**
     * 派出所地址
     */
    private String policeStationAddr;

    /**
     * 反假联系人姓名
     */
    private String policeName;

    /**
     * 联系电话
     */
    private String policePhone;

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

//
//    @ApiModelProperty(value = "网格评级信息表主键id", required = true)
//    private Long id;
//
//
//    @ApiModelProperty(value = "网格信息id")
//    private Long gridMessageId;
//
//
//    @ApiModelProperty(value = "网格编码")
//    private String gridCode;
//
//
//    @ApiModelProperty(value = "评级年度")
//    private String ratingYear;
//
//
//    @ApiModelProperty(value = "网格评级")
//    private String gridRating;
//
//    @ApiModelProperty(value = "派出所名称")
//    private String policeName;
//    @ApiModelProperty(value = "派出所地址")
//    private String policeAddress;
//    @ApiModelProperty(value = "反假联系人名称")
//    private String unFakerName;
//    @ApiModelProperty(value = "反假联系人座机电话")
//    private String unFakerPhone;
//    @ApiModelProperty(value = "反假联系人移动电话")
//    private String unFakerMobile;
//
//    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createTime;
//
//
//    @ApiModelProperty(value = "更新日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date updateTime;


}
