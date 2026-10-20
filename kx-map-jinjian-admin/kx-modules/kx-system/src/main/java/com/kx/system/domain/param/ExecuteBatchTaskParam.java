package com.kx.system.domain.param;

import lombok.Data;

import java.util.Date;

@Data
public class ExecuteBatchTaskParam {
    private String jobName;
    private Date date;
}
