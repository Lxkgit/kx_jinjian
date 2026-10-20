package com.kx.system.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 营销活动信息表
 */
@Data
public class ActMarketInfo implements Serializable {
    private Long id;

    /**
    * 活动名称
    */
    private String actName;

    /**
    * 活动状态
    */
    private Integer actStatus;

    /**
    * 创建人名称
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

    /**
    * 活动开始时间
    */
    private Date actTimeStart;

    /**
    * 活动结束时间
    */
    private Date actTimeEnd;

    /**
    * 链接
    */
    private String link;

    /**
    * 二维码
    */
    private String qrCode;

    /**
    * 活动时长
    */
    private String actDuration;

    /**
    * 活动描述
    */
    private String actDesc;

    /**
    * 活动参与人数
    */
    private Integer actParticipateNum;

    /**
    * 其他预期成果描述
    */
    private String otherExpectDesc;

    private Date createTime;

    /**
    * 0-未删除  1-已删除
    */
    private Integer delFlag;

    private static final long serialVersionUID = 1L;



    private Date updateTime;


    private String updateBy;

    private String updateManagerNum;

    private String updateDeptName;

    private String updateOrgNum;
    /**
     * 边界数据
     */
    private String coordinates;
    private String actType;


    private String gridName;//网格名称
    private String gridCenter;//网格中心
}
