package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaCustomMessageDTO {

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private String startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private String endDate;

    /**
     * 消息标题
     */
    @ApiModelProperty(value = "消息标题")
    private String messageTitle;

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型")
    private String messageType;

    /** 通知对象 */
    @ApiModelProperty(name = "通知对象")
    private String notifyObject;

    /** 通知时间 */
    @ApiModelProperty(name = "通知时间")
    private String notifyTime;

    /**
     * 经办人
     */
    @ApiModelProperty(value = "经办人")
    private String managerId;

    /**
     * 消息来源
     */
    @ApiModelProperty(value = "消息来源")
    private String messageSources;

    /**
     * 消息状态
     */
    @ApiModelProperty(value = "消息状态")
    private String messageState;

    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读")
    private String messageReadState;

    /**
     * 星标
     */
    @ApiModelProperty(value = "星标")
    private String messageStar;

    /**
     * 所属支行
     */
    @ApiModelProperty(value = "所属支行")
    private String networkCode;
}
