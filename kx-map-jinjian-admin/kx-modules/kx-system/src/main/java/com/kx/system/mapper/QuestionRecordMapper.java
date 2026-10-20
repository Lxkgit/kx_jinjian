package com.kx.system.mapper;

import com.kx.system.domain.QuestionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionRecordMapper {
    List<QuestionRecord> list(QuestionRecord questionRecord);

    void insert(QuestionRecord questionRecord);

    void update(QuestionRecord questionRecord);

    void delete(@Param("id") String id);
}
