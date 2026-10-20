package com.kx.system.service.impl;

import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.grid.GridCruxInfo;
import com.kx.system.mapper.GridCruxInfoMapper;
import com.kx.system.service.GridCruxInfoService;
import com.kx.system.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
/**
 * <p>
 * 网格关键信息维护表 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Slf4j
@Service
public class GridCruxInfoServiceImpl implements GridCruxInfoService {

  @Autowired
  private GridCruxInfoMapper gridCruxInfoMapper;

    @Override
    public List<GridCruxInfo> list(GridCruxInfo gridCruxInfo) {
        List<GridCruxInfo> list = gridCruxInfoMapper.list(gridCruxInfo);
        return list;
    }

    @Override
    public GridCruxInfo getById(Long id) {
        return gridCruxInfoMapper.getById(id);
    }

    @Override
    public GridCruxInfo saveGridCruxInfo(GridCruxInfo gridCruxInfo) {
        GridCruxInfo existGridCruxInfo=null;
        if(gridCruxInfo.getId()!=null){
            existGridCruxInfo=gridCruxInfoMapper.getById(gridCruxInfo.getId());
        }
        if(existGridCruxInfo ==null){
            gridCruxInfo.setCreateTime(DateUtils.getNowDate());
            gridCruxInfo.setUpdateTime(DateUtils.getNowDate());
            gridCruxInfoMapper.insertGridCruxInfo(gridCruxInfo);
        }else{
            BeanUtils.copyBeanIgnoreNull(gridCruxInfo,existGridCruxInfo);
            existGridCruxInfo.setUpdateTime(DateUtils.getNowDate());
            gridCruxInfoMapper.updateGridCruxInfo(existGridCruxInfo);
        }
        return gridCruxInfo;
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
        gridCruxInfoMapper.deleteById(id);
    }

}
