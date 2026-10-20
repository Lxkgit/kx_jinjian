package com.kx.system.domain.small;

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
 * 小微视图-网格概况-网格、客户经理纬度
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pa_view_grid_overview_manager_grid")
@ApiModel(value = "小微视图-网格概况-网格、客户经理纬度对象", description = "小微视图-网格概况-网格、客户经理纬度")
public class PaViewGridOverview implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "网格概况id", required = true)
    private Long gridOverviewId;

    //首页指标展示
    private String gridName;
    private String gridUserName;
    private String gridLevel;
    private String orgName;

    @ApiModelProperty(value = "客户经理编号 1")
    @CsvBindByName(column = "manager_code")
    private String managerCode;

    @ApiModelProperty(value = "网格编号 1")
    @CsvBindByName(column = "grid_code")
    private String gridCode;

    @ApiModelProperty(value = "机构编号 1")
    @CsvBindByName(column = "org_code")
    private String orgCode;

    @ApiModelProperty(value = "当年累计FTP模拟利润(万元,保留两位小数) 1")
    @CsvBindByName(column = "ftp")
    private String ftp;

    @ApiModelProperty(value = "客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户 1")
    @CsvBindByName(column = "customer_type")
    private String customerType;

    @ApiModelProperty(value = "四级网格数量 ")
    @CsvBindByName(column = "level_four_grid_num")
    @CsvCustomBindByName(column = "level_four_grid_num", converter = CsvIntegerConvert.class)
    private Integer levelFourGridNum;

    @ApiModelProperty(value = "五级网格数量")
    @CsvCustomBindByName(column = "level_five_grid_num", converter = CsvIntegerConvert.class)
    private Integer levelFiveGridNum;

    @ApiModelProperty(value = "客户总数")
    @CsvCustomBindByName(column = "customer_total_num", converter = CsvIntegerConvert.class)
    private Integer customerTotalNum;

    @ApiModelProperty(value = "绩效客户总数 1")
    @CsvCustomBindByName(column = "performance_customer_num", converter = CsvIntegerConvert.class)
    private Integer performanceCustomerNum;

    @ApiModelProperty(value = "网格内绩效客户数 1")
    @CsvCustomBindByName(column = "performance_inside_customer_num", converter = CsvIntegerConvert.class)
    private Integer performanceInsideCustomerNum;

    @ApiModelProperty(value = "网格内绩效客户数占比 1")
    @CsvBindByName(column = "performance_inside_customer_ratio")
    private String performanceInsideCustomerRatio;

    @ApiModelProperty(value = "网格外绩效客户数 ")
    @CsvCustomBindByName(column = "performance_outside_customer_num", converter = CsvIntegerConvert.class)
    private Integer performanceOutsideCustomerNum;

    @ApiModelProperty(value = "网格外绩效客户数占比 ")
    @CsvBindByName(column = "performance_outside_customer_ratio")
    private String performanceOutsideCustomerRatio;

    @ApiModelProperty(value = "授信客户总数 1")
    @CsvCustomBindByName(column = "credit_customer_num", converter = CsvIntegerConvert.class)
    private Integer creditCustomerNum;

    @ApiModelProperty(value = "网格内授信客户数 1")
    @CsvCustomBindByName(column = "credit_inside_customer_num", converter = CsvIntegerConvert.class)
    private Integer creditInsideCustomerNum;

    @ApiModelProperty(value = "网格内授信客户数占比 1")
    @CsvBindByName(column = "credit_inside_customer_ratio")
    private String creditInsideCustomerRatio;

    @ApiModelProperty(value = "网格外授信客户数 ")
    @CsvCustomBindByName(column = "credit_outside_customer_num", converter = CsvIntegerConvert.class)
    private Integer creditOutsideCustomerNum;

    @ApiModelProperty(value = "网格外授信客户数占比 ")
    @CsvBindByName(column = "credit_outside_customer_ratio")
    private String creditOutsideCustomerRatio;

    @ApiModelProperty(value = "收单客户总数 1")
    @CsvCustomBindByName(column = "acquire_customer_num", converter = CsvIntegerConvert.class)
    private Integer acquireCustomerNum;

    @ApiModelProperty(value = "网格内收单客户数 1")
    @CsvCustomBindByName(column = "acquire_inside_customer_num", converter = CsvIntegerConvert.class)
    private Integer acquireInsideCustomerNum;

    @ApiModelProperty(value = "网格内收单客户数占比 1")
    @CsvBindByName(column = "acquire_inside_customer_ratio")
    private String acquireInsideCustomerRatio;

    @ApiModelProperty(value = "网格外收单客户数 ")
    @CsvCustomBindByName(column = "acquire_outside_customer_num", converter = CsvIntegerConvert.class)
    private Integer acquireOutsideCustomerNum;

    @ApiModelProperty(value = "网格外收单客户数占比 ")
    @CsvBindByName(column = "acquire_outside_customer_ratio")
    private String acquireOutsideCustomerRatio;

    @ApiModelProperty(value = "价值客户数 1")
    @CsvCustomBindByName(column = "value_customer_num", converter = CsvIntegerConvert.class)
    private Integer valueCustomerNum;

    @ApiModelProperty(value = "网格内价值客户数 1")
    @CsvCustomBindByName(column = "value_inside_customer_num", converter = CsvIntegerConvert.class)
    private Integer valueInsideCustomerNum;

    @ApiModelProperty(value = "网格内价值客户数占比 1")
    @CsvBindByName(column = "value_inside_customer_ratio")
    private String valueInsideCustomerRatio;

    @ApiModelProperty(value = "网格外价值客户数 ")
    @CsvCustomBindByName(column = "value_outside_customer_num", converter = CsvIntegerConvert.class)
    private Integer valueOutsideCustomerNum;

    @ApiModelProperty(value = "网格外价值客户数占比 ")
    @CsvBindByName(column = "value_outside_customer_ratio")
    private String valueOutsideCustomerRatio;

    @ApiModelProperty(value = "建档客户数")
    @CsvCustomBindByName(column = "record_customer_num", converter = CsvIntegerConvert.class)
    private Integer recordCustomerNum;

    @ApiModelProperty(value = "网格内建档客户数")
    @CsvCustomBindByName(column = "record_inside_customer_num", converter = CsvIntegerConvert.class)
    private Integer recordInsideCustomerNum;

    @ApiModelProperty(value = "网格内建档客户数占比")
    @CsvBindByName(column = "record_inside_customer_ratio")
    private String recordInsideCustomerRatio;

    @ApiModelProperty(value = "网格外建档客户数")
    @CsvCustomBindByName(column = "record_outside_customer_num", converter = CsvIntegerConvert.class)
    private Integer recordOutsideCustomerNum;

    @ApiModelProperty(value = "网格内建档客户数占比")
    @CsvBindByName(column = "record_outside_customer_ratio")
    private String recordOutsideCustomerRatio;

    @ApiModelProperty(value = "网格人数 ")
    @CsvCustomBindByName(column = "grid_people_num", converter = CsvIntegerConvert.class)
    private Integer gridPeopleNum;

    @ApiModelProperty(value = "经济总量（万元）")
    @CsvBindByName(column = "gdp")
    private String gdp;

    @ApiModelProperty(value = "小区数量")
    @CsvCustomBindByName(column = "village_num", converter = CsvIntegerConvert.class)
    private Integer villageNum;

    @ApiModelProperty(value = "小区人口")
    @CsvCustomBindByName(column = "village_people_num", converter = CsvIntegerConvert.class)
    private Integer villagePeopleNum;

    @ApiModelProperty(value = "工业园数量")
    @CsvCustomBindByName(column = "industrial_park_num", converter = CsvIntegerConvert.class)
    private Integer industrialParkNum;

    @ApiModelProperty(value = "企业数量")
    @CsvCustomBindByName(column = "company_num", converter = CsvIntegerConvert.class)
    private Integer companyNum;

    @ApiModelProperty(value = "金融机构数量")
    @CsvCustomBindByName(column = "financial_institution_num", converter = CsvIntegerConvert.class)
    private Integer financialInstitutionNum;

    @ApiModelProperty(value = "商圈数量")
    @CsvCustomBindByName(column = "business_area_num", converter = CsvIntegerConvert.class)
    private Integer businessAreaNum;

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
