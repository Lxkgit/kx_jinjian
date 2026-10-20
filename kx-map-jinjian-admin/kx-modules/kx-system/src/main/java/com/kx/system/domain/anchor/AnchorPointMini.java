package com.kx.system.domain.anchor;

import java.math.BigDecimal;

import com.kx.common.core.annotation.Excel;

import lombok.Data;

/**
 * 锚点对象 anchor_point
 *
 * @author kangxu
 * @date 2024-04-03
 */
@Data
public class AnchorPointMini {
    @Excel(name = "经度")
    private BigDecimal lon;

    @Excel(name = "纬度")
    private BigDecimal lat;
}
