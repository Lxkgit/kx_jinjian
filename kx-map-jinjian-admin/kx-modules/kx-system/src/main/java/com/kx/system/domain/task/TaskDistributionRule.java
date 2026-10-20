//package com.kx.system.domain.task;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//
//import java.io.Serializable;
//
///**
// * <p>
// * 任务分配规则及客群表
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-26
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@TableName("task_distribution_rule")
//@ApiModel(value="任务分配规则及客群表对象", description="任务分配规则及客群表")
//public class TaskDistributionRule implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @ApiModelProperty(value = "id")
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//
//    @ApiModelProperty(value = "父级id")
//    private Long parentId;
//
//
//    @ApiModelProperty(value = "任务对象唯一标识")
//    private String taskObjectId;
//
//
//    @ApiModelProperty(value = "任务对象名称")
//    private String taskObjectName;
//
//
//    @ApiModelProperty(value = "分配规则唯一标识")
//    private String ruleId;
//
//    @ApiModelProperty(value = "分配规则名称")
//    private String ruleName;
//
//
//    @ApiModelProperty(value = "客群类型")
//    private String customType;
//
//
//    @ApiModelProperty(value = "客群名称")
//    private String customName;
//
//
//}
