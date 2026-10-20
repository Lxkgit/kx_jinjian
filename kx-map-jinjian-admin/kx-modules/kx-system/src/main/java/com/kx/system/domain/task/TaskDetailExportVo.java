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
// *  任务明细导出结果集
// */
//@Data
//public class TaskDetailExportVo implements Serializable {
//
//        private static final long serialVersionUID = 1L;
//        /**
//         * 任务id
//         */
//        private Long id;
//        /**
//         * 任务下派记录id
//         */
//        @Excel(name = " 明细id")
//        private Long detailId;
//
//        /**
//         *   任务名称
//         */
//        @Excel(name = " 任务名称")
//        private String taskName;
//        /**
//         *   客户名称
//         */
//        @Excel(name = " 客户名称")
//        private String name;
//        /**
//         *   客户内码
//         */
//        @Excel(name = " 客户内码")
//        private String code;
//        /**
//         *  任务类型id
//         */
//        private Long taskType;
//        /**
//         *  任务类型名称
//         */
//        @Excel(name = " 任务类型名称")
//        private String taskTypeName;
//        /**
//         * 主任务状态  0-未开始  1-进行中 2-已结束
//         */
//        @Excel(name = " 主任务状态")
//        private Integer status;
//        /**
//         * 子任务状态  0-未完成 1-已完成 2-结束
//         */
//        @Excel(name = " 子任务状态")
//        private Integer childStatus;
//        /**
//         * 任务优先级
//         */
//        @Excel(name = " 任务优先级")
//        private Integer priority;
//        /**
//         * 责任机构
//         */
//        private String networkCode;
//        /**
//         * 责任机构名称
//         */
//        @Excel(name = " 明细责任机构")
//        private String networkName;
//        /**
//         *  任务责任人
//         */
//        @Excel(name = " 任务责任人")
//        private String taskManagerName;
//        /**
//         *  开始时间
//         */
//        @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
//        @DateTimeFormat(pattern="yyyy-MM-dd")
//        @Excel(name = " 开始时间",dateFormat = "yyyy-MM-dd")
//        private Date beginDate;
//
//        /**
//         *  结束时间
//         */
//        @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
//        @DateTimeFormat(pattern="yyyy-MM-dd")
//        @Excel(name = " 结束时间",dateFormat = "yyyy-MM-dd")
//        private Date endDate;
//        /**
//         *  信息完整度(走访信息)
//         */
//        @Excel(name = "信息完整度")
//        private String completePercent;
//        /**
//         * 创建人
//         */
//        private String createBy;
//        /**
//         *  明细id集
//         */
//        private List<Long> ids;
//        /**
//         * 导出记录id集
//         */
//        private List<Long> idList;
//        /**
//         * 登陆用户机构及下辖
//         */
//        private List<String> userList;
//        /**
//         * 任务状态
//         */
//        private List<Integer> statusList;
//        /**
//         * 责任机构
//         */
//        private List<String> networkCodeList;
//        /**
//         * 责任机构
//         */
//        private List<String> list;
//}
