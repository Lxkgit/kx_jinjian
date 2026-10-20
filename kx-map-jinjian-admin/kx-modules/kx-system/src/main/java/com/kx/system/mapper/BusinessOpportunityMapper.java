package com.kx.system.mapper;

import com.kx.system.domain.business.BusinessOpportunity;

import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-12
 */
public interface BusinessOpportunityMapper {

    List<BusinessOpportunity> list(BusinessOpportunity businessOpportunity);

    BusinessOpportunity getById(Long id);

    Long insertBusinessOpportunity(BusinessOpportunity businessOpportunity);

    Long updateBusinessOpportunity(BusinessOpportunity businessOpportunity);

    void deleteById(Long id);
}
