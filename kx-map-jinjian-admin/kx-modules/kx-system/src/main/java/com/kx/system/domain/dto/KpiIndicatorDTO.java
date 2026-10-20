package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author xifan_h
 * @date 2022/4/13 10:48
 * @project bg-panorama-vr-center
 * @description
 */
@Data
public class KpiIndicatorDTO {

    @ApiModelProperty("传id则为更新逻辑")
    private Long id;

    /**
     * 指标名称
     */
    @NotBlank(message = "指标名称不能为空")
    @ApiModelProperty("指标名称")
    private String name;

    /**
     * 是否启用 1-启用 0-关闭
     */
    @ApiModelProperty("是否启用 1-启用 0-关闭")
    private Integer enable = 0;

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
    @ApiModelProperty("计算表达式, 变量使用@{field_id}匹配")
    private String calExpression;

    @ApiModelProperty("计算表达式前端结构")
    private String calExpressionFront;

    /**
     * 创建人用户id
     */
    @ApiModelProperty("创建人用户id")
    private Long createUserId;

    /**
     * 使用部门ids
     */
    @ApiModelProperty("使用部门id二维数组")
    private List<List<Long>> networkIdArray;

    /**
     * 指标父Id, 在指标类型下新增指标时需要传
     */
    @ApiModelProperty("指标父Id, 在指标类型下新增指标时需要传")
    private Long parentId;
}
