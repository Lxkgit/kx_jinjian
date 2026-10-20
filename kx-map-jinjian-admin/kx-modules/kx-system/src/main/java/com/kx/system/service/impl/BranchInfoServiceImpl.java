package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.kx.system.domain.branch.BranchArticleInfo;
import com.kx.system.domain.branch.BranchArticleRead;
import com.kx.system.domain.branch.BranchExcel;
import com.kx.system.domain.branch.BranchInfo;
import com.kx.system.domain.branch.vo.BranchArticleInfoVo;
import com.kx.system.domain.branch.vo.BranchInfoVo;
import com.kx.system.mapper.BranchArticleInfoMapper;
import com.kx.system.mapper.BranchArticleReadMapper;
import com.kx.system.mapper.BranchInfoMapper;
import com.kx.system.service.BranchInfoService;
import com.kx.system.util.excel.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Lenovo
 */
@Service
public class BranchInfoServiceImpl implements BranchInfoService {

    private static final Logger logger = LoggerFactory.getLogger(BranchInfoServiceImpl.class);

    @Resource
    private BranchInfoMapper branchInfoMapper;

    @Resource
    private BranchArticleInfoMapper branchArticleInfoMapper;

    @Resource
    private BranchArticleReadMapper branchArticleReadMapper;


    @Override
    public void getBranchStatistics() {

    }

    @Override
    public void getBranchStatisticsList() {

    }

    @Override
    public void exportBranchStatisticsList() {

    }

    @Override
    public List<BranchInfo> selectBranchList(BranchInfoVo branchInfoVo) {
        BranchInfo branchInfo = new BranchInfo();
        BeanUtil.copyProperties(branchInfoVo, branchInfo);
        return branchInfoMapper.selectBranchList(branchInfo);
    }

    @Override
    public void importBranchData(MultipartFile file) {
        try {
            List<BranchExcel> list = new ArrayList<>();
            // 读取数据并转换为Java对象
            EasyExcel.read(file.getInputStream(), BranchExcel.class, new PageReadListener<BranchExcel>(list::addAll))
                    .headRowNumber(6).sheet().doRead();

            List<BranchInfo> branchInfoList = new ArrayList<>();
            for (BranchExcel branchExcel : list) {
                BranchInfo branchInfo = new BranchInfo();
                BeanUtil.copyProperties(branchExcel, branchInfo);
                branchInfoList.add(branchInfo);
            }
            branchInfoMapper.insertBranchList(branchInfoList);

        } catch (Exception e) {

        }
    }

    @Override
    public void exportBranchData(HttpServletResponse response, BranchInfoVo branchInfoVo) {

        BranchInfo branchInfo = new BranchInfo();
        BeanUtil.copyProperties(branchInfoVo, branchInfo);
        List<BranchInfo> branchInfoList = branchInfoMapper.selectBranchList(branchInfo);

        List<BranchExcel> branchExcelList = new ArrayList<>();
        for (BranchInfo branchInfo1 : branchInfoList) {
            BranchExcel branchExcel = new BranchExcel();
            BeanUtil.copyProperties(branchInfo1, branchExcel);
            branchExcelList.add(branchExcel);
        }

        ExcelUtils<BranchExcel> utils = new ExcelUtils<>(BranchExcel.class);
        List<String> headList = new ArrayList<>();
        headList.add("导入说明：");
        headList.add("1.标红字段为必输项，其他字段可选填");
        headList.add("2.银行机构类型可点击下拉框");
        headList.add("3.网点功能编码：01人民币现金存取；02本外币现金存取；03小面额兑换支取；04数字人民币服务；05假币鉴定；06真伪授权鉴定；07特殊残损人民币兑换；08停止流通人民币兑换；09外币清分机鉴定；10纪念币承办网点服务；");
        utils.exportExcel(response, "网点数据.xlsx", headList, branchExcelList);
    }

    /**
     * 根据id查询网点基础信息与统计信息
     *
     * @param id
     * @return
     */
    @Override
    public BranchInfoVo selectBranchById(Long id) {
        BranchInfoVo branchInfoVo = new BranchInfoVo();
        BranchInfo branchInfo = branchInfoMapper.selectBranchById(id);
        branchInfoVo.setBranchInfo(branchInfo);
        return branchInfoVo;
    }

    /**
     * 更新网点基础信息与统计数据信息
     *
     * @param branchInfoVo
     */
    @Override
    public void updateBranch(BranchInfoVo branchInfoVo) {
        branchInfoMapper.updateBranchByOrgNum(branchInfoVo.getBranchInfo());
    }

    @Override
    public List<BranchArticleInfoVo> selectBranchArticleList(String orgNum) {
        List<BranchArticleInfo> branchArticleInfoList = branchArticleInfoMapper.selectArticleListByOrgNum(orgNum);


        List<Long> articleIds = branchArticleInfoList.stream().map(BranchArticleInfo::getId).collect(Collectors.toList());
        // todo 获取用户id
        Long userId = 0L;
        List<BranchArticleRead> branchArticleReadList = branchArticleReadMapper.selectArticleReadByArticleIdsAndUserId(articleIds, userId);
        Set<Long> readArticleIdSet = branchArticleReadList.stream().map(BranchArticleRead::getArticleId).collect(Collectors.toSet());
        return branchArticleInfoList.stream()
                .map(source -> {
                    BranchArticleInfoVo target = new BranchArticleInfoVo();
                    BeanUtils.copyProperties(source, target);
                    if (readArticleIdSet.contains(source.getId())) {
                        target.setReadStatus(1);
                    } else {
                        target.setReadStatus(0);
                    }
                    return target;
                }).collect(Collectors.toList());
    }

    @Override
    public BranchArticleInfo selectBranchArticleById(Long id) {
        BranchArticleInfo articleInfo = branchArticleInfoMapper.selectArticleInfoById(id);
        if (articleInfo != null) {
            BranchArticleRead branchArticleRead = new BranchArticleRead();
            branchArticleRead.setArticleId(articleInfo.getId());
            Long userId = 0L;
            branchArticleRead.setUserId(userId);

            branchArticleRead.setCreateTime(new Date());
//            branchArticleRead.setCreateName();
            branchArticleRead.setCreateBy(userId);
            branchArticleReadMapper.insertArticleRead(branchArticleRead);
        }

        return articleInfo;
    }

    @Override
    public void insertBranchArticle(BranchArticleInfo branchArticleInfo) {
        branchArticleInfoMapper.insertArticle(branchArticleInfo);
    }

    @Override
    public void updateBranchArticle(BranchArticleInfo branchArticleInfo) {
        branchArticleInfoMapper.updateArticleById(branchArticleInfo);
    }

    @Override
    public void deleteBranchArticleById(Long id) {
        branchArticleInfoMapper.deleteArticleById(id);
    }
}
