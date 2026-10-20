package com.kx.system.mapper;

import com.kx.system.domain.branch.BranchArticleRead;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 文章阅读表
 * @Author lxk
 * @CreateTime 2025-07-07
 */

public interface BranchArticleReadMapper {

    void insertArticleRead(BranchArticleRead branchArticleRead);

    List<BranchArticleRead> selectArticleReadByArticleIdsAndUserId(@Param("articleIds") List<Long> articleIds,
                                                        @Param("userId") Long userId
    );
}
