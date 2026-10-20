package com.kx.system.domain;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import com.kx.system.domain.group.CustomGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 任务对象 pa_task
 *
 * @author kangxu
 * @date 2024-01-23
 */
@Data
public class PaTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private Long taskType;

    /** 任务类型名称 */
    @Excel(name = "任务类型名称")
    private String taskTypeName;

    /** 责任机构id */
    @Excel(name = "责任机构id")
    private String deptId;

    /** 责任机构名称 */
    @Excel(name = "责任机构名称")
    private String deptName;

    /** 任务优先级 */
    @Excel(name = "任务优先级")
    private Integer taskLevel;

    @ApiModelProperty("客户经理工号")
    @TableField(exist = false)
    private String managerJobNumber;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 拥有者 */
    @Excel(name = "拥有者")
    private Long owner;

    /** 拥有者姓名 */
    @Excel(name = "拥有者姓名")
    private String ownerName;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private Integer taskStatus;

    /**
     * 机构id集合
     */
    private List<String> deptIds;

    /**
     * 客群id
     */
    @ApiModelProperty("客群id")
    private String customGroupId;

    /** 客群名称 */
    @Excel(name = "客群名称")
    private String customGroupName;

    private List<Long> customGroupIdList;

    @ApiModelProperty("是否重复周期")
    private Integer isCycle;

    @ApiModelProperty("周期类型")
    private Integer cycleType;

    @ApiModelProperty("周期时间")
    private Integer cycleTime;

    @ApiModelProperty("任务描述")
    private String taskDesc;

    @ApiModelProperty("完成度")
    private String completeProcess;

    @ApiModelProperty("任务ids")
    private List<Long> ids;

    private List<CustomGroup> customGroups;

    /**
     * 任务对象1，2，3
     */
    private Integer taskObjectId;

    /**
     * 逻辑删除（0 未删除、1 删除）
     */
    private Integer deleteFlag;
}
