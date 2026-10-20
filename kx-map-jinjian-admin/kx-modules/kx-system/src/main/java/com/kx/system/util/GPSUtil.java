package com.kx.system.util;


import com.kx.system.domain.anchor.Coordinate;

public class GPSUtil {
    public static double pi = 3.1415926535897932384626;
    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;

    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
                * pi)) * 2.0 / 3.0;
        return ret;
    }
    public static double[] transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat,mgLon};
    }
    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }



    public static Coordinate gps84_To_Gcj02(Coordinate coordinate) {
        double[] doubles = gps84_To_Gcj02(coordinate.getLat(), coordinate.getLon());
        coordinate.setLat(doubles[0]);
        coordinate.setLat(doubles[1]);
        return coordinate;
    }

    public static Coordinate gcj02_To_Gps84(Coordinate coordinate) {
        double[] doubles = gcj02_To_Gps84(coordinate.getLat(), coordinate.getLon());
        coordinate.setLat(doubles[0]);
        coordinate.setLat(doubles[1]);
        return coordinate;
    }
    public static Coordinate gcj02_To_Bd09(Coordinate coordinate) {
        double[] doubles = gcj02_To_Bd09(coordinate.getLat(), coordinate.getLon());
        coordinate.setLat(doubles[0]);
        coordinate.setLat(doubles[1]);
        return coordinate;
    }
    public static Coordinate bd09_To_Gcj02(Coordinate coordinate) {
        double[] doubles = bd09_To_Gcj02(coordinate.getLat(), coordinate.getLon());
        coordinate.setLat(doubles[0]);
        coordinate.setLat(doubles[1]);
        return coordinate;
    }

    public static Coordinate gps84_To_bd09(Coordinate coordinate) {
        double[] doubles = gps84_To_bd09(coordinate.getLat(), coordinate.getLon());
        coordinate.setLat(doubles[0]);
        coordinate.setLat(doubles[1]);
        return coordinate;
    }
    public static Coordinate bd09_To_gps84(Coordinate coordinate) {
        double[] doubles = bd09_To_gps84(coordinate.getLat(), coordinate.getLon());
        coordinate.setLat(doubles[0]);
        coordinate.setLat(doubles[1]);
        return coordinate;
    }



    /**
     * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    /**
     * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
     * */
    public static double[] gcj02_To_Gps84(double lat, double lon) {
        double[] gps = transform(lat, lon);
        double lontitude = lon * 2 - gps[1];
        double latitude = lat * 2 - gps[0];
        return new double[]{latitude, lontitude};
    }
    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param lat
     * @param lon
     */
    public static double[] gcj02_To_Bd09(double lat, double lon) {
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        double[] gps = {tempLat,tempLon};
        return gps;
    }

    /**
     * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
     * bd_lat * @param bd_lon * @return
     */
    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat,tempLon};
        return gps;
    }

    /**将gps84转为bd09
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_bd09(double lat,double lon){
        double[] gcj02 = gps84_To_Gcj02(lat,lon);
        double[] bd09 = gcj02_To_Bd09(gcj02[0],gcj02[1]);
        return bd09;
    }
    public static double[] bd09_To_gps84(double lat,double lon){
        double[] gcj02 = bd09_To_Gcj02(lat, lon);
        double[] gps84 = gcj02_To_Gps84(gcj02[0], gcj02[1]);
        //保留小数点后六位
        gps84[0] = retain6(gps84[0]);
        gps84[1] = retain6(gps84[1]);
        return gps84;
    }

    /**保留小数点后六位
     * @param num
     * @return
     */
    private static double retain6(double num){
        String result = String .format("%.6f", num);
        return Double.parseDouble(result);
    }


    public static String covertCoordPointTo(Coordinate coordinate, int to){

        if (to == 1){
            return covertCoordPointToLocation(GPSUtil.gcj02_To_Gps84(coordinate));
        }
        if (to == 2){
            return covertCoordPointToLocation(coordinate);
        }
        else{
            return covertCoordPointToLocation(GPSUtil.gcj02_To_Bd09(coordinate));
        }

    }


    public static String covertCoordPointFrom(Coordinate coordinate, int from){

        if (from == 1){
            return covertCoordPointToLocation(GPSUtil.gps84_To_Gcj02(coordinate));
        }
        if (from == 2){
            return covertCoordPointToLocation(coordinate);
        }
        else{
            return covertCoordPointToLocation(GPSUtil.bd09_To_Gcj02(coordinate));
        }

    }

    public static Coordinate covertCoordLocationToPoint(String location){
        double lon = Double.parseDouble(location.split(",")[0]);
        double lat = Double.parseDouble(location.split(",")[1]);
        return new Coordinate(lon,lat);
    }

    public static String covertCoordPointToLocation(Coordinate coordinate){

        return String .format("%.6f", coordinate.getLon()) + "," +String .format("%.6f", coordinate.getLat());
    }




//    public static void main(String[] args) {
//        gcj02_To_Gps84()
//    }


}
