package com.kx.system.domain;

import java.util.Date;
import lombok.Data;

/**
 * 产品库信息
 */
@Data
public class ProductInfo {
    private Long id;

    /**
    * 产品编号
    */
    private String productNm;

    /**
    * 产品名称
    */
    private String productName;

    /**
    * 产品类型
    */
    private String productType;

    /**
    * 业务连接
    */
    private String businessLink;

    /**
    * 创建人
    */
    private String createBy;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 创建人工号
    */
    private String createNum;
}