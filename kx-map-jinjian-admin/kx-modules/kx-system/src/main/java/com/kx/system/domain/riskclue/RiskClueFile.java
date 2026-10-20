package com.kx.system.domain.riskclue;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RiskClueFile implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long clueId;
    /** 1现场照片、2附件资料。 */
    private Integer fileType;
    private String originalName;
    private String objectName;
    private String fileUrl;
    private String fileExt;
    private Long fileSize;
    private Integer sortOrder;
    private String delFlag;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
