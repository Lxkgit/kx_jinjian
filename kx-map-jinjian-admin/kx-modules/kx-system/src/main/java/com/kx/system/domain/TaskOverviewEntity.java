package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 数据概览对象
 *
 * @author gary
 * @date 2024-01-30
 */
@Data
public class TaskOverviewEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 日期 */
    private String startTime;

    /** 任务总数 */
    private Integer taskTotalNumber;

    /** 任务完成数量 */
    private Integer finishTaskNumber;

    /** 任务未完成数量 */
    private Integer unFinishTaskNumber;

    /** 任务完成比例 */
    private String finishRatio;

}
