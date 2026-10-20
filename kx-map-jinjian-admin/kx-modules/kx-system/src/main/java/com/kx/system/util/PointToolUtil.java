package com.kx.system.util;

import com.alibaba.fastjson.JSONArray;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.util.vgrid.GridUtil;

import java.util.List;


public class PointToolUtil {

    /**
     * 给定 [ (经度1,纬度1) ,(经度2,纬度2),  (经度3,纬度3) 。。。]边界集合，获取边界中心点
     * @param locationList　边界集合
     * @return  经度,纬度
     */
    public static String getCenterPoint(List<String> locationList) {
        //lon处于偶数位，lat处于奇数位,坐标全为逗号分隔, 所以点的数量需要除2
        int locationNum = locationList.size() / 2;
        double X = 0, Y = 0, Z = 0;
        for (int i = 0; i < locationNum; i++) {
            double lat, lon, x, y, z;
            lon = Double.parseDouble(locationList.get(i * 2)) * Math.PI / 180;
            lat = Double.parseDouble(locationList.get(i * 2 + 1)) * Math.PI / 180;
            x = Math.cos(lat) * Math.cos(lon);
            y = Math.cos(lat) * Math.sin(lon);
            z = Math.sin(lat);
            X += x;
            Y += y;
            Z += z;
        }
        X = X / locationNum;
        Y = Y / locationNum;
        Z = Z / locationNum;
        double Lon = Math.atan2(Y, X);
        double Hyp = Math.sqrt(X * X + Y * Y);
        double Lat = Math.atan2(Z, Hyp);
        StringBuffer sb = new StringBuffer();
        sb.append(Lon * 180 / Math.PI).append(",").append(Lat * 180 / Math.PI);
        return sb.toString();
    }


    /**
     * 校验经纬度是否在范围内
     * @param boundary 边界数据
     * @param poiLatitude 维度
     * @param poiLongitude 经度
     * @return  true/false
     */
    public static boolean checkLot(String boundary, String poiLatitude, String poiLongitude) {
        if (StringUtils.isEmpty(boundary) || StringUtils.isEmpty(poiLatitude) || StringUtils.isEmpty(poiLongitude)) {
            return false;
        }
        JSONArray boundaryArr = JSONArray.parseArray(boundary);
        Boolean flag =false;
        for (int i = 0; i < boundaryArr.getJSONArray(0).size(); i++) {
            JSONArray last = boundaryArr.getJSONArray(0).getJSONArray(i);
            StringBuilder jsonstr = new StringBuilder("[");
            if (StringUtils.isNotEmpty(last)) {
                for (int j = 0; j < last.size(); j++) {
                    JSONArray lot = last.getJSONArray(j);
                    jsonstr.append(lot.get(0)).append(",").append(lot.get(1)).append(",");
                }
                if (jsonstr.length() > 1) {
                    jsonstr = new StringBuilder(jsonstr.substring(0, jsonstr.length() - 1));
                    jsonstr.append("]");
                }
                if (jsonstr.length() == 1 || jsonstr.length() == 2) {
                    continue;
                }
            }
            flag = GridUtil.isPtInPoly(Double.valueOf(poiLongitude), Double.valueOf(poiLatitude), jsonstr.toString());
            if (flag)break;
        }
        return flag;
    }



}
