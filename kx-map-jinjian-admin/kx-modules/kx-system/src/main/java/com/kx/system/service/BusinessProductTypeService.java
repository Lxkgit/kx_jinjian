package com.kx.system.service;

import com.kx.system.domain.business.BusinessProductType;
import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
public interface BusinessProductTypeService {

  /**
  * @Description: 列表查询
  * @param businessProductType
  * @return List<BusinessProductType>
  */
  List<BusinessProductType> list(BusinessProductType businessProductType);

    /**
    * @Description: 根据id查询
    * @param id
    * @return BusinessProductType
    */
    BusinessProductType getById(Long id);

    /**
    * @Description: 新增/修改
    * @param businessProductType
    * @return BusinessProductType
    */
    BusinessProductType saveBusinessProductType(BusinessProductType businessProductType);

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
