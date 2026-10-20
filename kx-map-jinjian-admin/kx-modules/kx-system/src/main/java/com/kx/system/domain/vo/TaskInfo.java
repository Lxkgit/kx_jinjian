package com.kx.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huhao
 * @created 2022/5/30
 * @Description 任务信息
 */
@Data
public class TaskInfo implements Serializable {
    private static final long serialVersionUID = -8968689681050911442L;
    private Long id;
    private String taskName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private Integer cnt;
}
