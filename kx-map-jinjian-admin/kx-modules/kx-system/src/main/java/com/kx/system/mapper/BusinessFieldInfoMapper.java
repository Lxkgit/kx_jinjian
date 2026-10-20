package com.kx.system.mapper;

import com.kx.system.domain.business.BusinessFieldInfo;
import java.util.List;
/**
 * <p>
 * 商机产品反馈字段表 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
public interface BusinessFieldInfoMapper {

    List<BusinessFieldInfo> list(BusinessFieldInfo businessFieldInfo);

    BusinessFieldInfo getById(Long id);

    Long insertBusinessFieldInfo(BusinessFieldInfo businessFieldInfo);

    Long updateBusinessFieldInfo(BusinessFieldInfo businessFieldInfo);

    void deleteById(Long id);
}
