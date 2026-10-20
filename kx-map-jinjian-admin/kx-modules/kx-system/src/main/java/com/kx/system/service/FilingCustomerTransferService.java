package com.kx.system.service;

import com.kx.system.domain.filing.FilingCustomerTransfer;

import java.util.List;

/**
 * <p>
 * 建档客户移交审批表 服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-09
 */
public interface FilingCustomerTransferService {

    /**
     * @param filingCustomerTransfer
     * @return List<FilingCustomerTransfer>
     * @Description: 列表查询
     */
    List<FilingCustomerTransfer> list(FilingCustomerTransfer filingCustomerTransfer);

    /**
     * @param id
     * @return FilingCustomerTransfer
     * @Description: 根据id查询
     */
    FilingCustomerTransfer getById(Long id);

    /**
     * @param filingCustomerTransfer
     * @return FilingCustomerTransfer
     * @Description: 新增/修改
     */
    FilingCustomerTransfer saveFilingCustomerTransfer(FilingCustomerTransfer filingCustomerTransfer);

    /**
     * @param ids
     * @Description: 批量删除
     */
    String deleteByIds(String ids);

    /**
     * @param id
     * @Description: 删除
     */
    void deleteById(Long id);

    String moveBatch(List<FilingCustomerTransfer> list);

    String judgeBatch(List<FilingCustomerTransfer> list);


}
