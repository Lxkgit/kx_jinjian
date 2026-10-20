package com.kx.system.service.impl;

import cn.hutool.json.JSONObject;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.api.domain.SysUser;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.*;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.grid.GridMessageInfo2;
import com.kx.system.mapper.*;
import com.kx.system.service.GridMessageInfoService;
import com.kx.system.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * <p>
 * 网格基础信息维护表 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Slf4j
@Service
public class GridMessageInfoServiceImpl implements GridMessageInfoService {

  @Autowired
  private GridMessageInfoMapper gridMessageInfoMapper;
  @Autowired
  private GridCruxInfoMapper gridCruxInfoMapper;
  @Autowired
  private GridRatingInfoMapper gridRatingInfoMapper;
  @Autowired
  private PaAddrInfoTypeMapper paAddrInfoTypeMapper;
  @Autowired
  private AnchorPointMapper anchorPointMapper;
  @Autowired
  private SysUserMapper sysUserMapper;
  @Autowired
  private SysDeptMapper sysDeptMapper;
  @Autowired
  private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Override
    public List<GridMessageInfo2> list(GridMessageInfo2 gridMessageInfo) {
        List<GridMessageInfo2> list = gridMessageInfoMapper.list(gridMessageInfo);
        list.forEach(this::fillDutyNetworkFromGridAssignment);
        return list;
    }

    @Override
    public GridMessageInfo2 getById(Long id) {
        return gridMessageInfoMapper.getById(id);
    }

    @Override
    public GridMessageInfo2 saveGridMessageInfo(GridMessageInfo2 gridMessageInfo) {

//        if (StringUtils.isEmpty(gridMessageInfo.getGridOverview())){
//            throw new BusinessValidationException("网格概况不允许为空");
//        }
//
//        if (StringUtils.isEmpty(gridMessageInfo.getPicture())){
//            throw new BusinessValidationException("网格图片不允许为空");
//        }
        if (StringUtils.isNotEmpty(gridMessageInfo.getGridOverview())&&gridMessageInfo.getGridOverview().length()>1000){
            throw new BusinessValidationException("网格概况不能超出1000个字");
        }
        if (StringUtils.isNotEmpty(gridMessageInfo.getGridRemark())&&gridMessageInfo.getGridRemark().length()>1000){
            throw new BusinessValidationException("网格备注不能超出1000个字");
        }
        GridMessageInfo2 existGridMessageInfo=null;
        if(gridMessageInfo.getId()!=null){
            existGridMessageInfo=gridMessageInfoMapper.getById(gridMessageInfo.getId());
        }
        if (gridMessageInfo.getGridCode()!=null){
            existGridMessageInfo = gridMessageInfoMapper.getByGridCode(gridMessageInfo.getGridCode());
        }
        if(existGridMessageInfo ==null){
            gridMessageInfo.setCreateTime(DateUtils.getNowDate());
            gridMessageInfo.setUpdateTime(DateUtils.getNowDate());
            gridMessageInfoMapper.insertGridMessageInfo(gridMessageInfo);
        }else{
            BeanUtils.copyBeanIgnoreNull(gridMessageInfo,existGridMessageInfo);
            existGridMessageInfo.setUpdateTime(DateUtils.getNowDate());
            gridMessageInfoMapper.updateGridMessageInfo(existGridMessageInfo);
        }
        //人行演示 同步更新网格长信息
        if (gridMessageInfo.getGridMainManagerName()!=null){
            PaAddrInfoType paAddrInfoType = new PaAddrInfoType();
            paAddrInfoType.setCode(gridMessageInfo.getGridCode());
            paAddrInfoType.setUserName(gridMessageInfo.getGridMainManagerName());
            paAddrInfoTypeMapper.updateAddrInfoByCode(paAddrInfoType);
        }
        return gridMessageInfo;
    }

    @Override
    public String deleteByIds(String ids) {
        if (StringUtils.isEmpty(ids)){
            throw new BusinessValidationException("参数不能为空");
        }
        String[] idsArr=ids.split(",");
        if (idsArr==null || idsArr.length==0){
            throw new BusinessValidationException("参数不能为空");
        }
        for (int i = 0; i < idsArr.length; i++) {
            deleteById(Long.valueOf(idsArr[i]));
        }
        return "ok";
    }

    public void deleteById(Long id) {
        gridMessageInfoMapper.deleteById(id);
    }

    @Override
    public GridMessageInfo2 getByGridCode(String gridCode) {
        GridMessageInfo2 messageInfo = gridMessageInfoMapper.getByGridCode(gridCode);
        if (messageInfo==null){
            PaAddrInfoType grid = paAddrInfoTypeMapper.getByGridCode(gridCode);
            if (grid == null) {
                return null;
            }
            messageInfo = new GridMessageInfo2();
            messageInfo.setGridCode(grid.getCode());
            messageInfo.setGridName(grid.getName());
            messageInfo.setRuralGridType(grid.getRuralGridType());
        }
        fillDutyNetworkFromGridAssignment(messageInfo);
        return messageInfo;
    }

    /**
     * 兼容历史责任网点分配数据：旧流程只在 pa_addr_info_type.user_id 中保存关联，
     * 没有同步 grid_message_info，导致网格列表和详情为空。
     */
    private void fillDutyNetworkFromGridAssignment(GridMessageInfo2 messageInfo) {
        if (messageInfo == null || StringUtils.isNotEmpty(messageInfo.getDutyNetworkName())
                || StringUtils.isEmpty(messageInfo.getGridCode())) {
            return;
        }
        PaAddrInfoType grid = paAddrInfoTypeMapper.getByGridCode(messageInfo.getGridCode());
        if (grid == null || grid.getUserId() == null) {
            return;
        }
        PerformanceCustomerInfo customer = performanceCustomerInfoMapper
                .selectPerformanceCustomerInfoById(grid.getUserId());
        if (customer == null || StringUtils.isEmpty(customer.getCustomerNm())) {
            return;
        }
        AnchorPoint anchorPoint = anchorPointMapper.selectByCustNm(customer.getCustomerNm(), "1");
        if (anchorPoint == null) {
            return;
        }
        messageInfo.setDutyNetworkName(anchorPoint.getAnchorName());
        messageInfo.setDutyNetworkCode(anchorPoint.getCustomerNm());
        messageInfo.setDutyNetworkAddress(anchorPoint.getAddressDesc());
        messageInfo.setBankAgencyName(anchorPoint.getBankAgencyName());
        messageInfo.setDutyName(anchorPoint.getDutyName());
        messageInfo.setDutyPhone(anchorPoint.getDutyPhone());
        messageInfo.setDutyTelephone(anchorPoint.getDutyTelephone());
    }


    @Transactional
    public void gridMessageSynchroPaAddrInfo(String code){
        List<PaAddrInfoType> paAddrInfoTypes = new ArrayList<>();
        if (StringUtils.isNotEmpty(code)) {
            paAddrInfoTypes = paAddrInfoTypeMapper.selectByCodeList(Arrays.asList(code));
        } else {
            paAddrInfoTypes= paAddrInfoTypeMapper.selectAllCode();
        }
        List<GridMessageInfo2> list = new ArrayList<>();
        for (PaAddrInfoType paAddrInfoType : paAddrInfoTypes) {
            GridMessageInfo2 gridMessageInfo = new GridMessageInfo2();
            gridMessageInfo.setGridCode(paAddrInfoType.getCode());
            gridMessageInfo.setGridName(paAddrInfoType.getName());
            gridMessageInfo.setRuralGridType(paAddrInfoType.getRuralGridType());
            /**
             * todo 查询网格人数
             * gridMessageInfo.setGridCustomerTotal("人数");
             */
            SysUser sysUserMain = sysUserMapper.selectUserById(paAddrInfoType.getUserId());
            if (sysUserMain!=null){
                gridMessageInfo.setGridMainManagerCode(sysUserMain.getManagerNum()==null?"":sysUserMain.getManagerNum());
                gridMessageInfo.setGridMainManagerName(sysUserMain.getNickName()==null?"":sysUserMain.getNickName());
            }

            List<String> secManagerNums = new ArrayList<>();
            List<String> secUSerName = new ArrayList<>();
            if (paAddrInfoType.getSecondUserId()!=null) {
                for (String secUserId : paAddrInfoType.getSecondUserId()) {
                    SysUser sysUserSide = sysUserMapper.selectUserById(new Long(secUserId));
                    if (sysUserSide != null) {
                        secManagerNums.add(sysUserSide.getManagerNum() == null ? "" : sysUserSide.getManagerNum());
                        secUSerName.add(sysUserSide.getNickName() == null ? "" : sysUserSide.getNickName());
                    }
                }
            }
                gridMessageInfo.setGridSideManagerCode(secManagerNums);
                gridMessageInfo.setGridSideManagerName(secUSerName);

            // if (paAddrInfoType.getDeptId()==null){
            //     continue;
            // }
            // SysDept sysDept = sysDeptMapper.selectDeptById(paAddrInfoType.getDeptId());
            // if (null != sysDept) {
            //     gridMessageInfo.setBelongTeam(sysDept.getDeptName());
            //branch 设置为 所属机构。
            //     gridMessageInfo.setBranch(sysDept.getDeptName());

//            gridMessageInfo.setFirstLevelBranch(sysDept.getDeptName());
//            SysDept sysDept1 = sysDeptMapper.selectDeptById(sysDept.getParentId());
//            if (sysDept1!=null){
//                gridMessageInfo.setSecondLevelBranch(sysDept1.getDeptName());
//                SysDept sysDept2 = sysDeptMapper.selectDeptById(sysDept1.getParentId());
//                if (sysDept2!=null){
//                    gridMessageInfo.setBranch(sysDept2.getDeptName());
//                }
//            }
//             }
            list.add(gridMessageInfo);
        }
        if (list == null) {
            throw new RuntimeException("插入错误");
        }
        for (GridMessageInfo2 messageInfo : list) {
            this.saveGridMessageInfo(messageInfo);
        }

    }

    @Override
    public List<AnchorPoint> getGridInfo(String gridCode) {
        return gridMessageInfoMapper.getGridInfo(gridCode);
    }

    @Override
    public List<PaAddrInfoType> getAllByGridCode(String gridCode) {
        return gridMessageInfoMapper.getAllByGridCode(gridCode);
    }

    @Override
    public void selectAllGrid(HttpServletResponse response, GridMessageInfo2 gridExport) throws UnsupportedEncodingException {
        List<ExportGrid> grids = gridMessageInfoMapper.selectAllGrids(gridExport);

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        // 为了解决中文乱码问题，使用URLEncoder编码文件名
        String fileName = URLEncoder.encode("网格信息导出", "UTF-8") + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);

        // 使用EasyExcel写入数据到输出流
        try {
            EasyExcel.write(response.getOutputStream(), ExportGrid.class)
                    .sheet("现金服务网点")
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(grids);
        } catch (IOException e) {
            // 处理异常，例如记录日志
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject pageLsit(GridMessageInfo2 gridMessageInfo) {
        JSONObject jsonObject = new JSONObject();

        // 1. 手动执行 COUNT 查询
        Long total = gridMessageInfoMapper.countList(gridMessageInfo);

        // 2. 分页设置
        PageHelper.startPage(gridMessageInfo.getPageNum(), gridMessageInfo.getPageSize());
        List<GridMessageInfo2> list = gridMessageInfoMapper.list(gridMessageInfo);

        // 3. 封装结果
        jsonObject.put("total", total);
        jsonObject.put("rows", list);
        jsonObject.put("code", 200);
        jsonObject.put("msg", "查询成功");
        return jsonObject;
    }

}
