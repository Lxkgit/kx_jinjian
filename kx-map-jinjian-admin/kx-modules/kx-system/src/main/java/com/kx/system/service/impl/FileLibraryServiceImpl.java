package com.kx.system.service.impl;

import com.kx.common.core.exception.base.BaseException;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.api.domain.SysUser;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.FileLibraryDownLog;
import com.kx.system.domain.FileLibraryInfo;
import com.kx.system.domain.vo.FileLibraryInfoSearch;
import com.kx.system.mapper.FileLibraryDownLogMapper;
import com.kx.system.mapper.FileLibraryInfoMapper;
import com.kx.system.service.FileLibraryService;
import com.kx.system.util.MinioUtils;
import com.kx.system.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FileLibraryServiceImpl implements FileLibraryService {

    @Resource
    private FileLibraryInfoMapper fileLibraryInfoMapper;
    @Resource
    private FileLibraryDownLogMapper fileLibraryDownLogMapper;
    @Resource
    private UserUtil userUtil;
    @Resource
    private MinioUtils minioUtils;

    @Override
    public List<FileLibraryInfo> selectFileLibraryPage(FileLibraryInfoSearch fileLibraryInfo) {
        return fileLibraryInfoMapper.selectFileLibraryPage(fileLibraryInfo);
    }

    @Override
    public int addFileLibrary(FileLibraryInfo fileLibraryInfo) {
        //校验
        if (StringUtils.isEmpty(fileLibraryInfo.getFileName())){
            throw new BusinessValidationException("请输入资料名称");
        }
        if (StringUtils.isEmpty(fileLibraryInfo.getFileUrl())){
            throw new BusinessValidationException("请上传文件");
        }
        SysUser loginUser = userUtil.getLoginUser();
        fileLibraryInfo.setCreateTime(new Date());
        fileLibraryInfo.setCreateBy(loginUser.getNickName());
        fileLibraryInfo.setCreateNum(loginUser.getManagerNum());

        return fileLibraryInfoMapper.insertSelective(fileLibraryInfo);
    }

    @Override
    public int deleteBatchFileLibrary(FileLibraryInfoSearch fileLibraryInfo) {
        return fileLibraryInfoMapper.deleteBatchFileLibrary(fileLibraryInfo.getIds());
    }

    @Override
    public void downloadFileLibrary(FileLibraryInfoSearch search, HttpServletResponse response) {

        try {
            boolean flag = minioUtils.bucketExists(minioUtils.getBucketName());
            if (flag){
                FileLibraryInfo fileLibraryInfo = fileLibraryInfoMapper.selectByPrimaryKey(search.getId());
                if (fileLibraryInfo == null){
                    throw  new BusinessValidationException("资料不存在");
                }

                String fileUrl = fileLibraryInfo.getFileUrl();
                fileUrl = fileUrl.substring(fileUrl.lastIndexOf("/")+1);


                minioUtils.downloadFile(minioUtils.getBucketName(),fileUrl,"",response);
                SysUser loginUser = userUtil.getLoginUser();
                FileLibraryDownLog fileLibraryDownLog = new FileLibraryDownLog();
                fileLibraryDownLog.setFileLibraryId(search.getId());
                fileLibraryDownLog.setDownTime(new Date());
                fileLibraryDownLog.setDownUserNum(loginUser.getManagerNum());
                fileLibraryDownLogMapper.insertSelective(fileLibraryDownLog);
                int num = fileLibraryDownLogMapper.selectCount(search.getId());
                fileLibraryInfo.setDownLoadNum(num);
                fileLibraryInfoMapper.updateByPrimaryKeySelective(fileLibraryInfo);
            }else {
                throw new BaseException("桶不存在！");
            }
        }catch (Exception e){
            log.info("下载接口异常：{}",e.getMessage());
            throw new BaseException("下载接口异常！");
        }

    }

    @Override
    public String previewFileLibrary(FileLibraryInfoSearch search) {

        FileLibraryInfo fileLibraryInfo = fileLibraryInfoMapper.selectByPrimaryKey(search.getId());
        String fileUrl = fileLibraryInfo.getFileUrl();
        fileUrl = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        return minioUtils.getPresignedObjectUrl(fileUrl);

    }


}
