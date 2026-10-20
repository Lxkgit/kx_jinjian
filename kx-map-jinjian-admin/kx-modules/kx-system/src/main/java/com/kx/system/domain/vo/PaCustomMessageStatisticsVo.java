package com.kx.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaCustomMessageStatisticsVo {

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型")
    private String messageType;

    /**
     * 涉及客户数
     */
    @ApiModelProperty(value = "涉及客户数")
    private int clientQuantity;

    /**
     * 未服务客户数
     */
    @ApiModelProperty(value = "未服务客户数")
    private int alreadyClientQuantity;

    /**
     * 已服务客户数
     */
    @ApiModelProperty(value = "已服务客户数")
    private int notClientQuantity;

    /**
     * 完成率
     */
    @ApiModelProperty(value = "完成率")
    private String completionRate;
}
