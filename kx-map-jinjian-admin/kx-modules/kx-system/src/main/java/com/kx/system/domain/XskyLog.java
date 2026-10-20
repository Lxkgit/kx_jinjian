package com.kx.system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class XskyLog {
    private String serialNumber;
    private String url;
    private String busType;
    private String fileType;
    private Date createTime;
    private Date updateTime;
    private String bucketName;
    private String picKey;
}
