<template>
  <i-dialog
    :modal="false"
    title="范围选择"
    v-model="showDialog"
    width="80%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="false"
  >
  <mapContent class="map-content" v-if="value">
    <div id="olMap" slot="map"></div>
    <template #header>
      <operateTitle title="范围选择"></operateTitle>
    </template>
    <template #operate>
      <el-form ref="subForm" :model="subForm" label-width="80px" class="form-wrapper">
        <el-form-item label="网格类型" prop="type">
          <el-radio-group v-model="subForm.type" size="mini">
            <el-radio-button label="0" >手动绘制</el-radio-button>
            <el-radio-button label="1" >引用网格</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="操作" v-if='subForm.type=="0"'>
          <el-button size="mini" @click="drawBoundary">绘制</el-button>
          <el-button size="mini" @click="editBoundary">修改</el-button>
        </el-form-item>
        <el-form-item label="引用区域" v-if='subForm.type=="1"'>
          <el-cascader  v-model="subForm.area" size="mini"
                 :collapse-tags="true" 
                 :props="cascaderProps" class="filter-item"
                 :show-all-levels="false"
                 />
          <el-button size="mini" @click="getBoundary">确定</el-button>
        </el-form-item>
        <el-form-item label="网格名称" >
          <el-input
              size="mini"
              v-model="formInfo.gridName"
              placeholder="请输入网格名称"
            ></el-input>
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <i-button-bar size="small" :plain="false" :btn-lists="btnLists" @click="clickHandle"
                    ref="btnBar" justifyContent="space-around">
      </i-button-bar>
    </template>
  </mapContent>
  </i-dialog>
</template>

<script>
import { mapState } from "vuex";
import dialogMixin from "@/mixins/dialogMixin.vue";
import mapContent from "@/modules/module-gridManagement/components/mapContent";
import operateTitle from "@/modules/module-gridManagement/components/operateTitle";
import mapMixin from "@/mixins/mapMixin.vue";
import kxMap from "@/utils/kxMap";
import service from "../api";
import * as turf from '@turf/turf';

export default {
  name: "addArea",
  mixins: [dialogMixin,mapMixin],
  components: { mapContent,operateTitle },
  props: {
    defaultValue: {
      type: Object,
    },
    status: {
      type: String,
    },
    formInfo:Object,
    value:Boolean
  },
  data() {
    return {
      size: "mini",
      formLabelWidth: "150px",
      btnLists: [
        {
          cname: "确定",
          ename: "confirm",
          type: "primary",
        },
        {
          cname: "取消",
          ename: "cancel",
        },
      ],
      subForm:{
        type:"0",
        area:''
      },
      cascaderProps:{
        value: "code",
        label: "name",
        emitPath: false,
        lazy: true,
        lazyLoad: this.lazyLoad,
        multiple: false,
        checkStrictly: true
      }
    };
  },
  watch:{
    value(v){
      if(v){
        this.$nextTick(()=>{
          this.initMap();
          setTimeout(()=>{
            this.initBoundary() 
          },1000)
        })
      }
    },
    "subForm.type"(){
      this.currentLayer.clear()
    }
  },
  computed: {
    ...mapState({ userInfo: (state) => state.user.userInfo }),
  },
  methods: {
    //初始化数据
    initBoundary(){
      let boundary = this.formInfo.coordinates
      if(boundary){
        this.currentLayer.clear()
        this.currentLayer.addGeoFeature(JSON.parse(boundary)[0])
        const extent = turf.bbox(JSON.parse(boundary)[0]);
        this.control.setView(extent);
      }
    },
    async initMap() {
      await this.initMapBase();

      this.currentLayer = new kxMap.DrawLayer(this.olMap, { zIndex: 4 }, false, false)
      this.currentLayer.setDrawStyle({ strokeColor: '#18A8F5', lineDash: [] })
      this.currentLayer.addSelect()
      this.currentLayer.addModify()
      this.initDrawEnd()

      this.control = new this.olMap.BaseViewControl(this.olMap);
      this.control.move([110.21174813071887,25.26326])
    },
      //获取归属网格下拉
    lazyLoad(node, resolve) {
      const { value } = node;
      const param = {
        parentCode: value,
      };
      service.getPaAddrInfoList(param).then((res) => {
        const { data } = res;
        data.forEach((item) => {
          item.leaf = !item.isLeaf;
        });
        resolve(data);
      });
    },
    // 关闭弹窗
    handleClose() {
      this.showDialog = false;
    },
    // 弹窗确认按钮
    handleConfirm() {
      console.log('确认')
    },
    clickHandle(type) {
      this[type].call();
    },
    confirm() {
      let coordinates = this.currentLayer.getDataCoordinates()
      this.formInfo.coordinates = JSON.stringify(coordinates)
      var centroid = turf.centroid(coordinates[0]);
      let center=  centroid.geometry.coordinates
      this.formInfo.gridCenter =JSON.stringify(center)
      this.showDialog = false;
    },
    cancel() {
      this.showDialog = false;
    },
        // 绘制完结事件
    initDrawEnd() {
      this.currentLayer.drawEndCallBack = (e) => {
        const { feature } = e
        const coordinates = feature.getGeometry().getCoordinates()
        const geoData = turf.polygon(coordinates)
        console.log(geoData)
      }
    },
    drawBoundary(){
      this.currentLayer.clear()
      this.ifDraw = true
      this.currentLayer.setDrawType('Polygon')
      this.currentLayer.startDraw()
    },
    editBoundary(){
      this.ifDraw = false
      this.currentLayer.setDrawType('None')
      this.currentLayer.startDraw()
    },
    getBoundary(){
      const param = {
        areaCode: this.subForm.area,
        jurisdiction: 0,
      };
      service.getCoordinatesByCode(param).then((res) => {
        const { data } = res
        this.currentLayer.clear()
        console.log(data,111)
        this.currentLayer.addGeoFeature(data)
        const extent = turf.bbox(data);
        this.control.setView(extent);
        this.formInfo.gridName = data.features[0].properties.name
      });
    }
  },
};
</script>

<style lang="scss" scoped>
.map-content{
  height: 70vh;
}
#olMap {
  width: 100%;
  height: 100%;
}
</style>
