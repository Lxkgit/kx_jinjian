package com.kx.system.service.impl;

import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.XskyLog;
import com.kx.system.domain.XskyReq;
import com.kx.system.domain.XskyResult;
import com.kx.system.domain.dto.XskyDTO;
import com.kx.system.mapper.XskyLogMapper;
import com.kx.system.service.IXskyLogService;
import com.kx.system.util.XskyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class XskyLogServiceImpl implements IXskyLogService {
    @Autowired
    private XskyUtil xskyUtil;

    @Autowired
    private XskyLogMapper xskyLogMapper;

    @Override
    public XskyResult upload(MultipartFile file, XskyDTO xskyDTO){
        //file
        String originalFilename = file.getOriginalFilename();
        //最后一个.号的所在位置,确定文件前缀后缀名称
        int index = originalFilename.lastIndexOf(".");
        //前缀
        String prefix = originalFilename.substring(0,index);
        //后缀
        String suffix = originalFilename.substring(index+1);
        //前缀长度小于3会报错
        if(prefix.length() < 3){
            prefix += "grid_system_";
        }
        File tempFile = null;
        try {
            //创建临时文件
            tempFile = File.createTempFile(prefix,suffix);
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessValidationException("文件读取失败");
        }
        //key
        String key = System.currentTimeMillis() + originalFilename;
        XskyReq xskyReq = new XskyReq();
        xskyReq.setBucketName(xskyUtil.getBucket());
        xskyReq.setKey(key);
        xskyReq.setFile(tempFile);
        xskyReq.setContentType(suffix);
        XskyResult xskyResult = xskyUtil.uploadFile(xskyReq);
        xskyResult.setFileName(originalFilename);
        if("0000".equals(xskyResult.getResultCode())){
            XskyLog xskyLog = new XskyLog();
            xskyLog.setCreateTime(new Date());
            xskyLog.setUpdateTime(new Date());
            xskyLog.setBucketName(xskyReq.getBucketName());
            xskyLog.setPicKey(xskyReq.getKey());
            xskyLog.setUrl(xskyResult.getFileUrl());
            xskyLog.setBusType("rmms");
            xskyLog.setFileType(xskyDTO.getFileType());
            xskyLog.setSerialNumber(String.valueOf(System.currentTimeMillis()));
            xskyLogMapper.insert(xskyLog);
        }
        return xskyResult;
    }
}
