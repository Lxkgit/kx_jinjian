package com.kx.system.mapper;

import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.customer.CustomerVisits;
import com.kx.system.domain.customer.VisitsManager;
import com.kx.system.domain.param.ListVisitsManagerParam;
import com.kx.system.domain.vo.VisitInfoVO;

import java.util.List;
/**
 * <p>
 * 客户走访信息表 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
public interface CustomerVisitsMapper {

    List<CustomerVisits> list(CustomerVisits customerVisits);

    CustomerVisits getById(Long id);

    int insertCustomerVisits(CustomerVisits customerVisits);

    Long updateCustomerVisits(CustomerVisits customerVisits);

    void deleteById(Long id);

    /**
     * 查询客户旅程中客户走访信息
     * @param customInfoDTO
     * @return
     */
    List<VisitInfoVO> listInfo(CustomInfoDTO customInfoDTO);

    List<VisitsManager> listGroupByManager(ListVisitsManagerParam param);

    List<CustomerVisits> listByMangerCode(ListVisitsManagerParam param);

    List<VisitsManager> listExportGroupByManager(ListVisitsManagerParam param);

    List<CustomerVisits> listExportByMangerCode(ListVisitsManagerParam param);
    List<CustomerVisits> selectVisitLableNtNull();
}
