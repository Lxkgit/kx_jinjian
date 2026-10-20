package com.kx.system.mapper;

import com.kx.system.domain.filing.FilingMessageDetail;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-15
 */
public interface FilingMessageDetailMapper {

    List<FilingMessageDetail> list(FilingMessageDetail filingMessageDetail);

    FilingMessageDetail getById(Long id);

    Long insertFilingMessageDetail(FilingMessageDetail filingMessageDetail);

    Long updateFilingMessageDetail(FilingMessageDetail filingMessageDetail);

    void deleteById(Long id);

    void deleteByFilingId(Long id);


    List<String> listByMap(@Param("maps") HashMap<String, String> maps,
                           @Param("managerCode") String managerCode,
                           @Param("modelId") String modelId,
                           @Param("networkCode") String networkCode
    );

    FilingMessageDetail getByIdentityAndModelId(@Param("identity") String identity, @Param("modelId") Long modelId);

    void batchInsert(List<FilingMessageDetail> list);

    FilingMessageDetail getByModelIdAndFilingId(@Param("modelId") Long modelId, @Param("filingId") Long filingId);

}
