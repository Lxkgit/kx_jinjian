package com.kx.system.domain.vo;

import lombok.Data;

/**
 * 网点对象 sys_network
 *
 * @author xiatian
 * @date 2020-08-07
 */
@Data
public class SysNetworkVo
{
    private static final long serialVersionUID = 1L;

    /** 网点id */
    private Long networkId;

    /** 网点名称 */
    private String networkName;

    /** 网点编号 */
    private String networkCode;

    private String ancestors;

    /** 地址 */
    private String address;

    /** 经度 */
    private String lng;

    /** 纬度 */
    private String lat;

    /** 电话 */
    private String phone;

    /** 网点类型 0-支行 1-分理处 */
    private String typeTag;

    /**
     * 分析报告地址
     */
    private String analysisReportUrl;

    /**
     * 其他系统分析报告地址
     */
    private String otherReportUrl;
}
