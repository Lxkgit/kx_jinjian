package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.FileLibraryInfo;
import com.kx.system.domain.vo.FileLibraryInfoSearch;
import com.kx.system.service.FileLibraryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/standard/data")
public class FileLibraryController   extends BaseController {

    @Resource
    private FileLibraryService fileLibraryService;


    @GetMapping("/list")
    public TableDataInfo list(FileLibraryInfoSearch fileLibraryInfoSearch){
        startPage();
        List<FileLibraryInfo> fileLibraryInfos = fileLibraryService.selectFileLibraryPage(fileLibraryInfoSearch);
        return getDataTable(fileLibraryInfos);
    }


    @PostMapping("/save")
    public AjaxResult add(@RequestBody FileLibraryInfo fileLibraryInfo){
        int i = fileLibraryService.addFileLibrary(fileLibraryInfo);
        return i > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @PostMapping("/del")
    public AjaxResult del(@RequestBody FileLibraryInfoSearch search){
        int i = fileLibraryService.deleteBatchFileLibrary(search);
        return i > 0 ? AjaxResult.success() : AjaxResult.error();
    }


    @PostMapping("/down/file")
    public AjaxResult downFile( FileLibraryInfoSearch search, HttpServletResponse httpServletResponse){
        fileLibraryService.downloadFileLibrary(search,httpServletResponse);
        return AjaxResult.success();
    }


    @PostMapping("/file/preview")
    public AjaxResult downFile(@RequestBody FileLibraryInfoSearch search){
        String url = fileLibraryService.previewFileLibrary(search);
        return AjaxResult.success("操作成功",url);
    }
}
