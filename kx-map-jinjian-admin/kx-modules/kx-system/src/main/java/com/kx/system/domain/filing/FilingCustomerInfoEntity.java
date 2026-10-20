package com.kx.system.domain.filing;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("filing_customer_info")
@ApiModel(value = "建档客户基础信息表对象", description = "建档客户基础信息表")
@HeadRowHeight(25)
public class FilingCustomerInfoEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "建档id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    @ExcelIgnore
    private List<Long> ids;

    /**
     * 客群id
     */
    @TableField(exist = false)
    @ExcelIgnore
    private Long customGroupId;

    @ApiModelProperty(value = "客户类型 1-个人 2-企业 3-商户 4-其他")
    @ExcelProperty("客户类型")
    @HeadFontStyle(color = 2)
    private String customerType;

    @ApiModelProperty("所属网格编号集合")
    @TableField(exist = false)
    @ExcelIgnore
    private List<String> belongGridCodeArr;

    @ApiModelProperty(value = "客户名称")
    @ExcelProperty("客户名称")
    @HeadFontStyle(color = 2)
    private String customerName;


    @ApiModelProperty(value = "联系地址")
    @ExcelProperty("联系地址")
    @HeadFontStyle(color = 2)
    private String customerAddress;

    @ApiModelProperty(value = "地址类型 1-经营地址 2-工作地址 3-家庭地址 4-户籍地址")
    @ExcelProperty("地址类型")
    @HeadFontStyle(color = 2)
    private String customerAddressType;


    @ApiModelProperty(value = "手机号码")
    @ExcelProperty("手机号码")
    @HeadFontStyle(color = 2)
    private String customerPhone;


    @ApiModelProperty(value = "证件号(身份证号/统一社会信用代码)")
    @ExcelProperty("证件号(身份证号/统一社会信用代码)")
    @HeadFontStyle(color = 2)
    private String customerIdentity;


    @ApiModelProperty(value = "客户来源 1-客户介绍 2-第三方介绍 3-陌拜")
    @ExcelProperty("客户来源")
    @HeadFontStyle(color = 2)
    private String customerSource;

    @ApiModelProperty(value = "介绍人证件号(身份证号/统一社会信用代码)")
    @ExcelProperty("介绍人证件号(身份证号/统一社会信用代码)")
    private String customerIntroducerIdentity;


    @ApiModelProperty(value = "所属网格编码")
    @ExcelIgnore
    private String belongGridCode;

    @ApiModelProperty(value = "所属网格名称")
    @ExcelIgnore
    private String belongGridName;


    @ApiModelProperty(value = "建档人编码")
    @ExcelProperty("建档人工号")
    @HeadFontStyle(color = 2)
    private String filingManagerCode;


    @ApiModelProperty(value = "建档人名称")
    @ExcelProperty("建档人")
    @HeadFontStyle(color = 2)
    private String filingManagerName;


    @ApiModelProperty(value = "建档机构编码")
    @ExcelProperty("建档机构号")
    @HeadFontStyle(color = 2)
    private String filingOrgCode;


    @ApiModelProperty(value = "建档机构名称")
    @ExcelProperty("建档机构")
    @HeadFontStyle(color = 2)
    private String filingOrgName;


    @ApiModelProperty(value = "建档进度")
    @ExcelIgnore
    private String filingProgress;


    @ApiModelProperty(value = "建档来源 1-人工导入 2-批量导入")
    @ExcelIgnore
    private String filingSource;


    @ApiModelProperty(value = "建档状态 0-临时建档 1-已建档 2-完成建档")
    @ExcelIgnore
    private String filingStatus;


    @ApiModelProperty(value = "建档日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelIgnore
    private Date filingDate;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date createTime;


    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date updateTime;


    @ApiModelProperty(value = "客户编号")
    @ExcelIgnore
    private String customerNm;

    @ApiModelProperty(value = "客户照片")
    @ExcelIgnore
    private String customerPhoto;

    @ApiModelProperty(value = "打卡(经纬度)")
    @ExcelIgnore
    private String clockIn;

    /**
     * 是否暂存
     */
    @ExcelIgnore
    private String isTmp;

    @ExcelIgnore
    private FilingMessageDetail filingMessageDetail;

    @ExcelIgnore
    @ApiModelProperty(value = "当前客户绑定的模板id")
    private Long modelId;

    @ExcelIgnore
    @ApiModelProperty(value = "当前客户移交状态 0-未审核 1-同意 2-拒绝")
    private String moveStatus;

    /** 所属部门 */
    @ExcelIgnore
    private Long deptId;

    @ExcelIgnore
    private List<String> deptList;

    @ExcelIgnore
    @ApiModelProperty(value = "移动端搜索参数")
    private String query;

    /** 客群集合 */
    @ExcelIgnore
    @TableField(exist = false)
    private List<Long> groupIdList;
}
