package com.kx.system.domain.anchor;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class PaPocAddress extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /**
     * 点位名称
     */
    private String name;

    /**
     * 归属五级网格
     */
    private String code;
    /**
     * 经度
     */
    private String lon;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 锚点地址
     */
    private String address;
}
