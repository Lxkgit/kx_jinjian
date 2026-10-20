package com.kx.system.mapper;

import com.kx.system.domain.param.PaViewSmallParam;
import com.kx.system.domain.small.PaViewDepositModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小微视图-存款模块-网格、机构纬度 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-29
 */
public interface PaViewDepositModuleOrgGridMapper {

    List<PaViewDepositModule> list(PaViewDepositModule paViewDepositModule);

    PaViewDepositModule getById(Long id);

    Long insertPaViewDepositModuleOrgGrid(PaViewDepositModule paViewDepositModule);

    Long batchInsert(@Param("paViewDepositModules") List<PaViewDepositModule> paViewDepositModules);

    void truncate();

    Long updatePaViewDepositModuleOrgGrid(PaViewDepositModule paViewDepositModule);

    void deleteById(Long id);

    PaViewDepositModule getByQuery(PaViewSmallParam paViewSmallParam);

    /**
     * 查询是否统计过数据
     *
     * @return
     */
    boolean statisticed();

    /**
     * 查询所有4级网格的指标数据
     *
     * @return
     */
    List<PaViewDepositModule> listGridLevel4();

}
