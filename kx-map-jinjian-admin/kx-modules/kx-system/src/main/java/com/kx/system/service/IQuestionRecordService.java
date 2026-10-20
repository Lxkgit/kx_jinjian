package com.kx.system.service;

import com.kx.system.domain.QuestionRecord;

import java.util.List;

public interface IQuestionRecordService {
    List<QuestionRecord> list(QuestionRecord questionRecord);

    void insert(QuestionRecord questionRecord);

    void update(QuestionRecord questionRecord);

    void delete(String id);
}
