package com.kx.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author kangxu
 * @date 2024/4/8
 */
@Data
public class VisitInfoVO {

    //走访信息
    @ApiModelProperty(value = "走访人员")
    private String visitsManager;

    @ApiModelProperty(value = "走访时间")
    private Date visitsTime;
}
