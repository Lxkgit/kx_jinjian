package com.kx.system.domain.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class VisitsManager {
    @Excel(name = "走访客户经理名称")
    private String visitsManagerName;

    @Excel(name = "走访客户经理编码")
    private String visitsManagerCode;

    @Excel(name = "归属机构名称")
    private String visitsOrgName;

    @Excel(name = "归属机构编码")
    private String visitsOrgCode;

    @Excel(name = "走访次数")
    private Integer visitsCount;

    @Excel(name = "走访客户数")
    private Integer visitsCustomerCount;

//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "走访开始时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
//    private Date visitsStartTime;
//
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "走访结束时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
//    private Date visitsEndTime;
}
