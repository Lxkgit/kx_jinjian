package com.kx.system.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.anchor.Location;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;

/**
 * 百度地图工具类
 *
 * @author zhangwenxuan
 */
@Component
@RefreshScope
public class GeocodingUtil {
    /**
     * 地址解析路径
     */
    @Value("${baiduMap.geoCodeUrl}")
    private String url;

    /**
     * 逆地址解析路径
     */
    @Value("${baiduMap.reGeoCodeUrl}")
    private String reUrl;

    @Value("${baiduMap.key}")
    private String ak;

    @Value("${baiduMap.proxy.host:}")
    private String proxyHost;

    @Value("${baiduMap.proxy.port:}")
    private Integer proxyPort;

    private Proxy proxy;

    @PostConstruct
    protected void initProxy() {
        if (!StringUtils.isEmpty(proxyHost) && proxyPort != null) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        }
    }

    /**
     * 地址解析(结果需要转换为gps84)
     *
     * @param address
     * @return
     */
    public JSONObject geoCode(String address) {
        JSONObject object = new JSONObject();
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            // 构建请求URL
            String requestUrl = url + "/?address=" + encodedAddress + "&output=json&ak=" + ak;

            String jsonResponse = sengRequest(requestUrl);

            JSONObject jsonObject = JSON.parseObject(jsonResponse, JSONObject.class);
            if (jsonObject.getInteger("status").equals(0)) {
                object = JSON.parseObject(jsonObject.getString("result"), JSONObject.class);
                return object;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 逆地址解析
     *
     * @param location 纬度,经度
     * @return
     */
    public JSONObject reGeoCode(String location) {
        JSONObject object = new JSONObject();
        try {
            String[] point = location.split(",");
            //谷歌坐标系转为百度
            double[] bd = GPSUtil.gps84_To_bd09(Double.parseDouble(point[0]), Double.parseDouble(point[1]));
            location = bd[0] + "," + bd[1];
            String encodedLocation = URLEncoder.encode(location, "UTF-8");
            // 构建请求URL
            String requestUrl = reUrl + "/?location=" + encodedLocation + "&coordtype=bd09ll" + "&output=json&ak=" + ak;

            String jsonResponse = sengRequest(requestUrl);

            JSONObject jsonObject = JSON.parseObject(jsonResponse, JSONObject.class);
            if (jsonObject.getInteger("status").equals(0)) {
                object = JSON.parseObject(jsonObject.getString("result"), JSONObject.class);
            } else {
                throw new BusinessValidationException("逆解析失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 发送HTTP请求并获取响应字符串
     *
     * @param requestUrl
     * @return
     * @throws IOException
     */
    @NotNull
    private String sengRequest(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}

