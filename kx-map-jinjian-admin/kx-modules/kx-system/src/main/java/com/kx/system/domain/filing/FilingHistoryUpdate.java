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
 *
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("filing_history_update")
@ApiModel(value="对象", description="")
public class FilingHistoryUpdate implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "操作档案历史记录表 主键id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "模板表id")
    private Long model;


    @ApiModelProperty(value = "被操作档案的身份证号")
    private String identity;


    @ApiModelProperty(value = "被操作档案的建档id")
    private Long filingId;


    @ApiModelProperty(value = "档案修改人")
    private String updateBy;


    @ApiModelProperty(value = "档案修改人编码")
    private String updateByCode;


    @ApiModelProperty(value = "档案修改机构")
    private String updateOrg;


    @ApiModelProperty(value = "档案修改机构编码")
    private String updateOrgCode;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
