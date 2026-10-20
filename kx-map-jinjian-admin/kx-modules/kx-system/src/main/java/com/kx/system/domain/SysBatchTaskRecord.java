package com.kx.system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SysBatchTaskRecord {
    private Long id;
    private String taskName;
    private String params;
    private Boolean autoExecute;
    private Boolean status;
    private String ip;
    private String log;
    private Date beginTime;
    private Date endTime;
    private Long executeTime;
}
