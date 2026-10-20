//package com.kx.system.domain.task;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.kx.common.core.annotation.Excel;
//import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
///**
// * 任务列表导出结果集
// */
//@Data
//public class TaskBaseExportVo implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    /**
//     * 任务id
//     */
//    private Long id;
//    /**
//     * 任务名称
//     */
//    @Excel(name = " 任务名称")
//    private String taskName;
//    /**
//     * 任务类型id
//     */
//    private Long taskType;
//    /**
//     * 任务类型名称
//     */
//    @Excel(name = " 任务类型名称")
//    private String taskTypeName;
//    /**
//     * 创建人
//     */
//    @Excel(name = " 创建人")
//    private String createBy;
//    /**
//     * 责任机构list
//     */
//    @Excel(name = " 责任机构")
//    private String networks;
//    /**
//     * 主任务状态  0-未开始  1-进行中 2-已结束 3-手动结束
//     */
//    @Excel(name = "任务状态")
//    private Integer status;
//    /**
//     * 任务优先级
//     */
//    @Excel(name = " 任务优先级")
//    private Integer priority;
//    /**
//     * 开始时间
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = " 开始时间", dateFormat = "yyyy-MM-dd")
//    private Date beginDate;
//
//    /**
//     * 结束时间
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = " 结束时间", dateFormat = "yyyy-MM-dd")
//    private Date endDate;
//    /**
//     * 完成进度
//     */
//    @Excel(name = " 完成进度")
//    private String completeProcess;
//
//    /**查询参数*/
//    /**
//     * 导出记录id集
//     */
//    private List<Long> idList;
//    /**
//     * 登陆用户机构及下辖
//     */
//    private List<String> userList;
//    /**
//     * 任务状态
//     */
//    private List<Integer> statusList;
//    /**
//     * 责任机构
//     */
//    private List<String> networkCodeList;
//}
