package com.kx.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 产品-客户经理业务统计表
 */
@Data
public class ProductBusStatisticsManager {
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
    * 客户经理名称
    */
    private String managerName;

    /**
    * 客户经理编号
    */
    private String managerNm;

    /**
    * 产品业务办理总量
    */
    private Integer personBusTotal;

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
    * 数据同步时间
    */
    private Date etlDt;

    private String query;

}
