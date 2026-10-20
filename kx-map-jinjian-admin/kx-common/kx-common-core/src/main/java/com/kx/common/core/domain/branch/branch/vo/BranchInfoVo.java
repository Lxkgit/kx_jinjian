package com.kx.common.core.domain.branch.branch.vo;

import com.kx.common.core.domain.branch.branch.BranchInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description
 * @Author lxk
 * @CreateTime 2025-07-07
 */

@Data
public class BranchInfoVo extends BranchInfo {

    // 请求参数
    /**
     * 网点导入文件上传参数
     */
    private MultipartFile file;

    /**
     * 网点类型搜索入参（多选，对应 branchType 字段，前端发数组）
     */
    private List<String> branchTypeList;
}
