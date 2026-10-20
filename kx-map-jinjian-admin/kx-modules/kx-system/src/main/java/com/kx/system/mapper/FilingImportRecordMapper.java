package com.kx.system.mapper;

import com.kx.system.domain.filing.FilingImportRecord;
import java.util.List;
/**
 * <p>
 * 建档客户导入记录 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-11
 */
public interface FilingImportRecordMapper {

    List<FilingImportRecord> list(FilingImportRecord filingImportRecord);

    FilingImportRecord getById(Long id);

    Long insertFilingImportRecord(FilingImportRecord filingImportRecord);

    Long updateFilingImportRecord(FilingImportRecord filingImportRecord);

    void deleteById(Long id);
}
