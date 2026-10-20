package com.kx.system.domain.business;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 *
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("business_opportunity")
@ApiModel(value="对象", description="")
public class BusinessOpportunity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "商机id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "客户名称")
    private String name;


    @ApiModelProperty(value = "证件号/统一社会信用代码")
    private String identity;


    @ApiModelProperty(value = "客户类型 1-个人 2-企业 3-商户")
    private String type;


    @ApiModelProperty(value = "商机名称")
    private String businessName;


    @ApiModelProperty(value = "业务扩展方向 0-潜客扩展 1-信贷提升  2-存量活客")
    private String businessExtend;


    @ApiModelProperty(value = "商机状态 0-营销中 1-已完成 2-已结束")
    private String businessStatus;


    @ApiModelProperty(value = "商机来源 0-手工创建 1-信贷系统 2-外部接入")
    private String businessResource;


    @ApiModelProperty(value = "公共池状态 1-机构公共池 2-总行公共池 3-已分配")
    private String commonStatus;


    @ApiModelProperty(value = "商机发布人")
    private String createBy;


    @ApiModelProperty(value = "执行客户经理名称")
    private String executeManagerName;


    @ApiModelProperty(value = "执行客户经理编号")
    private String executeManagerCode;


    @ApiModelProperty(value = "执行机构名称")
    private String executeNetworkName;


    @ApiModelProperty(value = "执行机构编号")
    private String executeNetworkCode;


    @ApiModelProperty(value = "商机开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;


    @ApiModelProperty(value = "商机结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "商机开始时间 --搜索范围开始时间")
    private String startTimeSearchStart;

    @ApiModelProperty(value = "商机开始时间 --搜索范围结束时间")
    private String startTimeSearchEnd;

    @ApiModelProperty(value = "商机结束时间 --搜索范围开始时间")
    private String endTimeSearchStart;

    @ApiModelProperty(value = "商机结束时间 --搜索范围结束时间")
    private String endTimeSearchEnd;

}
