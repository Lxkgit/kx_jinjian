package com.kx.system.service.impl.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.es.*;
import com.kx.system.domain.req.GroupManageReq;
import com.kx.system.mapper.AnchorPointMapper;
import com.kx.system.mapper.CreditCustomerInfoMapper;
import com.kx.system.mapper.FilingCustomerInfoMapper;
import com.kx.system.mapper.PerformanceCustomerInfoMapper;
import com.kx.system.service.es.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author  ligen
 */
@Slf4j
@Service
public class EsCustMemberServiceImpl implements EsCustMemberService {

    @Resource
    private EsAnchorPointGroupService esAnchorPointGroupService;
    @Resource
    private EsPerCustGroupMemberService esPerCustGroupMemberService;
    @Resource
    private EsCreditCustMemberService esCreditCustMemberService;
    @Resource
    private EsFilingCustMemberService esFilingCustMemberService;
    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;
    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;
    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;

    @Resource
    private AnchorPointMapper anchorPointMapper;

    @Override
    public void removeMember(GroupManageReq req) {

        //删除成员信息
        if (req.getCustomType().compareTo(new Integer(1)) == 0){
            esPerCustGroupMemberService.remove(req);
        }else if (req.getCustomType().compareTo(new Integer(2)) == 0){
            esCreditCustMemberService.remove(req);
        }else if (req.getCustomType().compareTo(new Integer(3)) == 0){
            esFilingCustMemberService.remove(req);
        }
        //删除锚点信息
        Long remove = esAnchorPointGroupService.remove(req);
        if (remove.longValue()<=0){
            log.error("ES 锚点客群关联信息删除失败 客群ID ： {}",req.getGroupId());
        }
    }

    @Override
    @Async
    public void insertBatchAsync(int total,Integer type,Long groupId) {
        //查询客群成员信息
        Integer page = (total+1000-1)/1000;
        for (int i = 0; i < page; i++) {
            int index = i*1000;
            int endIndex = index+1000;
            if (endIndex>total){
                endIndex = total;
            }

            if (type.compareTo(new Integer(1)) == 0){
                List<EsPerMemberDto> esPerMemberDtos = performanceCustomerInfoMapper.selectEsDataByGroupId(groupId, index, endIndex);
                List<JSONObject> jsonObjects = JSONArray.parseArray(JSONObject.toJSONString(esPerMemberDtos), JSONObject.class);
                esPerCustGroupMemberService.insertBatch(jsonObjects);
            }else if (type.compareTo(new Integer(2)) == 0){
                List<EsCreditMemberDto> esCreditMemberDtos = creditCustomerInfoMapper.selectEsDataByGroupId(groupId, index, endIndex);
                List<JSONObject> jsonObjects = JSONArray.parseArray(JSONObject.toJSONString(esCreditMemberDtos), JSONObject.class);
                esCreditCustMemberService.insertBatch(jsonObjects);
            }else if (type.compareTo(new Integer(3)) == 0){
                List<EsFilingMemberDto> esFilingMemberDtos = filingCustomerInfoMapper.selectEsDataByGroupId(groupId, index, endIndex);
                List<JSONObject> jsonObjects = JSONArray.parseArray(JSONObject.toJSONString(esFilingMemberDtos), JSONObject.class);
                esFilingCustMemberService.insertBatch(jsonObjects);
            }
            //新增锚点通过Es查询
            List<EsAnchorPointGroupDto> esAnchorPointDtos = anchorPointMapper.selectEsDataByGroupId(groupId,type, index, endIndex);
            List<JSONObject> jsonObjects = JSONArray.parseArray(JSONObject.toJSONString(esAnchorPointDtos), JSONObject.class);
            esAnchorPointGroupService.insertBatch(jsonObjects);
        }

    }






}
