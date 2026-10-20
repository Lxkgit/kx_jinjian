package com.kx.common.core.domain.branch.branch;

import lombok.Data;

import java.util.Date;

/**
 * @Description 网点文章阅读表
 * @Author lxk
 * @CreateTime 2025-07-07
 */

@Data
public class BranchArticleRead {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 用户id
     */
    private Long userId;

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
}
