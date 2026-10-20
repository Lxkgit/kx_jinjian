package com.kx.system.service;

import com.kx.system.domain.branch.BranchArticleInfo;
import com.kx.system.domain.branch.BranchInfo;
import com.kx.system.domain.branch.vo.BranchArticleInfoVo;
import com.kx.system.domain.branch.vo.BranchInfoVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 针对大屏网格视图的数据库操作Service
 *
 * @author kangxu
 */
public interface BranchInfoService {

    void getBranchStatistics();

    void getBranchStatisticsList();

    void exportBranchStatisticsList();

    List<BranchInfo> selectBranchList(BranchInfoVo branchInfoVo);

    void importBranchData(MultipartFile file);

    void exportBranchData(HttpServletResponse response, BranchInfoVo branchInfoVo);

    BranchInfoVo selectBranchById(Long id);

    void updateBranch(BranchInfoVo branchInfoVo);

    List<BranchArticleInfoVo> selectBranchArticleList(String orgNum);

    BranchArticleInfo selectBranchArticleById(Long id);

    void insertBranchArticle(BranchArticleInfo branchArticleInfo);

    void updateBranchArticle(BranchArticleInfo branchArticleInfo);

    void deleteBranchArticleById(Long id);
}
