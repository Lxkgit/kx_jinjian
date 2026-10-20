package com.kx.system.service;

import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.filing.FilingMessageDetail;

import java.util.HashMap;
import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-15
 */
public interface FilingMessageDetailService {

  /**
  * @Description: 列表查询
  * @param filingMessageDetail
  * @return List<FilingMessageDetail>
  */
  List<FilingMessageDetail> list(FilingMessageDetail filingMessageDetail);

    /**
    * @Description: 根据id查询
    * @param id
    * @return FilingMessageDetail
    */
    FilingMessageDetail getById(Long id);

    /**
    * @Description: 新增/修改
    * @param filingMessageDetail
    * @return FilingMessageDetail
    */
    FilingMessageDetail saveFilingMessageDetail(FilingMessageDetail filingMessageDetail);

    /**
    * @Description: 批量删除
    * @param ids
    */
    String deleteByIds(String ids);

    String deleteByFilingIds(String ids);

    /**
    * @Description: 删除
    * @param id
    */
    void deleteById(Long id);


  List<String> listByMap(HashMap<String, String> map, String managerCode, String modelId, String networkCode);

    Object getByCustomerId(Long customerId, Long modelId);
}
