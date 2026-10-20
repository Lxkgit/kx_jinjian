package com.kx.system.mapper;

import com.kx.system.domain.branch.BranchArticleInfo;

import java.util.List;

/**
 * @author lxk
 * @description 网点文章动态
 * @date 2025/07/07
 */

public interface BranchArticleInfoMapper {

    List<BranchArticleInfo> selectArticleListByOrgNum(String orgNum);

    BranchArticleInfo selectArticleInfoById(Long id);

    void insertArticle(BranchArticleInfo branchArticleInfo);

    void updateArticleById(BranchArticleInfo branchArticleInfo);

    void deleteArticleById(Long id);

}
