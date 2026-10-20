package com.kx.system.domain.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashServicePointsExcelDTO {

    @ApiModelProperty(value = "序号")
    @ExcelProperty("序号") // 如果需要序号，可以添加此字段
    private long id;

    @ApiModelProperty(value = "现金服务点责任银行")
    @ExcelProperty("现金服务点责任银行")
    private String serveBank;

    @ApiModelProperty(value = "现金服务点责任网点")
    @ExcelProperty("现金服务点责任网点")
    private String serveAnchor;

    @ApiModelProperty(value = "现金服务点责任网点地址")
    @ExcelProperty("现金服务点责任网点地址")
    private String serveAnchorAddr;

    @ApiModelProperty(value = "服务类别")
    @ExcelProperty("类别")
    private String serviceType;

    @ApiModelProperty(value = "服务点名称")
    @ExcelProperty("名称")
    private String servePointName;

    @ApiModelProperty(value = "服务点地址")
    @ExcelProperty("地址")
    private String servePointAddr;

    @ApiModelProperty(value = "选点位置")
    @ExcelProperty("选点位置")
    private String chosenPosition;

    @ApiModelProperty(value = "现金服务点责任人")
    @ExcelProperty("现金服务点责任人")
    private String dutyName;

    @ApiModelProperty(value = "联系电话")
    @ExcelProperty("联系电话")
    private String dutyPhone;

    @ApiModelProperty(value = "建设模式（自建/合建）")
    @ExcelProperty("建设模式（自建/合建）")
    private String constructingMode;

    @ApiModelProperty(value = "推进情况（1.洽谈中；2.建设中；3.运行中）")
    @ExcelProperty("推进情况（1.洽谈中；2.建设中；3.运行中）")
    private long constructingCase;

    @ApiModelProperty(value = "计划启用时间")
    @ExcelProperty("计划启用时间")
    private String startTime;

    @ApiModelProperty(value = "服务模式")
    @ExcelProperty("服务模式（1.与商户营业时间一致；2.定期开展服务，填写具体上门周期）")
    private String serviceMode;

    @ApiModelProperty(value = "本期零钞兑换业务总笔数")
    @ExcelProperty("本期零钞兑换业务笔数：总笔数")
    private String exchangeMoneyCount;

    @ApiModelProperty(value = "本期零钞兑换业务零钱包兑换个数")
    @ExcelProperty("其中：零钱包兑换个数")
    private String exchangeMoneyNumber;

    @ApiModelProperty(value = "本期零钞兑换业务总金额")
    @ExcelProperty("本期零钞兑换业务金额：总金额")
    private double moneyTotal;

    @ApiModelProperty(value = "本期零钞兑换业务零钱包兑换金额")
    @ExcelProperty("其中：零钱包兑换金额")
    private double lqbMoneyTotal;

    @ApiModelProperty(value = "零钞兑换业务累计总笔数")
    @ExcelProperty("零钞兑换业务累计笔数：总笔数")
    private long strokesTotal;

    @ApiModelProperty(value = "零钞兑换业务累计零钱包累计兑换个数")
    @ExcelProperty("其中：零钱包累计兑换个数")
    private long lqbStrokesTotal;

    @ApiModelProperty(value = "零钞兑换业务累计总金额")
    @ExcelProperty("零钞兑换业务累计金额：总金额")
    private double moneyTotalAccumulative;

    @ApiModelProperty(value = "零钞兑换业务零钱包累计兑换金额")
    @ExcelProperty("其中：零钱包累计兑换金额")
    private double lqbMoneyTotalAccumulative;

    @ApiModelProperty(value = "本期开展残币收兑金额")
    @ExcelProperty("本期开展残币收兑金额")
    private double scrappedConversionMoney;

    @ApiModelProperty(value = "累计开展残币收兑金额")
    @ExcelProperty("累计开展残币收兑金额")
    private double aScrappedConversionMoney;


    @ApiModelProperty(value = "是否布放小面额现金自助兑换设备")
    @ExcelProperty("是否布放小面额现金自助兑换设备")
    private String publishingDevice;

    @ApiModelProperty(value = "好的经验/存在问题")
    @ExcelProperty("好的经验/存在问题")
    private String remark;

    private List<String> belongGridCodeList;

}