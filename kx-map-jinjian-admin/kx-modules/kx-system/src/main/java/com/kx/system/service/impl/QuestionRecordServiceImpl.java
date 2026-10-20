package com.kx.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.kx.common.core.utils.uuid.UUID;
import com.kx.system.domain.QuestionRecord;
//import com.kx.system.domain.task.TaskDetailContent;
//import com.kx.system.domain.task.TaskTypeChild;
import com.kx.system.mapper.QuestionRecordMapper;
import com.kx.system.service.IQuestionRecordService;
import com.kx.system.util.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionRecordServiceImpl implements IQuestionRecordService {
    @Resource
    private QuestionRecordMapper questionRecordMapper;
    @Resource
    private UserUtil userUtil;

    @Override
    public List<QuestionRecord> list(QuestionRecord questionRecord) {
        PageHelper.startPage(questionRecord.getPageNum(),questionRecord.getPageSize());
        String managerNum = userUtil.getLoginUser().getManagerNum();
        questionRecord.setCreateBy(managerNum);
        List<QuestionRecord> list = questionRecordMapper.list(questionRecord);
//        list.forEach(l -> l.setDetails(JSON.parseArray(l.getContent(), TaskDetailContent.class)));
        return list;
    }

    @Override
    public void insert(QuestionRecord questionRecord) {
        String managerNum = userUtil.getLoginUser().getManagerNum();
        questionRecord.setId(UUID.randomUUID().toString());
//        questionRecord.setContent(JSON.toJSONString(questionRecord.getDetails()));
        questionRecord.setCreateBy(managerNum);
        questionRecord.setCreateTime(new Date());
        questionRecord.setUpdateTime(new Date());
        questionRecordMapper.insert(questionRecord);
    }

    @Override
    public void update(QuestionRecord questionRecord) {
//        questionRecord.setContent(JSON.toJSONString(questionRecord.getDetails()));
        questionRecord.setUpdateTime(new Date());
        questionRecordMapper.update(questionRecord);
    }

    @Override
    public void delete(String id) {
        questionRecordMapper.delete(id);
    }
}
