package com.kx.system.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ActStatisticsManager {
    private Long id;

    /**
    * 活动id
    */
    private Long actId;

    /**
    * 客户经理名称
    */
    private String managerName;

    /**
    * 客户经理工号
    */
    private String managerNum;

    /**
    * 机构号
    */
    private String deptNo;

    /**
    * 机构名称
    */
    private String deptName;

    /**
    * 活动参与人数
    */
    private Integer actParticipateNum;

    /**
    * 信息收集人数
    */
    private Integer infoCollectNum;

    /**
    * 信息收集率
    */
    private BigDecimal infoCollectRate;

    /**
    * 有效信息人数
    */
    private Integer infoCollectEffectNum;

    /**
    * 有效信息率
    */
    private BigDecimal infoCollectEffectRate;

    /**
    * 业务办理人数
    */
    private Integer busProcNum;

    /**
    * 业务办理率
    */
    private BigDecimal busProcRate;
    /**
     * 扫码人数
     */
    private Integer scanCodeNum;

}
