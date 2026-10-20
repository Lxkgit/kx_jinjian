package com.kx.system.task;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.kx.system.api.domain.SysDept;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.SyncAddrBelongRelationVo;
import com.kx.system.mapper.PaAddrInfoTypeMapper;
import com.kx.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 杭州市网格围栏归属关系同步
 */
@Component
public class AddrBelongRelationSyncJob extends BaseJob {

    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;
    /**
     * 文件路径，后续从nfs获取
     */
    private static final String filePath = "D:/data/杭州市网格围栏归属关系20240417.xlsx";

//    @Value("${data-runtime.AddrBelongRelationSyncJob:-}")
    private String cron;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date date) {
        List<SyncAddrBelongRelationVo> syncAddrBelongRelationVos = new ArrayList<>();
        EasyExcel.read(filePath, SyncAddrBelongRelationVo.class, new AnalysisEventListener<SyncAddrBelongRelationVo>() {
            @Override
            public void invoke(SyncAddrBelongRelationVo syncAddrBelongRelationVo, AnalysisContext analysisContext) {
                syncAddrBelongRelationVos.add(syncAddrBelongRelationVo);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                handlesyncRelations(syncAddrBelongRelationVos);
            }
        }).sheet().doRead();
        System.out.println("finis");
    }

    private void handlesyncRelations(List<SyncAddrBelongRelationVo> vos) {
        if (CollectionUtils.isEmpty(vos)) {
            return;
        }
        List<SysDept> sysDepts = sysDeptMapper.selectDeptList(new SysDept());
        Map<String, Long> codeIdMap = sysDepts.stream()
                .collect(Collectors.toMap(SysDept::getOrgNum, SysDept::getDeptId));

        List<PaAddrInfoType> paAddrInfoTypeList = vos.stream().map(vo -> {
            PaAddrInfoType paAddrInfoType = new PaAddrInfoType();
            paAddrInfoType.setCode(vo.getTownCode());
            paAddrInfoType.setDeptCode(vo.getOrgId());
            paAddrInfoType.setDeptId(codeIdMap.get(vo.getOrgId()));
            paAddrInfoType.setManagerNum(vo.getManagerId());
            return paAddrInfoType;
        }).collect(Collectors.toList());

        for (PaAddrInfoType paAddrInfoType : paAddrInfoTypeList) {
            paAddrInfoTypeMapper.updateAddrInfoByCode(paAddrInfoType);
        }
    }

    @Override
    public String getJobDesc() {
        return "杭州市网格围栏归属关系同步";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
