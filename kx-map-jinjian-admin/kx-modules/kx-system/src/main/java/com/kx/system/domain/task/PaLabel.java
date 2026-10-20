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
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
///**
// * <p>
// * 标签表
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-06-27
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@TableName("pa_label")
//@ApiModel(value = "标签表对象", description = "标签表")
//public class PaLabel implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//
//    @ApiModelProperty(value = "标签表id", required = true)
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//
//    @ApiModelProperty(value = "父级id(顶级为0)")
//    private Long parentId;
//
//
//    @ApiModelProperty(value = "标签名称")
//    private String labelName;
//
//
//    @ApiModelProperty(value = "用户id")
//    private Long userId;
//
//
//    @ApiModelProperty(value = "用户名称")
//    private String userName;
//
//
//    @ApiModelProperty(value = "排序")
//    private Integer sort;
//
//
//    @ApiModelProperty(value = "是否单选 1-单选 2-多选")
//    private Integer isSingle;
//
//
//    @ApiModelProperty(value = "标签级别")
//    private Integer level;
//
//
//    @ApiModelProperty(value = "是否删除 0-未删除  1-已删除")
//    private Integer isDeleted;
//
//
//    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createDate;
//
//
//    @ApiModelProperty(value = "更新时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date updateDate;
//
//    /**
//     *  非表字段
//     */
//    @ApiModelProperty(value = "是否有子标签")
//    @TableField(exist = false)
//    private List<PaLabel> children;
//
//    @ApiModelProperty(value = "子标签数量")
//    @TableField(exist = false)
//    private Integer labelNum;
//
//    @ApiModelProperty(value = "是否单选中文名 1-单选 2-多选")
//    @TableField(exist = false)
//    private String isSingleName;
//
//    @ApiModelProperty(value = "父级标签名称")
//    @TableField(exist = false)
//    private String parentLabelName;
//
//    @ApiModelProperty(value = "子标签集合")
//    private List<PaLabel> childPaLabelList;
//}
