package com.kx.system.mapper;

import com.kx.system.domain.filing.FilingCustomerTransfer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 建档客户移交审批表 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-09
 */
public interface FilingCustomerTransferMapper {

    List<FilingCustomerTransfer> list(FilingCustomerTransfer filingCustomerTransfer);

    FilingCustomerTransfer getById(Long id);

    Long insertFilingCustomerTransfer(FilingCustomerTransfer filingCustomerTransfer);

    Long updateFilingCustomerTransfer(FilingCustomerTransfer filingCustomerTransfer);

    void deleteById(Long id);

    void insertBatch(List<FilingCustomerTransfer> list);

    void updateBatchById(List<FilingCustomerTransfer> list);

    List<FilingCustomerTransfer> getByFilingIds(@Param("ids") List<Long> ids);
}
