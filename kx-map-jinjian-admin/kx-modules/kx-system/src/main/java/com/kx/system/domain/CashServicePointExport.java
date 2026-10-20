package com.kx.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode
public class CashServicePointExport {
    @ApiModelProperty(value = "现金服务点责任银行")
    @ExcelProperty( "现金服务点责任银行")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String serveBank;

    @ApiModelProperty(value = "现金服务点责任网点")
    @ExcelProperty( "现金服务点责任网点")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String serveAnchor;

    @ApiModelProperty(value = "现金服务点责任网点地址")
    @ExcelProperty( "现金服务点责任网点地址")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String serveAnchorAddr;

    @ApiModelProperty(value = "服务类别")
    @ExcelProperty( "服务类别")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String serviceType;

    @ApiModelProperty(value = "服务点名称")

    @ExcelProperty( "服务点名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String servePointName;

    @ApiModelProperty(value = "服务点地址")
    @ExcelProperty( "服务点地址")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String servePointAddr;

    @ApiModelProperty(value = "选点位置")

    @ExcelProperty( "选点位置")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String chosenPosition;

    @ApiModelProperty(value = "现金服务点责任人")
    @ExcelProperty( "现金服务点责任人")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String dutyName;

    @ApiModelProperty(value = "联系电话")
    @ExcelProperty( "联系电话")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String dutyPhone;

    @ApiModelProperty(value = "建设模式（自建/合建）")
    @ExcelProperty( "联系电话")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String constructingMode;

    @ApiModelProperty(value = "推进情况（1.洽谈中；2.建设中；3.运行中）")
    @ExcelProperty( "推进情况（1.洽谈中；2.建设中；3.运行中）")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String constructingCase;

    @ApiModelProperty(value = "计划启用时间")
    @ExcelProperty( "计划启用时间")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String startTime;

    @ApiModelProperty(value = "服务模式")
    @ExcelProperty( "服务模式")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String serviceMode;

    @ApiModelProperty(value = "是否布放小面额现金自助兑换设备")
    @ExcelProperty( "是否布放小面额现金自助兑换设备")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String publishingDevice;

    @ApiModelProperty(value = "所属网格名称")
    @ExcelProperty( "所在网格")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String gridName;

    @ApiModelProperty(value = "本期零钞兑换业务总笔数")
    @ExcelProperty({"本期零钞兑换业务笔数", "总笔数"})
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String exchangeMoneyCount;

    @ApiModelProperty(value = "本期零钞兑换业务零钱包兑换个数")
    @ExcelProperty({"本期零钞兑换业务笔数", "零钱包兑换个数"})
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String exchangeMoneyNumber;

    @ApiModelProperty(value = "本期零钞兑换业务总金额")
    @ExcelProperty({"本期零钞兑换业务金额", "总金额"})
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private double moneyTotal;

    @ApiModelProperty(value = "本期零钞兑换业务零钱包兑换金额")
    @ExcelProperty({"本期零钞兑换业务金额", "零钱包兑换金额"})
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private double lqbMoneyTotal;

    @ApiModelProperty(value = "零钞兑换业务累计总笔数")
    @ExcelProperty({"零钞兑换业务累计笔数", "总笔数"})
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private long strokesTotal;

    @ApiModelProperty(value = "零钞兑换业务累计零钱包累计兑换个数")
    @ExcelProperty({"零钞兑换业务累计笔数", "零钱包累计兑换个数"})
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private long lqbStrokesTotal;

    @ApiModelProperty(value = "零钞兑换业务累计总金额")
    @ExcelProperty({"零钞兑换业务累计金额", "总金额"})
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private double moneyTotalAccumulative;

    @ApiModelProperty(value = "零钞兑换业务零钱包累计兑换金额")
    @ExcelProperty({"零钞兑换业务累计金额", "零钱包累计兑换金额"})
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private double lqbMoneyTotalAccumulative;

    @ApiModelProperty(value = "本期开展残币收兑金额")
    @ExcelProperty( "本期开展残币收兑金额")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private double scrappedConversionMoney;

    @ApiModelProperty(value = "累计开展残币收兑金额")
    @ExcelProperty( "累计开展残币收兑金额")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private double aScrappedConversionMoney;

    @ApiModelProperty(value = "好的经验/存在问题")
    @ExcelProperty( "好的经验/存在问题")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String remark;

}
