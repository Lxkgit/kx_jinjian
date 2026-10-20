package com.kx.system.domain.filing;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 建档考核字段表
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("filing_field_info")
@ApiModel(value="建档考核字段表对象", description="建档考核字段表")
public class FilingFieldInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "建档字段表id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "模板表id")
    private Long modelId;


    @ApiModelProperty(value = "字段中文名")
    private String chineseName;


    @ApiModelProperty(value = "字段英文名称")
    private String englishName;


    @ApiModelProperty(value = "使用者类型 1-家庭 2-企业  3-商户 4-其他")
    private String useType;


    @ApiModelProperty(value = "字段类型 ")
    private String fieldType;


    @ApiModelProperty(value = "是否为完成要素  0-否  1-是")
    private String isFinish;


    @ApiModelProperty(value = "是否必填 0-否 1是")
    private String isMust;


    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "字段阈值")
    private String content;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
