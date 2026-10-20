package com.kx.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 待办事项对象 pa_backlog
 *
 * @author kangxu
 * @date 2024-01-31
 */
@Data
public class PaBacklog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 事项类型 */
    @Excel(name = "事项类型")
    private Long matterType;

    /** 事项类型 */
    @Excel(name = "事项类型名称")
    private String matterTypeName;

    /** 事项说明 */
    @Excel(name = "事项说明")
    private String matterContent;

    /** 状态（待处理、已处理） */
    @Excel(name = "状态", readConverterExp = "待处理、已处理")
    private Integer status;

    /** 状态（待处理、已处理） */
    @Excel(name = "状态名称", readConverterExp = "待处理、已处理")
    private String statusName;

    /** 发起人 */
    @Excel(name = "发起人")
    private Long promoterUserId;

    /** 发起人 */
    @Excel(name = "发起人姓名")
    private String promoterUserName;

    /** 发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发起时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date promoterTime;

    /** 经办人 */
    @Excel(name = "经办人")
    private Long operatorUserId;

    /** 经办人 */
    @Excel(name = "经办人姓名")
    private String operatorUserName;

    /** 经办时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "经办时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operatorTime;

}
