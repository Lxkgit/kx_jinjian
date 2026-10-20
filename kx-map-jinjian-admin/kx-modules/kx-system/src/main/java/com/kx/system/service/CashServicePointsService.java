package com.kx.system.service;

import com.alibaba.fastjson2.JSONObject;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.CashServicePoints;
import com.kx.system.domain.dto.CashServicePointsDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CashServicePointsService {
    String modelDownload();
    JSONObject getCashServicePoints(CashServicePointsDTO cash);

    CashServicePoints getCashDetails(long id);

    List<CashServicePoints> getAnchorInfo(String gridCode);

    void cashAnchCode();

    String getCashServiceTotal(String gridCode);

    void anchCode();

    void importBatch(InputStream file);

    void exportBatch(HttpServletResponse response, CashServicePoints cashServicePoints) throws UnsupportedEncodingException;

    void exportBatchTest(CashServicePoints cashServicePoints) throws UnsupportedEncodingException;
}
