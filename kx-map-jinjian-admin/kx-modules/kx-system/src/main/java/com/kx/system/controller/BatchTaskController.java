package com.kx.system.controller;

import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.param.ExecuteBatchTaskParam;
import com.kx.system.mapper.AnchorPointMapper;
import com.kx.system.mapper.PaAddrInfoTypeMapper;
import com.kx.system.task.AnchorGridCodeSyncJob;
import com.kx.system.task.BaseJob;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 跑批任务控制器
 */

@Slf4j
@Api(tags = "跑批任务控制器")
@RestController
@RequestMapping("/batchTask")
public class BatchTaskController {

    @Resource
    private AnchorPointMapper anchorPointMapper;

    @Resource
    private AnchorGridCodeSyncJob anchorGridCodeSyncJob;

    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;

    @GetMapping("/list")
    @ApiOperation("查询跑批任务")
    public AjaxResult listTasks() {
        return AjaxResult.success(BaseJob.getDetails());
    }

    @PostMapping("/executeTask")
    @ApiOperation("手动调用跑批任务")
    public AjaxResult executeTask(@RequestBody ExecuteBatchTaskParam param) {
        Map<String, BaseJob> register = BaseJob.getRegister();
        if (StringUtils.isEmpty(param.getJobName()) || !register.containsKey(param.getJobName())) {
            return AjaxResult.error("任务不存在");
        }
        register.get(param.getJobName()).executeTask(param.getDate());
        return AjaxResult.success("跑批完成");
    }

    @PostMapping("/syncAnchorGridCode")
    @ApiOperation("全量锚点落格（分页调用initAnchor）")
    public AjaxResult syncAnchorGridCode() {
        List<PaAddrInfoType> paAddrInfoTypes = paAddrInfoTypeMapper.selectAllCode();
        int pageSize = 100;
        int total = anchorPointMapper.selectAnchorTotalCount();
        int pages = (total + pageSize - 1) / pageSize;
        log.info("syncAnchorGridCode start, total={}, pages={}", total, pages);
        for (int i = 0; i < pages; i++) {
            List<AnchorPoint> list = anchorPointMapper.selectAnchorPageForSync(i * pageSize, pageSize);
            if (list.isEmpty()) {
                break;
            }
            anchorGridCodeSyncJob.initAnchor2("33",1,list,paAddrInfoTypes);
            log.info("syncAnchorGridCode progress: page={}/{}", i + 1, pages);
        }
        log.info("syncAnchorGridCode done, total={}", total);
        return AjaxResult.success("落格完成，共处理 " + total + " 条");
    }
}
