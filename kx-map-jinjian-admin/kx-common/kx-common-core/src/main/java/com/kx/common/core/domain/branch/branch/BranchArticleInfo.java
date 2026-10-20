package com.kx.common.core.domain.branch.branch;

import lombok.Data;

import java.util.Date;

/**
 * @Description 网点文章动态表
 * @Author lxk
 * @CreateTime 2025-07-07
 */

@Data
public class BranchArticleInfo {

    /** 自增id */
    private Long id;

    /** sys_dept表org_num字段（网点编码） */
    private String orgNum;

    /** 文章标题 */
    private String title;

    /** 文章内容 */
    private String article;

    /** 文章删除标识 0已删除 1 正常 */
    private Integer delFlag;

    /** 创建时间 */
    private Date createTime;

    /** 创建人名称 */
    private String createName;

    /** 创建人账号 */
    private Long createBy;

    /** 最近修改时间 */
    private Date updateTime;

    /** 修改人名称 */
    private String updateName;

    /** 修改人账号 */
    private Long updateBy;
}
