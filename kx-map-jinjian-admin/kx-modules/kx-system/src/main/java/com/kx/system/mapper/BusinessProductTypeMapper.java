package com.kx.system.mapper;

import com.kx.system.domain.business.BusinessProductType;
import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
public interface BusinessProductTypeMapper {

    List<BusinessProductType> list(BusinessProductType businessProductType);

    BusinessProductType getById(Long id);

    Long insertBusinessProductType(BusinessProductType businessProductType);

    Long updateBusinessProductType(BusinessProductType businessProductType);

    void deleteById(Long id);
}
