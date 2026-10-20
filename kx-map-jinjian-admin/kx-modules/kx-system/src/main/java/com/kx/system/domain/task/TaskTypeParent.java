//package com.kx.system.domain.task;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.kx.common.core.web.domain.BaseEntity;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
///**
// * <p>
// * 任务类型主表
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-24
// */
//@Data
//@ApiModel(value="任务类型主表对象", description="任务类型主表")
//public class TaskTypeParent extends BaseEntity implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @ApiModelProperty(value = "id")
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//    @ApiModelProperty(value = "任务群组分类")
//    private String groupType;
//
//    @ApiModelProperty(value = "类型名称")
//    private String taskTypeName;
//
//
//    @ApiModelProperty(value = "任务对象唯一标识")
//    private Integer taskObjectId;
//
//   @ApiModelProperty(value = "任务对象")
//    private String taskObject;
//
//
//    @ApiModelProperty(value = "备注")
//    private String remark;
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
//    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private Date createDate;
//
//    @ApiModelProperty(value = "更新时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private Date updateDate;
//
//    @ApiModelProperty(value = "字段集合")
//    @TableField(exist = false)
//    private List<TaskTypeChild> list;
//
//    @ApiModelProperty(value = "任务对象集")
//    @TableField(exist = false)
//    private String taskObjectIds;
//
//    @ApiModelProperty(value = "任务对象list")
//    @TableField(exist = false)
//    private List taskObjectArr;
//
//    /**
//     * 登陆用户机构及下辖
//     */
//    @TableField(exist = false)
//    private List<String> userList;
//}
