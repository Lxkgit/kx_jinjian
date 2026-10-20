package com.kx.system.domain;

import com.kx.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnchorExport {

    @Excel(name = "银行机构")
    private String bankAgencyName;
    @Excel(name = "网点名称")
    private String customerName;
    @Excel(name = "网点类型")
    private String dotType;
    @Excel(name = "网点级别")
    private String anchorRanks;
    @Excel(name = "网点负责人")
    private String  dutyName ;
    @Excel(name = "负责人固定电话")
    private String dutyTelephone;
    @Excel(name = "负责人移动电话")
    private String dutyPhone;
    @Excel(name = "网点地址")
    private String customerAddress;
    @Excel(name = "网点所在地类型")
    private String ruralGridType;
    @Excel(name = "所属网格")
    private String belongGridName;
    @Excel(name = "所属网格网格长")
    private String gridMainManagerName;
    @Excel(name = "网格长固定电话")
    private String gridMainManagerPhone;
    @Excel(name = "自动取款机ATM数量")
    private String atmMachine;
    @Excel(name = "存取一体机CRS数量")
    private String depositMachine;
    @Excel(name = "现金柜员机数量")
    private String cashTellerMachine;
    @Excel(name = "网点功能")
    private String  serviceType;
    @Excel(name = "管理网格名称")
    private String gridName;
    @Excel(name = "管理网格所在镇街")
    private String zjName;
    @Excel(name = "管理网格所在县域")
    private String xyName;

    /**
     *网点类型集合
     */
    private List<String> dotTypeList;

    /**
     * 所属网格编号集合
     */
    private List<String> belongGridCodeList;

    /**
     * 网点服务类型集合
     */
    private List<String> serviceTypeList;




}
