package com.kx.common.core.domain.branch.branch.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 网点导入数据实体类
 * @Author lxk
 * @CreateTime 2025-07-10
 */

@Data
@ExcelIgnoreUnannotated // 忽略没有注解的字段
public class BranchExcel {

    @ExcelProperty(value = "网点编码", index = 0)
    private String orgNum;

    @ExcelProperty(value = "网点名称", index = 1)
    private String branchName;

    @ExcelProperty(value = "网点地址", index = 2)
    private String branchAddr;

    @ExcelProperty(value = "所属银行机构", index = 3)
    private String parentDeptName;

    @ExcelProperty(value = "所属银行机构编号", index = 4)
    private String parentOrgNum;

    @ExcelProperty(value = "网点负责人", index = 5)
    private String branchChargePerson;

    @ExcelProperty(value = "负责人固定电话", index = 6)
    private String chargeTelephone;

    @ExcelProperty(value = "负责人移动电话", index = 7)
    private String chargeMobilePhone;

    @ExcelProperty(value = "网点功能（支持输入多个网点功能的编码，中间用逗号隔开（英文））", index = 8)
    private String branchFunction;

    @ExcelProperty(value = "网点介绍", index = 9)
    private String branchDesc;

    /** 网点服务柜台数 */
    @ExcelProperty(value = {"网点机具", "网点服务柜台数"}, index = 10)
    private Integer branchServiceMachines;

    /** 自助取款机ATM数量 */
    @ExcelProperty(value = {"网点机具", "自助取款机ATM数量"}, index = 11)
    private Integer atmMachines;

    /** 小面额自助现金机具数量（整换零） */
    @ExcelProperty(value = {"网点机具", "小面额自助现金机具数量（整换零）"}, index = 12)
    private Integer antoSmallCashMachines;

    /** 小面额功能机具数量 */
    @ExcelProperty(value = {"网点机具", "小面额功能机具数量"}, index = 13)
    private Integer smallCashMachines;

    /** 存款取一体机CRS */
    @ExcelProperty(value = {"网点机具", "存款取一体机CRS"}, index = 14)
    private Integer crsMachines;

    /** 现金柜员机 */
    @ExcelProperty(value = {"网点机具", "现金柜员机"}, index = 15)
    private Integer artificialCashMachines;

    /** 硬币兑换机数量 */
    @ExcelProperty(value = {"网点机具", "硬币兑换机数量"}, index = 16)
    private Integer coinExchangeMachines;

    /** 网点零钱包兑换数量 */
    @ExcelProperty(value = "网点零钱包兑换数量", index = 17)
    private Integer changeWalletsNum;

    /** 网点零钱包兑换次数 */
    @ExcelProperty(value = "网点零钱包兑换次数", index = 18)
    private Integer changeWalletsCount;

    /** 残损币收兑金额 */
    @ExcelProperty(value = "残损币收兑金额", index = 19)
    private BigDecimal brokenCoinAmount;

    /** 残损币收兑次数 */
    @ExcelProperty(value = "残损币收兑次数", index = 20)
    private Integer brokenCoinCount;

    /** 硬币兑换金额 */
    @ExcelProperty(value = "硬币兑换金额", index = 21)
    private BigDecimal coinExchangeAmount;

    /** 硬币兑换枚数 */
    @ExcelProperty(value = "硬币兑换枚数", index = 22)
    private Integer coinExchangeCount;


}
