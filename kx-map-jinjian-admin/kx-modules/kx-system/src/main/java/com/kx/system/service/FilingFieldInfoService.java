package com.kx.system.service;

import com.kx.system.domain.filing.FilingFieldInfo;

import java.util.List;
/**
 * <p>
 * 建档考核字段表 服务类
 * </p>
 *
 * @author YuanShiYi
 * @since 2022-12-20
 */
public interface FilingFieldInfoService {

  /**
   * @Description: 列表查询
   * @param filingFieldInfo
   * @return List<FilingFieldInfo>
   */
  List<FilingFieldInfo> list(FilingFieldInfo filingFieldInfo);

  /**
   * @Description: 根据id查询
   * @param id
   * @return FilingFieldInfo
   */
  FilingFieldInfo getById(Long id);

  /**
   * @Description: 新增/修改
   * @param filingFieldInfo
   * @return FilingFieldInfo
   */
  FilingFieldInfo saveFilingFieldInfo(FilingFieldInfo filingFieldInfo);

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

  /**
   *  根据模板id查询字段集
   * @param modelId
   * @return
   */
  List<FilingFieldInfo> getListByModelId(Long modelId);

  /**
   *  根据英文名称查询字段
   * @param englishName
   * @return
   */
  FilingFieldInfo getByEnglishNameAndModelId(String englishName, Long modelId);

  void deleteByModelId(Long id);

  /**
   *  根据模板id和字段名称删除
   * @param id
   * @param englishName
   */
  void deleteByModelIdAndEnglishName(Long id, String englishName);
}
