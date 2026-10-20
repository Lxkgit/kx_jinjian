package com.kx.system.domain.vo;

import lombok.Data;

/**
 * @author kicker
 * @date 2021/12/15 14:11
 **/
@Data
public class TmCustomInfoVo {
    /**
     * 客户内码
     */
    private String nm;

    /**
     * 客户姓名
     */
    private String name;
    /**
     * 网格归属客户经理名称
     */
    private String managerName;

    /**
     * 网格归属客户经理id
     */
    private String managerId;
}
