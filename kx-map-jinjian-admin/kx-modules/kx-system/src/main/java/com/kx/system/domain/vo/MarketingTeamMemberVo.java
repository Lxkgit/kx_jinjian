package com.kx.system.domain.vo;

import com.kx.system.domain.MarketingTeam;
import com.kx.system.domain.MarketingTeamMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * auth ligen
 */
@Data
public class MarketingTeamMemberVo extends MarketingTeamMember {

    /**
     * 成员姓名
     */
    @ApiModelProperty("成员姓名")
    private String userName;

}
