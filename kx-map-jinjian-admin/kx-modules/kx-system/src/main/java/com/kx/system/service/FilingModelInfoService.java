package com.kx.system.service;


import com.kx.system.domain.filing.FilingModelInfo;
import com.kx.system.domain.vo.FillingVillageTypeVo;

import java.util.List;

/**
 * <p>
 * 表单字段模板表 服务类
 * </p>
 *
 * @author YuanShiYi
 * @since 2022-12-28
 */
public interface FilingModelInfoService {

    /**
     * @param filingModelInfo
     * @return List<FilingModelInfo>
     * @Description: 列表查询
     */
    List<FilingModelInfo> list(FilingModelInfo filingModelInfo);

    /**
     * @param id
     * @return FilingModelInfo
     * @Description: 根据id查询
     */
    FilingModelInfo getById(Long id);

    /**
     * @param filingModelInfo
     * @return FilingModelInfo
     * @Description: 新增/修改
     */
    FilingModelInfo saveFilingModelInfo(FilingModelInfo filingModelInfo);

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

    /**
     *  查询非修改记录外开启记录条数
     * @param model
     * @return
     */
    FilingModelInfo countModelByCodeAndType(FilingModelInfo model);

    /**
     * 采集表模板 -- 三大类型
     * @return
     */
    List<FillingVillageTypeVo> listModelSelect();

    String startFilingModelInfo(Long id);

    FilingModelInfo getFilingModelInfoById(Long id, String isFinishParam);

    String editFilingModelInfo(FilingModelInfo filingModelInfo);

    String deleteFilingModelInfo(Long id);

    FilingModelInfo getCustomerProfileHeaders(String customType);

    String modelDownload();

    Long getModelIdByType(String type);
}
