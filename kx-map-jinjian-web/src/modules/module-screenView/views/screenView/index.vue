<template>
  <div class="screen-view-wrapper" ref="contentWrapper">
    <router-view></router-view>
    <!-- <div id="myMap" /> -->


    <!-- 聚合点详情 -->
    <popUpCollect class="pop-up-build" v-model="collectShow" :collectList="collectList" @pointClick="pointClick">
    </popUpCollect>
    <!-- 客户详情 -->
    <!-- <customerDetail v-model="showDetail" :customerType="customerType"></customerDetail> -->
    <NetworkDetail v-model="showDetail"></NetworkDetail>
    <serviceDetail v-model="showCashService" :info="pointDefaultInfo"></serviceDetail>
    <ruralFinancialPointDetail v-model="showRuralPoint" :info="pointDefaultInfo"></ruralFinancialPointDetail>
    <!-- 悬浮效果 -->
    <div class="popup-wrapper">
      <div id="popup" class="popup-box">
        {{ hoverName }}
      </div>
    </div>
  </div>
</template>

<script>
import kxMap from "@/utils/kxMap";
import service from "./api";
import popUpCollect from "../../components/popUpCollect";
import customerDetail from "../../components/customerDetail";
import viewMixin from "@/modules/module-screenView/mixins/viewMixin.vue";
import serviceDetail from "@/modules/module-gridManagement/views/servicePointManagement/components/serviceDetail.vue";
import ruralFinancialPointDetail from "@/modules/module-gridManagement/views/servicePointManagement/components/ruralFinancialPointDetail.vue";

import * as turf from "@turf/turf";
import axios from "axios";
const IMG_TYPE = {
  1: require(`@/modules/module-screenView/assets/icon/customer_1.png`),
  // 2: require(`@/modules/module-screenView/assets/icon/customer_2.png`),
  20: require(`@/modules/module-screenView/assets/icon/bank_2.png`),
  21: require(`@/modules/module-screenView/assets/icon/bank_1.png`),
  22: require(`@/modules/module-screenView/assets/icon/bank_3.png`),
  3: require(`@/modules/module-screenView/assets/icon/customer_3.png`),
  4: require(`@/modules/module-screenView/assets/icon/customer_4.png`),
  6: require(`@/modules/module-screenView/assets/icon/customer_6.png`),
  dep: require(`@/modules/module-screenView/assets/icon/dep.png`),
  activity: require(`@/modules/module-screenView/assets/icon/activity.png`),
};
export default {
  components: {
    popUpCollect,
    customerDetail,
    serviceDetail,
    ruralFinancialPointDetail,
  },
  mixins: [viewMixin],
  data() {
    return {
      screenStyle: null,
      collectShow: false,
      collectList: [],
      showDetail: false,
      customerType: "1",
      hoverName: "",
      ifInit: true,
      heatData: {},
      dep: [], // 机构数据
      activity: [], // 营销活动数据
      pointInfo: [], //锚点数据
      boundaryInfo: [], //边界数据
      importBoundaryLayer: null,
      amountPointLayer: null,
      cashServiceLayer: null,
      showCashService: false,
      showRuralPoint: false,
      pointDefaultInfo: {},
    };
  },
  computed: {
    orgList() {
      return this.$store.getters["screenView/orgList"];
    },
    orgCode() {
      return this.$store.getters["screenView/orgCode"];
    },
    orgInfo() {
      return this.$store.getters["screenView/orgInfo"];
    },
    /* 左右侧 */
    leftHide() {
      return this.$store.getters["screenView/leftHide"];
    },
    rightHide() {
      return this.$store.getters["screenView/rightHide"];
    },
    /* 角色切换 */
    roleParam() {
      return this.$store.getters["screenView/roleParam"];
    },
    initRole() {
      return this.$store.getters["screenView/initRole"];
    },
    /* 筛选 */
    filterType() {
      return this.$store.getters["screenView/filterType"];
    },
    filterParam() {
      return this.$store.getters["screenView/filterParam"];
    },
    anchorInfo() {
      return this.$store.getters["screenView/anchorInfo"];
    },
    /* 热力图开关 */
    customerHot() {
      return this.$store.getters["screenView/customerHot"];
    },
    creditHot() {
      return this.$store.getters["screenView/creditHot"];
    },
    mapType() {
      return this.$store.getters["screenView/mapType"];
    },
    userInfo() {
      return this.$store.getters["userInfo"];
    },
    layerCheck() {
      return this.$store.getters["screenView/layerCheck"];
    },
    activityDetail() {
      return this.$store.getters["screenView/activityDetail"];
    },
    depVisiable() {
      return this.layerCheck.includes("dep");
    },
    activityVisiable() {
      return this.layerCheck.includes("activity");
    },
    // 现金服务锚点开关
    serviceFlag() {
      return this.$store.getters["screenView/serviceFlag"];
    },
    ruralFinancialFlag() {
      return this.$store.getters["screenView/ruralFinancialFlag"];
    },
  },
  // watch: {
  //   activityDetail: {
  //     handler(v) {
  //       this.toggleActivityLayer(v);
  //     },
  //   },
  //   depVisiable: {
  //     handler(v) {
  //       this.toggleDep(v);
  //     },
  //   },
  //   activityVisiable: {
  //     handler(v) {
  //       this.toggleActivity(v);
  //     },
  //   },
  //   orgCode: {
  //     handler(val) {
  //       if (val && val != "point") {
  //         this.orgDataFun();
  //       } else if (!val) {
  //         this.$store.commit("screenView/SET_ORG_INFO", {});
  //         this.$store.commit("screenView/SET_ORG_LIST", []);
  //         this.boundaryLayer && this.boundaryLayer.clear();
  //       }
  //     },
  //     deep: true,
  //   },
  //   filterParam: {
  //     handler(value) {
  //       const { groupCategory } = value;
  //       if (!this.ifInit) {
  //         this.getPointData();
  //       }
  //     },
  //     deep: true,
  //   },
  //   customerHot: {
  //     handler(val) {
  //       if (!this.ifInit) {
  //         this.getPointData();
  //       }
  //     },
  //   },
  //   roleParam: {
  //     handler() {
  //       if (!this.ifInit) {
  //         this.getPointData();
  //       }
  //     },
  //     deep: true,
  //   },
  //   creditHot: {
  //     handler(val) {
  //       if (!this.ifInit) {
  //         this.creditIter = this.getBoundary();
  //         this.creditIter.next();
  //       }
  //     },
  //   },
  //   anchorInfo: {
  //     handler(val) {
  //       this.searchAnchor(val);
  //     },
  //     deep: true,
  //   },
  //   mapType: {
  //     handler(val) {
  //       if (val === "1") {
  //         this.olMap.baseLayer.hide();
  //         this.road.hide();
  //         this.normalLayer.show();
  //       } else {
  //         this.olMap.baseLayer.show();
  //         this.road.show();
  //         this.normalLayer.hide();
  //       }
  //     },
  //   },
  //   serviceFlag: {
  //     handler(val) {
  //       if (!this.ifInit) {
  //         this.getPointData();
  //       }
  //     },
  //   },
  //   ruralFinancialFlag: {
  //     handler(val) {
  //       if (!this.ifInit) {
  //         this.getPointData();
  //       }
  //     },
  //   },
  // },
  mounted() {
    // let _this = this;
    // window.onreset = () => {
    //   _this.scaleStyle();
    // };
    // // this.initMap();
    // this.scaleStyle();
  },
  methods: {
    scaleStyle() {
      const width = 1920,
        height = 1080;
      const wrapper = this.$refs.contentWrapper;
      const { width: cW, height: cH } = wrapper.getBoundingClientRect();
      // 初始化即进行缩放
      const hScale = cH / height;
      const wScale = cW / width;
      this.screenStyle = [wScale, hScale];
    },
    async initMap() {
      const { data: config } = await axios.get("/map.json");
      this.olMap = new kxMap.KMap(
        "myMap",
        {
          center: [120.7, 31.65],
          zoom: 8,
          minZoom: 3,
          maxZoom: 20,
        },
        {
          url: config.gg_satellite,
        }
      );
      this.road = new kxMap.BaseLayer(
        this.olMap,
        (x, y, z) =>
          config.bd_road.replace("{x}", x).replace("{y}", y).replace("{z}", z),
        { tilePixelRatio: 2 },
        "baiduRoad",
        "2"
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

      this.boundaryLayer = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 2 },
        "boundary"
      );
      this.importBoundaryLayer = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 1 },
        "importBoundaryLayer"
      );
      this.pointLayer = new kxMap.ClusterLayer(
        this.olMap,
        { zIndex: 3 },
        { distance: 4 },
        {},
        "point"
      );
      const clusterStyle = [
        {
          text: {
            fill: {
              color: "#fff",
            },
            text: (p) => p.text.toString(),
          },
        },
        {
          circle: {
            fill: {
              color: "rgba(219,97,60,0.5)",
            },
            stroke: {
              color: "#fff",
              width: 1.5,
            },
            radius: 8,
          },
        },
      ];
      this.pointLayer.setStyle(clusterStyle);
      // 现金服务网点的锚点图层
      this.cashServiceLayer = new kxMap.ClusterLayer(
        this.olMap,
        { zIndex: 2 },
        { distance: 4 },
        {},
        "point"
      );
      this.heatmapLayer = new kxMap.HeatmapLayer(this.olMap, { zIndex: 2 });
      // 用单个锚点显示对应网格中的客户锚点数量
      this.amountPointLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 10 },
        "amountPointLayer"
      );

      this.curPointLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 2 },
        "curPoint"
      );

      //营销活动 图层
      this.activityLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 2 },
        "activity"
      );
      this.depLayer = new kxMap.MarkerLayer(this.olMap, { zIndex: 2 }, "dep");
      //营销活动 图层
      this.activityBoundaryLayer = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 1 },
        "activityBoundary"
      );

      this.overlay = new kxMap.KOverlay(this.olMap, "popup");
      this.control = new this.olMap.BaseViewControl(this.olMap);

      this.initOrgCode();
      this.initCheck();
      this.initSingleclick();
      this.initPointermove();

      this.lineLayer = new kxMap.LineLayer(this.olMap, { zIndex: 4 }); //任务轨迹图层
      this.markLayer = new kxMap.MarkerLayer(this.olMap, { zIndex: 5 }); //任务点图层
      // 范围样式
      const activityStyle = {
        fill: {
          color: "#bb5e4a60",
        },
        stroke: {
          color: "#bb5e4a",
          width: 2,
        },
      };
      this.activityBoundaryLayer.setStyle(activityStyle);

      const importBoundaryStyle = {
        fill: {
          color: "rgba(218, 212, 37, 0.226)",
        },
        stroke: {
          color: "#fff",
          width: 2,
        },
      };
      this.importBoundaryLayer.setStyle(importBoundaryStyle);

      const textOption = [
        {
          icon: {
            scale: 0.6,
            src: require("@/modules/module-screenView/assets/icon/amountPointNum.png"),
          },
          zIndex: 1,
        },
        {
          text: {
            fill: {
              color: "#fff",
            },
            text: (p) => p.name,
            offsetY: -10,
            font: "bold 14px Arial",
          },
          zIndex: 2,
        },
      ];
      this.amountPointLayer.setStyle(textOption);
    },
    //默认选择图层
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
      }
    },
    async toggleDep(v) {
      if (v) {
        if (!this.dep.length) {
          let data = [];
          let { data: list } = await service.networkList({
            areaCode: "520100",
          });
          //todo 获取数据 await
          data = list.map((v) => {
            const { lng, lat, address, deptName } = v;
            return {
              address,
              name: deptName,
              coordinate: [lng, lat],
              src: IMG_TYPE.dep,
            };
          });
          this.depLayer.addFeature(data);
          this.dep = list;
        }
        this.depLayer.show();
      } else {
        this.depLayer.hide();
      }
    },
    async toggleActivity(v) {
      if (v) {
        if (!this.activity.length) {
          let data = [];
          let rst = await service.searchAct({ pageSize: 100, pageNum: 1 });
          let list = rst.rows.filter((v) => v.gridCenter);
          //todo 获取数据 await
          data = list.map((v) => {
            const { coordinates, gridCenter, actName, id } = v;
            return {
              ...v,
              name: actName,
              coordinate: JSON.parse(gridCenter),
              path: JSON.parse(coordinates),
              src: IMG_TYPE.activity,
            };
          });
          this.activityLayer.addFeature(data);
          this.activity = list;
        }
        this.activityLayer.show();
      } else {
        this.activityLayer.hide();
      }
    },
    toggleActivityLayer(v) {
      if (v) {
        this.activityBoundaryLayer.clear();
        this.activityBoundaryLayer.addGeoFeature(v.path[0]);
        const extent = turf.bbox(v.path[0]);
        this.setView(extent);
        this.boundaryLayer.hide();
      } else {
        this.activityBoundaryLayer.clear();
        this.boundaryLayer.show();
        this.boundaryLayer.setExtent();
        this.setView();
      }
    },
    // 点击事件
    initSingleclick() {
      const singleclick = new kxMap.SingleclickEvent(this.olMap);
      singleclick.addEvent(
        ["boundary", "point", "activity", "curPoint"],
        (e, features) => {
          if (features.length > 0) {
            const [feature] = features;
            const properties = feature.getProperties();
            if (properties.vectorType) {
              if (properties.vectorType === "activity") {
                this.activityClick(properties);
                return;
              }
              if (
                properties.vectorType === "curPoint" &&
                properties.searchType == 0
              ) {
                this.pointClick(properties);
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
                console.log(properties);
                const { code } = properties;
                this.$store.commit("screenView/SET_RIGHT_FLAG", true);
                this.$store.commit("screenView/SET_RIGHT_INFO", {
                  gridCode: code,
                });
              }
            } else {
              const pointProperties = properties.features.map((item) => {
                return item.getProperties();
              });
              if (pointProperties.length === 1) {
                const [anchorInfo] = pointProperties;
                this.pointClick(anchorInfo);
              } else {
                this.collectList = pointProperties;
                if (this.serviceFlag || this.ruralFinancialFlag) {
                  this.collectList.forEach((i) => {
                    i.anchorName = i.servePointName;
                    i.addressDesc = i.servePointAddr;
                  });
                }
                this.collectShow = true;
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
        ["boundary", "point", "activity", "curPoint"],
        (e, features) => {
          if (features.length > 0) {
            const [feature] = features;
            const properties = feature.getProperties();
            if (properties.vectorType) {
              this.hoverName = properties.name;
              this.overlay.setPosition(e.coordinate);
            } else {
              if (properties.features.length === 1) {
                this.hoverName = properties.features[0].getProperties().name;
                this.overlay.setPosition(e.coordinate);
              } else {
                this.overlay.setPosition(undefined);
              }
            }
          } else {
            this.overlay.setPosition(undefined);
          }
        }
      );
    },
    // 搜索锚点
    searchAnchor(anchorInfo) {
      this.curPointLayer.clear();
      // 搜索锚点
      let code = anchorInfo.code;

      this.$store.commit("screenView/SET_ORG_CODE", code);
      if (
        this.orgList.slice(-1)[0] &&
        this.orgList.slice(-1)[0].code !== code
      ) {
        this.$store.commit("screenView/SET_ORG_LIST", [
          ...this.orgList,
          { type: "boundary", code },
        ]);
      } else if (this.orgList[0].code === code) {
        this.$store.commit("screenView/SET_ORG_LIST", [
          ...this.orgList,
          { type: "boundary", code },
        ]);
        this.orgDataFun();
        this.$EventBus.$emit("mapView");
      }

      if (anchorInfo.lev == 5) {
        this.$store.commit("screenView/SET_RIGHT_FLAG", true);
        this.$store.commit("screenView/SET_RIGHT_INFO", {
          gridCode: anchorInfo.code,
        });
      }

      if (anchorInfo.lon) {
        setTimeout(() => {
          const point = {
            ...anchorInfo,
            name: anchorInfo.name,
            coordinate: [anchorInfo.lon, anchorInfo.lat],
            src: IMG_TYPE[22],
          };
          this.curPointLayer.addFeature(point);
        }, 1000);
      }
    },
    // 营销活动点击
    activityClick(data) {
      this.$store.commit("screenView/SET_ACTIVITY_DETAIL", data);
    },
    // 锚点点击
    pointClick(data) {
      if (this.serviceFlag) {
        this.showCashService = true;
        this.pointDefaultInfo = data;
        return;
      }
      if (this.ruralFinancialFlag) {
        this.showRuralPoint = true;
        this.pointDefaultInfo = data;
        return;
      }
      // if (this.serviceFlag || this.ruralFinancialFlag) return;
      const {
        anchorTypeCode,
        identity,
        type,
        customerPhone,
        name,
        dotType,
        userId,
      } = data;
      this.customerType = anchorTypeCode + "";
      const param = {
        identity,
        customerPhone,
        customerType: this.customerType,
        type,
        dotType,
        customerName: name,
        id: userId,
      };
      console.log("11111111111111111", data);
      this.$store.commit("gridManagement/SET_CUSTOMER_PARAM", param);
      this.$store.commit("gridManagement/SET_MODEL_STATUS", "view");
      this.showDetail = true;
    },
    orgDataFun() {
      this.iterator = this.drawFunction();
      this.iterator.next();
    },
    *drawFunction() {
      yield this.getGridCreditData();
      yield this.getBoundaryData();
      yield this.getPointData();
    },
    *getBoundary() {
      yield this.getGridCreditData();
      yield this.getBoundaryData();
    },
    // 锚点渲染
    getPointData() {
      if (this.orgCode === "point") {
        this.$modal.msgWarning("请回退");
        return;
      }
      this.cashServiceLayer.clear();
      this.amountPointLayer.clear();
      this.curPointLayer.clear();
      this.pointLayer.clear();
      this.heatmapLayer.clear();
      // if ((!this.customerHot && this.orgInfo.level < 4) || (!this.customerHot && !this.orgInfo.level)) {
      //   return
      // }
      const { orgCodes, managerCodes } = this.roleParam;
      const param = {
        gridCode: this.orgCode,
        type: this.filterType,
        typeList: ["2"],
        ...this.filterParam,
        orgCodes: orgCodes && orgCodes.split(","),
        managerCodes: managerCodes && managerCodes.split(","),
      };
      orgCodes === "" && delete param.orgCodes;
      managerCodes === "" && delete param.managerCodes;
      this.orgList.length === 1 && delete param.gridCode;
      if (this.serviceFlag) {
        this.getServicePoint(param);
        return;
      } else if (this.ruralFinancialFlag) {
        this.getRuralFinancialPoints(param);
        return;
      }
      service.queryBySelect(param).then((res) => {
        let { data } = res;
        let temp = [];
        data.forEach((item) => {
          if (item.lon && item.lat) {
            temp.push(item);
          }
        });
        this.pointInfo = [...temp];
        // 下钻到二三层级的时候展示网格内的锚点数量
        const { level } = this.orgInfo;
        let boolean1 =
          (level == 2 || level == 3) && this.boundaryInfo.length > 1;
        if (boolean1) {
          this.drawAmountLayer();
          return;
        }
        const pointData = temp.map((item) => {
          return turf.point([item.lon, item.lat], {
            ...item,
            name: item.anchorName,
            coordinate: [item.lon, item.lat],
            src: IMG_TYPE[`${item.type}${item.isDuty ?? 0}`],
          });
        });
        const collection = turf.featureCollection(pointData);
        if (!boolean1 && !this.serviceFlag && !this.ruralFinancialFlag) {
          this.pointLayer.clear();
          this.heatmapLayer.clear();
          this.pointLayer.addGeoFeature(collection);
        }
        // this.pointLayer.addGeoFeature(collection);
      });
    },
    // 渲染现金服务点
    getServicePoint(param) {
      let { gridCode } = param;
      service
        .getServicePoints({ gridCode: gridCode ? `${gridCode}` : "330700" })
        .then((res) => {
          let { rows } = res;
          this.$store.commit("screenView/SET_CASHPOINTTOTAL", res.total);
          let temp = [];
          rows.forEach((item) => {
            if (item.lon && item.lat) {
              temp.push(item);
            }
          });
          this.pointInfo = [...temp];
          // 下钻到二三层级的时候展示网格内的锚点数量
          const { level } = this.orgInfo;
          if ((level == 2 || level == 3) && this.boundaryInfo.length > 1) {
            this.drawAmountLayer();
            return;
          }
          const pointData = temp.map((item) => {
            return turf.point([item.lon, item.lat], {
              ...item,
              name: item.servePointName,
              coordinate: [item.lon, item.lat],
              src: require("@/modules/module-screenView/assets/icon/bank_4.png"),
            });
          });
          const collection = turf.featureCollection(pointData);
          if (this.serviceFlag) {
            this.cashServiceLayer.clear();
            this.cashServiceLayer.addGeoFeature(collection);
          }
        });
    },
    // 渲染乡村金融服务点
    getRuralFinancialPoints(param) {
      let { gridCode } = param;
      service
        .getRuralFinancialPoints({
          gridCode: gridCode ? `${gridCode}` : "330700",
        })
        .then((res) => {
          let { rows } = res;
          this.$store.commit("screenView/SET_RURALPOINTTOTAL", res.total);
          let temp = [];
          rows.forEach((item) => {
            if (item.lon && item.lat) {
              temp.push(item);
            }
          });
          this.pointInfo = [...temp];
          // 下钻到二三层级的时候展示网格内的锚点数量
          const { level } = this.orgInfo;
          if ((level == 2 || level == 3) && this.boundaryInfo.length > 1) {
            this.drawAmountLayer();
            return;
          }
          const pointData = temp.map((item) => {
            return turf.point([item.lon, item.lat], {
              ...item,
              name: item.servePointName,
              coordinate: [item.lon, item.lat],
              src: require("@/modules/module-screenView/assets/icon/bank_5.png"),
            });
          });
          const collection = turf.featureCollection(pointData);
          if (this.ruralFinancialFlag) {
            this.cashServiceLayer.clear();
            this.cashServiceLayer.addGeoFeature(collection);
          }
        });
    },
    // 获取边界授信客户
    getGridCreditData() {
      if (!this.orgCode || this.orgCode == "point") {
        this.boundaryLayer.clear();
        return;
      }
      this.heatData = {};
      // service.getHeatMap({ gridCodes: this.orgCode.split(',') }).then((res) => {
      //   this.heatData = res.data
      //   this.iterator && this.iterator.next()
      //   this.creditIter && this.creditIter.next()
      // })
      this.$nextTick(() => {
        this.iterator && this.iterator.next();
        this.creditIter && this.creditIter.next();
      });
    },
    // 边界渲染
    async getBoundaryData() {
      if (!this.orgCode) {
        this.boundaryLayer.clear();
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
          if (resList.length > 1) {
            let level = 999;
            let bestNum = 0;
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
              bestNum = Math.min(parentItem.bestNum, bestNum);
              geoData.push(feature);
            });
            // if (this.creditHot) {
            //   let max = Math.max(...Object.values(this.heatData)) ?? 0
            //   geoData.forEach(f => {
            //     const { properties } = f
            //     properties.fillColor = [255, 0, 0]
            //     properties.alpha = max ? this.heatData[properties.code] / max * 0.8 : 0
            //   })
            // }
            this.$store.commit("screenView/SET_ORG_INFO", { level });
          } else {
            [geoData] = resList.map((item) => item.data.features);
            let currentOrg = {};
            if (geoData.length > 1 && this.orgList.length > 1) {
              currentOrg = geoData.shift().properties;
            } else if (geoData.length === 1) {
              currentOrg = geoData[0].properties;
            } else {
              geoData.splice(1, geoData.length - 1);
              currentOrg = geoData[0].properties;
            }
            this.$store.commit("screenView/SET_ORG_INFO", currentOrg);

            // // 热力图处理
            // if (this.creditHot) {
            //   let max = Math.max(...Object.values(this.heatData)) ?? 0
            //   geoData.forEach(feature => {
            //     const { properties } = feature
            //     properties.fillColor = [255, 0, 0]
            //     properties.alpha = max ? this.heatData[properties.code] / max * 0.8 : 0
            //   })
            // }
          }
          this.boundaryInfo = [...geoData];
          let importantGeoData = geoData.filter(
            (i) => i.properties.isCash == "1"
          );
          console.log(
            "boundaryInfoboundaryInfoboundaryInfo",
            this.boundaryInfo
          );
          let unionData = turf.featureCollection(geoData);
          this.boundaryLayer.clear();
          this.boundaryLayer.addGeoFeature(unionData);
          this.boundaryLayer.setExtent();
          let importUnionData = turf.featureCollection(importantGeoData);
          this.importBoundaryLayer.clear();
          this.importBoundaryLayer.addGeoFeature(importUnionData);
          this.importBoundaryLayer.setExtent();
          this.setView();
          this.ifInit = false;
          this.iterator && this.iterator.next();
          //  // this.importBoundaryInfo = [...geoData.filter(i=>i.code=='330702')]
        })
      );
    },
    // 下钻到二三层级展示网格内的锚点数量
    drawAmountLayer() {
      let arr = [];
      let pointArr = this.pointInfo.map((item) => [item.lon, item.lat]);
      this.boundaryInfo.forEach((item) => {
        let gridCenter = turf.center(item);
        let obj = {
          count: 0,
          gridCenter: gridCenter.geometry.coordinates,
          ...item.properties,
        };
        let polygon = turf.polygon(item.geometry.coordinates[0]);
        pointArr.forEach((target) => {
          if (turf.booleanPointInPolygon(turf.point(target), polygon))
            obj.count++;
        });
        arr.push(obj);
      });
      const pointData = arr.map((item) => {
        let { count, gridCenter, center } = item;
        return {
          coordinate: [gridCenter[0], gridCenter[1] + 0.005],
          // coordinate: [...center],
          type: "Point",
          name: `${count}`,
        };
      });
      this.amountPointLayer.addFeature(pointData);
    },

    //可视范围 变化
    setView(extent) {
      let padding = [
        160 * this.screenStyle[1],
        this.rightHide ? 20 : 450 * this.screenStyle[0],
        20,
        this.leftHide ? 20 : 450 * this.screenStyle[0],
      ];
      let e = extent ? extent : this.boundaryLayer.extent;
      this.control.setView(e, { padding, duration: 500 });
    },
    renderTask(coordinate) {
      let rings = {
        coordinates: [
          [120.159558, 30.275977],
          [120.159488, 30.276667],
          [120.159478, 30.276857],
          [120.159448, 30.276977],
        ],
      };
      this.lineLayer.addFeature(rings);
      const point = [
        {
          coordinate,
          name: "轨迹点",
          src: require("@/modules/module-screenView/assets/icon/shop.png"),
          type: "",
        },
      ];
      this.markLayer.addFeature(point);
      this.markLayer.setExtent();
      this.control.setView(this.markLayer.extent);
    },
  },
  created() {
    this.$EventBus.$on("mapEvent", (value) => {
      console.log(111, value);
      const [mapMethods, ...other] = value;
      this[mapMethods].call(this, ...other);
    });
  },
  beforeDestroy() {
    this.$EventBus.$off("mapEvent");
  },
};
</script>

<style lang="scss" scoped>
.screen-view-wrapper {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
  background: #1A2566;

  ::v-deep .el-dialog__wrapper {
    overflow: hidden;
  }

  #myMap {
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    z-index: 11;

  }

  .pop-up-build {
    top: 0;
    position: absolute;
    z-index: 999;
    pointer-events: auto;
    width: 100%;
    height: 100%;
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
}
</style>
