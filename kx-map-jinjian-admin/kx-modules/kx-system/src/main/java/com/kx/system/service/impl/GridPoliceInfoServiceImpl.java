package com.kx.system.service.impl;

import com.kx.system.service.GridPoliceInfoService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

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
public class GridPoliceInfoServiceImpl implements GridPoliceInfoService {

//  @Autowired
//  private GridPoliceInfoMapper gridRatingInfoMapper;
//
//    @Override
//    public List<GridPoliceInfo> list(GridPoliceInfo gridRatingInfo) {
//        List<GridPoliceInfo> list = gridRatingInfoMapper.list(gridRatingInfo);
//        return list;
//    }
//
//    @Override
//    public GridPoliceInfo getById(Long id) {
//        return gridRatingInfoMapper.getById(id);
//    }
//
//    @Override
//    public GridPoliceInfo saveGridRatingInfo(GridPoliceInfo gridRatingInfo) {
//        GridPoliceInfo existGridRatingInfo=null;
//        if(gridRatingInfo.getId()!=null){
//            existGridRatingInfo=gridRatingInfoMapper.getById(gridRatingInfo.getId());
//        }
//        if(existGridRatingInfo ==null){
//            gridRatingInfo.setCreateTime(DateUtils.getNowDate());
//            gridRatingInfo.setUpdateTime(DateUtils.getNowDate());
//            gridRatingInfoMapper.insertGridRatingInfo(gridRatingInfo);
//        }else{
//            BeanUtils.copyBeanIgnoreNull(gridRatingInfo,existGridRatingInfo);
//            existGridRatingInfo.setUpdateTime(DateUtils.getNowDate());
//            gridRatingInfoMapper.updateGridRatingInfo(existGridRatingInfo);
//        }
//        return gridRatingInfo;
//    }
//
//    @Override
//    public String deleteByIds(String ids) {
//        if (StringUtils.isEmpty(ids)){
//            throw new BusinessValidationException("参数不能为空");
//        }
//        String[] idsArr=ids.split(",");
//        if (idsArr==null || idsArr.length==0){
//            throw new BusinessValidationException("参数不能为空");
//        }
//        for (int i = 0; i < idsArr.length; i++) {
//            deleteById(Long.valueOf(idsArr[i]));
//        }
//        return "ok";
//    }
//
//    public void deleteById(Long id) {
//        gridRatingInfoMapper.deleteById(id);
//    }

}
