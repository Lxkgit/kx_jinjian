package com.kx.system.domain;

import lombok.Data;

@Data
public class XskyResult {
    private String resultCode;
    private String resultMsg;
    private String versionId;
    private String eTag;
    private String expirationTime;
    private String expirationTimeRuleId;
    private String contentMd5;
    private String fileUrl;
    private String fileName;
}
