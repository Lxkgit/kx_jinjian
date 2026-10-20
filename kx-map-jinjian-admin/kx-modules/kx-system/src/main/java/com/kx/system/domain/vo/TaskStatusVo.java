package com.kx.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *   任务对象
 */
@Data
public class TaskStatusVo implements Serializable {
    /**
     *  code
     */
    private Integer code;
    /**
     *  中文名
     */
    private String name;

    public TaskStatusVo(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public void FillFormVo(){};
}
