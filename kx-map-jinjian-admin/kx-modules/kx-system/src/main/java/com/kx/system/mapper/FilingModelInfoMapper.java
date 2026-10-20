package com.kx.system.mapper;


import com.kx.system.domain.filing.FilingModelInfo;

import java.util.List;

/**
 * <p>
 * 表单字段模板表 Mapper 接口
 * </p>
 *
 * @author YuanShiYi
 * @since 2022-12-28
 */
public interface FilingModelInfoMapper {

    List<FilingModelInfo> list(FilingModelInfo filingModelInfo);

    FilingModelInfo getById(Long id);

    Long insertFilingModelInfo(FilingModelInfo filingModelInfo);

    Long updateFilingModelInfo(FilingModelInfo filingModelInfo);

    void deleteById(Long id);

    FilingModelInfo countModelByCodeAndType(FilingModelInfo model);
}
