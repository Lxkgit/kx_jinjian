package com.kx.system.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 乡镇村对象 addr_info
 *
 * @author gary
 * @date 2021-11-25
 */
@Data
public class PaVillageInfo
{
    private static final long serialVersionUID = 1L;

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;
    /**
     * 村总收入
     */
    private BigDecimal villageGeneralIncome;
    /**
     * 村状态
     */
    private String villageSituation;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getVillageGeneralIncome() {
        return villageGeneralIncome;
    }

    public void setVillageGeneralIncome(BigDecimal villageGeneralIncome) {
        this.villageGeneralIncome = villageGeneralIncome;
    }

    public String getVillageSituation() {
        return villageSituation;
    }

    public void setVillageSituation(String villageSituation) {
        this.villageSituation = villageSituation;
    }
}
