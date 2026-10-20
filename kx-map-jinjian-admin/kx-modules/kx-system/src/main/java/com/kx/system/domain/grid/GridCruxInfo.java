package com.kx.system.domain.grid;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 网格关键信息维护表
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("grid_crux_info")
@ApiModel(value="其他网点信息表", description="其他网点信息表")
public class GridCruxInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "网格关键信息表主键id", required = true)
    private Long id;


    @ApiModelProperty(value = "网格基础信息id")
    private Long gridMessageId;


    @ApiModelProperty(value = "网格编码")
    private String gridCode;

    @ApiModelProperty(value = "网点名称")
    private String networkName;

    @ApiModelProperty(value = "网点编码")
    private String networkCode;

    @ApiModelProperty(value = "网点地址")
    private String networkAddress;

    @ApiModelProperty(value = "银行机构名称")
    private String bankAgencyName;

    @ApiModelProperty(value = "银行机构编码")
    private String bankAgencyCode;


    @ApiModelProperty(value = "关键人名称")
    private String cruxName;


    @ApiModelProperty(value = "关键人性别")
    private String cruxSex;


    @ApiModelProperty(value = "关键人手机")
    private String cruxPhone;


    @ApiModelProperty(value = "关键人地址")
    private String cruxAddress;


    @ApiModelProperty(value = "关键人职位")
    private String cruxWork;


    @ApiModelProperty(value = "关键人证件号")
    private String cruxIdentity;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "网点责任人名称")
    private String dutyName;

    @ApiModelProperty(value = "网点责任人电话")
    private String dutyPhone;

}
