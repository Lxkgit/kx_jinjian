package com.kx.system.domain;


import lombok.Data;

import java.util.Date;

@Data
public class PaManagerBaseInfo {

    private Long id;
    private String managerCode;
    private String managerName;
    private String networkCode;
    private String networkName;
    private String managerPost;
    private Integer householdsNum;
    private Integer populactionNum;
    private Integer villageCommunity;
    private Integer lowIncomeFramers;
    private Integer companyNum;
    private Date    createTime;
    private Integer    networkCustomNum;
    private Date updateTime;

}
