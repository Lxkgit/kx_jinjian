package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.util.GeocodingUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geo")
@Api(tags = "地图解析")
public class GeoController extends BaseController {
    @Autowired
    private GeocodingUtil geocodingUtil;

    @GetMapping("/geoCode")
    @ApiOperation("地址解析")
    public AjaxResult geoCode(String address){
        return success(geocodingUtil.geoCode(address));
    }

    @GetMapping("/reGeoCode")
    @ApiOperation("逆地址解析")
    public AjaxResult reGeoCode(String location){
        return success(geocodingUtil.reGeoCode(location));
    }
}
