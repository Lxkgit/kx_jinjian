package com.kx.system.domain;

import java.time.LocalDateTime;
import java.util.Date;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 日程对象 pa_schedule
 *
 * @author kangxu
 * @date 2024-01-25
 */
@Data
public class PaSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 日程标题 */
    @Excel(name = "日程标题")
    private String scheduleTitle;

    /** 开始时间 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /** 结束时间 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /** 是否开启提醒 */
    @Excel(name = "是否开启提醒")
    private Integer needNotice;

    /** 是否全天 */
    @Excel(name = "是否全天")
    private Integer isAllDay;

    /** 到期是否自动完成 */
    @Excel(name = "到期是否自动完成")
    private Integer autoFinish;

    /** 是否重复 */
    @Excel(name = "是否重复")
    private Integer isRepeat;

    /** 提醒时间类型 */
    @Excel(name = "提醒时间类型")
    private Integer noticeTimeType;

    /** 提醒时间类型名称 */
    @Excel(name = "提醒时间类型名称")
    private String noticeTimeTypeName;

    /** 提醒时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提醒时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date noticeTime;

    /** 标记颜色 */
    @Excel(name = "标记颜色")
    private String markColor;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchId;

    /** 任务id */
    @Excel(name = "任务id")
    private Long taskId;

    /** 日程拥有者 */
    @Excel(name = "日程拥有者")
    private Long owner;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 状态名称 */
    @Excel(name = "状态名称")
    private String statusName;

    /** 日程拥有者姓名 */
    @Excel(name = "日程拥有者姓名")
    private String ownerName;

    /** 创建者姓名 */
    @Excel(name = "创建者姓名")
    private String createByName;

    /** 日程拥有者id */
    @Excel(name = "日程拥有者id")
    private String ownerIds;

    /** 日程拥有者姓名 */
    @Excel(name = "日程拥有者姓名")
    private String ownerNames;

}
