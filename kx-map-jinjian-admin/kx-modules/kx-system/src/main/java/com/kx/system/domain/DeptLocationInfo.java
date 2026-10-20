package com.kx.system.domain;

import com.amazonaws.services.dynamodbv2.xspec.S;
import lombok.Data;

@Data
public class DeptLocationInfo {
    //部门编码
    private String orgNum;
    //点位（格式：lon,lat）
    private String point;
    //机构名称
    private String orgName;
    //地址
    private String address;
}
