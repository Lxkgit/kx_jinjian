package com.kx.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.web.domain.BaseEntity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionRecord extends BaseEntity {
    private String id;

    private Long typeId;

    private String content;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

//    private List<TaskDetailContent> details;
}