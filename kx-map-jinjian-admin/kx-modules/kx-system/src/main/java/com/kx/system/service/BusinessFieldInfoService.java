package com.kx.system.service;

import com.kx.system.domain.business.BusinessFieldInfo;
import java.util.List;
/**
 * <p>
 * 商机产品反馈字段表 服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
public interface BusinessFieldInfoService {

  /**
  * @Description: 列表查询
  * @param businessFieldInfo
  * @return List<BusinessFieldInfo>
  */
  List<BusinessFieldInfo> list(BusinessFieldInfo businessFieldInfo);

    /**
    * @Description: 根据id查询
    * @param id
    * @return BusinessFieldInfo
    */
    BusinessFieldInfo getById(Long id);

    /**
    * @Description: 新增/修改
    * @param businessFieldInfo
    * @return BusinessFieldInfo
    */
    BusinessFieldInfo saveBusinessFieldInfo(BusinessFieldInfo businessFieldInfo);

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
}
