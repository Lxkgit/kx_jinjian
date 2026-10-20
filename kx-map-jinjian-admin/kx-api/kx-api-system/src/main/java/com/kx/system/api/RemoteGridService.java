package com.kx.system.api;

import com.kx.common.core.constant.SecurityConstants;
import com.kx.common.core.constant.ServiceNameConstants;
import com.kx.common.core.domain.R;
import com.kx.common.core.web.page.TableDataBody;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.api.domain.shop.dto.GridInfoDto;
import com.kx.system.api.domain.shop.dto.OrgGridInfoDto;
import com.kx.system.api.domain.shop.param.GridInfoParam;
import com.kx.system.api.domain.shop.param.OrgGridInfoParam;
import com.kx.system.api.factory.RemoteGridFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 网格服务
 *
 * @author kangxu
 */
@FeignClient(contextId = "remoteGridService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteGridFallbackFactory.class)
public interface RemoteGridService
{
    @PostMapping("/business/addrInfo/getBaseCoordinatesByCode")
    public TableDataBody<OrgGridInfoDto> getPaAddrInfoTypeByOrgCode(@RequestBody OrgGridInfoParam orgGridInfoParam, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping("/business/addrInfo/getGridInfo")
    public TableDataBody<GridInfoDto> getGridInfo(@RequestBody GridInfoParam gridInfoParam, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("/business/addrInfo/getGridInfoByCode")
    public GridInfoDto getGridInfoByCode(@RequestParam("gridCode") String gridCode, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
