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
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Date;
//
///**
// * <p>
// * 任务客户对象清单表
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-27
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@TableName("task_custom_object")
//@ApiModel(value="任务客户对象清单表对象", description="任务客户对象清单表")
//public class TaskCustomObject implements Serializable {
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
//    @ApiModelProperty(value = "网格客户经理号")
//    private String gridManagerId;
//
//
//    @ApiModelProperty(value = "网格客户经理名称")
//    private String gridManagerName;
//
//
//    @ApiModelProperty(value = "责任客户经理号")
//    private String taskManagerId;
//
//
//    @ApiModelProperty(value = "责任客户经理名称")
//    private String taskManagerName;
//
//
//    @ApiModelProperty(value = "所属网点code")
//    private String networkCode;
//
//
//    @ApiModelProperty(value = "网点名称")
//    private String networkName;
//
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
//    @ApiModelProperty(value = "操作 ")
//    @TableField(exist = false)
//    private String operate;
//
//    /**
//     *锚点名称
//     */
//    private String clientAnchorName;
//
//    /**
//     *锚点ID
//     */
//    private Long clientAnchorId;
//
//    /**
//     * 经度
//     */
//    private BigDecimal lon;
//
//    /**
//     * 纬度
//     */
//    private BigDecimal lat;
//
//}
