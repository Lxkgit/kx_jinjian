package com.kx.system.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author huhao
 * @created 2022/5/26
 * @Description 进度信息
 */
@Data
@EqualsAndHashCode
public class ProgressInfo implements Serializable {
    private static final long serialVersionUID = -2683409056639218093L;
    // 任务完成率
    private double progress;
    // 任务数
    private Integer taskCnt;
    // 子任务完成数
    private Integer completeNum;
    // 子任务未完成数
    private Integer uncompleteNum;
}
