package com.kx.system.domain.vo;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaAddrBaseInfo {

    private Long id;
    private String addrCode;
    private String addrName;
    private String networkCode;
    private String networkName;
    private String managerCode;
    private String managerName;
    private Integer householdsNum;
    private Integer populactionNum;
    private Integer townshipNum;
    private Integer villageCommunity;
    private Integer lowIncomeFramers;
    private Integer companyNum;
    private Double GDP;
    private Double revenue;
    private Double perCapitaIncome;
    private Integer counryFinancialOrg;
    private Double countyDepositBalance;
    private Double countyLoanBalance;
    private Integer networkNum;
    private Integer staffNum;
    private Integer fieldNum;
    private Double villageCollectiveIncome;
    private String characteristicsIntroduce;
    private String majorProjectOne;
    private String majorProjectOneDate;
    private String majorProjectTwo;
    private String majorProjectTwoDate;
    private String majorProjectThree;
    private String majorProjectThreeDate;
    private String depositMarketShare;
    private String socialSecurityCardsShare;
    private Date   createTime;
    private BigDecimal villageGeneralIncome;
    private Integer creditLevelTownNum;
    private Integer creditLevelVillageNum;
    private String majorProjectOneDetail;
    private String majorProjectTwoDetail;
    private String majorProjectThreeDetail;
    private Date updateTime;

}
