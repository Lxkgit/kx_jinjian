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
 * 反馈详情表
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("business_detail_info")
@ApiModel(value="反馈详情表对象", description="反馈详情表")
public class BusinessDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "商机反馈详情id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "反馈产品id")
    private Long productId;


    @ApiModelProperty(value = "反馈字段id")
    private Long fieldId;


    @ApiModelProperty(value = "证件号 (身份证号/统一社会信用代码)")
    private String identity;


    @ApiModelProperty(value = "字段内容")
    private String fieldContent;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
