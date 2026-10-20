package com.kx.system.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: gary
 * @Date: Created in 17:18 2021/12/8
 */
@Data
public class ClientManageSelectDto {
    Long networkId;
    //客户类别 (1-公共池客户,2-网点归属客户)
    String clientType;
    String jobNumber;
    String managerId;
    List<Long> labelIdList;
    List<String> villageNameList;
    List<String> areaCodeList;
    String clientName;
    List<String> networkCodes;
    List<String> networkNames;
    List<Long> networkIds;
    String areaCode;
    Integer pageNum;
    Integer pageSize;
}
