package com.kx.system.service;

import com.kx.system.domain.business.BusinessOpportunity;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-12
 */
public interface BusinessOpportunityService {

  /**
  * @Description: 列表查询
  * @param businessOpportunity
  * @return List<BusinessOpportunity>
  */
  List<BusinessOpportunity> list(BusinessOpportunity businessOpportunity);

    /**
    * @Description: 根据id查询
    * @param id
    * @return BusinessOpportunity
    */
    BusinessOpportunity getById(Long id);

    /**
    * @Description: 新增/修改
    * @param businessOpportunity
    * @return BusinessOpportunity
    */
    BusinessOpportunity saveBusinessOpportunity(BusinessOpportunity businessOpportunity);

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
