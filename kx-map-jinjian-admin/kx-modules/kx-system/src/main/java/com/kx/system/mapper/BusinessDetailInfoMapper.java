package com.kx.system.mapper;

import com.kx.system.domain.business.BusinessDetailInfo;
import java.util.List;
/**
 * <p>
 * 反馈详情表 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
public interface BusinessDetailInfoMapper {

    List<BusinessDetailInfo> list(BusinessDetailInfo businessDetailInfo);

    BusinessDetailInfo getById(Long id);

    Long insertBusinessDetailInfo(BusinessDetailInfo businessDetailInfo);

    Long updateBusinessDetailInfo(BusinessDetailInfo businessDetailInfo);

    void deleteById(Long id);
}
