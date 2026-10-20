package com.kx.system.domain.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ActMarketInfoVo extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actTimeStart;

    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    /**
     * 0-未删除  1-已删除
     */
    private Integer delFlag;


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
    private BigDecimal infoCollectRate;
    /**
     * 有效信息人数
     */
    private Integer infoCollectEffectNum;
    private BigDecimal infoCollectEffectRate;

    /**
     * 业务办理人数
     */
    private Integer busProcNum;
    private BigDecimal busProcRate;


    private List<String> actTimeList;

    private Date createTime;
    private Date createTimeStart;
    private Date createTimeEnd;

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
