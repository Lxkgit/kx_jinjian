package com.kx.system.domain.grid.message;

import lombok.Data;

import java.util.Date;

@Data
public class GridCommunitySupervisors {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 网格编码
     */
    private String gridCode;

    /**
     * 监督员名称
     */
    private String supervisorName;

    /**
     * 联系电话
     */
    private String supervisorPhone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建人账号
     */
    private Long createBy;

    /**
     * 最近修改时间
     */
    private Date updateTime;

    /**
     * 修改人名称
     */
    private String updateName;

    /**
     * 修改人账号
     */
    private Long updateBy;

//    private Long id;
//    private String gridCode;
//    private String supervisorName;
//    private String supervisorPhone;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createTime;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updateTime;
}