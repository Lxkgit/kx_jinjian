package com.kx.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kx.system.domain.filing.FilingCustomerInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 建档客户基础信息表 服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
public interface FilingCustomerInfoService {

    /**
     * @param filingCustomerInfo
     * @return List<FilingCustomerInfo>
     * @Description: 列表查询
     */
    List<FilingCustomerInfo> list(FilingCustomerInfo filingCustomerInfo);

    /**
     * @param id
     * @return FilingCustomerInfo
     * @Description: 根据id查询
     */
    FilingCustomerInfo getById(Long id);

    /**
     * @param filingCustomerInfo
     * @return FilingCustomerInfo
     * @Description: 新增/修改
     */
    FilingCustomerInfo saveFilingCustomerInfo(FilingCustomerInfo filingCustomerInfo) throws JsonProcessingException;

    /**
     * @param filingCustomerInfo
     * @return FilingCustomerInfo
     * @Description: 修改
     */
    void updateFilingCustomerInfo(FilingCustomerInfo filingCustomerInfo);


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

    void checkFilingOne(FilingCustomerInfo filingCustomerInfo);

    FilingCustomerInfo getByModelIdAndFilingId(Long modelId, Long filingId);

    FilingCustomerInfo getByModelIdAndIdentity(Long modelId, String identity, String phone);

    String getFilingIsExist(Long modelId, String identity);

    void importBatch(InputStream file, HttpServletRequest request) throws IOException;

    void filingStatistics() throws JsonProcessingException;

    Long getTypeByPhoneAndIdentity(String phone, String identity);

    /**
     * 查询网格下建档客户数
     *
     * @param gridCode
     * @return
     */
    Integer countByGrid(String gridCode);

    Long getModelIdByCustomId(Long clientId);
}
