package com.kx.system.mapper;

import com.kx.system.domain.JobCodeRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobCodeRelationMapper {
     List<JobCodeRelation> listAll();
}
