package com.kx.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.PageHelper;
import com.kx.common.core.exception.base.BaseException;
import com.kx.common.core.utils.ServletUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.security.service.TokenService;
import com.kx.system.api.model.LoginUser;
import com.kx.system.domain.PaCustomMessage;
import com.kx.system.domain.dto.PaCustomMessageDTO;
import com.kx.system.service.IPaCustomMessageService;
import com.kx.system.util.RequestRSAUtil;
import lombok.extern.slf4j.Slf4j;

import org.simpleframework.xml.core.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 消息Controller
 *
 * @author lc
 * @date 2022-06-01
 */
@Controller
@RequestMapping("/message")
@Slf4j
public class PaCustomMessageController extends BaseController {
    private String prefix = "kangxu/message";

    @Autowired
    private IPaCustomMessageService paCustomMessageService;

    @Autowired
    private TokenService tokenService;


    private  String aesKey = "JSGx9Jw9WLbfkKop";
    /**
     * 查询消息列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(PaCustomMessageDTO paCustomMessageDTO, HttpServletRequest request, String content) {
        String gridDev = request.getHeader("gridDev");
        JSONObject analysisResult = new JSONObject();
        if (StringUtils.isNotEmpty(gridDev) ){
            if (!"1".equals(gridDev)){
                log.error("请求报文错误");
                return new TableDataInfo();
            }
            analysisResult = RequestRSAUtil.requestAnalysisAES( content,aesKey);
            paCustomMessageDTO = JSONObject.parseObject(analysisResult.toJSONString(), PaCustomMessageDTO.class);
        }

        if (analysisResult.size()>0){
            PageHelper.startPage(Integer.parseInt(analysisResult.get("pageNum").toString()), Integer.parseInt(analysisResult.get("pageSize").toString()));
        }else {
            startPage();
        }

        List<PaCustomMessage> list = paCustomMessageService.selectPaCustomMessageList(paCustomMessageDTO);
        TableDataInfo dataTable = getDataTable(list);
        if (StringUtils.isNotEmpty(gridDev) && "1".equals(gridDev)){
            //加密
            JSONObject encryption = RequestRSAUtil.encryptionAES(JSONArray.parseArray(JSONArray.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm:ss")), aesKey);
            List<Map> mapList = new ArrayList<>();
            Map map2 = new HashMap<>();
            map2.put("content",encryption.getString("content"));
            mapList.add(map2);
            dataTable.setRows(mapList);
            return dataTable;
        }

        return dataTable;

    }

    /**
     * 查询消息统计
     */
    @GetMapping("/listMessageStatistics")
    @ResponseBody
    public AjaxResult listMessageStatistics(PaCustomMessageDTO paCustomMessageDTO) {
        return AjaxResult.success(paCustomMessageService.listMessageStatistics(paCustomMessageDTO));
    }

    /**
     * 新增保存消息
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody @Validate PaCustomMessage paCustomMessage) {
        return toAjax(paCustomMessageService.insertPaCustomMessage(paCustomMessage));
    }


    /**
     * 批量/修改星级
     */
    @GetMapping("/updateMessageStar")
    @ResponseBody
    public AjaxResult updateMessageStar(String ids, String messageStar) {
        //变更为支持批量处理
        String[] idList = ids.split(",");
        return toAjax(paCustomMessageService.batchMessageStar(idList,messageStar));
    }

    /**
     * 批量/修改消息状态
     */
    @GetMapping("/updateMessageState")
    @ResponseBody
    public AjaxResult updateMessageState(String ids, String messageState) {
        //变更为支持批量处理
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        if (loginUser == null) {
//            throw new BaseException("无法获取当前账号！");
//        }
        String[] idList = ids.split(",");
        //设置经办人 只有在修改消息状态的时候，才会改变经办人
//        return toAjax(paCustomMessageService.batchMessageState(loginUser.getUsername(),idList,messageState));
        return toAjax(paCustomMessageService.batchMessageState("1",idList,messageState));
    }


}
