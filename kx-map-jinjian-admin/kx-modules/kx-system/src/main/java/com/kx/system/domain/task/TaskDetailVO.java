//package com.kx.system.domain.task;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.kx.common.core.annotation.Excel;
//import com.kx.common.core.web.domain.BaseEntity;
//import com.kx.system.domain.common.BasePageReq;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//
//@Data
//public class TaskDetailVO extends BaseEntity {
//    @ApiModelProperty("详情id")
//    private Long id;
//
//    @ApiModelProperty("任务id")
//    private Long taskId;
//
//    @ApiModelProperty("客户id")
//    private Long clientId;
//
//    @ApiModelProperty("客户名称")
//    @Excel(name = "任务对象")
//    private String customerName;
//
//    @ApiModelProperty("业务管户人")
//    private String businessManageUser;
//
//    @ApiModelProperty("客户内码")
//    @Excel(name = "行内编码")
//    private String customerInterNum;
//
//    @ApiModelProperty("任务类型")
//    @Excel(name = "任务类型")
//    private String taskTypeName;
//
//    @ApiModelProperty("任务类型Id")
//    private Long taskType;
//
//    @ApiModelProperty("任务对象唯一标识")
//    private Integer taskObjectId;
//
//    @ApiModelProperty("任务名称")
//    @Excel(name = "任务名称")
//    private String taskName;
//
//    @ApiModelProperty("任务状态")
//    private Integer status;
//
//    @ApiModelProperty("主任务状态列表")
//    private List<Integer> multiMasterStatus;
//
//    @ApiModelProperty("主任务状态")
//    private Integer masterTaskStatus;
//
//    @ApiModelProperty("主任务状态描述")
//    @Excel(name = "主任务状态")
//    private String masterTaskStatusName;
//
//    @ApiModelProperty("任务优先级")
//    private Integer taskLevel;
//
//    @ApiModelProperty("任务优先级名称")
//    @Excel(name = "任务优先级")
//    private String taskLevelName;
//
//    @ApiModelProperty("子任务状态列表")
//    private List<Integer> multiChildStatus;
//
//    @ApiModelProperty("子任务状态")
//    private Integer childTaskStatus;
//
//    @ApiModelProperty("子任务状态描述")
//    @Excel(name = "子任务状态")
//    private String childTaskStatusName;
//
//    @ApiModelProperty("责任机构id")
//    private List<Long> deptIds;
//
//    @ApiModelProperty("责任机构")
//    @Excel(name = "责任机构")
//    private String deptName;
//
//    @ApiModelProperty("客户经理工号")
//    private String managerJobNumber;
//
//    @ApiModelProperty("执行人")
//    @Excel(name = "执行人")
//    private String managerName;
//
//    @ApiModelProperty("开始时间")
//    @Excel(name = "开始时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date startTime;
//
//    @ApiModelProperty("结束时间")
//    @Excel(name = "结束时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date endTime;
//
//    @ApiModelProperty("完成度")
//    @Excel(name = "信息完整度")
//    private String integrity;
//
//    @ApiModelProperty("联系电话")
//    private String contactPhone;
//
//    @ApiModelProperty("联系地址")
//    private String customerAddress;
//
//    @ApiModelProperty("预约方式")
//    private String planMethod;
//
//    @ApiModelProperty("预约方式Code")
//    private Integer planMethodCode;
//
//    @ApiModelProperty("预约日期")
//    private Date planDate;
//
//    @ApiModelProperty("预约备注")
//    private String remark;
//
//    @ApiModelProperty("任务方式")
//    private String taskMethod;
//
//    @ApiModelProperty("任务方式Code")
//    private Integer taskMethodCode;
//
//    @ApiModelProperty("联系时间")
//    private Date contactTime;
//
//    @ApiModelProperty("图片")
//    private String pic;
//
//    @ApiModelProperty("任务类型下的字段内容")
//    private List<TaskDetailContent> contents;
//
//    @ApiModelProperty("任务类型下的字段内容json")
//    private String feedback;
//
//    @ApiModelProperty("网格归属客户经理")
//    private String gridManager;
//
//    @ApiModelProperty("任务负责客户经理")
//    private String businessManager;
//
//    @ApiModelProperty("客群ids")
//    private List<Long> customGroupIds;
//
//    @ApiModelProperty("详情ids")
//    private List<Long> ids;
//
//    @ApiModelProperty("证件号")
//    private String customerIdentity;
//
//    @ApiModelProperty(value = "客户编码")
//    private String customerNm;
//
//    private Integer pageNum;
//
//    private Integer pageSize;
//
//    //客户类型（1-个人 2-企业 3-商户）
//    private Integer customerType;
//
//
//    /**
//     * 经度
//     */
//    private BigDecimal lon;
//
//    /**
//     * 维度
//     */
//    private BigDecimal lat;
//}
