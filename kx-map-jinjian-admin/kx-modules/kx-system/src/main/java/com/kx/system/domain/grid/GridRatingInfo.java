package com.kx.system.domain.grid;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 网格评级信息维护表
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("grid_rating_info")
@ApiModel(value="网格评级信息维护表对象", description="网格评级信息维护表")
public class GridRatingInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "网格评级信息表主键id", required = true)
    private Long id;


    @ApiModelProperty(value = "网格信息id")
    private Long gridMessageId;


    @ApiModelProperty(value = "网格编码")
    private String gridCode;


    @ApiModelProperty(value = "评级年度")
    private String ratingYear;


    @ApiModelProperty(value = "网格评级")
    private String gridRating;

    @ApiModelProperty(value = "派出所名称")
    private String policeName;
    @ApiModelProperty(value = "派出所地址")
    private String policeAddress;
    @ApiModelProperty(value = "反假联系人名称")
    private String unFakerName;
    @ApiModelProperty(value = "反假联系人座机电话")
    private String unFakerPhone;
    @ApiModelProperty(value = "反假联系人移动电话")
    private String unFakerMobile;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
