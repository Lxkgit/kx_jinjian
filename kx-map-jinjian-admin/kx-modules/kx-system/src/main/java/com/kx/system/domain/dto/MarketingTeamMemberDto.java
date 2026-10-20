package com.kx.system.domain.dto;


import com.kx.system.domain.MarketingTeamMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * auth ligen
 */
@Data
public class MarketingTeamMemberDto extends MarketingTeamMember {

    /**
     * 成员姓名
     */
    @ApiModelProperty("成员姓名")
    private String userName;
    /**
     * 成员手机号
     */
    @ApiModelProperty("成员手机号")
    private String phonenumber;
    @ApiModelProperty("成员所属机构")
    private String orgName;



}
