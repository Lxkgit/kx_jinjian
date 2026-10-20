package com.kx.system.domain.filing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("filing_model_info")
@ApiModel(value = "表单字段模板表对象", description = "表单字段模板表")
public class FilingModelInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "采集字段模板表id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "模板表名称")
    private String modelName;


    @ApiModelProperty(value = "模板表类型 1-个人客户 2-企业 3-商户")
    private String modelType;


    @ApiModelProperty(value = "状态  0-未发布  1-已发布")
    private String status;


    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建组织code")
    private String createOrg;

    @ApiModelProperty(value = "优先级  1-业务优先  2-网格优先")
    private String priority;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 表字段
     */
    @TableField(exist = false)
    private List<FilingFieldInfo> fields;
    /**
     * 登陆用户机构及下辖
     */
    @TableField(exist = false)
    private List<String> userList;
    /**
     * 是否为完成要素建档参数
     */
    private String isFinishParam;

    private boolean editable;
}
