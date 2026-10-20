package com.kx.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * auth ligen
 * 营销团队
 */
@Data
public class MarketingTeam {

    /**
     * 营销团队编号
     */
    @ApiModelProperty("营销团队编号")
    private Long mktTeamId;
    /**
     *营销团队名称
     */
    @ApiModelProperty("营销团队名称")
    private String mktTeamName;
    /**
     *所属机构
     */
    @ApiModelProperty("所属机构")
    private String orgId;
    /**
     *营销团队负责人
     */
    @ApiModelProperty("营销团队负责人")
    private String teamLeader ;
    /**
     *创建日期
     */
    @ApiModelProperty("创建日期")
    private Date createDate;
    /**
     *负责人手机号
     */
    @ApiModelProperty("负责人手机号")
    private String leadTelephone ;
    /**
     *营销团队状态
     */
    @ApiModelProperty("营销团队状态")
    private String teamStatus ;
    /**
     *创建人ID
     */
    @ApiModelProperty("创建人ID")
    private String createUserId;
    /**
     *创建人名称
     */
    @ApiModelProperty("创建人名称")
    private String createUserName;
    /**
     *创建人所属机构
     */
    @ApiModelProperty("创建人所属机构")
    private String createUserOrgId;
    /**
     *营销团队负责人ID
     */
    @ApiModelProperty("营销团队负责人ID")
    private String teamLeaderId;
    /**
     *三级机构
     */
    @ApiModelProperty("三级机构")
    private String threeOrgId;
    /**
     *所属机构名称
     */
    @ApiModelProperty("所属机构名称")
    private String orgName ;
    /**
     *一级部门
     */
    @ApiModelProperty("一级部门")
    private String deptfirst;
    /**
     *二级部门
     */
    @ApiModelProperty("二级部门")
    private String deptsecond;
    /**
     *最近一次修改日期
     */
    @ApiModelProperty("最近一次修改日期")
    private String lastModifyDate;
    /**
     *最近一次修改人
     */
    @ApiModelProperty("最近一次修改人")
    private String lastModifyUser;
    /**
     *是否考核
     */
    @ApiModelProperty("是否考核")
    private String ifCheck;

}
