package com.kx.system.mapper;

import com.kx.system.domain.XskyLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface XskyLogMapper {
    void insert(XskyLog xskyLog);
}
