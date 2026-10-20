package com.kx.system.service;

import com.kx.system.domain.filing.FilingImportRecord;
import java.util.List;
/**
 * <p>
 * 建档客户导入记录 服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-11
 */
public interface FilingImportRecordService {

  /**
  * @Description: 列表查询
  * @param filingImportRecord
  * @return List<FilingImportRecord>
  */
  List<FilingImportRecord> list(FilingImportRecord filingImportRecord);

    /**
    * @Description: 根据id查询
    * @param id
    * @return FilingImportRecord
    */
    FilingImportRecord getById(Long id);


    void add(FilingImportRecord filingImportRecord);


}
