package com.kx.system.domain.vo;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.kx.system.domain.MarketingTeam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * auth ligen
 */
@Data
public class MarketingTeamVo extends MarketingTeam {

    /**
     *团队成员姓名
     */
    @ApiModelProperty("团队成员姓名")
    private String teamUserName;
    @ApiModelProperty("团队成员工号")
    private String teamUserId;

}
