package com.kx.system.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author kangxu
 * @date 2024/4/8
 */
@Data
public class OpenCardVO {
    //开卡信息
    /** 开卡时间 */
    private Date openCardCreateTime;

    /** 卡号 */
    private String cardNum;

    /** 卡类型 */
    private String cardType;
}
