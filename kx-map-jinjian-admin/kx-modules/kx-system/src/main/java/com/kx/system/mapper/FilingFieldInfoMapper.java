package com.kx.system.mapper;

import com.kx.system.domain.filing.FilingFieldInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 建档考核字段表 Mapper 接口
 * </p>
 *
 * @author YuanShiYi
 * @since 2022-12-20
 */
public interface FilingFieldInfoMapper {

    List<FilingFieldInfo> list(FilingFieldInfo filingFieldInfo);

    FilingFieldInfo getById(Long id);

    Long insertFilingFieldInfo(FilingFieldInfo filingFieldInfo);

    Long updateFilingFieldInfo(FilingFieldInfo filingFieldInfo);

    void deleteById(Long id);

    FilingFieldInfo getByEnglishNameAndModelId(@Param("englishName") String englishName, @Param("modelId") Long modelId);

    void deleteByModelId(Long modelId);

    void deleteByModelIdAndEnglishName(@Param("modelId") Long modelId, @Param("englishName") String englishName);
}
