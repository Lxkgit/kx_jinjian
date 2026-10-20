//package com.kx.system.domain;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.kx.system.domain.task.TaskCustomObject;
//import com.kx.system.domain.task.TaskDistributionRule;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
///**
// * <p>
// * 任务基本信息表
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-27
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@TableName("task_base_info")
//@ApiModel(value="任务基本信息表对象", description="任务基本信息表")
//public class TaskBaseInfo implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//
//    @ApiModelProperty(value = "id")
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//
//    @ApiModelProperty(value = "任务名称")
//    private String taskName;
//
//
//    @ApiModelProperty(value = "任务类型id")
//    private Long taskType;
//
//
//    @ApiModelProperty(value = "任务类型名称")
//    private String taskTypeName;
//
//
//    @ApiModelProperty(value = "分配规则id")
//    private Long distributionRuleParent;
//
//
//    @ApiModelProperty(value = "分配规则--客群id")
//    private String distributionRuleChild;
//
//
//    @ApiModelProperty(value = "开始时间")
//    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
//    @DateTimeFormat(pattern="yyyy-MM-dd")
//    private Date beginDate;
//
//    @ApiModelProperty(value = "结束时间")
//    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
//    @DateTimeFormat(pattern="yyyy-MM-dd")
//    private Date endDate;
//
//
//    @ApiModelProperty(value = "是否周期任务 0-否 1-是")
//    private Integer isCycle;
//
//
//    @ApiModelProperty(value = "周期类型")
//    private Integer cycleType;
//
//
//    @ApiModelProperty(value = "周期长(天)")
//    private Integer cycleTime;
//
//
//    @ApiModelProperty(value = "任务描述")
//    private String taskDescribe;
//
//
//    @ApiModelProperty(value = "任务状态  0-未开始  1-进行中 2-已结束 3-手动结束")
//    private Integer status;
//
//
//    @ApiModelProperty(value = "完成进度")
//    private String completeProcess;
//
//    @ApiModelProperty(value = "优先级,数值越大级别越高")
//    private Integer priority;
//
//    @ApiModelProperty(value = "优先级名称")
//    private String priorityName;
//
//
//    @ApiModelProperty(value = "创建用户id")
//    private Long createId;
//
//    @ApiModelProperty(value = "创建用户机构code")
//    private String createOrg;
//
//    @ApiModelProperty(value = "创建人")
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
//
//    @ApiModelProperty(value = "上传excleUrl")
//    @TableField(exist = false)
//    private String fileName;
//
//    @ApiModelProperty(value = "下派清单集")
//    @TableField(exist = false)
//    private List<TaskCustomObject> list;
//
//    @ApiModelProperty(value = "分配规则对象")
//    @TableField(exist = false)
//    private TaskDistributionRule parent;
//
//    @ApiModelProperty(value = "分配规则--客群对象集")
//    @TableField(exist = false)
//    private List customList;
//
//    @ApiModelProperty(value = "客户信息--发消息时使用")
//    @TableField(exist = false)
//    private PaCustomInfo custom;
//
//    /**
//     * 子任务数
//     */
//    private int taskDetailNum;
//
//    /**
//     * 未完成
//     */
//    private int unFinishNum;
//
//    /**
//     * 已完成
//     */
//    private int finishNum;
//
//    /**
//     * 手动结束
//     */
//    private int manualEndNum;
//
//    /**
//     * 完成率
//     */
//    private double completionRate;
//
//
//}
