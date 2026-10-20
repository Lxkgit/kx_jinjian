var GPSHelper = {
  PI: 3.1415926535897932384626,
  x_pi: 3.1415926535897932384626, //* 3000.0 / 180.0, //转换BD09时偏移
  delta: function (lat, lon) {
      // Krasovsky 1940
      //
      // a = 6378245.0, 1/f = 298.3
      // b = a * (1 - f)
      // ee = (a^2 - b^2) / a^2;
      var a = 6378245.0; //  a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
      var ee = 0.00669342162296594323; //  ee: 椭球的偏心率。
      var dLat = this.transformLat(lon - 105.0, lat - 35.0);
      var dLon = this.transformLon(lon - 105.0, lat - 35.0);
      var radLat = lat / 180.0 * this.PI;
      var magic = Math.sin(radLat);
      magic = 1 - ee * magic * magic;
      var sqrtMagic = Math.sqrt(magic);
      dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * this.PI);
      dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * this.PI);
      return { 'lat': dLat, 'lon': dLon };
  },

  //WGS-84 to GCJ-02
  gcj_encrypt: function (wgsLat, wgsLon) {
      if (this.outOfChina(wgsLat, wgsLon))
          return { 'lat': wgsLat, 'lon': wgsLon };

      var d = this.delta(wgsLat, wgsLon);
      return { 'lat': wgsLat + d.lat, 'lon': wgsLon + d.lon };
  },
  //GCJ-02 to WGS-84
  gcj_decrypt: function (gcjLat, gcjLon) {
      if (this.outOfChina(gcjLat, gcjLon))
          return { 'lat': gcjLat, 'lon': gcjLon };

      var d = this.delta(gcjLat, gcjLon);
      return { 'lat': gcjLat - d.lat, 'lon': gcjLon - d.lon };
  },
  //GCJ-02 to WGS-84 exactly
  gcj_decrypt_exact: function (gcjLat, gcjLon) {
      var initDelta = 0.01;
      var threshold = 0.000000001;
      var dLat = initDelta, dLon = initDelta;
      var mLat = gcjLat - dLat, mLon = gcjLon - dLon;
      var pLat = gcjLat + dLat, pLon = gcjLon + dLon;
      var wgsLat, wgsLon, i = 0;
      let flag = true
      while (flag) {
          wgsLat = (mLat + pLat) / 2;
          wgsLon = (mLon + pLon) / 2;
          var tmp = this.gcj_encrypt(wgsLat, wgsLon)
          dLat = tmp.lat - gcjLat;
          dLon = tmp.lon - gcjLon;
          if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
              break;

          if (dLat > 0) pLat = wgsLat; else mLat = wgsLat;
          if (dLon > 0) pLon = wgsLon; else mLon = wgsLon;

          if (++i > 10000) break;
      }
      //console.log(i);
      return { 'lat': wgsLat, 'lon': wgsLon };
  },
  //GCJ-02 to BD-09
  bd_encrypt: function (gcjLat, gcjLon) {
      var x = gcjLon, y = gcjLat;
      var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * this.x_pi);
      var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * this.x_pi);
      let bdLon = z * Math.cos(theta) + 0.0065;
      let bdLat = z * Math.sin(theta) + 0.006;
      return { 'lat': bdLat, 'lon': bdLon };
  },
  //BD-09 to GCJ-02
  bd_decrypt: function (bdLat, bdLon) {
      var x = bdLon - 0.0065, y = bdLat - 0.006;
      var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * this.x_pi);
      var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * this.x_pi);
      var gcjLon = z * Math.cos(theta);
      var gcjLat = z * Math.sin(theta);
      return { 'lat': gcjLat, 'lon': gcjLon };
  },
  //WGS-84 to Web mercator
  //mercatorLat -> y mercatorLon -> x
  mercator_encrypt: function (wgsLat, wgsLon) {
      var x = wgsLon * 20037508.34 / 180.;
      var y = Math.log(Math.tan((90. + wgsLat) * this.PI / 360.)) / (this.PI / 180.);
      y = y * 20037508.34 / 180.;
      return { 'lat': y, 'lon': x };
      /*
       if ((Math.abs(wgsLon) > 180 || Math.abs(wgsLat) > 90))
       return null;
       var x = 6378137.0 * wgsLon * 0.017453292519943295;
       var a = wgsLat * 0.017453292519943295;
       var y = 3189068.5 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a)));
       return {'lat' : y, 'lon' : x};
       //*/
  },
  // Web mercator to WGS-84
  // mercatorLat -> y mercatorLon -> x
  mercator_decrypt: function (mercatorLat, mercatorLon) {
      var x = mercatorLon / 20037508.34 * 180.;
      var y = mercatorLat / 20037508.34 * 180.;
      y = 180 / this.PI * (2 * Math.atan(Math.exp(y * this.PI / 180.)) - this.PI / 2);
      return { 'lat': y, 'lon': x };
      /*
       if (Math.abs(mercatorLon) < 180 && Math.abs(mercatorLat) < 90)
       return null;
       if ((Math.abs(mercatorLon) > 20037508.3427892) || (Math.abs(mercatorLat) > 20037508.3427892))
       return null;
       var a = mercatorLon / 6378137.0 * 57.295779513082323;
       var x = a - (Math.floor(((a + 180.0) / 360.0)) * 360.0);
       var y = (1.5707963267948966 - (2.0 * Math.atan(Math.exp((-1.0 * mercatorLat) / 6378137.0)))) * 57.295779513082323;
       return {'lat' : y, 'lon' : x};
       //*/
  },
     //BD-09 to wgs-84
     bd_to_wgs: function ([lon,lat]) {
        var x = lon - 0.0065, y = lat - 0.006;
        var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * this.x_pi);
        var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * this.x_pi);
        var gcjLon = z * Math.cos(theta);
        var gcjLat = z * Math.sin(theta);
        var res = this.gcj_decrypt_exact(gcjLat,gcjLon)
        // var d = this.delta(gcjLat, gcjLon);
        // return [gcjLon - d.lon,gcjLat - d.lat] 
        return [res.lon,res.lat] 
    },
    //gcj to bd
    wgs_to_bd: function ([lon,lat]){
        var d = this.delta(lat, lon);
        var x = lon + d.lon ;
        var y = lat + d.lat;
        var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * this.x_pi);
        var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * this.x_pi);
        let bdLon = z * Math.cos(theta) + 0.0065;
        let bdLat = z * Math.sin(theta) + 0.006;
        return [bdLon,bdLat]
    },

  // two point's distance
  distance: function (latA, lonA, latB, lonB) {
      var earthR = 6371000.;
      var x = Math.cos(latA * this.PI / 180.) * Math.cos(latB * this.PI / 180.) * Math.cos((lonA - lonB) * this.PI / 180);
      var y = Math.sin(latA * this.PI / 180.) * Math.sin(latB * this.PI / 180.);
      var s = x + y;
      if (s > 1) s = 1;
      if (s < -1) s = -1;
      var alpha = Math.acos(s);
      var distance = alpha * earthR;
      return distance;
  },
  outOfChina: function (lat, lon) {
      if (lon < 72.004 || lon > 137.8347)
          return true;
      if (lat < 0.8293 || lat > 55.8271)
          return true;
      return false;
  },
  transformLat: function (x, y) {
      var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
      ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
      ret += (20.0 * Math.sin(y * this.PI) + 40.0 * Math.sin(y / 3.0 * this.PI)) * 2.0 / 3.0;
      ret += (160.0 * Math.sin(y / 12.0 * this.PI) + 320 * Math.sin(y * this.PI / 30.0)) * 2.0 / 3.0;
      return ret;
  },
  transformLon: function (x, y) {
      var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
      ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
      ret += (20.0 * Math.sin(x * this.PI) + 40.0 * Math.sin(x / 3.0 * this.PI)) * 2.0 / 3.0;
      ret += (150.0 * Math.sin(x / 12.0 * this.PI) + 300.0 * Math.sin(x / 30.0 * this.PI)) * 2.0 / 3.0;
      return ret;
  },
  //地心 转 WGS84
  dx_wgs: function (value) {
      var _value = value.split('.');
      var y;
      if (_value.length > 3)
          y = TimeToValue(_value[0], _value[1], _value[2] + '.' + (_value[3] ?? 0));
      else
          y = TimeToValue(_value[0], _value[1], _value[2]);

      return y;
      function TimeToValue(Degrees, Minutes, Seconds) {
          var value = parseFloat(Degrees) + parseFloat(Minutes) / 60 + parseFloat(Seconds) / 3600;
          return parseFloat(value.toFixed(6));
      }
  },
  //WGS84 转 地心
  wgs_dx: function (value) {
      if (value < 0) {
          value = -value;
      }
      var minute = (value - parseInt(value, 10)) * 60;
      var second = (minute - parseInt(minute, 10)) * 60;
      return parseInt(value, 10) + "." + parseInt(minute, 10) + "." + second.toFixed(3);
  },
  /**
   * 批量转换
   * @param {Array} 数组
   * @param {string} 数组类型名称
   * @example
   * 数组格式:[{lng,lat},{lng,lat}...]
   * 数组类型名称:地心坐标,WGS84,GCJ02,BD09     
   */
  piliangzhuan: function (arr, typename) {
      var arr_dixin = []; //地心
      var arr_w84 = [];   //WGS84
      var arr_gcj02 = []; //GCJ02
      var arr_bd09 = [];  //BD09        
      switch (typename) {
          case "地心坐标":
            arr.forEach(function (i, item) {
                  arr_dixin.push(item.split(',')[0] + ',' + item.split(',')[1]);
                  var lng = GPSHelper.dx_wgs(item.split(',')[0]);
                  var lat = GPSHelper.dx_wgs(item.split(',')[1]);
                  arr_w84.push(lng + ',' + lat);
                  var gcj02 = GPSHelper.gcj_encrypt(lat, lng);
                  arr_gcj02.push(gcj02.lon + ',' + gcj02.lat);
                  var db09 = GPSHelper.bd_encrypt(gcj02.lat, gcj02.lon);
                  arr_bd09.push(db09.lon + ',' + db09.lat);
              });
              break;
          case "WGS84":
            arr.forEach(function (i, item) {
                  var w84 = { lng: item.split(',')[0], lat: item.split(',')[1] };
                  arr_w84.push(w84.lng + ',' + w84.lat);
                  arr_dixin.push(GPSHelper.wgs_dx(w84.lng) + ',' + GPSHelper.wgs_dx(w84.lat));
                  var gcj02 = GPSHelper.gcj_encrypt(w84.lat, w84.lng);
                  arr_gcj02.push(gcj02.lon + ',' + gcj02.lat);
                  var db09 = GPSHelper.bd_encrypt(gcj02.lat, gcj02.lng);
                  arr_bd09.push(db09.lon + ',' + db09.lat);
              });
              break;
          case "GCJ02":
            arr.forEach(function (i, item) {
                  var gcj02 = { lng: item.split(',')[0], lat: item.split(',')[1] };
                  arr_gcj02.push(gcj02.lng + ',' + gcj02.lat);
                  var w84 = GPSHelper.gcj_decrypt(gcj02.lat, gcj02.lng);
                  arr_w84.push(w84.lon + ',' + w84.lat);
                  arr_dixin.push(GPSHelper.wgs_dx(w84.lon) + ',' + GPSHelper.wgs_dx(w84.lat));
                  var db09 = GPSHelper.bd_encrypt(gcj02.lat, gcj02.lng);
                  arr_bd09.push(db09.lon + ',' + db09.lat);
              });
              break;
          case "BD09":
            arr.forEach(function (i, item) {
                  arr_bd09.push(item.split(',')[0] + ',' + item.split(',')[1]);
                  var gcj02 = GPSHelper.bd_decrypt(item.split(',')[1], item.split(',')[0]);
                  arr_gcj02.push(gcj02.lon + ',' + gcj02.lat);
                  var w84 = GPSHelper.gcj_decrypt(gcj02.lat, gcj02.lon);
                  arr_w84.push(w84.lon + ',' + w84.lat);
                  arr_dixin.push(GPSHelper.wgs_dx(w84.lon) + ',' + GPSHelper.wgs_dx(w84.lat));
              });
              break;
          default:
              break;
      }

      return { dx: arr_dixin, wgs84: arr_w84, gcj02: arr_gcj02, bd09: arr_bd09 };
  }
};
export default GPSHelper