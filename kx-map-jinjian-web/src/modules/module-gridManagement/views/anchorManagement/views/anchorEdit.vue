<template>
  <mapContent>
    <div id="olMap" slot="map"></div>
    <template #header>
      <operateTitle title="锚点调整"></operateTitle>
    </template>
    <template #operate>
      <el-form
        ref="subForm"
        :model="subForm"
        label-width="80px"
        class="form-wrapper"
        :rules="rules"
      >
        <el-form-item label="锚点名称" prop="anchorName">
          <el-input
            v-model="subForm.anchorName"
            placeholder="请输入锚点名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="地址类型" prop="addressType">
          <el-select v-model="subForm.addressType" placeholder="请选择地址类型">
            <el-option
              v-for="item in dict.type.grid_address_type"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <subTitle title="锚点地址">
          <el-button
            @click="anchorAddrHandle"
            size="small"
            type="primary"
            icon="el-icon-coordinate"
            circle
          ></el-button>
        </subTitle>
        <el-form-item label="锚点地址">
          <el-input
            class="input"
            v-model="subForm.addressDesc"
            placeholder="请输入详细地址说明"
          ></el-input>
        </el-form-item>
        <span class="tips">地址示例：**省**市**区/县**街道**号**</span>
        <subTitle title="锚点经纬度">
          <el-button
            @click="centerHandle"
            size="small"
            type="primary"
            icon="el-icon-coordinate"
            circle
          ></el-button>
        </subTitle>
        <el-form-item label="经度">
          <el-input disabled v-model="subForm.lon"></el-input>
        </el-form-item>
        <el-form-item label="纬度">
          <el-input disabled v-model="subForm.lat"></el-input>
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <i-button-bar
        size="small"
        :plain="false"
        :btn-lists="btnLists"
        @click="clickHandle"
        ref="btnBar"
        justifyContent="space-around"
      >
      </i-button-bar>
    </template>
  </mapContent>
</template>

<script>
import service from "../api";
import mapContent from "@/modules/module-gridManagement/components/mapContent";
import operateTitle from "@/modules/module-gridManagement/components/operateTitle";
import subTitle from "@/modules/module-gridManagement/components/subTitle";
import kxMap from "@/utils/kxMap";
import * as turf from "@turf/turf";
import mapMixin from "@/mixins/mapMixin.vue";
import { bd09towgs84, wgs84tobd09 } from "@/utils/Helper";

export default {
  components: {
    mapContent,
    operateTitle,
    subTitle,
  },
  mixins: [mapMixin],
  dicts: ["grid_address_type"],
  data() {
    return {
      options: [],
      props: {
        value: "code",
        label: "name",
        children: "childList",
      },
      subForm: {
        anchorName: "", //锚点名称
        addressType: "", //地址类型
        // addrInfo: [], //锚点的省市区
        addressDesc: "", //锚点的详细地址
        lon: "", //经度
        lat: "", //纬度
      },
      rules: {
        anchorName: [
          {
            required: true,
            message: "请输入锚点名称",
          },
        ],
        addressType: [
          {
            required: true,
            message: "请选择地址类型",
          },
        ],
      },
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
      currentCoordinate: [],
      belongGrid: "", //网格区域号
    };
  },
  computed: {
    anchorInfo() {
      return this.$store.getters["gridManagement/anchorInfo"];
    },
  },
  mounted() {
    this.initMap();
  },
  methods: {
    async initMap() {
      await this.initMapBase();
      this.boundaryLayer = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 1 },
        "boundary"
      );
      this.pointLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 2 },
        "point"
      );

      this.initModifyEnd();
      this.pointLayer.addSelect();
      this.pointLayer.addModify();

      this.control = new this.olMap.BaseViewControl(this.olMap);
      this.getAnchorDetail();
    },
    initModifyEnd() {
      const _ = this;
      this.pointLayer.modifyEndCallBack = (e) => {
        const {
          mapBrowserEvent: { coordinate },
        } = e;
        _.subForm.lon = coordinate[0];
        _.subForm.lat = coordinate[1];
        this.currentCoordinate = coordinate;
      };
    },
    // 获取锚点详情
    getAnchorDetail() {
      this.anchorInfo.addressType =
        this.anchorInfo.addressType && this.anchorInfo.addressType + "";
      this.subForm = Object.assign(this.subForm, this.anchorInfo);
      console.log("anchorInfo", this.anchorInfo);
      const { addrDetail, belongGrid, lat, lon } = this.anchorInfo;
      this.belongGrid = belongGrid;
      this.currentCoordinate = lat && lon && [lon, lat];
      if (!this.currentCoordinate || this.currentCoordinate.length === 0) {
        this.currentCoordinate = [120.7, 31.65];
      }
      this.iterator = this.initDraw();
      this.iterator.next();
    },
    *initDraw() {
      yield this.getBoundaryData();
      yield this.drawPoint();
    },
    drawPoint() {
      const point = turf.point(this.currentCoordinate, {
        name: this.anchorInfo.anchorName,
        type: null,
        src: require("@/modules/module-gridManagement/views/anchorManagement/assets/building.png"),
      });
      this.pointLayer.clear();
      this.pointLayer.addGeoFeature(point);
      this.control.move(this.currentCoordinate, true);
    },
    // 获取边界数据
    getBoundaryData() {
      const param = {
        areaCode: this.belongGrid,
        jurisdiction: 0,
      };
      service.getCoordinatesByCode(param).then((res) => {
        const { data } = res;
        this.boundaryData = data.features[0];
        this.boundaryLayer.clear();
        this.boundaryLayer.addShadyFeature(data);
        const extent = turf.bbox(data);
        this.control.setView(extent);
        this.iterator.next();
      });
    },
    //地址解析
    anchorAddrHandle() {
      if (this.subForm.addressDesc) {
        let data = {
          address: this.subForm.addressDesc,
        };
        service
          .geoCode(data)
          .then((res) => {
            const {
              data: { location },
            } = res;
            let [lon, lat] = bd09towgs84(location.lng, location.lat);
            this.subForm.lon = lon; //经度
            this.subForm.lat = lat; //纬度
            this.currentCoordinate = [lon, lat];
            this.drawPoint();
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        this.$modal.msgError("请输入锚点详细地址说明");
      }
    },
    centerHandle() {
      const [lng, lat] = this.currentCoordinate;
      let data = {
        location: [lat, lng].join(","), //纬度经度
      };
      this.control.move(this.currentCoordinate, true);
      service
        .reGeoCode(data)
        .then((res) => {
          const { data } = res;
          this.subForm.addressDesc = data.formatted_address;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    clickHandle(type) {
      this[type].call();
    },
    confirm() {
      if (
        !turf.booleanPointInPolygon(this.currentCoordinate, this.boundaryData)
      ) {
        this.$modal.msgError("锚点不在区域范围内");
        return;
      }
      this.$refs.subForm.validate((valid) => {
        if (valid) {
          service.anchorPointEdit(this.subForm).then((res) => {
            this.$modal.msgSuccess("锚点编辑成功");
            this.cancel();
          });
        }
      });
    },
    cancel() {
      this.$router.back();
    },
  },
};
</script>

<style lang="scss" scoped>
#olMap {
  width: 100%;
  height: 100%;
}
.form-wrapper {
  margin-top: 20px;
}
.input {
  margin-top: 5px;
}
.tips {
  font-size: 12px;
  color: #999;
  padding-left: 14px;
}
</style>
