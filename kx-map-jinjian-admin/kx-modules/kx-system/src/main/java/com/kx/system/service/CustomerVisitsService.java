package com.kx.system.service;

import com.kx.system.domain.customer.CustomerVisits;
import com.kx.system.domain.customer.VisitsManager;
import com.kx.system.domain.dto.VisitDTO;
import com.kx.system.domain.param.ListVisitsManagerParam;

import java.util.List;

/**
 * <p>
 * 客户走访信息表 服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
public interface CustomerVisitsService {

    /**
     * @param customerVisits
     * @return List<CustomerVisits>
     * @Description: 列表查询
     */
    List<CustomerVisits> list(CustomerVisits customerVisits);

    /**
     * @param id
     * @return CustomerVisits
     * @Description: 根据id查询
     */
    CustomerVisits getById(Long id);

    /**
     * @param customerVisits
     * @return CustomerVisits
     * @Description: 新增/修改
     */
    CustomerVisits saveCustomerVisits(CustomerVisits customerVisits);

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

    List<CustomerVisits> queryVisits(VisitDTO visitDTO);

    int insertCustomerVisits(CustomerVisits customerVisits);

    List<VisitsManager> listGroupByManager(ListVisitsManagerParam param);

    List<CustomerVisits> listByMangerCode(ListVisitsManagerParam param);

    /**
     * 查询需要导出的走访记录统计
     *
     * @param param
     * @return
     */
    List<VisitsManager> listExportGroupByManager(ListVisitsManagerParam param);

    /**
     * 查询要导出的走访详情
     *
     * @param param
     * @return
     */
    List<CustomerVisits> listExportByMangerCode(ListVisitsManagerParam param);
}
