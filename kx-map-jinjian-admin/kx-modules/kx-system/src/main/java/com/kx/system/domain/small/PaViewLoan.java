package com.kx.system.domain.small;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.system.util.convert.CsvIntegerConvert;
import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 小微视图-贷款模块-网格、机构纬度
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pa_view_loan_org_grid")
@ApiModel(value = "小微视图-贷款模块-网格、机构纬度对象", description = "小微视图-贷款模块-网格、机构纬度")
public class PaViewLoan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "贷款视图id", required = true)
    @TableId(value = "loan_id", type = IdType.AUTO)
    private Long loanId;

    @ApiModelProperty(value = "贷款ftp(万元，保留两位小数)")
    @CsvBindByName(column = "loan_ftp")
    private String loanFtp;

    @ApiModelProperty(value = "机构编号")
    @CsvBindByName(column = "org_code")
    private String orgCode;

    @ApiModelProperty(value = "网格编号")
    @CsvBindByName(column = "grid_code")
    private String gridCode;

    @ApiModelProperty(value = "客户经理编号")
    @CsvBindByName(column = "manage_code")
    private String managerCode;

    @ApiModelProperty(value = "客户类型 1-绩效客户 2-授信客户  3-不含乡贷授信客户")
    @CsvBindByName(column = "customer_type")
    private String customerType;

    @ApiModelProperty(value = "授信户数")
    @CsvCustomBindByName(column = "grant_credit_num", converter = CsvIntegerConvert.class)
    private Integer grantCreditNum;

    @ApiModelProperty(value = "授信户数较上月")
    @CsvCustomBindByName(column = "grant_credit_compare_month_num", converter = CsvIntegerConvert.class)
    private Integer grantCreditCompareMonthNum;

    @ApiModelProperty(value = "授信户数较年初")
    @CsvCustomBindByName(column = "grant_credit_compare_year_num", converter = CsvIntegerConvert.class)
    private Integer grantCreditCompareYearNum;

    @ApiModelProperty(value = "网格内授信户数")
    @CsvCustomBindByName(column = "grant_credit_grid_inside_num", converter = CsvIntegerConvert.class)
    private Integer grantCreditGridInsideNum;

    @ApiModelProperty(value = "网格内授信户数占比")
    @CsvBindByName(column = "grant_credit_inside_num_ratio")
    private String grantCreditInsideNumRatio;

    @ApiModelProperty(value = "网格内授信户数较上月")
    @CsvCustomBindByName(column = "grant_credit_inside_compare_month_num", converter = CsvIntegerConvert.class)
    private Integer grantCreditInsideCompareMonthNum;

    @ApiModelProperty(value = "网格内授信户数较年初")
    @CsvCustomBindByName(column = "grant_credit_inside_compare_year_num", converter = CsvIntegerConvert.class)
    private Integer grantCreditInsideCompareYearNum;

    @ApiModelProperty(value = "网格外授信户数 0")
    @CsvCustomBindByName(column = "grant_credit_grid_outside_num", converter = CsvIntegerConvert.class)
    private Integer grantCreditGridOutsideNum;

    @ApiModelProperty(value = "网格外授信户数占比 0")
    @CsvBindByName(column = "grant_credit_outside_num_ratio")
    private String grantCreditOutsideNumRatio;

    @ApiModelProperty(value = "网格外授信户数较上月 0")
    @CsvCustomBindByName(column = "grant_credit_outside_compare_month_num", converter = CsvIntegerConvert.class)
    private Integer grantCreditOutsideCompareMonthNum;

    @ApiModelProperty(value = "网格外授信户数较年初 0")
    @CsvCustomBindByName(column = "grant_credit_outside_compare_year_num", converter = CsvIntegerConvert.class)
    private Integer grantCreditOutsideCompareYearNum;

    @ApiModelProperty(value = "用信户数")
    @CsvCustomBindByName(column = "use_credit_num", converter = CsvIntegerConvert.class)
    private Integer useCreditNum;

    @ApiModelProperty(value = "用信户数较上月")
    @CsvCustomBindByName(column = "use_credit_compare_month_num", converter = CsvIntegerConvert.class)
    private Integer useCreditCompareMonthNum;

    @ApiModelProperty(value = "用信户数较年初")
    @CsvCustomBindByName(column = "use_credit_compare_year_num", converter = CsvIntegerConvert.class)
    private Integer useCreditCompareYearNum;

    @ApiModelProperty(value = "网格内用信户数")
    @CsvCustomBindByName(column = "use_credit_grid_inside_num", converter = CsvIntegerConvert.class)
    private Integer useCreditGridInsideNum;

    @ApiModelProperty(value = "网格内用信户数占比")
    @CsvBindByName(column = "use_credit_inside_num_ratio")
    private String useCreditInsideNumRatio;

    @ApiModelProperty(value = "网格内用信户数较上月")
    @CsvCustomBindByName(column = "use_credit_inside_compare_month_num", converter = CsvIntegerConvert.class)
    private Integer useCreditInsideCompareMonthNum;

    @ApiModelProperty(value = "网格内用信户数较年初")
    @CsvCustomBindByName(column = "use_credit_inside_compare_year_num", converter = CsvIntegerConvert.class)
    private Integer useCreditInsideCompareYearNum;

    @ApiModelProperty(value = "网格外用信户数 0")
    @CsvCustomBindByName(column = "use_credit_grid_outside_num", converter = CsvIntegerConvert.class)
    private Integer useCreditGridOutsideNum;

    @ApiModelProperty(value = "网格外用信户数占比 0")
    @CsvBindByName(column = "use_credit_outside_num_ratio")
    private String useCreditOutsideNumRatio;

    @ApiModelProperty(value = "网格外用信户数较上月 0")
    @CsvCustomBindByName(column = "use_credit_outside_compare_month_num", converter = CsvIntegerConvert.class)
    private Integer useCreditOutsideCompareMonthNum;

    @ApiModelProperty(value = "网格外用信户数较年初 0")
    @CsvCustomBindByName(column = "use_credit_outside_compare_year_num", converter = CsvIntegerConvert.class)
    private Integer useCreditOutsideCompareYearNum;

    @ApiModelProperty(value = "授信金额(万元，整数)")
    @CsvBindByName(column = "grant_credit_bal")
    private String grantCreditBal;

    @ApiModelProperty(value = "授信金额较上月(万元，整数)")
    @CsvBindByName(column = "grant_credit_compare_month_bal")
    private String grantCreditCompareMonthBal;

    @ApiModelProperty(value = "授信金额较年初(万元，整数)")
    @CsvBindByName(column = "grant_credit_compare_year_bal")
    private String grantCreditCompareYearBal;

    @ApiModelProperty(value = "网格内授信金额(万元，整数)")
    @CsvBindByName(column = "grant_credit_grid_inside_bal")
    private String grantCreditGridInsideBal;

    @ApiModelProperty(value = "网格内授信金额占比")
    @CsvBindByName(column = "grant_credit_inside_bal_ratio")
    private String grantCreditInsideBalRatio;

    @ApiModelProperty(value = "网格内授信金额较上月(万元，整数)")
    @CsvBindByName(column = "grant_credit_inside_compare_month_bal")
    private String grantCreditInsideCompareMonthBal;

    @ApiModelProperty(value = "网格内授信金额较年初(万元，整数)")
    @CsvBindByName(column = "grant_credit_inside_compare_year_bal")
    private String grantCreditInsideCompareYearBal;

    @ApiModelProperty(value = "网格外授信金额(万元，整数) 0")
    @CsvBindByName(column = "grant_credit_grid_outside_bal")
    private String grantCreditGridOutsideBal;

    @ApiModelProperty(value = "网格外授信金额占比 0")
    @CsvBindByName(column = "grant_credit_outside_bal_ratio")
    private String grantCreditOutsideBalRatio;

    @ApiModelProperty(value = "网格外授信金额较上月(万元，整数) 0")
    @CsvBindByName(column = "grant_credit_outside_compare_month_bal")
    private String grantCreditOutsideCompareMonthBal;

    @ApiModelProperty(value = "网格外授信金额较年初(万元，整数) 0")
    @CsvBindByName(column = "grant_credit_outside_compare_year_bal")
    private String grantCreditOutsideCompareYearBal;

    @ApiModelProperty(value = "用信金额(万元，整数)")
    @CsvBindByName(column = "use_credit_bal")
    private String useCreditBal;

    @ApiModelProperty(value = "用信金额较上月(万元，整数)")
    @CsvBindByName(column = "use_credit_compare_month_bal")
    private String useCreditCompareMonthBal;

    @ApiModelProperty(value = "用信金额较年初(万元，整数)")
    @CsvBindByName(column = "use_credit_compare_year_bal")
    private String useCreditCompareYearBal;

    @ApiModelProperty(value = "网格内用信金额(万元，整数)")
    @CsvBindByName(column = "use_credit_grid_inside_bal")
    private String useCreditGridInsideBal;

    @ApiModelProperty(value = "网格内用信金额占比")
    @CsvBindByName(column = "use_credit_inside_bal_ratio")
    private String useCreditInsideBalRatio;

    @ApiModelProperty(value = "网格内用信金额较上月(万元，整数)")
    @CsvBindByName(column = "use_credit_inside_compare_month_bal")
    private String useCreditInsideCompareMonthBal;

    @ApiModelProperty(value = "网格内用信金额较年初(万元，整数)")
    @CsvBindByName(column = "use_credit_inside_compare_year_bal")
    private String useCreditInsideCompareYearBal;

    @ApiModelProperty(value = "网格外用信金额(万元，整数) 0")
    @CsvBindByName(column = "use_credit_grid_outside_bal")
    private String useCreditGridOutsideBal;

    @ApiModelProperty(value = "网格外用信金额占比 0")
    @CsvBindByName(column = "use_credit_outside_bal_ratio")
    private String useCreditOutsideBalRatio;

    @ApiModelProperty(value = "网格外用信金额较上月(万元，整数) 0")
    @CsvBindByName(column = "use_credit_outside_compare_month_bal")
    private String useCreditOutsideCompareMonthBal;

    @ApiModelProperty(value = "网格外用信金额较年初(万元，整数) 0")
    @CsvBindByName(column = "use_credit_outside_compare_year_bal")
    private String useCreditOutsideCompareYearBal;

    @ApiModelProperty(value = "总和-不良余额(万元，整数)")
    @CsvBindByName(column = "total_bad_bal")
    private String totalBadBal;

    @ApiModelProperty(value = "总和-不良余额较上月(万元，整数)")
    @CsvBindByName(column = "total_bad_compare_month_bal")
    private String totalBadCompareMonthBal;

    @ApiModelProperty(value = "总和-不良余额较年初(万元，整数)")
    @CsvBindByName(column = "total_bad_compare_year_bal")
    private String totalBadCompareYearBal;

    @ApiModelProperty(value = "总和-不良率")
    @CsvBindByName(column = "total_bad_ratio")
    private String totalBadRatio;

    @ApiModelProperty(value = "总和-网格内不良率")
    @CsvBindByName(column = "total_bad_grid_inside_ratio")
    private String totalBadGridInsideRatio;

    @ApiModelProperty(value = "总和-网格外不良率 0")
    @CsvBindByName(column = "total_bad_grid_outside_ratio")
    private String totalBadGridOutsideRatio;

    @ApiModelProperty(value = "总和-逾欠余额(万元，整数)")
    @CsvBindByName(column = "total_overdue_bal")
    private String totalOverdueBal;

    @ApiModelProperty(value = "总和-逾欠余额较上月(万元，整数)")
    @CsvBindByName(column = "total_overdue_compare_month_bal")
    private String totalOverdueCompareMonthBal;

    @ApiModelProperty(value = "总和-逾欠余额较年初(万元，整数)")
    @CsvBindByName(column = "total_overdue_compare_year_bal")
    private String totalOverdueCompareYearBal;

    @ApiModelProperty(value = "总和-逾欠率")
    @CsvBindByName(column = "total_overdue_ratio")
    private String totalOverdueRatio;

    @ApiModelProperty(value = "总和-网格内逾欠率")
    @CsvBindByName(column = "total_overdue_grid_inside_ratio")
    private String totalOverdueGridInsideRatio;

    @ApiModelProperty(value = "总和-网格外逾欠率 0")
    @CsvBindByName(column = "total_overdue_grid_outside_ratio")
    private String totalOverdueGridOutsideRatio;

    @ApiModelProperty(value = "总和-收息率")
    @CsvBindByName(column = "total_interest_ratio")
    private String totalInterestRatio;

    @ApiModelProperty(value = "总和-网格内收息率")
    @CsvBindByName(column = "total_interest_grid_inside_ratio")
    private String totalInterestGridInsideRatio;

    @ApiModelProperty(value = "总和-网格外收息率 0")
    @CsvBindByName(column = "total_interest_grid_outside_ratio")
    private String totalInterestGridOutsideRatio;

    @ApiModelProperty(value = "信用-户数")
    @CsvCustomBindByName(column = "credit_num", converter = CsvIntegerConvert.class)
    private Integer creditNum;

    @ApiModelProperty(value = "信用-金额(万元，整数)")
    @CsvBindByName(column = "credit_bal")
    private String creditBal;

    @ApiModelProperty(value = "信用-不良余额(万元，整数)")
    @CsvBindByName(column = "credit_bad_bal")
    private String creditBadBal;

    @ApiModelProperty(value = "信用-不良率")
    @CsvBindByName(column = "credit_bad_ratio")
    private String creditBadRatio;

    @ApiModelProperty(value = "信用-逾欠余额(万元，整数)")
    @CsvBindByName(column = "credit_overdue_bal")
    private String creditOverdueBal;

    @ApiModelProperty(value = "信用-逾欠率")
    @CsvBindByName(column = "credit_overdue_ratio")
    private String creditOverdueRatio;

    @ApiModelProperty(value = "保证-户数")
    @CsvCustomBindByName(column = "ensure_num", converter = CsvIntegerConvert.class)
    private Integer ensureNum;

    @ApiModelProperty(value = "保证-金额(万元，整数)")
    @CsvBindByName(column = "ensure_bal")
    private String ensureBal;

    @ApiModelProperty(value = "保证-不良余额(万元，整数)")
    @CsvBindByName(column = "ensure_bad_bal")
    private String ensureBadBal;

    @ApiModelProperty(value = "保证-不良率")
    @CsvBindByName(column = "ensure_bad_ratio")
    private String ensureBadRatio;

    @ApiModelProperty(value = "保证-逾欠余额(万元，整数)")
    @CsvBindByName(column = "ensure_overdue_bal")
    private String ensureOverdueBal;

    @ApiModelProperty(value = "保证-逾欠率")
    @CsvBindByName(column = "ensure_overdue_ratio")
    private String ensureOverdueRatio;

    @ApiModelProperty(value = "抵押-户数")
    @CsvCustomBindByName(column = "mortgage_num", converter = CsvIntegerConvert.class)
    private Integer mortgageNum;

    @ApiModelProperty(value = "抵押-金额(万元，整数)")
    @CsvBindByName(column = "mortgage_bal")
    private String mortgageBal;

    @ApiModelProperty(value = "抵押-不良余额(万元，整数)")
    @CsvBindByName(column = "mortgage_bad_bal")
    private String mortgageBadBal;

    @ApiModelProperty(value = "抵押-不良率")
    @CsvBindByName(column = "mortgage_bad_ratio")
    private String mortgageBadRatio;

    @ApiModelProperty(value = "抵押-逾欠余额(万元，整数)")
    @CsvBindByName(column = "mortgage_overdue_bal")
    private String mortgageOverdueBal;

    @ApiModelProperty(value = "抵押-逾欠率")
    @CsvBindByName(column = "mortgage_overdue_ratio")
    private String mortgageOverdueRatio;

    @ApiModelProperty(value = "质押-户数")
    @CsvCustomBindByName(column = "pledge_num", converter = CsvIntegerConvert.class)
    private Integer pledgeNum;

    @ApiModelProperty(value = "质押-金额(万元，整数)")
    @CsvBindByName(column = "pledge_bal")
    private String pledgeBal;

    @ApiModelProperty(value = "质押-不良余额(万元，整数)")
    @CsvBindByName(column = "pledge_bad_bal")
    private String pledgeBadBal;

    @ApiModelProperty(value = "质押-不良率")
    @CsvBindByName(column = "pledge_bad_ratio")
    private String pledgeBadRatio;

    @ApiModelProperty(value = "质押-逾欠余额(万元，整数)")
    @CsvBindByName(column = "pledge_overdue_bal")
    private String pledgeOverdueBal;

    @ApiModelProperty(value = "质押-逾欠率")
    @CsvBindByName(column = "pledge_overdue_ratio")
    private String pledgeOverdueRatio;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CsvCustomBindByName(column = "create_time", converter = CsvJavaUtilDateConvert.class)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CsvCustomBindByName(column = "update_time", converter = CsvJavaUtilDateConvert.class)
    private Date updateTime;
}
