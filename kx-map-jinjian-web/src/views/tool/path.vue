<template>
  <div class="tool-content">
      <!-- 地图部分 -->
      <div id="olMap" class="map"></div>
      <!-- 表单 -->
      <div class="form">
        <div class="path">
           <ul v-for="(item,index) in path">
              <li>
                <span>{{typeMap[item.type]}}{{item.type==1?index:""}}</span>
                <el-input style="width: 300px;" v-model="item.name"></el-input> 
                <el-input style="width: 200px;" v-model="item.location" readonly></el-input> 
                <el-time-select
                  v-if="item.type==1"
                  style="width: 100px"
                  placeholder="起始时间"
                  v-model="item.time[0]"
                  :picker-options="{
                    start: '08:30',
                    step: '00:15',
                    end: '18:30'
                  }">
                </el-time-select>
                <el-time-select
                  v-if="item.type==1"
                  style="width: 100px"
                  placeholder="结束时间"
                  v-model="item.time[1]"
                  :picker-options="{
                    start: '08:30',
                    step: '00:15',
                    end: '18:30',
                    minTime: item.time[0]
                  }">
                </el-time-select>
                <el-button icon="el-icon-location-outline" circle  @click="goLocation(item)"></el-button>
                <el-button icon="el-icon-minus" circle  v-if="item.type==1&&index!=path.length-2" @click="delPath(index)"></el-button>
                <el-button icon="el-icon-plus" circle  v-if="index==path.length-2" @click="addPath"></el-button>
              </li>
           </ul>
        </div>
        <el-button @click="createPath">生成路径</el-button>
      </div>
      <!-- 结果输出 -->
      <div class="result">
        <el-input  
        v-model="coordinates"
        type="textarea"
        :rows="10"></el-input>
      </div>
  </div>
</template>

<script>
import mapMixin from '@/mixins/mapMixin.vue';
import request from "@/utils/request";
import kxMap from '@/utils/kxMap';
// import * a{bd09towgs84} from '@/utils/Helper';
import GPSHelper from '@/utils/kxMap/GPSHelper'

const KEY = "3b47806486fe7e59908f02f845a6bde0";

import axios from 'axios'
export default {
  data() {
    return {
      coordinates:'',
      location:'',
      typeMap:['起点','途径点','终点'],
      path:[
        // {name:'杭州市城北文澜大厦',location:[120.12427262644746,30.308204403009185],time:['',''],type:0},
        // {name:'蒋村花园兴达苑',location:[120.0794215036496,30.284750155542184],time:['',''],type:1},
        // {name:'稠银大厦',location:[120.19113472587512,30.231970796413577],time:['',''],type:2}
        {name:'',location:[],time:['',''],type:0},
        {name:'',location:[],time:['',''],type:1},
        {name:'',location:[],time:['',''],type:2}
      ]
    };
  },
  mixins: [mapMixin],
  mounted() {
    this.initMap()
  },
  computed: {
    list() {
      return this.path.slice(1, -1);
    },
  },
  methods:{
    gcj_encrypt(arr) {
      let res = GPSHelper.gcj_encrypt(arr[1], arr[0]);
      return [res.lon, res.lat];
    },
     // 高德->谷歌
    gcj_decrypt(arr) {
      let res = GPSHelper.gcj_decrypt(arr[1], arr[0]);
      return [res.lon, res.lat];
    },
    async initMap() {
      await this.initMapBase()
      this.control = new this.olMap.BaseViewControl(this.olMap)
      this.lineMarker = new kxMap.LineLayer(this.olMap, {}, true);
      this.markerLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 1 },
        "marker"
      );
    },
    async goLocation(item){
      // 这个接口返回的是百度坐标系
      let rst = await request({
        url:`/system/geo/geoCode?address=${item.name}`,
        method: "get",
      })
      const {lng,lat} = rst.data.location
      const res =  GPSHelper.bd_to_wgs([lng,lat])
      console.log(res)
      this.control.move(res,true,{zoom:15})
      this.markerLayer.clear()
      let typeMap = ['s','j','e']
      const point = [
        {
          coordinate:res,
          name: "当前位置",
          src: require(`@/assets/images/task_${typeMap[item.type]}.png`),
          type: "",
          },
        ];
      item.location = res
      this.markerLayer.addFeature(point);
     
    },
    // 新增途经点
    addPath(){
      this.path.splice(-1,0, {name:'',location:[],time:['',''],type:1})
    },
    delPath(index){
      this.path.splice(index,1)
    },
    async createPath(){
      this.markerLayer.clear()
      let l = this.path.length;
      let arr = [];
      arr.push({ coordinate: this.path[0].location, src: require(`@/assets/images/task_s.png`)});
      this.list.forEach((v) => {
        v.pointStr = this.gcj_encrypt(v.location).join(",");
        arr.push({ coordinate: v.location, src: require(`@/assets/images/task_j.png`)});
      });

      arr.push({ coordinate: this.path[l - 1].location, src: require(`@/assets/images/task_e.png`)});
      let origin = this.gcj_encrypt(this.path[0].location).join(",");
      let destination = this.gcj_encrypt(this.path[l - 1].location).join(",");
      this.waypoints = this.list.map((v) => v.pointStr);
      let waypoints = this.waypoints.join(";");
      let url = `https://restapi.amap.com/v3/direction/driving?key=${KEY}&origin=${origin}&destination=${destination}&waypoints=${waypoints}`;
      const points = [];

      const {
        data: {
          route: {
            paths: [{ steps }],
          },
        },
      } = await axios.get(url);
      steps.forEach(({ polyline }) => {
        let p = polyline.split(";").map((v) => v.split(","));
        points.push(...p);
      });
      let nPoints = points.map((v) => {
        const [v1, v2] = this.gcj_decrypt(v);
        return [parseFloat(v1), parseFloat(v2)];
      });
      this.drawRuning(nPoints);
      this.coordinates = JSON.stringify(nPoints)
      this.markerLayer.addFeature(arr);
    },
    //绘制轨迹
    drawRuning(coordinates){
      this.lineMarker.clear()
      let rings = {coordinates}
      this.lineMarker.addFeature(rings);
      this.lineMarker.setExtent();
      this.control.setView(this.lineMarker.extent);
    },
  }
};
</script>
<style lang="scss" scoped>
.tool-content{
  display: grid;
  grid-template-areas:
  "map form"
  "map result";
  grid-template-columns: 40% 60%;;
  grid-template-rows:60% 40%;
  height: 100%;
  .map{
    grid-area: map;
  }
  .form{
    grid-area: form;
    padding: 10px;
    .location{
      display: flex;
    }
    .path{
      ul>li{
        display: flex;
        align-items: center;
        justify-content: flex-start;
        >span{
          width: 70px;
        }
      }
    }
  }
  .result{
    grid-area: result;
  }
}
</style>
