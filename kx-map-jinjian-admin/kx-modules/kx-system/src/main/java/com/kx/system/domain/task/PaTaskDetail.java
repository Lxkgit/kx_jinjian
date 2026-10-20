//package com.kx.system.domain.task;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.kx.common.core.web.domain.BaseEntity;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
///**
// * 任务详情
// */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class PaTaskDetail extends BaseEntity {
//    private static final long serialVersionUID = 1L;
//
//    private Long id;
//
//    @ApiModelProperty(value = "任务ID")
//    private Long taskId;
//
//    @ApiModelProperty(value = "客户ID")
//    private Long clientId;
//
//    @ApiModelProperty(value = "客户名称")
//    private String customerName;
//
//    @ApiModelProperty(value = "证件号")
//    private String customerIdentity;
//
//    @ApiModelProperty(value = "任务状态")
//    private Integer status;
//
//    @ApiModelProperty(value = "信息完整度")
//    private String integrity;
//
//    @ApiModelProperty(value = "客户经理工号")
//    private String managerJobNumber;
//
//    @ApiModelProperty(value = "执行人")
//    private String managerName;
//
//    @ApiModelProperty(value = "部门id")
//    private Long deptId;
//
//    @ApiModelProperty(value = "部门名称(支行)")
//    private String deptName;
//
//    @ApiModelProperty(value = "分行名称")
//    private String separateBank;
//
//    @ApiModelProperty(value = "联系方式")
//    private Integer taskMethod;
//
//    @ApiModelProperty(value = "联系时间")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date contactTime;
//
//    @ApiModelProperty(value = "图片")
//    private String pic;
//
//    @ApiModelProperty(value = "备注")
//    private String remark;
//
//    @ApiModelProperty(value = "任务反馈json")
//    private String feedback;
//
//    @ApiModelProperty(value = "客户编码")
//    private String customerNm;
//
//    private Integer customerType;
//}
