package com.kx.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 消息对象 pa_custom_message
 *
 * @author lc
 * @date 2022-06-01
 */
@Data
public class PaCustomMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户内码 */
    @ApiModelProperty(name = "客户内码")
    private String nm;

    /** 消息类型 */
    @ApiModelProperty(name = "消息类型")
    @NotEmpty(message = "消息类型不能为空")
    private String messageType;

    /** 消息标题 */
    @ApiModelProperty(name = "消息标题")
    @NotEmpty(message = "消息标题不能为空")
    private String messageTitle;

    /** 消息来源 */
    @ApiModelProperty(name = "消息来源")
    @NotEmpty(message = "消息来源不能为空")
    private String messageSources;

    /** 通知对象 */
    @ApiModelProperty(name = "通知对象")
    @NotEmpty(message = "通知对象不能为空")
    private String notifyObject;

    /** 通知时间 */
    @ApiModelProperty(name = "通知时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date notifyTime;

    /** 优先级 */
    @ApiModelProperty(name = "优先级")
    private String priority;

    /** 消息状态 1、待处理，2、已处理 */
    @ApiModelProperty(name = "消息状态 1、待处理，2、已处理")
    private String messageState;

    /** 星标 0、否，1是 */
    @ApiModelProperty(name = "星标 0、否，1是")
    private String messageStar;

    /** 是否已读 0、否，1是 */
    @ApiModelProperty(name = "是否已读 0、否，1是")
    private String messageReadState;

    /** 消息备注 */
    @ApiModelProperty(name = "消息备注")
    private String messageRemark;

    /** 消息详情 */
    @ApiModelProperty(name = "消息详情")
    private String messageDetails;

    /** 所属机构 */
    @ApiModelProperty(name = "所属机构")
    @NotEmpty(message = "所属机构不能为空")
    private String networkCode;

    /** 所属机构名称 */
    @ApiModelProperty(name = "所属机构名称")
    private String networkName;

    /** 经办人 */
    @ApiModelProperty(name = "经办人")
    private String managerId;

    /** 经办人姓名 */
    @ApiModelProperty(name = "经办人姓名")
    private String managerName;

    /** 删除字段 */
    private Long delFlag;
}
