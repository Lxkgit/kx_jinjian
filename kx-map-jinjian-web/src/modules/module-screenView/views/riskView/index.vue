<template>
  <div class="view">
    <div class="map2" ref="myMap2" id="myMap2"></div>

    <div v-auto-scale class="main-content">
      <div
        class="main-content-wrapper"
        ref="contentWrapper"
        v-loading="loading"
      >
        <div class="clipbg"></div>
        <ScreenHead :right-show="rightShow" @turnSwitch="turnSwitch" />
        <ScreenLeft @viewLeft="viewLeft">
          <riskLeftPanel
            :showLevelView="showLevelView"
            :chartsLabelList="chartsLabelList"
          ></riskLeftPanel>
        </ScreenLeft>
        <ScreenRight class="risk-screen-right" @turnSwitch="turnSwitch">
          <riskRightPanel
            :showLevelView="showLevelView"
            :chartsLabelList="chartsLabelList"
          ></riskRightPanel>
        </ScreenRight>
        <div class="centerBox">
          <div v-if="false" class="center-header">
            <div class="title">
              <span class="org">{{ layers }}</span>
            </div>

            <div class="top-cards-group group-gold">
              <ul class="top-cards">
                <li
                  v-for="(box, i) in list.slice(0, 4)"
                  :key="i"
                  class="top-card"
                >
                  <div class="card-left">
                    <p class="card-label">{{ box.label }}</p>
                    <p class="card-value">
                      <countTo
                        :value="box.value"
                        :duration="3000"
                        :decimals="0"
                      >
                        <template #default="{ num }">
                          {{ num }}
                        </template>
                      </countTo>
                    </p>
                  </div>
                  <div class="card-right" v-if="box.add">
                    <div class="card-label">新增</div>
                    <div class="card-sub">
                      <countTo :value="box.add" :duration="3000" :decimals="0">
                        <template #default="{ num }">
                          {{ num }}
                        </template>
                      </countTo>
                      <countTo
                        :value="parseInt((box.add / box.value) * 10000) / 100"
                        :duration="3000"
                        :decimals="2"
                      >
                        <template #default="{ num }">
                          <span style="font-size: 13px; color: #ef595a">{{
                            num + "%"
                          }}</span>
                        </template>
                      </countTo>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="top-cards-group group-blue">
              <ul class="top-cards">
                <li
                  v-for="(box, i) in list.slice(4, 8)"
                  :key="i + 4"
                  class="top-card"
                >
                  <div class="card-left">
                    <p class="card-label">{{ box.label }}</p>
                    <p class="card-value">
                      <countTo
                        :value="box.value"
                        :duration="3000"
                        :decimals="0"
                      >
                        <template #default="{ num }">
                          <span class="num">{{ num }}</span
                          ><span class="unit" v-if="box.unit">{{
                            box.unit
                          }}</span>
                        </template>
                      </countTo>
                    </p>
                  </div>
                </li>
              </ul>
            </div>
          </div>
          <div class="mapBg"></div>
          <div class="map-point-legend">
            <div class="legend-title">
              <span>地图点位</span><small>MAP LAYERS</small>
            </div>
            <button
              v-for="item in pointLegend"
              :key="item.key"
              :class="['legend-item', item.key, { disabled: !pointFilters[item.key] }]"
              @click="togglePointFilter(item.key)"
            >
              <i></i><span>{{ item.label }}</span><b>{{ item.count }}</b>
            </button>
          </div>
          <div class="operate-wrapper">
            <searchPart></searchPart>
          </div>
          <div class="operate-right-wrapper">
            <backPart></backPart>
          </div>
          <pointDialog3 v-model="riskPointShow" :info="riskPointInfo" />
          <customerPointDialog v-model="customerPointShow" :info="customerPointInfo" />
        </div>
      </div>
      <!-- 悬浮效果 -->
      <div class="popup-wrapper">
        <div id="popup" class="popup-box">
          {{ hoverName }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import kxMap from "@/utils/kxMap";
import service from "./api";
import riskLeftPanel from "./cell/riskLeftPanel";
import riskRightPanel from "./cell/riskRightPanel";
import viewMixin from "@/modules/module-screenView/mixins/viewMixin.vue";
import searchPart from "@/modules/module-screenView/components/searchPart";
import backPart from "@/modules/module-screenView/components/operate/backPart";
import countTo from "@/modules/module-screenView/viewComponents/components/count";
import pointDialog3 from "./components/pointDialog3.vue";
import customerPointDialog from "./components/customerPointDialog.vue";
import { riskPointData } from "./riskPointData";
import { customerPointData } from "./customerPointData";
import * as turf from "@turf/turf";
import axios from "axios";

const POINT_ICONS = {
  risk: require("@/modules/module-screenView/assets/icon/riskPoint.png"),
  personal: require("@/modules/module-screenView/assets/icon/customer_1.png"),
  enterprise: require("@/modules/module-screenView/assets/icon/customer_11.png"),
  merchant: require("@/modules/module-screenView/assets/icon/customer_10.png"),
};

export default {
  mixins: [viewMixin],
  components: {
    riskLeftPanel,
    riskRightPanel,
    searchPart,
    backPart,
    countTo,
    pointDialog3,
    customerPointDialog,
  },
  data() {
    return {
      loading: false,
      hoverName: "",
      ifInit: true,
      screenStyle: null,
      showLevelView: null,
      boundaryInfo: [],
      importBoundaryLayer: null,
      riskPointShow: false,
      riskPointInfo: {},
      customerPointShow: false,
      customerPointInfo: {},
      pointFilters: {
        risk: true,
        personal: true,
        enterprise: true,
        merchant: true,
      },
      pointLegend: [
        { key: "risk", label: "风险事件", count: riskPointData.length },
        { key: "personal", label: "个人客户", count: customerPointData.filter((i) => i.customerType === "personal").length },
        { key: "enterprise", label: "企业客户", count: customerPointData.filter((i) => i.customerType === "enterprise").length },
        { key: "merchant", label: "商户客户", count: customerPointData.filter((i) => i.customerType === "merchant").length },
      ],
      list: [
        {
          label: "线索总数",
          value: 5886,
          add: 1249,
        },
        {
          label: "扫楼清街",
          value: 15634,
        },
        {
          label: "复核通过",
          value: 14451,
        },
        {
          label: "待复核",
          value: 3452,
        },
        {
          label: "预警下发",
          value: 123,
          unit: "家",
        },
        {
          label: "核查反馈",
          value: 86,
          unit: "家",
        },
        {
          label: "新立案件",
          value: 59,
          unit: "起",
        },
        {
          label: "未兑付",
          value: 27,
          unit: "亿元",
        },
      ],
    };
  },
  computed: {
    chartsLabelList() {
      return this.boundaryInfo.map((item) => item.properties.name);
    },
    orgInfo() {
      return this.$store.getters["screenView/orgInfo"];
    },
    orgList() {
      return this.$store.getters["screenView/orgList"];
    },
    layers() {
      return this.$store.getters["screenView/layers"];
    },
    orgCode() {
      return this.$store.getters["screenView/orgCode"];
    },
    mapType() {
      return this.$store.getters["screenView/mapType"];
    },
    userInfo() {
      return this.$store.getters["userInfo"];
    },
  },
  watch: {
    orgList: {
      handler(val) {
        if (val.length > 0) {
          this.getLayers();
        }
      },
    },
    "orgInfo.level": {
      handler(val) {
        this.showLevelView = val;
      },
      immediate: true,
      deep: true,
    },
    orgCode: {
      handler(val) {
        if (val && val != "point") {
          // 下钻或返回上级时先移除旧区域点位，避免接口返回前短暂显示越界数据。
          this.boundaryInfo = [];
          this.riskPointLayer && this.riskPointLayer.clear();
          this.customerPointLayer && this.customerPointLayer.clear();
          this.orgDataFun();
        } else if (!val) {
          this.$store.commit("screenView/SET_ORG_INFO", {});
          this.$store.commit("screenView/SET_ORG_LIST", []);
          this.boundaryLayer && this.boundaryLayer.clear();
          this.boundaryOutlineLayer && this.boundaryOutlineLayer.clear();
          this.importBoundaryLayer && this.importBoundaryLayer.clear();
          this.riskPointLayer && this.riskPointLayer.clear();
          this.customerPointLayer && this.customerPointLayer.clear();
        }
      },
      deep: true,
    },
    mapType: {
      handler(val) {
        if (val === "1") {
          this.olMap && this.olMap.baseLayer.hide();
          this.normalLayer && this.normalLayer.show();
        } else {
          this.olMap && this.olMap.baseLayer.show();
          this.normalLayer && this.normalLayer.hide();
        }
      },
    },
  },
  mounted() {
    this.initMap();
    this.scaleStyle();
  },
  methods: {
    viewLeft(val) {
      console.log(val);
    },
    getLayers() {
      const orgList = JSON.parse(JSON.stringify(this.orgList));
      const [lastObj] = orgList.slice(-1);
      service.category({ code: lastObj.code }).then((res) => {
        this.$store.commit("screenView/SET_LAYERS", res.data.path);
      });
    },
    scaleStyle() {
      const width = 1920,
        height = 1080;
      const wrapper = this.$refs.contentWrapper;
      const { width: cW, height: cH } = wrapper.getBoundingClientRect();
      const hScale = cH / height;
      const wScale = cW / width;
      this.screenStyle = [wScale, hScale];
    },
    async initMap() {
      const { data: config } = await axios.get("/map.json");
      this.olMap = new kxMap.KMap(
        "myMap2",
        {
          center: [120.7, 31.65],
          zoom: 8,
          minZoom: 3,
          maxZoom: 20,
        },
        {
          url: (x, y, z) =>
            config.bd_normal
              .replace("{x}", x)
              .replace("{y}", y)
              .replace("{z}", z),
          tileType: "2",
        }
      );
      this.normalLayer = new kxMap.BaseLayer(
        this.olMap,
        (x, y, z) =>
          config.bd_normal
            .replace("{x}", x)
            .replace("{y}", y)
            .replace("{z}", z),
        { tilePixelRatio: 2 },
        "normal",
        "2"
      );
      this.normalLayer.hide();

      // 边界图层
      this.boundaryLayer = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 1 },
        "boundary"
      );
      this.importBoundaryLayer = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 2 },
        "importBoundaryLayer"
      );
      this.boundaryOutlineLayer = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 8 },
        "boundaryOutlineLayer"
      );

      this.riskPointLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 20 },
        "riskPointLayer"
      );
      this.customerPointLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 21 },
        "customerPointLayer"
      );

      this.overlay = new kxMap.KOverlay(this.olMap, "popup");
      this.control = new this.olMap.BaseViewControl(this.olMap);

      this.initOrgCode();
      this.initCheck();
      this.initSingleclick();
      this.initPointermove();
      this.renderMapPoints();


      // 行政区域底色：保持在所有业务图层的最底部。
      // 行政区内部仅保留轻透明填充；整张地图的蓝色底色由瓦片底图滤镜提供。
      const boundaryFillStyle = {
        fill: {
          color: "rgba(255, 255, 255, 0.1)",
        },
        stroke: {
          color: "#fff",
          width: 2,
        },
      };
      this.boundaryLayer.setStyle(boundaryFillStyle);

      // 行政边界线独立成层，位于填充层之上、所有锚点之下。
      const boundaryOutlineStyle = {
        fill: {
          color: "rgba(61, 120, 223, 0.15)",
        },
        stroke: {
          color: "#417fe4",
          width: 2,
        },
      };
      this.boundaryOutlineLayer.setStyle(boundaryOutlineStyle);

      // 重点区域同样保持在点位图层下方
      const importBoundaryStyle = {
        fill: {
          color: "rgba(198, 169, 69, 0.16)",
        },
        stroke: {
          color: "rgba(255, 255, 255, 0)",
          width: 0,
        },
      };
      this.importBoundaryLayer.setStyle(importBoundaryStyle);

      // 与 baseView 保持一致：给最底部的瓦片画布统一着色，使边界内外
      // 都具有相同的深蓝地图底色，后续矢量边界和锚点保持原始颜色。
      // setTimeout(() => {
      //   const canvas = document.querySelectorAll(
      //     "#myMap2 >.ol-viewport >.ol-layers .ol-layer>canvas"
      //   );
      //   if (canvas[0]) {
      //     canvas[0].style.filter =
      //       "brightness(0.7) contrast(2) grayscale(5) hue-rotate(360deg) opacity(1) saturate(1.8) sepia(0.94) invert(1)";
      //   }
      // }, 100);
    },
    initCheck() {
      this.$store.commit("screenView/SET_LAYER_CHECK", []);
      this.$store.commit("screenView/SET_ACTIVITY_DETAIL", null);
    },
    initOrgCode() {
      const { addrTypeCodeSet } = this.userInfo;
      const orgCodes = addrTypeCodeSet.join(",");
      this.$store.commit("screenView/SET_ORG_INFO", {});
      this.$store.commit("screenView/SET_ORG_LIST", [
        {
          type: "boundary",
          code: orgCodes,
        },
      ]);
      this.$store.commit("screenView/SET_ORG_CODE", orgCodes);
      if (this.orgCode === orgCodes && orgCodes) {
        this.iterator = this.drawFunction();
        this.iterator.next();
      } else if (!orgCodes) {
        this.boundaryLayer && this.boundaryLayer.clear();
        this.boundaryOutlineLayer && this.boundaryOutlineLayer.clear();
        this.importBoundaryLayer && this.importBoundaryLayer.clear();
      }
    },
    togglePointFilter(key) {
      this.$set(this.pointFilters, key, !this.pointFilters[key]);
      this.renderMapPoints();
    },
    renderMapPoints() {
      if (!this.riskPointLayer || !this.customerPointLayer) return;
      this.riskPointLayer.clear();
      this.customerPointLayer.clear();

      const scopedRiskPoints = riskPointData.filter((item) =>
        this.isPointInCurrentRegion(item)
      );
      const scopedCustomerPoints = customerPointData.filter((item) =>
        this.isPointInCurrentRegion(item)
      );

      this.pointLegend.forEach((item) => {
        if (item.key === "risk") {
          item.count = scopedRiskPoints.length;
        } else {
          item.count = scopedCustomerPoints.filter(
            (customer) => customer.customerType === item.key
          ).length;
        }
      });

      if (this.pointFilters.risk) {
        this.renderPoint(scopedRiskPoints, {
          layer: this.riskPointLayer,
          img: POINT_ICONS.risk,
          innerData: { pointKind: "risk" },
        });
      }

      const customerPoints = scopedCustomerPoints
        .filter((item) => this.pointFilters[item.customerType])
        .map((item) => ({ ...item, src: POINT_ICONS[item.customerType] }));
      this.renderPoint(customerPoints, {
        layer: this.customerPointLayer,
        isClear: false,
      });
    },
    isPointInCurrentRegion(item) {
      if (!this.boundaryInfo.length) return true;
      const lon = Number(item.lon);
      const lat = Number(item.lat);
      if (!Number.isFinite(lon) || !Number.isFinite(lat)) return false;
      const point = turf.point([lon, lat]);
      return this.boundaryInfo.some((boundary) => {
        try {
          return turf.booleanPointInPolygon(point, boundary);
        } catch (e) {
          return false;
        }
      });
    },
    renderPoint(pointList, config = {}) {
      const curConfig = {
        isClear: true,
        layer: null,
        img: null,
        innerData: {},
        ...config,
      };
      if (!curConfig.layer) return;
      const features = pointList.map((item) =>
        turf.point([Number(item.lon), Number(item.lat)], {
          ...item,
          coordinate: [Number(item.lon), Number(item.lat)],
          src: item.src || curConfig.img,
          ...curConfig.innerData,
        })
      );
      if (curConfig.isClear) curConfig.layer.clear();
      if (features.length) curConfig.layer.addGeoFeature(turf.featureCollection(features));
    },
    // 点击事件 - 地图点位与边界下钻
    initSingleclick() {
      const singleclick = new kxMap.SingleclickEvent(this.olMap);
      singleclick.addEvent(
        ["boundary", "riskPointLayer", "customerPointLayer"],
        (e, features) => {
          if (features.length > 0) {
            const [feature] = features;
            const properties = feature.getProperties();
            if (properties.vectorType) {
              if (properties.vectorType === "riskPointLayer") {
                this.riskPointInfo = { ...properties };
                this.riskPointShow = true;
                return;
              }
              if (properties.vectorType === "customerPointLayer") {
                this.customerPointInfo = { ...properties };
                this.customerPointShow = true;
                return;
              }
              this.$store.commit("screenView/SET_ORG_CODE", properties.code);
              const { properties: p, geometry, ...other } = properties;
              if (
                this.orgList.slice(-1)[0] &&
                this.orgList.slice(-1)[0].code !== properties.code
              ) {
                this.$store.commit("screenView/SET_ORG_LIST", [
                  ...this.orgList,
                  { ...other, type: "boundary" },
                ]);
              } else if (this.orgList[0].code === properties.code) {
                this.$store.commit("screenView/SET_ORG_LIST", [
                  ...this.orgList,
                  { ...other, type: "boundary" },
                ]);
                this.orgDataFun();
                this.$EventBus.$emit("mapView");
              }
              if (properties.level == 5) {
                const { code } = properties;
                this.$store.commit("screenView/SET_RIGHT_FLAG", true);
                this.$store.commit("screenView/SET_RIGHT_INFO", {
                  gridCode: code,
                });
              }
            }
          }
        }
      );
    },
    // 鼠标悬浮事件
    initPointermove() {
      const pointermove = new kxMap.PointermoveEvent(this.olMap);
      pointermove.addEvent(
        ["boundary", "riskPointLayer", "customerPointLayer"],
        (e, features) => {
          if (features.length > 0) {
            const [feature] = features;
            const properties = feature.getProperties();
            if (properties.vectorType) {
              const prefix = properties.vectorType === "riskPointLayer"
                ? "风险事件 · "
                : properties.vectorType === "customerPointLayer"
                  ? `${properties.typeName || "客户"} · `
                  : "";
              this.hoverName = `${prefix}${properties.name || ""}`;
              this.overlay.setPosition(e.coordinate);
            }
          } else {
            this.overlay.setPosition(undefined);
          }
        }
      );
    },
    orgDataFun() {
      this.iterator = this.drawFunction();
      this.iterator.next();
    },
    *drawFunction() {
      yield this.getGridCreditData();
      yield this.getBoundaryData();
    },
    // 获取边界授信客户
    getGridCreditData() {
      if (!this.orgCode || this.orgCode == "point") {
        this.boundaryLayer.clear();
        this.boundaryOutlineLayer && this.boundaryOutlineLayer.clear();
        this.importBoundaryLayer && this.importBoundaryLayer.clear();
        return;
      }
      this.$nextTick(() => {
        this.iterator && this.iterator.next();
      });
    },
    // 边界渲染
    async getBoundaryData() {
      if (!this.orgCode) {
        this.boundaryLayer.clear();
        this.boundaryOutlineLayer && this.boundaryOutlineLayer.clear();
        this.importBoundaryLayer && this.importBoundaryLayer.clear();
        return;
      }
      let fns = [];
      this.orgCode.split(",").forEach((item) => {
        const param = {
          areaCode: item,
          jurisdiction: 2,
        };
        fns.push(service.getCoordinatesByCode(param));
      });

      axios.all(fns).then(
        axios.spread((...resList) => {
          let geoData = [];
          this.loading = false;
          if (resList.length > 1) {
            let level = 999;
            resList.forEach((item) => {
              const {
                data: { features },
              } = item;
              let parentItem = null;
              let feature = null;
              if (features.length > 1) {
                feature = features.shift();
                parentItem = feature.properties;
              } else {
                feature = features[0];
                parentItem = features[0].properties;
              }
              level = Math.min(parentItem.level, level);
              geoData.push(feature);
            });
            this.$store.commit("screenView/SET_ORG_INFO", { level });
          } else {
            [geoData] = resList.map((item) => item.data.features);
            let currentOrg = {};
            if (geoData.length > 1 && this.orgList.length > 1) {
              currentOrg = geoData.shift().properties;
            } else if (geoData.length === 1) {
              currentOrg = geoData[0].properties;
            } else {
              currentOrg = geoData[0].properties;
              geoData.shift();
            }
            this.$store.commit("screenView/SET_ORG_INFO", currentOrg);
          }

          this.boundaryInfo = [...geoData];

          let importantGeoData = geoData.filter(
            (i) => i.properties.isCash == "1"
          );
          let unionData = turf.featureCollection(geoData);
          this.boundaryLayer?.clear();
          this.boundaryLayer?.addGeoFeature(unionData);
          this.boundaryLayer?.setExtent();
          this.boundaryOutlineLayer.clear();
          this.boundaryOutlineLayer.addGeoFeature(unionData);
          let importUnionData = turf.featureCollection(importantGeoData);
          this.importBoundaryLayer.clear();
          this.importBoundaryLayer.addGeoFeature(importUnionData);
          this.importBoundaryLayer.setExtent();
          this.renderMapPoints();
          this.setView();
          this.ifInit = false;
          this.iterator && this.iterator.next();
        })
      );
    },
    setView(extent) {
      let padding = [
        200 * this.screenStyle[1],
        540 * this.screenStyle[0],
        20,
        540 * this.screenStyle[0],
      ];
      let e = extent ? extent : this.boundaryLayer.extent;
      this.control.setView(e, { padding, duration: 500 });
    },
  },
};
</script>
<style lang="scss">
.risk-screen-right .main-wrapper {
  height: 920px !important;
}

.mypopper {
  background: rgba(6, 14, 57, 0.5);
  border: 1px solid #004085;

  .el-select-dropdown__item {
    background: rgba(6, 14, 57, 0.5);
    backdrop-filter: blur(10.88px);
    color: #fff;
    font-size: 12px;
  }

  .el-select-dropdown__item.hover {
    background: linear-gradient(
      180deg,
      #5392ff 0%,
      rgba(16, 39, 93, 0.72) 100%
    );
    color: #fff;
  }

  .el-select-dropdown__item.selected {
    background: linear-gradient(
      180deg,
      #5392ff 0%,
      rgba(16, 39, 93, 0.72) 100%
    );
    color: #fff;
  }

  &.el-popper .popper__arrow {
    display: none;
  }
}
</style>

<style lang="scss" scoped>
.main-content {
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  z-index: 1;

  .main-content-wrapper {
    position: relative;
    width: 100%;
    height: 100%;
    pointer-events: none;
  }

  .clipbg {
    width: 100%;
    height: 100%;
    background: url("~@/modules/module-screenView/assets/image/bg.png");
    background-size: 100% 100%;
    pointer-events: none;
    clip-path: polygon(
      0 0,
      0 100%,
      547px 100%,
      547px calc(100% - 30px),
      547px 100px,
      calc(100% - 553px) 100px,
      calc(100% - 553px) calc(100% - 30px),
      547px calc(100% - 30px),
      547px 100%,
      100% 100%,
      100% 0
    );
  }
}

.centerBox {
  width: 824px;
  position: absolute;
  left: 546px;
  top: 100px;
  height: 1009px;

  .map-point-legend {
    position: absolute;
    z-index: 18;
    top: 70px;
    left: 18px;
    width: 178px;
    padding: 12px;
    pointer-events: auto;
    border: 1px solid rgba(62, 180, 255, 0.48);
    border-radius: 7px;
    background: linear-gradient(145deg, rgba(7, 31, 70, 0.92), rgba(4, 18, 47, 0.88));
    box-shadow: 0 0 22px rgba(0, 126, 255, 0.22), inset 0 0 18px rgba(49, 155, 255, 0.06);
    backdrop-filter: blur(8px);

    &::before,
    &::after {
      content: "";
      position: absolute;
      width: 18px;
      height: 18px;
      pointer-events: none;
    }

    &::before {
      left: -1px;
      top: -1px;
      border-left: 2px solid #46d9ff;
      border-top: 2px solid #46d9ff;
    }

    &::after {
      right: -1px;
      bottom: -1px;
      border-right: 2px solid #2b89ff;
      border-bottom: 2px solid #2b89ff;
    }

    .legend-title {
      display: flex;
      align-items: flex-end;
      justify-content: space-between;
      padding-bottom: 8px;
      margin-bottom: 7px;
      border-bottom: 1px solid rgba(77, 169, 239, 0.18);

      span {
        color: #dff5ff;
        font-size: 14px;
        font-weight: 600;
      }

      small {
        color: rgba(86, 191, 255, 0.45);
        font-size: 8px;
        letter-spacing: 1px;
      }
    }

    .legend-item {
      width: 100%;
      height: 29px;
      margin: 2px 0;
      padding: 0 6px;
      display: grid;
      grid-template-columns: 16px 1fr 24px;
      align-items: center;
      color: #a9cae6;
      font-size: 12px;
      text-align: left;
      border: 0;
      border-radius: 4px;
      background: transparent;
      cursor: pointer;
      transition: 0.2s ease;

      &:hover {
        color: #fff;
        background: rgba(43, 139, 218, 0.18);
      }

      i {
        width: 9px;
        height: 9px;
        border-radius: 50%;
        background: #ff4d58;
        box-shadow: 0 0 8px #ff4d58;
      }

      b {
        color: #6edcff;
        font-weight: 600;
        text-align: right;
      }

      &.personal i { background: #2fa7ff; box-shadow: 0 0 8px #2fa7ff; }
      &.enterprise i { background: #9b72ff; box-shadow: 0 0 8px #9b72ff; }
      &.merchant i { background: #ffae42; box-shadow: 0 0 8px #ffae42; }
      &.disabled { opacity: 0.34; filter: grayscale(0.8); }
    }
  }

  .center-header {
    width: 100%;
    height: 170px;

    &.p50 {
      padding: 0 60px;
    }

    .title {
      margin: 0 auto 5px;
      height: 20px;
      display: flex;
      justify-content: center;
      align-items: center;
      position: relative;
      top: -15px;
      span.org {
        position: relative;
        display: block;
        padding: 10px 120px;
        color: #fff;
        font-size: 14px;
        background: linear-gradient(
          270deg,
          rgba(0, 70, 206, 0) 1%,
          #3d6bc5 40%,
          rgba(61, 107, 197, 0.9938) 60%,
          rgba(0, 70, 206, 0) 97%
        );

        &::before {
          content: "";
          position: absolute;
          left: 60px;
          top: 16px;
          width: 30px;
          height: 1px;
          background: linear-gradient(
            90deg,
            rgba(0, 255, 227, 0) 0%,
            #00ffe7 26%,
            #00fff0 75%,
            rgba(0, 255, 243, 0) 100%
          );
        }

        &::after {
          content: "";
          position: absolute;
          right: 60px;
          top: 16px;
          width: 30px;
          height: 1px;
          background: linear-gradient(
            90deg,
            rgba(0, 255, 227, 0) 0%,
            #00ffe7 26%,
            #00fff0 75%,
            rgba(0, 255, 243, 0) 100%
          );
        }
      }
    }
  }

  .mapBg {
    position: absolute;
    left: -47px;
    top: -50px;
    background: url("~@/modules/module-screenView/assets/image/mapbg.png")
      no-repeat;
    background-size: 100% 100%;
    width: 914px;
    height: 1052px;
    pointer-events: none;
  }
}

.popup-wrapper {
  display: none;
}

.popup-box {
  padding: 8px;
  color: #fff;
  font-weight: 900;
  border-radius: 6px;
  border: 1px solid #00f6ff;
  background: linear-gradient(180deg, #0f3243 0%, #15e0e7 100%);
  box-shadow: 0px 0px 6px 0px rgba(220, 215, 175, 0.75);
}

.view {
  position: relative;
  width: 100%;
  height: 100%;
}

.map2 {
  position: absolute;
  width: 100%;
  height: calc(100% - 80px);
  left: 0;
  bottom: 0;
}

.operate-wrapper {
  position: absolute;
  left: 20px;
  top: 20px;
  z-index: 999;
  pointer-events: auto;
}

.bottom-wrapper {
  position: absolute;
  bottom: 60px;
  right: 20px;
  pointer-events: auto;
}

.operate-right-wrapper {
  position: absolute;
  right: 20px;
  top: 20px;
  z-index: 999;
  pointer-events: auto;
  display: flex;
}

/* Top cards 分组容器样式 */
.center-header .top-cards-group {
  display: inline-block;
  vertical-align: top;
  padding: 2px;
  border-radius: 8px;
  height: 70px;
  width: 100%;
  margin-bottom: 18px;
  &.group-gold {
    position: relative;
    border: 1px solid #9b6128;
    background: linear-gradient(90deg, #ece9c612 0%, #8a6b1e40 100%);

    &::before {
      content: "";
      position: absolute;
      top: -15px;
      left: -15px;
      width: 65px;
      height: 32px;
      background: url("~@/modules/module-screenView/assets/icon/bg2.png")
        no-repeat;
      background-size: contain;
      pointer-events: none;
      z-index: 1;
    }
  }

  &.group-blue {
    position: relative;
    border: 1px solid #28619b;
    background: linear-gradient(90deg, #c6d8ec12 0%, #1e538a5c 100%);
    &::before {
      content: "";
      position: absolute;
      top: -15px;
      left: -15px;
      width: 94px;
      height: 32px;
      background: url("~@/modules/module-screenView/assets/icon/bg1.png")
        no-repeat;
      background-size: contain;
      pointer-events: none;
      z-index: 1;
    }
  }

  > .top-cards {
    display: grid;
    grid-template-columns: repeat(10, 1fr);
    grid-template-rows: 69px 61px;
    gap: 12px;
    justify-content: space-between;
    padding: 2px;
    border-radius: 6px;
  }

  &.group-gold > .top-cards {
  }

  &.group-blue > .top-cards {
    grid-template-columns: repeat(4, 1fr);
  }

  /* 第一组：1-4卡片 */
  &.group-gold {
    > .top-cards > .top-card:nth-of-type(1) {
      display: flex;
      gap: 0 4px;
      grid-column: span 4;
      padding-left: 76px;
      background: url("~@/modules/module-screenView/assets/icon/totalCount.png")
        left center/76px 69px no-repeat;
    }

    > .top-cards > .top-card:nth-of-type(2) {
      grid-column: span 2;
      padding-left: 76px;
      background: url("~@/modules/module-screenView/assets/icon/806.png") left
        center/76px 76px no-repeat;
    }

    > .top-cards > .top-card:nth-of-type(3) {
      grid-column: span 2;
      padding-left: 54px;
      background: url("~@/modules/module-screenView/assets/icon/leftCount.png")
        left center/54px 54px no-repeat;
    }

    > .top-cards > .top-card:nth-of-type(4) {
      grid-column: span 2;
      padding-left: 54px;
      background: url("~@/modules/module-screenView/assets/icon/rightCount.png")
        left center/44px 44px no-repeat;
    }
  }

  /* 第二组：5-8卡片 */
  &.group-blue {
    > .top-cards > .top-card:nth-of-type(1) {
      width: 100%;

      .card-left {
        padding-right: 10px;
        height: 61px;
        display: flex;
        justify-content: space-between;
        align-items: flex-start;

        .card-label {
          font-size: 14px;
          color: #fff;
          margin-top: 20px;
        }

        .card-value {
          font-size: 23px;
          display: inline-flex;
          align-items: flex-end;
          background: linear-gradient(
            180deg,
            #ffffff 0%,
            #ffffff 24%,
            #00d5ff 96%,
            #00ffff 100%
          );
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          background-clip: text;
          text-fill-color: transparent;
          position: relative;
          top: 25px;
          .unit {
            font-size: 10px;
            color: #fff;
            margin-left: 3px;
            -webkit-text-fill-color: #fff;
            text-fill-color: #fff;
          }
        }
      }

      padding-left: 48px;
      background: url("~@/modules/module-screenView/assets/icon/successleft.png")
        8px center/36px 34px no-repeat;
    }

    > .top-cards > .top-card:nth-of-type(2) {
      width: 100%;

      .card-left {
        padding-right: 10px;
        height: 61px;
        display: flex;
        justify-content: space-between;
        align-items: flex-start;

        .card-label {
          font-size: 14px;
          color: #fff;
          margin-top: 20px;
        }

        .card-value {
          font-size: 23px;
          display: inline-flex;
          align-items: flex-end;
          background: linear-gradient(
            180deg,
            #ffffff 0%,
            #ffffff 24%,
            #00d5ff 96%,
            #00ffff 100%
          );
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          background-clip: text;
          text-fill-color: transparent;
          position: relative;
          top: 25px;
          .unit {
            font-size: 10px;
            color: #fff;
            margin-left: 3px;
            -webkit-text-fill-color: #fff;
            text-fill-color: #fff;
          }
        }
      }

      padding-left: 50px;
      background: url("~@/modules/module-screenView/assets/icon/bg22.png") 8px
        center/36px 34px no-repeat;
    }

    > .top-cards > .top-card:nth-of-type(3) {
      width: 100%;

      .card-left {
        padding-right: 10px;
        height: 61px;
        display: flex;
        justify-content: space-between;
        align-items: flex-start;

        .card-label {
          font-size: 14px;
          color: #fff;
          margin-top: 20px;
        }

        .card-value {
          font-size: 23px;
          display: inline-flex;
          align-items: flex-end;
          background: linear-gradient(
            180deg,
            #ffffff 0%,
            #ffffff 24%,
            #00ff22 96%,
            #00ffaa 100%
          );

          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          background-clip: text;
          text-fill-color: transparent;
          position: relative;
          top: 25px;
          .unit {
            font-size: 10px;
            color: #fff;
            margin-left: 3px;
            -webkit-text-fill-color: #fff;
            text-fill-color: #fff;
          }
        }
      }

      padding-left: 50px;
      background: url("~@/modules/module-screenView/assets/icon/bg23.png") 8px
        center/36px 34px  no-repeat;
    }
    > .top-cards > .top-card:nth-of-type(4) {
      width: 100%;

      .card-left {
        padding-right: 10px;
        height: 61px;
        display: flex;
        justify-content: space-between;
        align-items: flex-start;

        .card-label {
          font-size: 14px;
          color: #fff;
          margin-top: 20px;
        }

        .card-value {
          font-size: 23px;
          display: inline-flex;
          align-items: flex-end;
          background: linear-gradient(
            180deg,
            #ffffff 0%,
            #ffffff 24%,
            #ff0000 91%,
            #ff0011 100%
          );
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          background-clip: text;
          text-fill-color: transparent;

          position: relative;
          top: 25px;
          .unit {
            font-size: 10px;
            color: #fff;
            margin-left: 3px;
            -webkit-text-fill-color: #fff;
            text-fill-color: #fff;
          }
        }
      }

      padding-left: 50px;
      background: url("~@/modules/module-screenView/assets/icon/errorright.png")
        8px center/36px 34px  no-repeat;
    }
  }

  .top-card {
    .card-left,
    .card-right {
      padding-left: 10px;
    }

    .card-label {
      white-space: nowrap;
      font-size: 18px;
      line-height: 28px;
      font-weight: 500;
      color: rgba($color: #fff, $alpha: 0.6);
    }

    .card-value {
      font-size: 24px;
      line-height: 28px;
      font-weight: bold;
      color: rgba($color: #fff, $alpha: 0.87);
    }

    .card-sub {
      display: flex;
      line-height: 28px;
      gap: 0 12px;
      white-space: nowrap;
      padding-left: 20px;
      font-size: 18px;
      font-weight: bold;
      color: #fff;
      background: url("~@/modules/module-screenView/assets/icon/sub.png") left
        center/12px 8px no-repeat;
    }
  }
}
</style>
