package com.kx.common.core.domain.branch.station.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 农村金融服务站excel导入表格
 * @Author lxk
 * @CreateTime 2025-07-15
 */

@Data
@ExcelIgnoreUnannotated // 忽略没有注解的字段
public class StationExcel {

    /**
     * 服务点编码（关联sys_dept.org_num）
     */
    @ExcelProperty(value = "农村金融服务站编码", index = 0)
    private String orgNum;

    /**
     * 现金服务点名称
     */
    @ExcelProperty(value = "服务站名称", index = 1)
    private String stationName;

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
     * 具体地址
     */
    @ExcelProperty(value = "具体地址", index = 4)
    private String stationAddr;

    /**
     * 站长姓名
     */
    @ExcelProperty(value = "站长姓名", index = 5)
    private String stationCharge;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话", index = 6)
    private String stationPhone;

    /**
     * 小面额功能机具数
     */
    @ExcelProperty(value = "小面额功能机具数", index = 7)
    private Integer stationMachinesNum;

    /**
     * 人工现金柜台总数
     */
    @ExcelProperty(value = "人工现金柜台总数", index = 8)
    private Integer stationCounterNum;

    /**
     * 存款取一体机CRS数量
     */
    @ExcelProperty(value = "存款取一体机CRS", index = 9)
    private Integer stationCrsNum;

    /**
     * 硬币兑换机数量
     */
    @ExcelProperty(value = "硬币兑换机数量", index = 10)
    private Integer coinExchangeMachinesNum;

    /**
     * 网点零钱包兑换数量
     */
    @ExcelProperty(value = "零钱包兑换数量", index = 11)
    private Integer changeWalletsNum;

    /**
     * 网点零钱包兑换金额
     */
    @ExcelProperty(value = "零钱包兑换金额", index = 12)
    private BigDecimal changeWalletsAmount;

    /**
     * 硬币兑换枚数
     */
    @ExcelProperty(value = "硬币兑换枚数", index = 13)
    private Integer coinExchangeCount;

    /**
     * 硬币兑换金额
     */
    @ExcelProperty(value = "硬币兑换金额", index = 14)
    private BigDecimal coinExchangeAmount;



}
