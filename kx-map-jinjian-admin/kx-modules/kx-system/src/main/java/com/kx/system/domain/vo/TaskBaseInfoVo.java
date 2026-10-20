package com.kx.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import com.kx.system.domain.mobile.TaskMobileDetailVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 任务列表信息返回结果集
 */
@Data
public class TaskBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 任务id
     */
    private Long id;
    /**
     * 任务下派记录id
     */
    private Long detailId;
    /**
     * 客户内码
     */
    @Excel(name = " 客户内码")
    private String code;
    /**
     * 客户名称
     */
    @Excel(name = " 客户名称")
    private String name;
    /**
     * 任务名称
     */
    @Excel(name = " 任务名称")
    private String taskName;
    /**
     * 客群id
     */
    @ApiModelProperty("客群id")
    private String customGroupId;
    private List<Long> customGroupIdList;
    private String[] customGroupIds;

    /** 客群名称 */
    @Excel(name = "客群名称")
    private String customGroupName;
    /**
     * 任务类型id
     */
    private Long taskType;
    /**
     * 任务类型名称
     */
    @Excel(name = " 任务类型名称")
    private String taskTypeName;
    /**
     * 任务责任人
     */
    @Excel(name = " 任务责任人")
    private String taskManagerName;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 责任机构(多个以逗号隔开)
     */
    private String deptId;
    /**
     * 明细责任机构(返回)
     */
    @Excel(name = " 明细责任机构")
    private String deptName;

    /**
     * 主任务状态  0-未开始  1-进行中 2-已结束
     */
    @Excel(name = " 主任务状态 0-未开始  1-进行中 2-已结束 3-手动结束")
    private Integer taskStatus;

    @ApiModelProperty("任务状态描述")
    private String statusName;
    /**
     * 子任务状态  0-未完成 1-已完成 2-结束
     */
    @Excel(name = " 子任务状态 0-未完成 1-已完成 2-结束")
    private Integer childStatus;
    /**
     * 任务优先级
     */
    @Excel(name = " 任务优先级")
    private Integer taskLevel;
    /**
     * 任务优先级中文名称
     */
    private String taskLevelName;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = " 开始时间", dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = " 结束时间", dateFormat = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 完成进度
     */
    private String completeProcess;

    @ApiModelProperty(value = "点集")
    private String positions;

    /**
     * 信息完整度(走访信息)
     */
    @Excel(name = "信息完整度")
    private String completePercent;
    /**
     *  是否自己创建的任务 0-否 1-是
     */
    private Integer isCreateBy;
    /**
     * 移交记录ids
     */
    private List ids;
    /**
     * 导出记录id集
     */
    private List<Long> idList;
    /**
     * 责任机构(查询)
     */
    private String networkId;
    /**
     * 责任机构(查询)
     */
    private List<String> queryList;
    /**
     * 登录用户名(查询)
     */
    private String nickName;
    /**
     * 登录用户id
     */
    private Long userId;
    /**
     * 登录用户客户经理号(查询)
     */
    private String userManagerCode;
    /**
     * 登陆用户机构及下辖
     */
    private List<String> userList;

    /**移动端参数*/
    /**
     * 任务类型
     */
    private List<Long> typeList;
    /**
     * 任务状态
     */
    private List<Integer> statusList;
    /**
     * 子任务状态
     */
    private List<Integer> childStatusList;
    /**
     * 优先级
     */
    private List<Integer> priorityList;
    /**
     * 任务名称
     */
    private String taskMasterName;
    /**
     * 是否周期任务 0-否 1-是
     */
    private Integer isCycle;

    /**
     *  周期类型
     */
    private Integer cycleType;

    /**
     *  周期类型中文名称
     */
    private String cycleTypeName;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 任务列表统计子任务数据
     */
    private TaskMobileDetailVo taskMobileDetailVo;

    /**
     *锚点名称
     */
    private String clientAnchorName;

    /**
     *锚点ID
     */
    private Long clientAnchorId;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 子任务数
     */
    private int taskDetailNum;

    /**
     * 未完成
     */
    private int unFinishNum;

    /**
     * 已完成
     */
    private int finishNum;

    /**
     * 手动结束
     */
    private int manualEndNum;

    private Long owner;
}
