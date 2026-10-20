package com.kx.system.mapper;

import com.kx.system.domain.param.PaViewSmallParam;
import com.kx.system.domain.small.PaViewGridOverview;
import com.kx.system.domain.small.PaViewProsperousLoan;

import java.util.List;
/**
 * <p>
 * 小微视图-凡荣贷模块-客户经理纬度 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
public interface PaViewProsperousLoanManagerMapper {

    List<PaViewProsperousLoan> list(PaViewProsperousLoan paViewProsperousLoan);

    PaViewProsperousLoan getById(Long id);

    Long insertPaViewProsperousLoanManager(PaViewProsperousLoan paViewProsperousLoan);

    Long updatePaViewProsperousLoanManager(PaViewProsperousLoan paViewProsperousLoan);

    void deleteById(Long id);

    PaViewProsperousLoan getByQuery(PaViewSmallParam paViewSmallParam);
}
