package com.kx.system.service;

import com.kx.system.domain.FileLibraryInfo;
import com.kx.system.domain.vo.FileLibraryInfoSearch;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author  ligen
 */
public interface FileLibraryService {

    //列表查询
    List<FileLibraryInfo> selectFileLibraryPage(FileLibraryInfoSearch fileLibraryInfo);
    //新增
    int addFileLibrary(FileLibraryInfo fileLibraryInfo);
    //批量删除
    int deleteBatchFileLibrary(FileLibraryInfoSearch fileLibraryInfo);
    //下载
    void downloadFileLibrary(FileLibraryInfoSearch search, HttpServletResponse response);
    //预览
    String previewFileLibrary(FileLibraryInfoSearch search);
}
