package com.kx.system.service;

import com.kx.system.domain.business.BusinessDetailInfo;
import java.util.List;
/**
 * <p>
 * 反馈详情表 服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
public interface BusinessDetailInfoService {

  /**
  * @Description: 列表查询
  * @param businessDetailInfo
  * @return List<BusinessDetailInfo>
  */
  List<BusinessDetailInfo> list(BusinessDetailInfo businessDetailInfo);

    /**
    * @Description: 根据id查询
    * @param id
    * @return BusinessDetailInfo
    */
    BusinessDetailInfo getById(Long id);

    /**
    * @Description: 新增/修改
    * @param businessDetailInfo
    * @return BusinessDetailInfo
    */
    BusinessDetailInfo saveBusinessDetailInfo(BusinessDetailInfo businessDetailInfo);

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
