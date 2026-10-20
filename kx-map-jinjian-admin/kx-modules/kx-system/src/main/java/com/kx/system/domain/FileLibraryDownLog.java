package com.kx.system.domain;

import java.util.Date;
import lombok.Data;

/**
 * 资料库料库下载记录
 */
@Data
public class FileLibraryDownLog {
    private Long id;

    /**
    * 文件库id
    */
    private Long fileLibraryId;

    /**
    * 下载时间
    */
    private Date downTime;

    /**
    * 下载人工号
    */
    private String downUserNum;
}