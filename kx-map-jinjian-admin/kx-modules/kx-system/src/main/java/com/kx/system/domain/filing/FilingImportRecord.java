package com.kx.system.domain.filing;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 建档客户导入记录
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("filing_import_record")
@ApiModel(value="建档客户导入记录对象", description="建档客户导入记录")
public class FilingImportRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "建档客户导入记录id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "导入状态 0-导入中 1-导入成功 2-导入失败 Records")
    private String importStatus;


    @ApiModelProperty(value = "导入数据量")
    private Integer importNum;


    @ApiModelProperty(value = "操作用户名称")
    private String userName;


    @ApiModelProperty(value = "操作用户id")
    private Long userId;


    @ApiModelProperty(value = "失败原因")
    private String failReason;

//
//    @ApiModelProperty(value = "导入时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createTime;
//
}
