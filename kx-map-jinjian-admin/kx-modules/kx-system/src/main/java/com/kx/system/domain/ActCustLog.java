package com.kx.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ActCustLog {
    private Long id;

    /**
    * 活动id
    */
    private Long actId;

    /**
    * 客户名称
    */
    private String customerName;

    /**
    * 客户手机号
    */
    private String customerPhone;

    /**
    * 客户身份证号
    */
    private String customerIdentity;

    /**
    * 是否扫码
    */
    private String isScan;

    /**
    * 扫码日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date scanTime;

    /**
    * 是否填写
    */
    private String isFillIn;

    /**
    * 填写日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date fillInTime;

    /**
    * 信息是否有效
    */
    private String isInfoEffect;

    /**
    * 是否办理业务
    */
    private String isBuiness;

    /**
    * 业务办理时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date buinessTime;
}
