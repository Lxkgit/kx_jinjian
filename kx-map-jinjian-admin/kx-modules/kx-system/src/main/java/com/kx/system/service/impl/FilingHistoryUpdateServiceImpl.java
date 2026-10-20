package com.kx.system.service.impl;


import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.filing.FilingHistoryUpdate;
import com.kx.system.mapper.FilingHistoryUpdateMapper;
import com.kx.system.service.FilingHistoryUpdateService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.util.BeanUtils;
import com.kx.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-15
 */
@Slf4j
@Service
public class FilingHistoryUpdateServiceImpl implements FilingHistoryUpdateService {

  @Autowired
  private FilingHistoryUpdateMapper filingHistoryUpdateMapper;

    @Override
    public List<FilingHistoryUpdate> list(FilingHistoryUpdate filingHistoryUpdate) {
        List<FilingHistoryUpdate> list = filingHistoryUpdateMapper.list(filingHistoryUpdate);
        return list;
    }

    @Override
    public FilingHistoryUpdate getById(Long id) {
        return filingHistoryUpdateMapper.getById(id);
    }

    @Override
    public FilingHistoryUpdate saveFilingHistoryUpdate(FilingHistoryUpdate filingHistoryUpdate) {
        FilingHistoryUpdate existFilingHistoryUpdate=null;
        if(filingHistoryUpdate.getId()!=null){
            existFilingHistoryUpdate=filingHistoryUpdateMapper.getById(filingHistoryUpdate.getId());
        }
        if(existFilingHistoryUpdate ==null){
            filingHistoryUpdate.setCreateTime(DateUtils.getNowDate());
            filingHistoryUpdate.setUpdateTime(DateUtils.getNowDate());
            filingHistoryUpdateMapper.insertFilingHistoryUpdate(filingHistoryUpdate);
        }else{
            BeanUtils.copyBeanIgnoreNull(filingHistoryUpdate,existFilingHistoryUpdate);
            existFilingHistoryUpdate.setUpdateTime(DateUtils.getNowDate());
            filingHistoryUpdateMapper.updateFilingHistoryUpdate(existFilingHistoryUpdate);
        }
        return filingHistoryUpdate;
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
        filingHistoryUpdateMapper.deleteById(id);
    }

    @Override
    public FilingHistoryUpdate getByIdentityAndModelId(Long identity, Long modelId) {
        return  filingHistoryUpdateMapper.getByIdentityAndModelId(identity,modelId);
    }

}
