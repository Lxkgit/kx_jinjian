package com.kx.system.mapper;

import com.kx.system.domain.param.PaViewSmallParam;
import com.kx.system.domain.small.PaViewGridOverview;
import com.kx.system.domain.small.PaViewLoan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 小微视图-资产模块-客户经理纬度 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
public interface PaViewLoanManagerMapper {

    List<PaViewLoan> list(PaViewLoan paViewLoan);

    PaViewLoan getById(Long id);

    Long insertPaViewLoanManager(PaViewLoan paViewLoan);

    Long batchInsert(@Param("paViewLoans") List<PaViewLoan> paViewLoans);

    void truncate();

    Long updatePaViewLoanManager(PaViewLoan paViewLoan);

    void deleteById(Long id);

    PaViewLoan getByQuery(PaViewSmallParam paViewSmallParam);
}
