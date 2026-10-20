package com.kx.system.service;

import com.kx.system.domain.filing.FilingHistoryUpdate;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-15
 */
public interface FilingHistoryUpdateService {

  /**
  * @Description: 列表查询
  * @param filingHistoryUpdate
  * @return List<FilingHistoryUpdate>
  */
  List<FilingHistoryUpdate> list(FilingHistoryUpdate filingHistoryUpdate);

    /**
    * @Description: 根据id查询
    * @param id
    * @return FilingHistoryUpdate
    */
    FilingHistoryUpdate getById(Long id);

    /**
    * @Description: 新增/修改
    * @param filingHistoryUpdate
    * @return FilingHistoryUpdate
    */
    FilingHistoryUpdate saveFilingHistoryUpdate(FilingHistoryUpdate filingHistoryUpdate);

    /**
    * @Description: 批量删除
    * @param ids
    */
    String deleteByIds(String ids);

    /**
    * @Description: 删除
    * @param id
    */
    void deleteById(Long id);

  FilingHistoryUpdate getByIdentityAndModelId(Long identity, Long modelId);
}
