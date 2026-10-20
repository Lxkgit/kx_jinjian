package com.kx.system.mapper;

import com.kx.system.domain.DeptLocationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptLocationInfoMapper {
    public List<DeptLocationInfo> listAll();

    void update(DeptLocationInfo deptLocationInfo);
}
