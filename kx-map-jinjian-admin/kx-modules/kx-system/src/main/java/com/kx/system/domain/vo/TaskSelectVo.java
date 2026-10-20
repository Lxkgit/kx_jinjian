package com.kx.system.domain.vo;

/**
 * @author huhao
 * @created 2022/5/26
 * @Description 任务排行信息
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TaskSelectVo {
    private String code;
    private String name;
}
