package com.kx.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 营销活动成果信息表
 */
@Data
public class ActMarketResultInfo implements Serializable {
    private Long id;

    /**
    * 活动id
    */
    private Long actId;

    /**
    * 照片
    */
    private String photo;

    /**
    * 打卡地点
    */
    private String checkinAddr;

    /**
    * 活动反馈
    */
    private String actFeedback;

    /**
    * 扫码人数
    */
    private Integer scanCodeNum;

    /**
    * 信息收集人数
    */
    private Integer infoCollectNum;

    /**
    * 有效信息人数
    */
    private Integer infoCollectEffectNum;

    /**
    * 业务办理人数
    */
    private Integer busProcNum;
    private BigDecimal busProcNumRate;
    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 创建人
    */
    private String createBy;

    /**
    * 创建人工号
    */
    private String createManagerNum;

    /**
    * 创建机构名称
    */
    private String createDeptName;

    /**
    * 创建机构号
    */
    private String createOrgNum;

    private BigDecimal infoCollectEffectRate;


    /**
    * 0-未删除  1-已删除
    */
    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}
