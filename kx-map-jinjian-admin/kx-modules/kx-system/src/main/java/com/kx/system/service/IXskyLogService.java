package com.kx.system.service;

import com.kx.system.domain.XskyResult;
import com.kx.system.domain.dto.XskyDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IXskyLogService {
    XskyResult upload(MultipartFile file, XskyDTO xskyDTO) throws IOException;
}
