package com.kx.system.domain.mobile;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户相关查询参数
 */
@Data
public class TaskMobileDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 子任务总数
     */
    private Integer childTotal;
    /**
     * 未完成数
     */
    private Integer uncompleteNum;
    /**
     * 完成数
     */
    private Integer completeNum;
    /**
     * 手动结束数
     */
    private Integer endNum;

}
