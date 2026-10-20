package com.kx.system.domain.small;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.system.util.convert.CsvIntegerConvert;
import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 小微视图-存款模块-网格、机构纬度
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pa_view_deposit_module_org_grid")
@ApiModel(value = "小微视图-存款模块-网格、机构纬度对象", description = "小微视图-存款模块-网格、机构纬度")
public class PaViewDepositModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "存款模块id", required = true)
    private Long depositModuleId;

    @ApiModelProperty(value = "机构编码")
    @CsvBindByName(column = "org_code")
    private String orgCode;

    @ApiModelProperty(value = "客户经理编码")
    @CsvBindByName(column = "manager_code")
    private String managerCode;

    @ApiModelProperty(value = "网格编号")
    @CsvBindByName(column = "grid_code")
    private String gridCode;

    @ApiModelProperty(value = "客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户")
    @CsvBindByName(column = "customer_type")
    private String customerType;

    @ApiModelProperty(value = "存款FTP (万元，保留两位小数)")
    @CsvBindByName(column = "save_ftp")
    private String saveFtp;

    @ApiModelProperty(value = "存款余额(万元，整数)")
    @CsvBindByName(column = "save_balance")
    private String saveBalance;

    @ApiModelProperty(value = "存款余额较上月(万元，整数)")
    @CsvBindByName(column = "save_balance_last_month")
    private String saveBalanceLastMonth;

    @ApiModelProperty(value = "存款余额较年初(万元，整数)")
    @CsvBindByName(column = "save_balance_begin_year")
    private String saveBalanceBeginYear;

    @ApiModelProperty(value = "存款日均(万元，整数)")
    @CsvBindByName(column = "save_day_avg")
    private String saveDayAvg;

    @ApiModelProperty(value = "存款日均较上月(万元，整数)")
    @CsvBindByName(column = "save_day_avg_last_month")
    private String saveDayAvgLastMonth;

    @ApiModelProperty(value = "存款日均较年初(万元，整数)")
    @CsvBindByName(column = "save_day_avg_begin_year")
    private String saveDayAvgBeginYear;

    @ApiModelProperty(value = "储蓄存款余额(万元，整数)")
    @CsvBindByName(column = "deposit_save_balance")
    private String depositSaveBalance;

    @ApiModelProperty(value = "储蓄存款余额较上月(万元，整数)")
    @CsvBindByName(column = "deposit_save_balance_last_month")
    private String depositSaveBalanceLastMonth;

    @ApiModelProperty(value = "储蓄存款余额较年初(万元，整数)")
    @CsvBindByName(column = "deposit_save_balance_begin_year")
    private String depositSaveBalanceBeginYear;

    @ApiModelProperty(value = "储蓄存款日均(万元，整数)")
    @CsvBindByName(column = "deposit_save_day_avg")
    private String depositSaveDayAvg;

    @ApiModelProperty(value = "储蓄存款日均较上月(万元，整数)")
    @CsvBindByName(column = "deposit_save_day_avg_last_month")
    private String depositSaveDayAvgLastMonth;

    @ApiModelProperty(value = "储蓄存款日均较年初(万元，整数)")
    @CsvBindByName(column = "deposit_save_day_avg_begin_year")
    private String depositSaveDayAvgBeginYear;

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
