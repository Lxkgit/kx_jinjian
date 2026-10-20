package com.kx.common.core.domain.branch.cash.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.kx.common.core.utils.excel.ExcelValueMap;
import com.kx.common.core.utils.excel.ExcleConverter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 现金服务点excel导入模板
 * @Author lxk
 * @CreateTime 2025-07-15
 */

@Data
@ExcelIgnoreUnannotated // 忽略没有注解的字段
public class CashServiceExcel {

    /**
     * 服务点编码（关联sys_dept.org_num）
     */
    @ExcelProperty(value = "现金服务点编码", index = 0)
    private String orgNum;

    /**
     * 现金服务点名称
     */
    @ExcelProperty(value = "服务点名称", index = 1)
    private String serviceName;

    /**
     * 父部门编号
     */
    @ExcelProperty(value = "网点编码", index = 2)
    private String parentOrgNum;

    /**
     * 父级部门名称
     */
    @ExcelProperty(value = "网点名称", index = 3)
    private String parentDeptName;

    /**
     * 服务点类别 1:交通枢纽,2:出租车,3:酒店,4:农贸市场,5:商场（综合体）,6:特色街区,7:文化旅游,8:医疗卫生,9:专业市场
     */
    @ExcelValueMap(valueMapping = "1:交通枢纽,2:出租车,3:酒店,4:农贸市场,5:商场（综合体）,6:特色街区,7:文化旅游,8:医疗卫生,9:专业市场", readDefault = -1, writeDefault = "未知")
    @ExcelProperty(value = "服务点类别", index = 4, converter = ExcleConverter.class)
    private Integer serviceType;

    /**
     * 现金服务点地址
     */
    @ExcelProperty(value = "现金服务点地址", index = 5)
    private String serviceAddr;

    /**
     * 选点位置描述
     */
    @ExcelProperty(value = "选点位置", index = 6)
    private String servicePoint;

    /**
     * 现金服务点责任人
     */
    @ExcelProperty(value = "现金服务点责任人", index = 7)
    private String serviceChargePerson;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话", index = 8)
    private String serviceChargePhone;

    /**
     * 建设模式：1:自建 2:合建 3:待定
     */
    @ExcelValueMap(valueMapping = "1:自建,2:合建,3:待定", readDefault = -1, writeDefault = "未知")
    @ExcelProperty(value = "建设模式", index = 9, converter = ExcleConverter.class)
    private Integer serviceBuildType;

    /**
     * 推进情况（1.洽谈中；2.建设中；3.运行中）
     */
    @ExcelValueMap(valueMapping = "1:洽谈中,2:建设中,3:运行中", readDefault = -1, writeDefault = "未知")
    @ExcelProperty(value = "推进情况", index = 10, converter = ExcleConverter.class)
    private Integer serviceBuildProgress;

    /**
     * 计划启用时间
     */
    @ExcelProperty(value = "计划启用时间", index = 11)
    private Date serviceEnableTime;

    /**
     * 服务模式（字典值，逗号分隔）
     */
    @ExcelProperty(value = "服务模式", index = 12)
    private String serviceModel;

    /**
     * 是否布放小面额现金自助兑换设备（1是 0否）
     */
    @ExcelValueMap(valueMapping = "1:是,0:否", readDefault = -1, writeDefault = "未知")
    @ExcelProperty(value = "是否布放小面额现金自助兑换设备", index = 13, converter = ExcleConverter.class)
    private Integer cashExchangeDeviceFlag;

    /**
     * 本期零钞兑换业务笔数-总笔数
     */
    @ExcelProperty(value = {"本期零钞兑换业务笔数", "总笔数"}, index = 14)
    private Integer changeCountAllCurrent;

    /**
     * 本期零钞兑换业务笔数-零钱包兑换个数
     */
    @ExcelProperty(value = {"本期零钞兑换业务笔数", "零钱包兑换个数"}, index = 15)
    private Integer changeCountWalletCurrent;

    /**
     * 本期零钞兑换业务金额-总金额
     */
    @ExcelProperty(value = {"本期零钞兑换业务金额", "总金额"}, index = 16)
    private BigDecimal changeAmountAllCurrent;

    /**
     * 本期零钞兑换业务金额-零钱包兑换金额
     */
    @ExcelProperty(value = {"本期零钞兑换业务金额", "零钱包兑换金额"}, index = 17)
    private BigDecimal changeAmountWalletCurrent;

    /**
     * 零钞兑换业务累计笔数-总笔数
     */
    @ExcelProperty(value = {"零钞兑换业务累计笔数", "总笔数"}, index = 18)
    private Integer changeCountAllTotal;

    /**
     * 零钞兑换业务累计笔数-零钱包兑换个数
     */
    @ExcelProperty(value = {"零钞兑换业务累计笔数", "零钱包累计兑换个数"}, index = 19)
    private Integer changeCountWalletTotal;

    /**
     * 零钞兑换业务累计金额-总金额
     */
    @ExcelProperty(value = {"零钞兑换业务累计金额", "总金额"}, index = 20)
    private BigDecimal changeAmountAllTotal;

    /**
     * 零钞兑换业务累计金额-零钱包兑换金额
     */
    @ExcelProperty(value = {"零钞兑换业务累计金额", "零钱包累计兑换金额"}, index = 21)
    private BigDecimal changeAmountWalletTotal;

    /**
     * 本期残币收兑累计金额
     */
    @ExcelProperty(value = "本期开展残币收兑金额", index = 22)
    private BigDecimal brokenCoinAmountCurrent;

    /**
     * 残币收兑累计金额
     */
    @ExcelProperty(value = "累计开展残币收兑金额", index = 23)
    private BigDecimal brokenCoinAmountTotal;

    /**
     * 备注（好的经验/存在问题）
     */
    @ExcelProperty(value = "好的经验/存在问题", index = 24)
    private String serviceRemark;

}
