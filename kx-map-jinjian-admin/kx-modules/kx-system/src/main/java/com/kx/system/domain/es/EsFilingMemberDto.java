package com.kx.system.domain.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class EsFilingMemberDto {


    private Long id;
    private String customerType;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerIdentity;
    private String customerSource;
    private String belongGridCode;
    private String belongGridName;
    private String filingManagerCode;
    private String filingManagerName;
    private String filingOrgCode;
    private String filingOrgName;
    private String filingProgress;
    private String filingSource;
    private String filingStatus;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date filingDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String customerAddressType;
    private String customerIntroducerIdentity;
    private String customerNm;
    private String customerPhoto;
    private String clockIn;
    private String branchBank;
    private String gridManager;
    private String separateBank;
    private String creditManagerJobNumber;
    private String creditManager;
    private String gridManagerName;
    private Long userDeptId;
    private Long userId;
    private String ancestorsId;
    private Long groupId;
    private String ancestorCodes;
}
