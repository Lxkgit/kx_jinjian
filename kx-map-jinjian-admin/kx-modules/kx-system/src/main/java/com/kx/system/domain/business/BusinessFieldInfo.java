package com.kx.system.domain.business;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 商机产品反馈字段表
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("business_field_info")
@ApiModel(value="商机产品反馈字段表对象", description="商机产品反馈字段表")
public class BusinessFieldInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "建档字段表id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "反馈产品id")
    private Long productId;


    @ApiModelProperty(value = "是必填 0-非必填 1-必填")
    private String isNeed;


    @ApiModelProperty(value = "字段中文名")
    private String fieldName;


    @ApiModelProperty(value = "字段类型  1-文字 2-布尔 3-图片 4-标签 5-打卡")
    private String fieldType;


    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
