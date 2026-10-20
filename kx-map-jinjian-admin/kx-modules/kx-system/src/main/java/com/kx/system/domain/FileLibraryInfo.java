package com.kx.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 标准化文件资料库
 */
@Data
public class FileLibraryInfo {
    private Long id;

    /**
    * 文件url
    */
    private String fileUrl;

    /**
    * 资料名称
    */
    private String fileName;

    /**
    * 创建人
    */
    private String createBy;

    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
    * 创建人工号
    */
    private String createNum;

    /**
    * 下载次数
    */
    private Integer downLoadNum;
}
