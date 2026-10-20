package com.kx.system.domain.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ListVisitsManagerParam extends ExportPageBaseParam {
    //走访客户经理名称
    private String visitsManagerName;
    //走访客户经理编码
    private String visitsManagerCode;
    //归属机构编码
    private String visitsOrgCode;
    //走访开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visitsStartTime;
    //走访结束时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visitsEndTime;
}
