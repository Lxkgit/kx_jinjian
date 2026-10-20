package com.kx.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author gary
 */
@Data
public class Village {
    private Long id;
    private String name;
    private String code;
    private String parentCode;
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String centerLocationInfo;
}
