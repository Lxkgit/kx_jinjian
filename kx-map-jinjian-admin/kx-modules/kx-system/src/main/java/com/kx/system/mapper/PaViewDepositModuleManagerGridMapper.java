package com.kx.system.mapper;

import com.kx.system.domain.param.PaViewSmallParam;
import com.kx.system.domain.small.PaViewDepositModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 小微视图-存款模块-网格、客户经理纬度 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-29
 */
public interface PaViewDepositModuleManagerGridMapper {

    List<PaViewDepositModule> list(PaViewDepositModule paViewDepositModule);

    PaViewDepositModule getById(Long id);

    Long insertPaViewDepositModuleManagerGrid(PaViewDepositModule paViewDepositModule);

    Long batchInsert(@Param("paViewDepositModules") List<PaViewDepositModule> paViewDepositModules);

    /**
     * 清空表
     */
    void truncate();

    Long updatePaViewDepositModuleManagerGrid(PaViewDepositModule paViewDepositModule);

    void deleteById(Long id);

    PaViewDepositModule getByQuery(PaViewSmallParam paViewSmallParam);
}
