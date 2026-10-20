package com.kx.system.domain;

import lombok.Data;

import java.io.File;

@Data
public class XskyReq {
    private String bucketName;
    private String key;
    private File file;
    private String contentType;
}
