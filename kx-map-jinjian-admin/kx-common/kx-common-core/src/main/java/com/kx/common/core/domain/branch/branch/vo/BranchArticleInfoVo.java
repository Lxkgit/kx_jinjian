package com.kx.common.core.domain.branch.branch.vo;

import com.kx.common.core.domain.branch.branch.BranchArticleInfo;
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
