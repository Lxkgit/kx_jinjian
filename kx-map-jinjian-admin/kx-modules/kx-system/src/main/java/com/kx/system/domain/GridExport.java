package com.kx.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GridExport {
    
    
    @Excel(name = "网格编码")
    private String code;

    
    @Excel(name = "网格名称")
    private String  name ;

    @Excel(name = "网格所在镇街")
    private String zjName;

    @Excel(name = "网格所在县域")
    private String xyName;

    @Excel(name = "网格类型")
    private String ruralGridType;
    
    @Excel(name = "是否重点网格")
    private String  isCash;
    
    @Excel(name = "银行网格长")
    private String gridMainManagerName;
    
    @Excel(name = "网格长联系电话")
    private String gridMainManagerPhone;
    
    @Excel(name = "责任网点")
    private String dutyNetworkName;
    
    @Excel(name = "网格内普通网点")
    private String  ordinaryNetwork;

    @Excel(name = "重点管理对象")
    private String  importName ;

    @Excel(name = "重点管理对象地址")
    private String importAddress;
    
    @Excel(name = "现金服务点名称")
    private String cServePointName;

    @Excel(name = "现金服务点地址")
    private String cServePointAddr;
    
    @Excel(name = "金融服务站名称")
    private String vServePointName;
    
    @Excel(name = "金融服务站地址")
    private String vServePointAddr;

    @Excel(name = "社会网格员名称")
    private String  supervisorName;

    @Excel(name = "社会网格员联系电话")
    private String  supervisorPhone ;

    @Excel(name = "派出所名称")
    private String  policeName ;
    
    @Excel(name = "派出所地址")
    private String  policeAddress ;
    
    @Excel(name = "反假联系人电话")
    private String  unFakerMobile ;
    
    @Excel(name = "反假联系人")
    private String unFakerName;
    
    @Excel(name = "反假联系人座机")
    private String unFakerPhone;

}
