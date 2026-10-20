package com.kx.system.mapper;

import com.kx.system.domain.PaCompanyInfo;
import com.kx.system.domain.dto.ClientManageSelectDto;
import com.kx.system.domain.vo.PaCompanyInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PaCompanyInfoMapper {
    /**
     * 查询客户信息
     *
     * @param nm 客户内码
     * @return 客户信息
     */
    public PaCompanyInfo selectPaCompanyInfoByNm(String nm);

    /**
     * 查询客户信息
     *
     * @param nm 客户内码
     * @return 客户信息
     */
    public PaCompanyInfo selectPaCompanyInfoAndBelongToByNm(String nm);

    /**
     * 查询客户信息列表
     *
     * @param clientManageSelectDto 客户信息
     * @return 客户信息集合
     */
    public List<PaCompanyInfo> selectPaCompanyInfoList(ClientManageSelectDto clientManageSelectDto);

    /**
     * 修改企业信息
     *
     * @param paCompanyInfo 客户信息
     * @return 结果
     */
    public int updatePaCompanyInfo(PaCompanyInfo paCompanyInfo);

    /**
     * 删除企业信息
     *
     * @param nm
     * @return 结果
     */
    int deletePaCompanyInfo(String nm);

    /**
     * 插入企业基本信息
     * @param paCompanyInfo
     * @return
     */
    int insertPaCompanyInfo(PaCompanyInfo paCompanyInfo);

    /**
     * 获取最新同步的企业信息
     * @return
     */
    List<PaCompanyInfoVO> selectNewCompanyInfo(String nowDate);

    void updatePaCompanyExtraInfo(PaCompanyInfo paCompanyInfo);

    void updatePaCompanyExtraInfoPoolTransfer(PaCompanyInfo paCompanyInfo);

    List<PaCompanyInfo> selectPublicPaCompanyInfoList(ClientManageSelectDto clientManageSelectDto);

    List<PaCompanyInfo> selectNetworkPaCompanyInfoList(ClientManageSelectDto clientManageSelectDto);

    PaCompanyInfo selectPaCompanyInfoByFullCompanyName(@Param("companyName") String companyName);

    List<PaCompanyInfoVO> selectAllCompanyInfo(@Param("etlDt") Date etlDt);

    void updatePaCompanyLonLat(PaCompanyInfo paCompanyInfo);

    List<PaCompanyInfo> selectByEtlDtBetween(@Param("beginEtlDt") Date beginEtlDt, @Param("endEtlDt") Date endEtlDt);

    PaCompanyInfo getByCreditCode(String creditCode);





}
