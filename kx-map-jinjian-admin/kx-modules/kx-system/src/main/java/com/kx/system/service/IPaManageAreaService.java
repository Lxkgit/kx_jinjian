package com.kx.system.service;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.MaintainNetwork;
import com.kx.system.domain.PaAddrInfo;
import com.kx.system.domain.dto.GridBoundaryDTO;
import com.kx.system.domain.grid.AddrInfoDto;
import com.kx.system.domain.grid.AddrInfoParam;
import com.kx.system.domain.vo.AddrInfoVO;
import com.kx.system.domain.vo.TaskSelectVo;

import java.util.List;

/**
 * 辖区管理Service
 *
 * @author gary
 * @date 2021-12-02
 */
public interface IPaManageAreaService {

    AjaxResult unMaintainedArea();

    List<MaintainNetwork> manageAreaList(String networkName, String villageName);

    int areaDistribution(PaAddrInfo paAddrInfo);

    int deleteAreaDistribution(PaAddrInfo paAddrInfo);

    List<AddrInfoDto> listAreaGrid(AddrInfoParam addrInfoParam);

    List<TaskSelectVo> getAreaManagers(AddrInfoParam addrInfoParam);

    int editAreaDistribution(AddrInfoParam addrInfoParam);

    int addFourGrid(AddrInfoParam addrInfoParam);

    List<PaAddrInfo> getFourGridList(String addrCode);

    int mergeFourGrid(AddrInfoParam addrInfoParam);

    int editFourAreaDistribution(AddrInfoParam addrInfoParam);

    int deleteFourGrid(AddrInfoParam addrInfoParam);

    List<GridBoundaryDTO> getSonGridBoundary(String addrCode);

    GridBoundaryDTO getGridBoundary(String addrCode);

    List<AddrInfoVO> listAddrTree();

    List<AddrInfoVO> listAddrTreeWeight();

    /** 按父级编码逐级查询行政区划，parentCode 为空时查询根节点。 */
    List<AddrInfoVO> listAddrChildren(String parentCode);
}
