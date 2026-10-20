package com.kx.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CustomGroupPageDTO implements Serializable {

    private Long id;

    @ApiModelProperty("客群类型（0：对私客户；1：对公客户；2：商户客户）")
    private Integer customType;

    @ApiModelProperty("客群分类id")
    private Long categoryId;

    @ApiModelProperty("客群编号")
    private String customGroupCode;

    @ApiModelProperty("客群名称")
    private String customGroupName;

    @ApiModelProperty("客户数量")
    private Integer customNum;

    @ApiModelProperty("客群来源")
    private String customGroupSource;

    @ApiModelProperty("说明")
    private String groupDesc;

    @ApiModelProperty("状态（0：待生成；1：已生成）")
    private Integer groupStatus;

    /**
     * 标签集合
     */
    private List<Long> labelIds;

    @ApiModelProperty("状态")
    private String groupStatusDesc;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("是否自动更新0：否；1：是")
    private Integer updateFlag;

    @ApiModelProperty("更新周期")
    private Integer updateCycle;

    @ApiModelProperty("客群标签信息，用于回显")
    private CascadeLabelDTO cascadeLabelDTO;

}
