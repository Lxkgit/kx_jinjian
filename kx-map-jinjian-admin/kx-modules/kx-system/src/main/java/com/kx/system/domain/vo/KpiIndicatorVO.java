package com.kx.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xifan_h
 * @date 2022/4/13 10:48
 * @project bg-panorama-vr-center
 * @description
 */
@Data
public class KpiIndicatorVO {

    private Long id;

    /**
     * 指标名称
     */
    @ApiModelProperty("指标名称")
    private String name;

    /**
     * 是否启用 1-启用 0-关闭
     */
    @ApiModelProperty("是否启用 1-启用 0-关闭")
    private Integer enable;

    /**
     * 指标备注
     */
    @ApiModelProperty("指标备注")
    private String remark;

    /**
     * 业务类型
     */
    @ApiModelProperty("业务类型")
    private String type;

    /**
     * 计算方式 SYS-系统计算 ART-人工填写
     */
    @ApiModelProperty("计算方式 SYS-系统计算 ART-人工填写")
    private String calType;

    /**
     * 计算表达式, 变量使用@{field_id}匹配
     */
    @ApiModelProperty("计算表达式")
    private String calExpression;


    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String calExpressionFrontStr;

    @ApiModelProperty("计算表达式前端结构")
    private List<Map> calExpressionFront;

    /**
     * 创建人用户id
     */
    @ApiModelProperty("创建人")
    private String createUserName;

    /**
     * 使用部门ids
     */
    @ApiModelProperty("使用部门ids, 逗号分隔")
    private String networkIds;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String networkIdArrayStr;

    @ApiModelProperty("使用部门id二维数组")
    private List<List<Long>> networkIdArray;

    /**
     * 指标父Id, 在指标类型下新增指标时需要传
     */
    @ApiModelProperty("父指标Id")
    private Long parentId;

    /**
     *
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("子指标集合")
    private List<KpiIndicatorVO> sonIndicatorList;
}
