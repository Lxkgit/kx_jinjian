package com.kx.system.domain;

import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 变更记录表
 *
 * @TableName pa_change_log
 */
@Data
public class PaChangeLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 更新主体
     */
    private String subject;

    /**
     * 操作类型（0-新增,1-修改,2-删除）
     */
//    @Excel(name = "操作类型", readConverterExp = "0=新增,1=修改,2=删除")
    private String operateType;

    @Excel(name = "操作类型")
    private String operateTypeVal;

    /**
     * 日志类型（0-锚点,1-网格,2-网格管理员）
     */
//    @Excel(name = "日志类型", readConverterExp = "0=网格,1=网格管理员,0=锚点")
    private String logType;

    /**
     * 操作前配置
     */
//    @Excel(name = "操作前配置")
    private String configBefore;

    /**
     * 操作后配置
     */
//    @Excel(name = "操作后配置")
    private String configUpdate;

    /**
     * 操作内容
     */
    @Excel(name = "操作内容")
    private String logContent;

    /**
     * 操作人Id
     */
    private Long operatorId;

    /**
     * 操作人工号
     */
    @Excel(name = "操作人工号")
    private String managerNum;

    /**
     * 操作人姓名
     */
    @Excel(name = "操作人姓名")
    private String operatorName;

    /**
     * 操作时间
     */
//    @Excel(name = "操作时间")
    private Date createTime;

    @Excel(name = "操作时间")
    private String createTimeStr ;
}