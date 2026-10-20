package com.kx.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * auth ligen
 * 营销团队成员
 */
@Data
public class MarketingTeamMember {
    /**
     *id
     */
    @ApiModelProperty("id")
    private Long Id;
    /**
     *加入日期
     */
    @ApiModelProperty("加入日期")
    private Date joinDate;
    /**
     *成员号
     */
    @ApiModelProperty("成员号")
    private String userId;
    /**
     *所属营销团队ID
     */
    @ApiModelProperty("所属营销团队ID")
    private Long mktTeamId;
    /**
     *是否团队负责人
     */
    @ApiModelProperty("是否团队负责人")
    private String isLeader;
    /**
     *创建人ID
     */
    @ApiModelProperty("创建人ID")
    private String createUserId;
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
     *岗位
     */
    @ApiModelProperty("岗位")
    private String position;
    /**
     *入行日期
     */
    @ApiModelProperty("入行日期")
    private String enterdate;
}
