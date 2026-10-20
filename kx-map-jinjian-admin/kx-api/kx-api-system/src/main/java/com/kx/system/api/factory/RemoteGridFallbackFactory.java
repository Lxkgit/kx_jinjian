package com.kx.system.api.factory;

import com.kx.common.core.domain.R;
import com.kx.common.core.web.page.TableDataBody;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.api.RemoteGridService;
import com.kx.system.api.domain.shop.dto.GridInfoDto;
import com.kx.system.api.domain.shop.dto.OrgGridInfoDto;
import com.kx.system.api.domain.shop.param.GridInfoParam;
import com.kx.system.api.domain.shop.param.OrgGridInfoParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 用户服务降级处理
 *
 * @author kangxu
 */
@Component
public class RemoteGridFallbackFactory implements FallbackFactory<RemoteGridService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteGridFallbackFactory.class);

    @Override
    public RemoteGridService create(Throwable throwable) {
        log.error("网格服务调用失败:{}", throwable.getMessage());
        return new RemoteGridService() {
            @Override
            public TableDataBody<OrgGridInfoDto> getPaAddrInfoTypeByOrgCode(OrgGridInfoParam orgGridInfoParam, String source) {
                TableDataBody tableDataBody = new TableDataBody();
                tableDataBody.setCode(500);
                tableDataBody.setMsg("获取orgCode下的网格信息失败:" + throwable.getMessage());
                return tableDataBody;
            }

            @Override
            public TableDataBody<GridInfoDto> getGridInfo(GridInfoParam gridInfoParam, String source) {
                TableDataBody tableDataBody = new TableDataBody();
                tableDataBody.setCode(500);
                tableDataBody.setMsg("获取网格基本信息失败:" + throwable.getMessage());
                return tableDataBody;
            }

            @Override
            public GridInfoDto getGridInfoByCode(String gridCode, String source) {
                return null;
            }
        };
    }
}
