package com.kx.system.domain.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * 变更日志查询参数
 */
@Data
public class SearchPaChangeLogListParam extends ExportPageBaseParam {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String logType;
    private String operatorName;
}
