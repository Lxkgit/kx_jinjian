package com.kx.system.mapper;

import com.kx.system.domain.filing.FilingHistoryUpdate;
import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-15
 */
public interface FilingHistoryUpdateMapper {

    List<FilingHistoryUpdate> list(FilingHistoryUpdate filingHistoryUpdate);

    FilingHistoryUpdate getById(Long id);

    Long insertFilingHistoryUpdate(FilingHistoryUpdate filingHistoryUpdate);

    Long updateFilingHistoryUpdate(FilingHistoryUpdate filingHistoryUpdate);

    void deleteById(Long id);

    FilingHistoryUpdate getByIdentityAndModelId(Long identity, Long modelId);
}
