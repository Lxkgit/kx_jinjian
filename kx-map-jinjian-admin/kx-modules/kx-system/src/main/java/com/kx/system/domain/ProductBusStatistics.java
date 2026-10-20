package com.kx.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 产品业务统计信息表
 */
@Data
public class ProductBusStatistics {
    private Long id;

    /**
    * 产品id
    */
    private Long productId;

    /**
    * 产品编号
    */
    private String productNm;

    /**
    * 扫码总数
    */
    private Integer scanTotal;

    /**
    * 扫码办理业务总数
    */
    private Integer scanBusTotal;

    /**
    * 业务转化率
    */
    private BigDecimal busConversionRatio;

    /**
    * 产品业务办理总量
    */
    private Integer productBusTotal;

    /**
    * 数据同步时间
    */
    private Date etlDt;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品类型
     */
    private String productType;
}
