package com.kx.system.domain.branch.vo;

import com.kx.system.domain.branch.BranchArticleInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author lxk
 * @CreateTime 2025-07-07
 */

@Getter
@Setter
public class BranchArticleInfoVo extends BranchArticleInfo {

    /** 文章阅读状态 0未读 1已读 */
    private Integer readStatus;

}
