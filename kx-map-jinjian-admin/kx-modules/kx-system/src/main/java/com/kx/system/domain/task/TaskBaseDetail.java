//package com.kx.system.domain.task;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.validation.constraints.NotBlank;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
///**
// * <p>
// * 任务下派明细表
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-27
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@TableName("task_base_detail")
//@ApiModel(value="任务下派明细表对象", description="任务下派明细表")
//public class TaskBaseDetail implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//
//    @ApiModelProperty(value = "id")
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//
//    @ApiModelProperty(value = "任务id")
//    private Long taskId;
//
//
//    @ApiModelProperty(value = "客户内码")
//    private String code;
//
//
//    @ApiModelProperty(value = "客户姓名")
//    private String name;
//
//
//    @ApiModelProperty(value = "客户经理号")
//    private String managerId;
//
//
//    @ApiModelProperty(value = "客户经理名称")
//    private String managerName;
//
//
//    @ApiModelProperty(value = "网点code")
//    private String networkCode;
//
//
//    @ApiModelProperty(value = "网点名称")
//    private String networkName;
//
//
//    @ApiModelProperty(value = "状态 0-未完成 1-已完成 2-结束")
//    private Integer status;
//
//
//    @ApiModelProperty(value = "是否删除  0-未删除 1-已删除")
//    private Integer isDeleted;
//
//
//    @ApiModelProperty(value = "创建人")
//    @NotBlank(message = "创建人不能为空")
//    private String createBy;
//
//
//    @ApiModelProperty(value = "更新人")
//    private String updateBy;
//
//
//    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private Date createDate;
//
//
//    @ApiModelProperty(value = "更新时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private Date updateDate;
//
//    @ApiModelProperty(value = "完整度")
//    private String completePercent;
//
//    @ApiModelProperty(value = "登陆用户机构及下辖")
//    @TableField(exist = false)
//    private List<String> userList;
//
//    @ApiModelProperty(value = "点集")
//    private String positions;
//}
