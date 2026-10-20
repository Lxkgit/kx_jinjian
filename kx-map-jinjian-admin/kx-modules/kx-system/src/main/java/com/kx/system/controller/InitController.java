package com.kx.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.DeptLocationInfo;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.anchor.Location;
import com.kx.system.mapper.AnchorPointMapper;
import com.kx.system.mapper.DeptLocationInfoMapper;
import com.kx.system.task.*;
import com.kx.system.util.GPSUtil;
import com.kx.system.util.GeocodingUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//@RestController
//@RequestMapping("/init")
//@Api(tags = "数据初始化")
public class InitController {
//
//    @Resource
//    private CustomInfoSyncJob customInfoSyncJob;
//
//    @Resource
//    private OpenCardSyncJob openCardSyncJob;
//
//    @Resource
//    private CreditSyncJob creditSyncJob;
//
//    @Resource
//    private DeptLocationInfoMapper deptLocationInfoMapper;
//
//    @Resource
//    private AnchorPointMapper anchorPointMapper;
//
//    @Resource
//    private GeocodingUtil geocodingUtil;
//
//    @Resource
//    private AnchorChangeJob anchorChangeJob;
//
//    @Resource
//    private CustomerUpdateJob customerUpdateJob;
//
//    @GetMapping("/importCustomInfo")
//    @ApiOperation("客户数据跑批")
//    public String importCustomInfo(String date) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = sdf.parse(date);
//        customInfoSyncJob.executeTask(d);
//        return "跑批成功";
//    }
//
//    @GetMapping("/importAnchorInfo")
//    @ApiOperation("锚点数据跑批")
//    public String customGrid() {
//        List<AnchorPoint> anchorPoints = anchorPointMapper.selectAnchorPointByBelongGrid("330111001");
//        customInfoSyncJob.initAnchor(anchorPoints);
//        return "跑批成功";
//    }
//
//    @GetMapping("/importOpenCardInfo")
//    @ApiOperation("开卡数据跑批")
//    public String importOpenCardInfo(String date) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = sdf.parse(date);
//        openCardSyncJob.executeTask(d);
//        return "跑批成功";
//    }
//
//    @GetMapping("/importCreditRecordInfo")
//    @ApiOperation("信贷数据跑批")
//    public String importCreditRecordInfo(String date) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = sdf.parse(date);
//        creditSyncJob.executeTask(d);
//        return "跑批成功";
//    }
//
//    @GetMapping("/importFullCustom")
//    @ApiOperation("全量客户数据+锚点跑批")
//    public String importFullCustom(String date) throws IOException, ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = sdf.parse(date);
//        customInfoSyncJob.importFullCustom(d);
//        return "跑批成功";
//    }
//
//    @GetMapping("/importDeptLocation")
//    @ApiOperation("部门地址坐标解析入库")
//    public String importDeptLocation() {
//        List<DeptLocationInfo> deptLocationInfos = deptLocationInfoMapper.listAll();
//        for (DeptLocationInfo deptLocationInfo : deptLocationInfos) {
//            if (StringUtils.isNotEmpty(deptLocationInfo.getAddress())) {
//                JSONObject jsonObject = geocodingUtil.geoCode(deptLocationInfo.getAddress());
//                Location location = JSON.parseObject(jsonObject.getString("location"), Location.class);
//                if (location != null) {
//                    double[] locationArr = GPSUtil.bd09_To_gps84(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLng()));
//                    deptLocationInfo.setPoint(locationArr[1] + "," + locationArr[0]);
//                    deptLocationInfoMapper.update(deptLocationInfo);
//                }
//            }
//        }
//        return "跑批成功";
//    }
//
//    @GetMapping("/updateGridAndanchor")
//    @ApiOperation("网格变更后锚点变更")
//    public String updateGridAndAnchor(String date) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = sdf.parse(date);
//        anchorChangeJob.executeTask(d);
//        return "锚点更新成功";
//    }
//
//    @GetMapping("/updateCustomer")
//    @ApiOperation("绩效授信客户信息更新")
//    public String updateCustomer(String date) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = sdf.parse(date);
//        customerUpdateJob.executeTask(d);
//        return "客户信息更新成功";
//    }
}
