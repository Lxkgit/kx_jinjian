package com.kx.system.domain;

import java.util.Date;

import lombok.Data;

/**
 * 锚点变更记录
 */
@Data
public class AnchorChangeLogVo {
    private Long id;
    private String anchorName;
    private String typeName;
    private Integer oldAddressType;
    private String oldAddress;
    private Integer newAddressType;
    private String newAddress;
    private String operatorName;
    private Date createTime;
}
