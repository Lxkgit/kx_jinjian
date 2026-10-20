package com.kx.system.domain.small;

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
 * 小微视图-凡荣贷模块-网格、机构纬度
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pa_view_prosperous_loan_org_grid")
@ApiModel(value="小微视图-凡荣贷模块-网格、机构纬度对象", description="小微视图-凡荣贷模块-网格、机构纬度")
public class PaViewProsperousLoan implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "贷款视图id", required = true)
    @TableId(value = "prosperous_loan_id", type = IdType.AUTO)
    private Long prosperousLoanId;


    @ApiModelProperty(value = "贷款ftp(万元，保留两位小数)")
    private String loanFtp;


    @ApiModelProperty(value = "机构编号")
    private String orgCode;


    @ApiModelProperty(value = "网格编号")
    private String gridCode;


    @ApiModelProperty(value = "客户经理编号")
    private String managerCode;


    @ApiModelProperty(value = "产品编号")
    private String productCode;


    @ApiModelProperty(value = "客户类型 1-绩效客户 2-授信客户  3-不含乡贷授信客户")
    private String customerType;


    @ApiModelProperty(value = "授信户数")
    private Integer grantCreditNum;


    @ApiModelProperty(value = "授信户数较上月")
    private Integer grantCreditCompareMonthNum;


    @ApiModelProperty(value = "授信户数较年初")
    private Integer grantCreditCompareYearNum;


    @ApiModelProperty(value = "网格内授信户数")
    private Integer grantCreditGridInsideNum;


    @ApiModelProperty(value = "网格内授信户数占比")
    private String grantCreditInsideNumRatio;


    @ApiModelProperty(value = "网格内授信户数较上月")
    private Integer grantCreditInsideCompareMonthNum;


    @ApiModelProperty(value = "网格内授信户数较年初")
    private Integer grantCreditInsideCompareYearNum;


    @ApiModelProperty(value = "网格外授信户数 0")
    private Integer grantCreditGridOutsideNum;


    @ApiModelProperty(value = "网格外授信户数占比 0")
    private String grantCreditOutsideNumRatio;


    @ApiModelProperty(value = "网格外授信户数较上月 0")
    private Integer grantCreditOutsideCompareMonthNum;


    @ApiModelProperty(value = "网格外授信户数较年初 0")
    private Integer grantCreditOutsideCompareYearNum;


    @ApiModelProperty(value = "用信户数")
    private Integer useCreditNum;


    @ApiModelProperty(value = "用信户数较上月")
    private Integer useCreditCompareMonthNum;


    @ApiModelProperty(value = "用信户数较年初")
    private Integer useCreditCompareYearNum;


    @ApiModelProperty(value = "网格内用信户数")
    private Integer useCreditGridInsideNum;


    @ApiModelProperty(value = "网格内用信户数占比")
    private String useCreditInsideNumRatio;


    @ApiModelProperty(value = "网格内用信户数较上月")
    private Integer useCreditInsideCompareMonthNum;


    @ApiModelProperty(value = "网格内用信户数较年初")
    private Integer useCreditInsideCompareYearNum;


    @ApiModelProperty(value = "网格外用信户数 0")
    private Integer useCreditGridOutsideNum;


    @ApiModelProperty(value = "网格外用信户数占比 0")
    private String useCreditOutsideNumRatio;


    @ApiModelProperty(value = "网格外用信户数较上月 0")
    private Integer useCreditOutsideCompareMonthNum;


    @ApiModelProperty(value = "网格外用信户数较年初 0")
    private Integer useCreditOutsideCompareYearNum;


    @ApiModelProperty(value = "授信金额(万元，整数)")
    private String grantCreditBal;


    @ApiModelProperty(value = "授信金额较上月(万元，整数)")
    private String grantCreditCompareMonthBal;


    @ApiModelProperty(value = "授信金额较年初(万元，整数)")
    private String grantCreditCompareYearBal;


    @ApiModelProperty(value = "网格内授信金额(万元，整数)")
    private String grantCreditGridInsideBal;


    @ApiModelProperty(value = "网格内授信金额占比")
    private String grantCreditInsideBalRatio;


    @ApiModelProperty(value = "网格内授信金额较上月(万元，整数)")
    private String grantCreditInsideCompareMonthBal;


    @ApiModelProperty(value = "网格内授信金额较年初(万元，整数)")
    private String grantCreditInsideCompareYearBal;


    @ApiModelProperty(value = "网格外授信金额(万元，整数) 0")
    private String grantCreditGridOutsideBal;


    @ApiModelProperty(value = "网格外授信金额占比 0")
    private String grantCreditOutsideBalRatio;


    @ApiModelProperty(value = "网格外授信金额较上月(万元，整数) 0")
    private String grantCreditOutsideCompareMonthBal;


    @ApiModelProperty(value = "网格外授信金额较年初(万元，整数) 0")
    private String grantCreditOutsideCompareYearBal;


    @ApiModelProperty(value = "用信金额(万元，整数)")
    private String useCreditBal;


    @ApiModelProperty(value = "用信金额较上月(万元，整数)")
    private String useCreditCompareMonthBal;


    @ApiModelProperty(value = "用信金额较年初(万元，整数)")
    private String useCreditCompareYearBal;


    @ApiModelProperty(value = "网格内用信金额(万元，整数)")
    private String useCreditGridInsideBal;


    @ApiModelProperty(value = "网格内用信金额占比")
    private String useCreditInsideBalRatio;


    @ApiModelProperty(value = "网格内用信金额较上月(万元，整数)")
    private String useCreditInsideCompareMonthBal;


    @ApiModelProperty(value = "网格内用信金额较年初(万元，整数)")
    private String useCreditInsideCompareYearBal;


    @ApiModelProperty(value = "网格外用信金额(万元，整数) 0")
    private String useCreditGridOutsideBal;


    @ApiModelProperty(value = "网格外用信金额占比 0")
    private String useCreditOutsideBalRatio;


    @ApiModelProperty(value = "网格外用信金额较上月(万元，整数) 0")
    private String useCreditOutsideCompareMonthBal;


    @ApiModelProperty(value = "网格外用信金额较年初(万元，整数) 0")
    private String useCreditOutsideCompareYearBal;


    @ApiModelProperty(value = "总和-不良余额(万元，整数)")
    private String totalBadBal;


    @ApiModelProperty(value = "总和-不良余额较上月(万元，整数)")
    private String totalBadCompareMonthBal;


    @ApiModelProperty(value = "总和-不良余额较年初(万元，整数)")
    private String totalBadCompareYearBal;


    @ApiModelProperty(value = "总和-不良率")
    private String totalBadRatio;


    @ApiModelProperty(value = "总和-网格内不良率")
    private String totalBadGridInsideRatio;


    @ApiModelProperty(value = "总和-网格外不良率 0")
    private String totalBadGridOutsideRatio;


    @ApiModelProperty(value = "总和-逾欠余额(万元，整数)")
    private String totalOverdueBal;


    @ApiModelProperty(value = "总和-逾欠余额较上月(万元，整数)")
    private String totalOverdueCompareMonthBal;


    @ApiModelProperty(value = "总和-逾欠余额较年初(万元，整数)")
    private String totalOverdueCompareYearBal;


    @ApiModelProperty(value = "总和-逾欠率")
    private String totalOverdueRatio;


    @ApiModelProperty(value = "总和-网格内逾欠率")
    private String totalOverdueGridInsideRatio;


    @ApiModelProperty(value = "总和-网格外逾欠率 0")
    private String totalOverdueGridOutsideRatio;


    @ApiModelProperty(value = "总和-收息率")
    private String totalInterestRatio;


    @ApiModelProperty(value = "总和-网格内收息率")
    private String totalInterestGridInsideRatio;


    @ApiModelProperty(value = "总和-网格外收息率 0")
    private String totalInterestGridOutsideRatio;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
