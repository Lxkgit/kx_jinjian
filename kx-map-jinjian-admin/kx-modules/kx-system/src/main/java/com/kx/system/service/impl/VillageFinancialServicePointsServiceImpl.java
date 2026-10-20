package com.kx.system.service.impl;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.CashServicePointExport;
import com.kx.system.domain.VillageExport;
import com.kx.system.domain.VillageFinancialServicePoints;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.dto.VillageFinancialServicePointsQuery;
import com.kx.system.mapper.VillageFinancialServicePointsMapper;
import com.kx.system.service.IVillageFinancialServicePointsService;
import com.kx.system.task.VillageFinancialServicePointsSyncJob;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * 乡村金融服务点信息Service业务层处理
 *
 * @author zxy
 * @date 2024-11-08
 */
@Service
public class VillageFinancialServicePointsServiceImpl implements IVillageFinancialServicePointsService {
    @Resource
    private VillageFinancialServicePointsMapper villageFinancialServicePointsMapper;

    @Resource
    private VillageFinancialServicePointsSyncJob villageFinancialServicePointsSyncJob;
    @Override
    public List<VillageFinancialServicePoints> selectVillageFinancialServicePointsList(VillageFinancialServicePointsQuery query) {
        return villageFinancialServicePointsMapper.selectServiceMapper(query);
    }

    @Override
    public void villageFinancialAnchCode() {
        List<AnchorPoint> anchorPoints = villageFinancialServicePointsMapper.selectAll();
        villageFinancialServicePointsSyncJob.initAnchor(anchorPoints);

    }

    @Override
    public List<VillageFinancialServicePoints> getAnchorInfo(String gridCode) {
        return villageFinancialServicePointsMapper.getAnchorInfo(gridCode);
    }

    @Override
    public void exportBatch(HttpServletResponse response, VillageFinancialServicePointsQuery village) throws UnsupportedEncodingException {
        // 从数据库或其他数据源获取数据
        List<VillageExport> anchorPoints = villageFinancialServicePointsMapper.exportBatch(village);

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        // 为了解决中文乱码问题，使用URLEncoder编码文件名
        String fileName = URLEncoder.encode("金融服务站信息", "UTF-8") + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);

        // 使用EasyExcel写入数据到输出流
        try {
            EasyExcel.write(response.getOutputStream(), VillageExport.class)
                    .sheet("金融服务站信息")
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(anchorPoints);
        } catch (IOException e) {
            // 处理异常，例如记录日志
            e.printStackTrace();
        }
    }

    @Override
    public void exportBatchTest(VillageFinancialServicePointsQuery village) throws UnsupportedEncodingException {
        // 从数据库或其他数据源获取数据
        List<VillageExport> anchorPoints = villageFinancialServicePointsMapper.exportBatch(village);
        String fileName = URLEncoder.encode("金融服务站信息", "UTF-8") + ".xlsx";
        // 使用EasyExcel写入数据到输出流
        EasyExcel.write(fileName, VillageExport.class)
                .sheet("金融服务站信息")
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(anchorPoints);
    }
}
