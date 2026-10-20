//package com.kx.system.domain.task;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import java.io.Serializable;
//import java.util.List;
//
///**
// * <p>
// * 任务类型字段表
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-24
// */
//@Data
//@ApiModel(value = "任务类型字段表对象", description = "任务类型字段表")
//public class TaskTypeChild implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @ApiModelProperty(value = "id")
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//    @ApiModelProperty(value = "类型父表id")
//    private Long parentId;
//
//
//    @ApiModelProperty(value = "字段名称")
//    private String fieldName;
//
//
//    @ApiModelProperty(value = "字段描述")
//    private String fieldDescription;
//
//
//    @ApiModelProperty(value = "是否必填 0-否 1-是")
//    private String isRequired;
//
//
//    @ApiModelProperty(value = "填写形式")
//    private String fillingForm;
//
//
//    @ApiModelProperty(value = "内容")
//    private String content;
//
//    @ApiModelProperty(value = "标签内容集")
//    @TableField(exist = false)
//    private List<List<PaLabel>> paLabels;
//}
