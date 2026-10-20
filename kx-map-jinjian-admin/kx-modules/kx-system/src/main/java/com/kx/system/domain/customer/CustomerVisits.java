package com.kx.system.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import com.kx.system.domain.label.LabelInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 客户走访信息表
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("customer_visits")
@ApiModel(value = "客户走访信息表对象", description = "客户走访信息表")
public class CustomerVisits implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "最近走访记录id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "客户小类型 1-个人 2-企业 3-商户")
    private String customerType;

    @ApiModelProperty(value = "客户大类型 1-绩效客户 2-授信客户 3-建档客户 4-收单客户 ")
    private String type;

    @ApiModelProperty(value = "证件号 (身份证号/统一社会信用代码)")
    private String identity;

    @ApiModelProperty(value = "客户名称")
    @Excel(name = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "联系电话")
    private String customerPhone;

    @ApiModelProperty(value = "工作单位")
    private String workUnit;

    @ApiModelProperty(value = "网格地址")
    private String gridAddress;

    @ApiModelProperty(value = "他行存储到期需求")
    private String otherBankDueDemand;

    @ApiModelProperty(value = "到期日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueTime;

    @ApiModelProperty(value = "到期金额(万元)")
    private String dueBal;

    @ApiModelProperty(value = "其他需求")
    private String otherDemand;

    @ApiModelProperty(value = "其他需求备注")
    private String otherDemandRemark;

    @ApiModelProperty(value = "走访黑户 0-否 1-是")
    private String isVisitsBlack;

    @ApiModelProperty(value = "风险客户备注")
    private String riskCustomerRemark;

    @ApiModelProperty(value = "走访打卡 0-否 1-是")
    private String visitsCheck;

    @ApiModelProperty(value = "走访时间")
    @DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    @Excel(name = "走访时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss")
    private Date visitsTime;

    @ApiModelProperty(value = "走访地址")
    @Excel(name = "走访地址")
    private String visitsAddress;

    @ApiModelProperty(value = "走访备注")
    private String visitsRemark;

    @ApiModelProperty(value = "走访图片")
    private String visitsPicture;

    @ApiModelProperty(value = "走访人员")
    @Excel(name = "走访人")
    private String visitsManager;

    @ApiModelProperty(value = "走访人员工号")
    @Excel(name = "走访人工号")
    private String visitsManagerCode;

    @ApiModelProperty(value = "走访机构")
    private String visitsOrg;

    @ApiModelProperty(value = "走访机构号")
    private String visitsOrgCode;

    @ApiModelProperty(value = "走访形式")
    private String visitsWay;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<CustomerVisits> list;

    /**
     * 标签ids
     */
    private List<LabelInfo> labels;

    @ApiModelProperty(value = "地址类型")
    private String gridAddressType;

    @ApiModelProperty(value = "详细地址补充")
    private String gridAddressDetail;

    /**
     * 客户编码
     */
    private String customerNm;

    /**
     * 坐标
     */
    private String location;

    /**
     * 打卡地点
     */
    private String checkinAddress;

    private String visitLabel;

    private String visitsLocation;

}
