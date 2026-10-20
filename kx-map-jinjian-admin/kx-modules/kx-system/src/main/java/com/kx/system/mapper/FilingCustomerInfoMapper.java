package com.kx.system.mapper;

import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.es.EsFilingMemberDto;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.group.CustomGroupCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilingCustomerInfoMapper {

    List<FilingCustomerInfo> list(FilingCustomerInfo filingCustomerInfo);

    List<FilingCustomerInfo> listAllIdentity(FilingCustomerInfo filingCustomerInfo);

    List<FilingCustomerInfo> listInfo(@Param("filingCustomerInfo") FilingCustomerInfo filingCustomerInfo);

    int listInfoCount(@Param("filingCustomerInfo") FilingCustomerInfo filingCustomerInfo);

    List<Long> listIds(FilingCustomerInfo filingCustomerInfo);

    int listIdCount(FilingCustomerInfo filingCustomerInfo);

    List<FilingCustomerInfo> getInfoByIdentity(FilingCustomerInfo filingCustomerInfo);

    FilingCustomerInfo getById(Long id);

    Long insertFilingCustomerInfo(FilingCustomerInfo filingCustomerInfo);

    Long updateFilingCustomerInfo(FilingCustomerInfo filingCustomerInfo);

    void deleteById(Long id);

    List<FilingCustomerInfo> checkFiling(@Param("id") Long id, @Param("identity") String identity, @Param("name") String name, @Param("phone") String phone);

    FilingCustomerInfo selectCustomerInfo(CustomInfoDTO customInfoDTO);

    void updateBatchById(@Param("list") List<FilingCustomerInfo> filingList);


    void updateFilingCustomerGrid();

    Long getTypeByPhoneAndIdentity(@Param("phone") String phone,@Param("identity") String identity);

    List<FilingCustomerInfo> listByIds(@Param("filingIds") List<Long> filingIds);

    Integer countByGrid(@Param("gridCode") String gridCode);

    void updateGridManager(String code);

    List<EsFilingMemberDto> selectEsDataByGroupId(@Param("groupId") Long groupId, @Param("startIndex")int startIndex, @Param("endIndex")int endIndex);


    List<FilingCustomerInfo> selectAll();

}
