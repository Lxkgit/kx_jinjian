package com.kx.system.domain;

import java.util.Date;
import lombok.Data;

/**
 * 产品扫码记录表
 */
@Data
public class ProductScanLog {
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
    * 扫码时间
    */
    private Date scanTime;

    /**
    * 扫码人名称
    */
    private String scanPersonName;

    /**
    * 扫码人手机号
    */
    private String scanPhone;

    /**
    * 是否办理业务0是 1否
    */
    private String isBuinsess;
}
