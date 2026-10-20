package com.kx.system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchInfoDTO {

    // 网点名称
    private String customerName;

    // 网点地址
    private String customerAddress;

    //所属银行机构
    private String bankAgencyName;

    //网点负责人
    private String dutyName;

    //网点负责人电话
    private String dutyPhone;

    //网点负责人移动电话
    private String dutyTelephone;

    //所属网格
    private String gridName;

    //所属网格长
    private String gridMainManagerName;

    //网格长联系电话
    private String gridMainManagerPhone;

    //网点介绍
    private String dotIntroduce;

    //网点级别
    private String anchorRanks;

    //所属网点类型 0 行政村网格 1 社区网格
    private String ruralGridType;


}
