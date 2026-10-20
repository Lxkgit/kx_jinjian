package com.kx.system.domain.evaluate;

import lombok.Data;

import java.util.Date;

/**
 * @Description 银行非现场评价
 * @Author lxk
 * @CreateTime 2025-07-10
 */

@Data
public class EvaluateOffSite {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 评价标题
     */
    private String evaluateTitle;

    /**
     * 评价年度
     */
    private String evaluateYear;

    /**
     * 银行占比json格式字符串
     */
    private String bankPercentJson;

    /**
     * 总进度
     */
    private Integer allProgress;

    /**
     * 当前进度
     */
    private Integer currentProgress;

    /**
     * 评价结果
     */
    private String result;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建人账号
     */
    private Long createBy;

    /**
     * 最近修改时间
     */
    private Date updateTime;

    /**
     * 修改人名称
     */
    private String updateName;

    /**
     * 修改人账号
     */
    private Long updateBy;
}
