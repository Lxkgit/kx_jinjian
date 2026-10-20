package com.kx.system.domain.branch.vo;

import com.kx.system.domain.branch.BranchInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author lxk
 * @CreateTime 2025-07-07
 */

@Getter
@Setter
public class BranchInfoVo {

    private BranchInfo branchInfo;

    private MultipartFile file;

//    private BranchStatistics branchStatistics;

}
