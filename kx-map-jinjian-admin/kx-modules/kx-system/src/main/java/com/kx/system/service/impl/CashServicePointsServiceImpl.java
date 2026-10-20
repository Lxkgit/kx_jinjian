package com.kx.system.service.impl;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.security.service.TokenService;
import com.kx.system.domain.CashServicePointExport;
import com.kx.system.domain.dto.CashServicePointsExcelDTO;
import com.kx.system.domain.vo.CashServicePointsVo;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.CashServicePoints;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.dto.CashServicePointsDTO;
import com.kx.system.listener.CashServicePointsDataListener;
import com.kx.system.mapper.CashServicePointsMapper;
import com.kx.system.mapper.GridMessageInfoMapper;
import com.kx.system.service.CashServicePointsService;
import com.kx.system.task.AnchorGridCodeSyncJob;
import com.kx.system.task.CashServicePointsSyncJob;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lenovo
 */
@Service
public class CashServicePointsServiceImpl implements CashServicePointsService {

    @Autowired
    private TokenService tokenService;

    @Override
    public String modelDownload() {
        String fileName = "现金服务点模板.xlsx";
        EasyExcel.write(ExcelUtil.getAbsoluteFile(fileName), CashServicePointsVo.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("现金服务点")
                .doWrite(Collections.singleton(CashServicePointsVo.class));
        return fileName;
    }

    @Autowired
    private CashServicePointsMapper cashServiceMapper;

    @Autowired
    private CashServicePointsSyncJob syncJob;

    @Autowired
    private AnchorGridCodeSyncJob anchorJob;

    /**
     * 查看现金服务
     *
     * @param cash
     * @return
     */
    @Override
    public JSONObject getCashServicePoints(CashServicePointsDTO cash) {
        JSONObject result = new JSONObject();
        Page<CashServicePoints> objects = PageHelper.startPage(cash.getPageNum(), cash.getPageSize()).doSelectPage(
                () -> cashServiceMapper.selectServiceMapper(cash)
        );
        // List<CashServicePoints> cashServicePoints = cashServiceMapper.selectServiceMapper(cash);
        result.put("row", objects.getResult());
        result.put("total", objects.getTotal());
        return result;
    }

    @Override
    public CashServicePoints getCashDetails(long id) {
        return cashServiceMapper.selectCashDetails(id);
    }

    @Override
    public List<CashServicePoints> getAnchorInfo(String gridCode) {
        return cashServiceMapper.getAnchorInfo(gridCode);
    }

    @Override
    public void cashAnchCode() {
        List<AnchorPoint> anchorPoints = cashServiceMapper.selectAll();
        syncJob.initAnchor(anchorPoints);
    }

    @Override
    public String getCashServiceTotal(String gridCode) {
        return cashServiceMapper.selectCashServiceTotal(gridCode);
    }

    @Override
    public void anchCode() {
        List<AnchorPoint> anchorPoints = cashServiceMapper.selectAnchorPointByAreaCodes();
        anchorJob.initAnchor(anchorPoints);
    }

    @Override
    public void importBatch(InputStream file) {
        try {
            CashServicePointsDataListener cashServicePointsDataListener = new CashServicePointsDataListener(cashServiceMapper);
            EasyExcel.read(file, CashServicePointsExcelDTO.class, cashServicePointsDataListener).sheet().doRead();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void exportBatch(HttpServletResponse response, CashServicePoints cashServicePoints) throws UnsupportedEncodingException {
        // 从数据库或其他数据源获取数据
        List<CashServicePointExport> anchorPoints = cashServiceMapper.exportBatch(cashServicePoints);

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        // 为了解决中文乱码问题，使用URLEncoder编码文件名
        String fileName = URLEncoder.encode("现金服务网点信息", "UTF-8") + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);

        // 使用EasyExcel写入数据到输出流
        try {
            EasyExcel.write(response.getOutputStream(), CashServicePointExport.class)
                    .sheet("现金服务网点")
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(anchorPoints);
        } catch (IOException e) {
            // 处理异常，例如记录日志
            e.printStackTrace();
        }
    }

    @Override
    public void exportBatchTest(CashServicePoints cashServicePoints) throws UnsupportedEncodingException {
        // 从数据库或其他数据源获取数据
        List<CashServicePointExport> anchorPoints = cashServiceMapper.exportBatch(cashServicePoints);

        String fileName = URLEncoder.encode("现金服务网点信息", "UTF-8") + ".xlsx";
        // 使用EasyExcel写入数据到输出流
        EasyExcel.write(fileName,CashServicePointExport.class)
                .sheet("现金服务网点")
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(anchorPoints);
    }


}

