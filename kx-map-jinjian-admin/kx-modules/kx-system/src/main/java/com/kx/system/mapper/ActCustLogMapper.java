package com.kx.system.mapper;

import com.kx.system.domain.ActCustLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface ActCustLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActCustLog record);

    int insertSelective(ActCustLog record);

    ActCustLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActCustLog record);

    int updateByPrimaryKey(ActCustLog record);

    List<ActCustLog> selectByActId(Long actId);

    int batchInsert(List<ActCustLog> list);

}
