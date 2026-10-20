package com.kx.system.service.impl;

import com.kx.common.core.utils.StringUtils;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.filing.FilingImportRecord;
import com.kx.system.mapper.FilingImportRecordMapper;
import com.kx.system.service.FilingImportRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
/**
 * <p>
 * 建档客户导入记录 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-11
 */
@Slf4j
@Service
public class FilingImportRecordServiceImpl implements FilingImportRecordService {

  @Autowired
  private FilingImportRecordMapper filingImportRecordMapper;

    @Override
    @DataScope(deptAlias = "u",userAlias = "u")
    public List<FilingImportRecord> list(FilingImportRecord filingImportRecord) {
        List<FilingImportRecord> list = filingImportRecordMapper.list(filingImportRecord);
        return list;
    }

    @Override
    public void add(FilingImportRecord filingImportRecord) {
        filingImportRecordMapper.insertFilingImportRecord(filingImportRecord);
    }

    @Override
    public FilingImportRecord getById(Long id) {
        return filingImportRecordMapper.getById(id);
    }



}
