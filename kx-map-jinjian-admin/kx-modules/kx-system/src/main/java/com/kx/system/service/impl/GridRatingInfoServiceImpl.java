package com.kx.system.service.impl;

import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.grid.GridRatingInfo;
import com.kx.system.mapper.GridRatingInfoMapper;
import com.kx.system.service.GridRatingInfoService;
import com.kx.system.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
/**
 * <p>
 * 网格评级信息维护表 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Slf4j
@Service
public class GridRatingInfoServiceImpl implements GridRatingInfoService {

  @Autowired
  private GridRatingInfoMapper gridRatingInfoMapper;

    @Override
    public List<GridRatingInfo> list(GridRatingInfo gridRatingInfo) {
        List<GridRatingInfo> list = gridRatingInfoMapper.list(gridRatingInfo);
        return list;
    }

    @Override
    public GridRatingInfo getById(Long id) {
        return gridRatingInfoMapper.getById(id);
    }

    @Override
    public GridRatingInfo saveGridRatingInfo(GridRatingInfo gridRatingInfo) {
        GridRatingInfo existGridRatingInfo=null;
        if(gridRatingInfo.getId()!=null){
            existGridRatingInfo=gridRatingInfoMapper.getById(gridRatingInfo.getId());
        }
        if(existGridRatingInfo ==null){
            gridRatingInfo.setCreateTime(DateUtils.getNowDate());
            gridRatingInfo.setUpdateTime(DateUtils.getNowDate());
            gridRatingInfoMapper.insertGridRatingInfo(gridRatingInfo);
        }else{
            BeanUtils.copyBeanIgnoreNull(gridRatingInfo,existGridRatingInfo);
            existGridRatingInfo.setUpdateTime(DateUtils.getNowDate());
            gridRatingInfoMapper.updateGridRatingInfo(existGridRatingInfo);
        }
        return gridRatingInfo;
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
        gridRatingInfoMapper.deleteById(id);
    }

}
