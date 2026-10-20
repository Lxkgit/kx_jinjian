<template>
  <div class="view">
    <div class="map" ref="myMap" id="myMap"></div>

    <div v-auto-scale class="main-content">
      <!-- <ScaleScreen center :width="1920" :height="1080" :selfAdaption="true"> -->
      <div
        class="main-content-wrapper"
        ref="contentWrapper"
        v-loading="loading"
      >
        <div class="clipbg"></div>
        <ScreenHead :right-show="rightShow" @turnSwitch="turnSwitch" />
        <ScreenLeft @viewLeft="viewLeft">
          <leftView
            :showLevelView="showLevelView"
            :chartsLabelList="chartsLabelList"
          ></leftView>
        </ScreenLeft>
        <ScreenRight @turnSwitch="turnSwitch">
          <rightView
            :showLevelView="showLevelView"
            :chartsLabelList="chartsLabelList"
          ></rightView>
        </ScreenRight>
        <div class="centerBox">
          <div class="center-header">
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
                    <roleSwitch v-show="false"></roleSwitch>
                    <customerFilter v-show="false"></customerFilter>
                    <viewDialog
                      v-model="showDialog"
                      :title="dialogTitle"
                      annotation=""
                    >
                      <selectInput />
                    </viewDialog>
                  </div>

                  <!-- <div class="bottom-wrapper">
                    <legendPart></legendPart>
                  </div> -->
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
          <div class="operate-wrapper">
            <searchPart></searchPart>
          </div>
          <div class="operate-right-wrapper">
            <backPart></backPart>
            <div class="button-name" @click="handlerShow">网点筛选</div>
            <roleSwitch v-show="false"></roleSwitch>
            <customerFilter v-show="false"></customerFilter>
            <viewDialog v-model="showDialog" :title="dialogTitle" annotation="">
              <selectInput />
            </viewDialog>
          </div>

          <div class="bottom-wrapper">
            <!-- <mapservice /> -->
            <legendPart></legendPart>
          </div>

          <pointDialog1 v-model="pointShow1" />
          <pointDialog2 v-model="pointShow2" />
          <pointDialog3 v-model="pointShow3" :info="pointData3" />
          <!-- <pointDialog4 v-model="pointShow4" :info="pointData4" /> -->
        </div>
      </div>
      <popUpCollect
        class="pop-up-build"
        v-model="collectShow"
        :collectList="collectList"
        @pointClick="pointClick"
      >
      </popUpCollect>
      <NetworkDetail v-model="showDetail"></NetworkDetail>
      <serviceDetail
        v-model="pointShow4"
        :info="pointDefaultInfo"
      ></serviceDetail>
      <ruralFinancialPointDetail
        v-model="showRuralPoint"
        :info="pointDefaultInfo"
      ></ruralFinancialPointDetail>
      <!-- 悬浮效果 -->
      <div class="popup-wrapper">
        <div id="popup" class="popup-box">
          {{ hoverName }}
        </div>
      </div>

      <!-- <trends class="pop-up-build" v-model="collectShow" :readmessageDetails="readmessageDetails"
          @messageDetails="messageDetailsClick"></trends> -->
      <!-- </ScaleScreen> -->
    </div>
  </div>
</template>

<script>
import ScaleScreen from "@/components/scale-screen/scale-screen.vue";
import kxMap from "@/utils/kxMap";
import service from "./api";
import leftView from "./cell/leftView";
import leftViewLevel2 from "./cell/leftViewLevel2";
import leftViewLevel5 from "./cell/leftViewLevel5";
import rightView from "./cell/rightView";
import rightViewLevel2 from "./cell/rightViewLevel2";
import rightViewLevel5 from "./cell/rightViewLevel5";
import leftViewActivity from "./cell/leftViewActivity.vue";
import rightViewActivity from "./cell/rightViewActivity.vue";
import viewMixin from "@/modules/module-screenView/mixins/viewMixin.vue";
import SystemIntroduceDialog from "./components/systemIntroduceDialog.vue";
// import mapservice from "@/modules/module-screenView/components/operate/mapservice";
// import mapSwitch from "@/modules/module-screenView/components/operate/mapSwitch.vue";
import searchPart from "@/modules/module-screenView/components/searchPart";
import backPart from "@/modules/module-screenView/components/operate/backPart";
import roleSwitch from "@/modules/module-screenView/components/operate/roleSwitch";
import customerFilter from "@/modules/module-screenView/components/operate/customerFilter";
import legendPart from "@/modules/module-screenView/components/operate/legendPart";
import mapTile from "@/modules/module-screenView/components/operate/mapTile";
import trends from "@/modules/module-screenView/components/trends";
import popUpCollect from "@/modules/module-screenView/components/popUpCollect";
import customerDetail from "@/modules/module-screenView/components/customerDetail";
import serviceDetail from "@/modules/module-gridManagement/views/servicePointManagement/components/serviceDetail.vue";
import ruralFinancialPointDetail from "@/modules/module-gridManagement/views/servicePointManagement/components/ruralFinancialPointDetail.vue";
import viewDialog from "@/modules/module-screenView/viewComponents/components/viewDialog";

import selectInput from "@/modules/module-screenView/components/operate/selectInput";
import pointDialog1 from "./components/pointDialog1.vue";
import pointDialog2 from "./components/pointDialog2.vue";
import pointDialog3 from "./components/pointDialog3.vue";
import pointDialog4 from "./components/pointDialog4.vue";
import { riskPointData } from "./riskPointData.js";
import { policeData } from "./policeData.js";

import countTo from "@/modules/module-screenView/viewComponents/components/count";
import * as turf from "@turf/turf";
import axios from "axios";
import pageMixin from "@/mixins/pageMixin";
const IMG_TYPE = {
  1: require(`@/modules/module-screenView/assets/icon/customer_1.png`),
  // 2: require(`@/modules/module-screenView/assets/icon/customer_2.png`),
  20: require(`@/modules/module-screenView/assets/icon/bank_2.png`),
  21: require(`@/modules/module-screenView/assets/icon/bank_1.png`),
  22: require(`@/modules/module-screenView/assets/icon/bank_3.png`),
  3: require(`@/modules/module-screenView/assets/icon/customer_3.png`),
  4: require(`@/modules/module-screenView/assets/icon/customer_4.png`),
  6: require(`@/modules/module-screenView/assets/icon/customer_6.png`),
  801: require(`@/modules/module-screenView/assets/icon/totalCount.png`),
  802: require(`@/modules/module-screenView/assets/icon/leftCount.png`),
  803: require(`@/modules/module-screenView/assets/icon/rightCount.png`),
  804: require(`@/modules/module-screenView/assets/icon/successleft.png`),
  805: require(`@/modules/module-screenView/assets/icon/errorright.png`),
  dep: require(`@/modules/module-screenView/assets/icon/dep.png`),
  activity: require(`@/modules/module-screenView/assets/icon/activity.png`),
  bankPoint: require(`@/modules/module-screenView/assets/icon/commonBank.png`),
  supervisionPoint: require(`@/modules/module-screenView/assets/icon/commonJJ.png`),
  hideArrow: require("@/modules/module-screenView/assets/icon/hideArrow.png"),
  lineHead: require("@/modules/module-screenView/assets/icon/lineHead1.png"),
  riskPoint: require("@/modules/module-screenView/assets/icon/riskPoint.png"),
  police: require("@/modules/module-screenView/assets/icon/police.png"),
  customer: require("@/modules/module-screenView/assets/icon/customer_10.png"),
  business: require("@/modules/module-screenView/assets/icon/customer_11.png"),
};

const BankImgEnum = {
  4: require(`../assets/bank_4.png`), // 工商
  7: require(`../assets/bank_7.png`), // 建设
  8: require(`../assets/bank_8.png`), // 交通
  9: require(`../assets/bank_9.png`), // 邮政
  5: require(`../assets/bank_5.png`), // 农业
  3: require(`../assets/bank_5.png`), // 农业发展
  10: require(`../assets/bank_10.png`), // 招商
  32: require(`../assets/bank_32.png`), // 稠州
  27: require(`../assets/bank_27.png`), // 温州
  34: require(`../assets/bank_27.png`), // 温州民商
  28: require(`../assets/bank_28.png`), // 台州
};

export default {
  mixins: [viewMixin],
  components: {
    selectInput,
    leftView,
    leftViewLevel2,
    leftViewLevel5,
    rightView,
    rightViewLevel2,
    rightViewLevel5,
    leftViewActivity,
    rightViewActivity,
    SystemIntroduceDialog,
    searchPart,
    // mapservice,
    // mapSwitch,
    backPart,
    roleSwitch,
    customerFilter,
    legendPart,
    mapTile,
    trends,
    popUpCollect,
    customerDetail,
    serviceDetail,
    ruralFinancialPointDetail,
    ScaleScreen,
    viewDialog,
    pointDialog1,
    pointDialog2,
    pointDialog3,
    pointDialog4,
    countTo,
  },
  data() {
    return {
      loading: false,
      showDialog: false,
      dialogTitle: "",
      infoMessage: [
        {
          id: 1,
          icon: "icon1",
          title: "现金服务网格数量",
          num: "21535",
        },
        {
          id: 2,
          icon: "icon2",
          title: "银行网点数量",
          num: "12332",
        },
        {
          id: 3,
          icon: "icon3",
          title: "现金服务点",
          num: "750",
        },
        {
          id: 4,
          icon: "icon4",
          title: "农村金融服务站",
          num: "12073",
        },
      ],
      screenStyle: null,
      pointShow1: false,
      pointShow2: false,
      pointShow3: false,
      pointData3: {},
      pointShow4: false,
      pointData4: {},
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
      pointLayer1: null, // 银行网点
      pointLayer2: null, // 金监网点
      pointLayer3: null, // 飞线-银行、金监
      showCashService: false,
      showRuralPoint: false,
      pointDefaultInfo: {},
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
      //动态效果
      lineLayerA: null,
      cycleAnimateTimer: null,
      point1: [120, 30],
      point2: [120, 32.5],
      movePoints: [],
      // [
      //   [120, 32.1],
      //   [121, 32.9],
      //   [121, 29.5],
      // ]
      point1To2: [],
      lineCoordinates: [],
      pointLayerA: null,
      bankList: [], // 当前区域银行网点列表
      allBankList: [], // 一级区域银行网点列表
      bigBankList: [], // 特别银行锚点
      supervisionList: [], // 当前区域金融监管局列表
      allSupervision: {}, // 所有金监列表
      recordTotalPoint: {}, // 所有统计图层count
      orgCenterPoints: [], // 区域中心点
      randomMoveTimer: null, // 轨迹循环移动定时任务
      curRandomLineType: "0",
      lineColor: {
        0: {
          color1: "rgba(255, 115, 116, 0.6)",
          color2: "255, 115, 116",
        },
        1: {
          color1: "rgba(255, 250, 65, 0.6)",
          color2: "255, 250, 65",
        },
      },
    };
  },
  computed: {
    chartsLabelList() {
      return this.boundaryInfo.map((item) => item.properties.name);
    },
    activityDetail() {
      return this.$store.getters["screenView/activityDetail"];
    },
    rightFlag() {
      return this.$store.getters["screenView/rightFlag"];
    },
    orgInfo() {
      return this.$store.getters["screenView/orgInfo"];
    },
    showSystem() {
      return this.$store.getters["screenView/systemIntroduce"];
    },
    orgName() {
      return this.$store.getters["screenView/orgInfo"]?.name ?? "";
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
    riskPointStatus() {
      return this.$store.getters["screenView/riskPoint"];
    },
    policeStatus() {
      return this.$store.getters["screenView/police"];
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
    pointStatus1() {
      return this.$store.getters["screenView/pointStatus1"];
    },
    pointStatus2() {
      return this.$store.getters["screenView/pointStatus2"];
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
        // 清空轨迹图层
        clearInterval(this.cycleAnimateTimer);
        clearTimeout(this.randomMoveTimer);
        this.pointLayerA && this.pointLayerA.clear();
        this.lineLayerA && this.lineLayerA.clear();
        this.lineLayerB && this.lineLayerB.clear();
        this.bankPointLayer && this.bankPointLayer.clear();
        this.pointLayer3 && this.pointLayer3.clear();
        this.businessPointLayer && this.businessPointLayer.clear();
        this.highlightLayer && this.highlightLayer.clear();
        this.renderRiskPoint();
        this.renderPolice();
        console.log("level层级: ", val);
        if (val == 3 && this.orgCode == "330324") {
          this.renderCustomerPoints();
          this.renderBoundary1();
        }

        // 热力图时不显示锚点
        if (!val || val == 1) {
          this.pointLayer1 && this.pointLayer1.clear();
          this.pointLayer2 && this.pointLayer2.clear();
        }

        this.showLevelView = val;
        if (val === 5) {
          this.infoMessage = [
            {
              id: 1,
              icon: "icon5",
              title: "人工现金柜台",
              num: "16",
            },
            {
              id: 2,
              icon: "icon6",
              title: "自助现金机具数",
              num: "28",
            },
            {
              id: 3,
              icon: "icon7",
              title: "小面额自助现金机具数",
              num: "25",
            },
          ];
        } else if (val === 2 || val === 3 || val === 4) {
          this.infoMessage = [
            {
              id: 1,
              icon: "icon1",
              title: "现金服务网格数量",
              num: "3332",
            },
            {
              id: 2,
              icon: "icon2",
              title: "银行网点数量",
              num: "1314",
            },
            {
              id: 3,
              icon: "icon3",
              title: "现金服务点",
              num: "54",
            },
            {
              id: 4,
              icon: "icon4",
              title: "农村金融服务站",
              num: "2686",
            },
          ];
        } else {
          this.infoMessage = [
            {
              id: 1,
              icon: "icon1",
              title: "现金服务网格数量",
              num: "22474",
            },
            {
              id: 2,
              icon: "icon2",
              title: "银行网点数量",
              num: "12778",
            },
            {
              id: 3,
              icon: "icon3",
              title: "现金服务点",
              num: "750",
            },
            {
              id: 4,
              icon: "icon4",
              title: "农村金融服务站",
              num: "12073",
            },
          ];
        }
      },
      immediate: true,
      deep: true,
    },
    activityDetail: {
      handler(v) {
        this.toggleActivityLayer(v);
      },
    },
    depVisiable: {
      handler(v) {
        this.toggleDep(v);
      },
    },
    activityVisiable: {
      handler(v) {
        this.toggleActivity(v);
      },
    },
    orgCode: {
      handler(val) {
        if (val && val != "point") {
          this.orgDataFun();
        } else if (!val) {
          this.$store.commit("screenView/SET_ORG_INFO", {});
          this.$store.commit("screenView/SET_ORG_LIST", []);
          this.boundaryLayer && this.boundaryLayer.clear();
        }
      },
      deep: true,
    },
    filterParam: {
      handler(value) {
        const { groupCategory } = value;
        if (!this.ifInit) {
          this.getPointData();
        }
      },
      deep: true,
    },
    customerHot: {
      handler(val) {
        if (!this.ifInit) {
          this.getPointData();
        }
      },
    },
    roleParam: {
      handler() {
        if (!this.ifInit) {
          this.getPointData();
        }
      },
      deep: true,
    },
    creditHot: {
      handler(val) {
        if (!this.ifInit) {
          this.creditIter = this.getBoundary();
          this.creditIter.next();
        }
      },
    },
    anchorInfo: {
      handler(val) {
        this.searchAnchor(val);
      },
      deep: true,
    },
    mapType: {
      handler(val) {
        if (val === "1") {
          this.olMap.baseLayer.hide();
          this.road.hide();
          this.normalLayer.show();
        } else {
          this.olMap.baseLayer.show();
          this.road.show();
          this.normalLayer.hide();
        }
      },
    },
    serviceFlag: {
      handler(val) {
        if (!this.ifInit) {
          // 显示金融监管局锚点
          this.togglePointLayer2(val);
        }
      },
      immediate: true,
    },
    ruralFinancialFlag: {
      handler(val) {
        if (!this.ifInit) {
          // 显示银行网点
          this.togglePointLayer1(val);
        }
      },
    },
    bankList: {
      handler(data) {
        if (data.length) {
          this.drawAmountLayer().then(() => {
            this.randomChangeAmountPoint();
          });
        }
      },
      deep: true,
    },
    riskPointStatus: {
      handler() {
        this.renderRiskPoint();
      },
    },
    policeStatus: {
      handler() {
        this.renderPolice();
      },
    },
    pointStatus1: {
      handler() {
        this.renderCustomerPoints();
      },
    },
    pointStatus2: {
      handler() {
        this.renderCustomerPoints();
      },
    },
  },
  mounted() {
    // window.addEventListener("resize", debounce(500, this.updateBox));
    this.initMap();
    this.scaleStyle();
    this.getSupervisionList().then((data) => {
      data.forEach((item) => {
        this.allSupervision[item.id] = { ...item };
        this.allSupervision[item.areaCode] = { ...item };
        this.allSupervision[`${item.lon},${item.lat}`] = { ...item };
      });
    });

    this.$EventBus.$on("flyWireStart", (eventType) => {
      if (eventType.pointType == 1) {
        this.list[0].value -= 50;
        this.list[0].add -= 50;
        this.list[4].value -= 50;
        this.list[5].value -= 50;
        setTimeout(() => {
          this.list[0].value += 51;
          this.list[0].add += 51;
          this.list[4].value += 51;
          this.list[5].value += 51;
        }, 400);
      } else if (eventType.pointType == 2) {
        setTimeout(() => {
          this.list[1].value += 51;
          this.list[3].value += 51;
        }, 400);
        this.list[1].value -= 50;
        this.list[3].value -= 50;
      }
    });
  },
  // beforeDestroy() {
  //   window.removeEventListener("resize", this.updateBox);
  // },
  methods: {
    /** toggle银行网点锚点 */
    togglePointLayer1(type) {
      if (!this.pointLayer1) return;
      if (!this.orgInfo.level || this.orgInfo.level == "1") {
        return this.pointLayer1.clear();
      }

      if (!type) {
        this.pointLayer1.clear();
      } else {
        this.renderPoint(this.bankList, {
          layer: this.pointLayer1,
          img: IMG_TYPE.bankPoint,
          useBankImg: true,
        });
      }
    },
    /** toggle金监锚点 */
    togglePointLayer2(type) {
      if (!this.pointLayer2) return;
      if (!this.orgInfo.level || this.orgInfo.level == "1") {
        return this.pointLayer2.clear();
      }

      if (!type) {
        this.pointLayer2.clear();
      } else {
        this.renderPoint(this.supervisionList, {
          layer: this.pointLayer2,
          img: IMG_TYPE.supervisionPoint,
        });
      }
    },
    getLayers() {
      const orgList = JSON.parse(JSON.stringify(this.orgList));
      const [lastObj] = orgList.slice(-1);
      service.category({ code: lastObj.code }).then((res) => {
        this.$store.commit("screenView/SET_LAYERS", res.data.path);
      });
    },
    handlerShow() {
      this.showDialog = true;
      this.dialogTitle = "网点筛选";
    },
    viewLeft(val) {
      console.log(val);
    },
    showIntroduce() {
      this.$store.commit("screenView/SET_SYSTEM_INTRODUCE", true);
    },
    closeIntroduce() {
      this.$store.commit("screenView/SET_SYSTEM_INTRODUCE", false);
    },
    scaleStyle() {
      const width = 1920,
        height = 1080;
      const wrapper = this.$refs.contentWrapper;
      const { width: cW, height: cH } = wrapper.getBoundingClientRect();
      // 初始化即进行缩放
      const hScale = cH / height;
      const wScale = cW / width;
      this.screenStyle = [wScale, hScale];

      // console.log('hScale,', hScale, 'wScale', wScale);

      // const operatorBox = document.getElementById("operatorBox");
      // if (operatorBox) {
      //   operatorBox.style.transformOrigin = 'top left'; // 设置缩放原点
      //   operatorBox.style.transform = `scaleX(${wScale}) scaleY(${hScale})`; // 直接应用缩放变换
      //   //left: 547px;top: 230px;width: 823px;height: 830px;
      //   // 如果需要保持宽高比同时调整位置和尺寸，可以注释掉上面的缩放变换，使用下面的代码
      //   // operatorBox.style.width = 823 * wScale + "px";
      //   // operatorBox.style.height = 830 * hScale + "px";
      //   operatorBox.style.top = 230 * hScale + "px"; // 调整top以保持垂直居中
      //   operatorBox.style.left = 547 * wScale + "px"; // 调整left以保持水平居中
      // }
    },
    // updateBox() {
    //   setTimeout(() => {
    //     this.scaleStyle()
    //   }, 0);
    // },
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
          // url: config.gg_satellite,
          url: (x, y, z) =>
            config.bd_normal
              .replace("{x}", x)
              .replace("{y}", y)
              .replace("{z}", z),
          tileType: "2",
        }
      );
      // this.road = new kxMap.BaseLayer(
      //   this.olMap,
      //   (x, y, z) =>
      //     config.bd_road.replace("{x}", x).replace("{y}", y).replace("{z}", z),
      //   { tilePixelRatio: 2 },
      //   "baiduRoad",
      //   "2"
      // );
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
      // 银行网点、金监网点
      this.bankPointLayer = new kxMap.ClusterLayer(
        this.olMap,
        { zIndex: 3 },
        { distance: 4 },
        {},
        "bankPoint"
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
      // 银行网点图层
      this.pointLayer1 = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 10 },
        "pointLayer1"
      );
      // 金融机构局
      this.pointLayer2 = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 20 },
        "pointLayer2"
      );
      // 飞线-银行网点、金监网点
      this.pointLayer3 = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 10 },
        "pointLayer3"
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
      // 风险点
      this.riskPointLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 10 },
        "riskPointLayer"
      );
      // 警力点
      this.policeLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 10 },
        "policeLayer"
      );
      // 县级-商户、企业点位
      this.businessPointLayer = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 11 },
        "businessPointLayer"
      );
      // 小区、写字楼、园区网格
      this.boundaryLayer1 = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 3 },
        "boundaryLayer1"
      );
      // 高亮图层：点击银行网点后，高亮覆盖距离该网点最近的边界
      this.highlightLayer = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 4 },
        "highlightLayer"
      );
      const highlightStyle = {
        fill: {
          color: "rgba(0, 230, 255, 0.35)",
        },
        stroke: {
          color: "#00e6ff",
          width: 3,
        },
      };
      this.highlightLayer.setStyle(highlightStyle);

      const styleObj = [
        {
          icon: {
            src: (info) => {
              return (
                BankImgEnum[info.properties.bankType] ||
                IMG_TYPE.supervisionPoint
              );
            },
            rotation: (p) => p.rotation,
            scale: 0.45,
          },
          text: {
            offsetY: 10,
            fill: {
              color: (p) => {
                if (p.properties.bankType) return "#dc362e";
                return "#ffb862";
              },
            },
            backgroundFill: {
              color: "#999",
            },
            text: (p) => {
              return p.name.toString();
            },
          },
        },
      ];
      this.pointLayer3.setStyle(styleObj);

      this.overlay = new kxMap.KOverlay(this.olMap, "popup");
      this.control = new this.olMap.BaseViewControl(this.olMap);

      this.initOrgCode();
      this.initCheck();
      this.initSingleclick();
      this.initPointermove();

      this.lineLayer = new kxMap.LineLayer(this.olMap, { zIndex: 4 }); //任务轨迹图层
      this.markLayer = new kxMap.MarkerLayer(this.olMap, { zIndex: 5 }); //任务点图层
      // 边界样式 - 白色
      const boundaryStyle = {
        fill: {
          color: "rgba(255, 255, 255, 0.1)",
        },
        stroke: {
          color: "#fff",
          width: 2,
        },
      };
      this.boundaryLayer.setStyle(boundaryStyle);

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
        // {
        //   icon: {
        //     scale: 0.6,
        //     src: require("@/modules/module-screenView/assets/icon/amountPointNum.png"),
        //   },
        //   zIndex: 1,
        // },
        {
          circle: {
            fill: {
              color: (p) => {
                if (p.pointType == 1) return "#04d1eb";
                return "#ffcb5b";
              },
            },
            stroke: {
              color: "#fff",
              width: 1.3,
            },
            radius: 12,
          },
        },
        {
          text: {
            fill: {
              color: "#fff",
            },
            text: (p) => p.name,
            offsetY: 2,
            font: "bold 12px Arial",
          },
          zIndex: 2,
        },
      ];
      this.amountPointLayer.setStyle(textOption);

      setTimeout(() => {
        const canvas = document.querySelectorAll(
          "#myMap >.ol-viewport >.ol-layers .ol-layer>canvas"
        );
        canvas[0].style.filter = `brightness(0.7) contrast(2) grayscale(5) hue-rotate(360deg) opacity(1) saturate(1.8) sepia(0.94) invert(1)`;
        // canvas[0].style.filter = `brightness(0.6) contrast(1.4) saturate(0.8)`;
        // canvas[0].style.filter = `brightness(0.55) contrast(1.5) saturate(0.65) sepia(0.25) hue-rotate(170deg)`;
        // canvas[0].style.filter = `brightness(0.5) contrast(1.5) saturate(0.6) sepia(0.4) hue-rotate(180deg)`;
      }, 100);
    },
    // 风险点渲染
    renderRiskPoint() {
      if (!this.riskPointLayer) return console.error("地图未初始化");

      this.riskPointLayer.clear();
      if (this.orgInfo.level == "1" && this.riskPointStatus) {
        this.renderPoint(riskPointData, {
          layer: this.riskPointLayer,
          bankType: "riskPoint",
          img: IMG_TYPE.riskPoint,
        });
      }
    },
    // 警力点渲染
    renderPolice() {
      if (!this.policeLayer) return console.error("地图未初始化");

      this.policeLayer.clear();
      // if (this.orgInfo.level == "1" && this.policeStatus) {
      if (this.policeStatus) {
        this.renderPoint(policeData, {
          layer: this.policeLayer,
          bankType: "police",
          img: IMG_TYPE.police,
        });
      }
    },
    // 渲染小区、写字楼、园区
    renderBoundary1() {
      service.boundaryList1().then((res) => {
        const geoData = res.data.features;
        if (!geoData.length) return this.boundaryLayer1.clear();
        const areaColor = {
          1: [62, 212, 149, 0.8], // 绿色
          2: [4, 209, 235, 0.8], // 蓝色
          3: [255, 115, 116, 0.8], // 红色
        };
        geoData.forEach((area) => {
          area.properties.fillColor = areaColor[area.properties.type || "1"];
        });
        const unionData = turf.featureCollection(geoData);
        this.boundaryLayer1.clear();
        this.boundaryLayer1?.addGeoFeature(unionData);
      });
    },
    // 商户、企业点渲染
    renderCustomerPoints() {
      // 商户
      const customerPointList = [];
      // 企业
      const businessPointList = [
        // {
        //   lon: "120.511774537824",
        //   lat: "28.2018567937933",
        //   name: "测试锚点",
        // },
      ];

      service.customerPoints({}).then((res) => {
        const data = res.data || [];
        data.forEach((item) => {
          if (item.type == "商户") {
            customerPointList.push({ ...item });
          } else if (item.type === "企业") {
            businessPointList.push({ ...item });
          }
        });

        this.businessPointLayer.clear();

        // 渲染商户
        if (this.pointStatus1) {
          this.renderPoint(customerPointList, {
            layer: this.businessPointLayer,
            img: IMG_TYPE.customer,
            isClear: false,
          });
        }

        // 渲染企业
        if (this.pointStatus2) {
          this.renderPoint(businessPointList, {
            layer: this.businessPointLayer,
            img: IMG_TYPE.business,
            isClear: false,
          });
        }
      });
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
        [
          "boundary",
          "point",
          "activity",
          "curPoint",
          "amountPointLayer",
          "riskPointLayer",
          "policeLayer",
          "businessPointLayer",
          "pointLayer1",
        ],
        (e, features) => {
          if (features.length > 0) {
            const [feature] = features;
            const properties = feature.getProperties();
            // return;
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

              if (properties.vectorType === "amountPointLayer") {
                if (properties.pointType == "1") {
                  return (this.pointShow1 = true);
                } else {
                  return (this.pointShow2 = true);
                }
              }

              if (properties.vectorType === "riskPointLayer") {
                this.pointData3 = { ...properties };
                return (this.pointShow3 = true);
              }

              // 警力点
              // if (properties.vectorType === "policeLayer") {
              //   this.pointData4 = { ...properties };
              //   return (this.pointShow4 = true);
              // }

              // 商户、企业客户
              if (properties.vectorType === "businessPointLayer") {
                console.log("businessPointLayer： ", properties);
                // this.setColorBoundary();
                // return (this.pointShow4 = true);
                return;
              }

              //
              if (properties.vectorType === "pointLayer1") {
                console.log("银行锚点: ", properties);
                // 点击银行网点 → 高亮距离该网点最近的边界
                this.highlightNearestBoundary(properties);
                if (properties.bankType == "27" && this.orgInfo.level == "3") {
                  this.setColorBoundary();
                  this.pointShow4 = true;
                }
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
        [
          "boundary",
          "point",
          "activity",
          "curPoint",
          "pointLayer1",
          "pointLayer3",
          "riskPointLayer",
          "policeLayer",
          "boundaryLayer1",
          "businessPointLayer",
        ],
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
      // if (this.serviceFlag) {
      //   this.getServicePoint(param);
      //   return;
      // } else if (this.ruralFinancialFlag) {
      //   this.getRuralFinancialPoints(param);
      //   return;
      // }
      const { level } = this.orgInfo;
      // 解决 this.orgInfo.level 为 undefined 的问题 访问了下一级页面直接刷新，会有这个问题
      let isLevel1 = level == 1 || level == undefined; // 顶层网格
      if (isLevel1) {
        service.queryBySelectMini(param).then((res) => {
          let { data } = res;
          let temp = [];
          data.forEach((item) => {
            if (item.lon && item.lat) {
              temp.push(item);
            }
          });
          this.pointInfo = [...temp];
          // 下钻到二三层级的时候展示网格内的锚点数量

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

          // !boolean1 && !this.serviceFlag && !this.ruralFinancialFlag
          // if (!boolean1 && !this.serviceFlag && !this.ruralFinancialFlag) {
          this.pointLayer.clear();
          this.heatmapLayer.clear();
          if (this.customerHot) this.heatmapLayer.addGeoFeature(collection);
          // }
        });

        this.getBankList(param.gridCode).then((data) => {
          this.bankList = data;
          this.togglePointLayer1(this.ruralFinancialFlag);
          this.bigBankList = data.filter((bank) => {
            return !!BankImgEnum[bank.bankType];
          });
        });
      } else {
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
        });
      }
      if ([1, 2, 3].includes(level) && param.gridCode) {
        this.getBankList(param.gridCode).then((data) => {
          this.bankList = data;
          this.togglePointLayer1(this.ruralFinancialFlag);
          this.bigBankList = data.filter((bank) => {
            return !!BankImgEnum[bank.bankType];
          });
        });
        this.getSupervisionList(param.gridCode).then((data) => {
          this.supervisionList = data;
          this.togglePointLayer2(this.serviceFlag);
        });
      }
    },
    /** 查询银行网点 */
    getBankList(areaCode) {
      return new Promise((resolve) => {
        // const level = this.orgInfo?.level;
        const apiData = {
          areaCode: areaCode || "330000",
          includeChildren: true,
        };
        service.bankList(apiData).then((res) => {
          resolve(res.data);
        });
      });
    },
    /** 查询金融监管机构 */
    getSupervisionList(areaCode) {
      return new Promise((resolve) => {
        const apiData = {
          areaCode: areaCode || "330000",
          includeChildren: true,
        };
        service.supervisionList(apiData).then((res) => {
          resolve(res.data);
        });
      });
    },
    // 查询网格内随机两点位
    getAreaSplitPoints(areaCode) {
      return new Promise((resolve) => {
        const apiData = {
          areaCode,
          isCurrent: false,
        };
        service.areaSplitPoints(apiData).then((res) => {
          resolve(res.data);
        });
      });
    },
    renderPoint(pointList, config = {}) {
      const curConfig = {
        isClear: true,
        layer: null,
        img: null,
        useBankImg: false,
        innerData: {},
        ...config,
      };
      if (!curConfig.layer) return console.error("renderPoint未找到图层");
      const pointData = pointList.map((item) => {
        return turf.point([item.lon, item.lat], {
          ...item,
          name: item.name || item.orgName,
          coordinate: [item.lon, item.lat],
          src: !curConfig.useBankImg
            ? curConfig.img
            : BankImgEnum[item.bankType] || curConfig.img,
          ...curConfig.innerData,
        });
      });

      const collection = turf.featureCollection(pointData);
      if (curConfig.isClear) curConfig.layer.clear();
      curConfig.layer.addGeoFeature(collection);
    },
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
          this.loading = false;
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
            // geoData.shift();
            let currentOrg = {};
            if (geoData.length > 1 && this.orgList.length > 1) {
              currentOrg = geoData.shift().properties;
            } else if (geoData.length === 1) {
              currentOrg = geoData[0].properties;
            } else {
              // geoData.splice(1, geoData.length - 1);
              currentOrg = geoData[0].properties;
              geoData.shift();
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
          // 边界更新后清空上次的高亮
          this.highlightLayer && this.highlightLayer.clear();

          let importantGeoData = geoData.filter(
            (i) => i.properties.isCash == "1"
          );
          let unionData = turf.featureCollection(geoData);
          this.boundaryLayer?.clear();
          this.boundaryLayer?.addGeoFeature(unionData);
          this.boundaryLayer?.setExtent();
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
    // 设置网格变色
    setColorBoundary() {
      // 330324104(桥下镇)、330324102(桥头镇)、330324113(金溪镇)
      let hasDistArea = false;
      const geoData = JSON.parse(JSON.stringify(this.boundaryInfo));
      geoData.forEach((area) => {
        if (
          ["330324104", "330324102", "330324113"].includes(area.properties.code)
        ) {
          area.properties.fillColor = [255, 255, 0, 0.5];
          hasDistArea = true;
        }
      });
      if (hasDistArea) {
        // let importantGeoData = geoData.filter(
        //   (i) => i.properties.isCash == "1"
        // );
        let unionData = turf.featureCollection(geoData);
        this.boundaryLayer?.clear();
        this.boundaryLayer?.addGeoFeature(unionData);
        this.boundaryLayer?.setExtent();
        // let importUnionData = turf.featureCollection(importantGeoData);
        // this.importBoundaryLayer.clear();
        // this.importBoundaryLayer.addGeoFeature(importUnionData);
      }
    },
    // 点击银行网点，高亮覆盖距离该网点最近的边界（level 为 4 时随机高亮最近 1-3 个网格）
    highlightNearestBoundary(bank) {
      if (!this.highlightLayer) return;
      this.highlightLayer.clear();
      const lon = bank?.lon ?? bank?.coordinate?.[0];
      const lat = bank?.lat ?? bank?.coordinate?.[1];
      if (lon == null || lat == null) return;
      if (!this.boundaryInfo || !this.boundaryInfo.length) return;

      const point = turf.point([lon, lat]);
      // 计算并排序所有边界到网点的距离
      const distances = this.boundaryInfo
        .map((boundary) => ({
          boundary,
          distance: this.getBoundaryMinDistance(point, boundary),
        }))
        .sort((a, b) => a.distance - b.distance);

      // level 为 4 时随机高亮最近的 1-3 个网格，其余层级只高亮最近的 1 个
      const highlightCount =
        this.orgInfo?.level == 4 ? Math.floor(Math.random() * 3) + 1 : 1;

      const targets = distances
        .slice(0, highlightCount)
        .filter((item) => Number.isFinite(item.distance))
        .map((item) => item.boundary);

      if (!targets.length) return;

      // 克隆并清理可能残留的 fillColor 属性，确保使用高亮图层自身样式
      const features = targets.map((nearest) => {
        const feature = JSON.parse(JSON.stringify(nearest));
        if (feature.properties) {
          delete feature.properties.fillColor;
          delete feature.properties.alpha;
        }
        return feature;
      });
      this.highlightLayer.addGeoFeature(turf.featureCollection(features));
    },
    // 计算点到多边形边界的最短距离（单位：千米）
    getBoundaryMinDistance(point, polygonFeature) {
      const geom = polygonFeature?.geometry;
      if (!geom || !geom.type) return Infinity;
      const rings =
        geom.type === "Polygon"
          ? geom.coordinates
          : geom.type === "MultiPolygon"
          ? geom.coordinates.flat()
          : [];
      let min = Infinity;
      rings.forEach((ring) => {
        if (!ring || ring.length < 2) return;
        const line = turf.lineString(ring);
        const distance = turf.pointToLineDistance(point, line, {
          units: "kilometers",
        });
        if (distance < min) min = distance;
      });
      return min;
    },
    // 下钻到二三层级展示网格内的锚点数量
    async drawAmountLayer(queryPoint = true) {
      return new Promise(async (resolve) => {
        if (queryPoint && this.orgInfo.level < 3) {
          this.orgCenterPoints = await this.getAreaSplitPoints(this.orgCode);
          this.orgCenterPoints.forEach((point) => {
            if (!this.recordTotalPoint[point.code]) {
              this.recordTotalPoint[point.code] = [];
            }

            const randomNum = Math.floor(Math.random() * 100) + 1;

            this.recordTotalPoint[point.code].push({
              pointType: point.pointType,
              count: randomNum,
              code: point.code,
              lon: point.lon,
              lat: point.lat,
            });
          });
        }

        let arr = [];
        // let pointArr = this.pointInfo.map((item) => [item.lon, item.lat]);
        // 原始的
        // this.boundaryInfo.forEach((item) => {
        //   let gridCenter = turf.center(item);
        //   if (this.recordTotalPoint[item.properties.code] == null) {
        //     this.recordTotalPoint[item.properties.code] = 0;
        //   }

        //   const curCount = this.recordTotalPoint[item.properties.code];
        //   let obj = {
        //     count: curCount,
        //     gridCenter: gridCenter.geometry.coordinates,
        //     ...item.properties,
        //   };
        //   console.log("aaaa---------", obj.count, item.properties.code);
        //   // 统计区域内锚点数量
        //   // let polygon = turf.polygon(item.geometry.coordinates[0]);
        //   // pointArr.forEach((target) => {
        //   //   if (turf.booleanPointInPolygon(turf.point(target), polygon))
        //   //     obj.count++;
        //   // });
        //   arr.push(obj);
        // });

        this.boundaryInfo.forEach((item) => {
          const curCode = item.properties.code;
          const list = this.orgCenterPoints.filter((point) => {
            return point.code === curCode;
          });
          list.forEach((point) => {
            const curCount = this.recordTotalPoint[item.properties.code].find(
              (curObj) => curObj.pointType === point.pointType
            ).count;
            let obj = {
              count: curCount,
              gridCenter: [point.lon, point.lat],
              pointType: point.pointType,
              ...item.properties,
            };
            arr.push(obj);
          });
        });
        const pointData = arr.map((item) => {
          let { count, gridCenter, center } = item;
          return {
            coordinate: [gridCenter[0], gridCenter[1] + 0.005],
            // coordinate: [...center],
            type: "Point",
            name: `${count}`,
            pointType: item.pointType,
          };
        });
        this.amountPointLayer.clear();
        this.amountPointLayer.addFeature(pointData);
        resolve(true);
      });
    },

    setView(extent) {
      let padding = [
        200 * this.screenStyle[1],
        540 * this.screenStyle[0],
        20,
        540 * this.screenStyle[0],
      ];
      // let padding = [20, 20, 20, 20];
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
    test() {
      this.initLayer();
      this.createCurves();
      // this.addAnimate();
      this.addAnimateHideWay();
    },
    initLayer() {
      this.pointLayerA = new kxMap.MarkerLayer(
        this.olMap,
        { zIndex: 999 },
        "apointLayerA"
      );
      // 动态路线
      this.lineLayerA = new kxMap.LineLayer(
        this.olMap,
        { zIndex: 999 },
        "lineLayerA"
      );
      // 路线底图
      this.lineLayerB = new kxMap.LineLayer(
        this.olMap,
        { zIndex: 999 },
        "lineLayerB"
      );
    },
    createCurves() {
      if (this.cycleAnimateTimer) clearInterval(this.cycleAnimateTimer);
      this.pointLayer.clear();
      // 生成多条曲线
      const generateCurves = (start, end, count, lineColor) => {
        const curves = [];
        for (let i = 1; i <= count; i++) {
          // 计算起点和终点的中点
          const midPoint = turf.midpoint(start, end).geometry.coordinates;
          // 计算起点和终点的方向
          const bearing = turf.bearing(start, end);

          // 调整中间点的位置，使其向外偏移，形成弧线
          const offsetDistance = 3 * i; // 偏移距离（单位：度）
          const offsetMidPoint = turf.destination(
            midPoint,
            offsetDistance,
            bearing + 90
          ).geometry.coordinates;

          // 生成贝塞尔曲线
          const curve = turf.bezierSpline(
            turf.lineString([start, offsetMidPoint, end])
          );
          curve.properties.color = lineColor;
          curves.push(curve);
        }
        return curves;
      };

      this.point1To2 = generateCurves(this.point1, this.point2, 1, "blue");
      // this.point1To2.forEach((line) => {
      // this.lineLayerA.addFeature({
      //   coordinates: line.geometry.coordinates,
      //   width: 3,
      //   // color: "#fff",
      //   color: "rgb(255, 115, 116)",
      // });
      // });
    },
    addAnimateOld() {
      if (this.cycleAnimateTimer) clearInterval(this.cycleAnimateTimer);
      //运动轨迹线
      this.lineCoordinates = this.generateMultiCurves(this.movePoints);
      let length = this.lineCoordinates.length;
      let index = 0;
      this.cycleAnimateTimer = setInterval(() => {
        this.lineLayerA.clear();
        let i = index % length; //当前点位下标
        let iNext = (index + 10) % length; //下一个点位的下标
        let dx = this.lineCoordinates[i][0] - this.lineCoordinates[iNext][0];
        let dy = this.lineCoordinates[i][1] - this.lineCoordinates[iNext][1];
        let rotation = Math.atan2(dy, dx); //从x轴到点(dy,dx)的角度
        let points = [
          {
            coordinate: this.lineCoordinates[i],
            rotation: -rotation + Math.PI / 2, //偏转的角度
          },
        ];
        this.pointLayerA.clear();
        const iconOption = [
          {
            icon: {
              src: require("@/modules/module-screenView/assets/icon/point.png"),
              rotation: (p) => p.rotation,
              scale: 0.35,
            },
          },
        ];
        this.pointLayerA.setStyle(iconOption); //设置运动点位的样式
        this.pointLayerA.addFeature(points);
        index += 4;
        let cCoordinates = JSON.parse(JSON.stringify(this.lineCoordinates));
        cCoordinates.length = i + 1; //设置边框折线跟随点位进行运动
        const whiteLine = {
          coordinates: cCoordinates,
          name: "轨迹线",
          color: "#fff",
          width: 2, //宽度
          // src: require("@/modules/module-screenView/assets/icon/point.png"),
        };
        this.lineLayerA.addFeature(whiteLine);

        if (cCoordinates.length > length - 4) {
          // this.pointLayerA.clear();
          // this.lineLayerA.clear();
          this.randomMoveTimer = setTimeout(() => {
            this.randomChangeAmountPoint();
          }, 3000);
          clearInterval(this.cycleAnimateTimer);
        }
      }, 20);
    },
    addAnimate() {
      if (this.cycleAnimateTimer) clearInterval(this.cycleAnimateTimer);

      this.lineCoordinates = this.generateMultiCurves(this.movePoints);

      const line = turf.lineString(this.lineCoordinates);

      const totalDistance = turf.length(line); // km

      const speed = 20; // km/s（统一速度）
      const duration = (totalDistance / speed) * 1000;

      const startTime = Date.now();

      const interval = 20;

      this.cycleAnimateTimer = setInterval(() => {
        this.lineLayerA.clear();

        const elapsed = Date.now() - startTime;
        let progress = elapsed / duration;

        if (progress > 1) progress = 1;

        // 当前距离
        const dist = totalDistance * progress;

        // 当前点（关键：按距离取点）
        const current = turf.along(line, dist).geometry.coordinates;

        // 前一帧点（用于算方向）
        const prevDist = Math.max(0, dist - 0.01);
        const prev = turf.along(line, prevDist).geometry.coordinates;

        let dx = current[0] - prev[0];
        let dy = current[1] - prev[1];
        let rotation = Math.atan2(dy, dx);

        // ===== 移动点 =====
        this.pointLayerA.clear();
        this.pointLayerA.setStyle([
          {
            icon: {
              src: require("@/modules/module-screenView/assets/icon/point.png"),
              rotation: () => -rotation + Math.PI / 2,
              scale: 0.35,
            },
          },
        ]);

        this.pointLayerA.addFeature([
          {
            coordinate: current,
            rotation: -rotation + Math.PI / 2,
          },
        ]);

        // ===== 轨迹线 =====
        const passed = turf.lineSlice(
          turf.point(this.lineCoordinates[0]),
          turf.point(current),
          line
        );

        // this.lineLayerA.addFeature({
        //   coordinates: passed.geometry.coordinates,
        //   color: "#fff",
        //   width: 2,
        // });
        // this.lineLayerB.addFeature({
        //   coordinates: passed.geometry.coordinates,
        //   color: "#fff",
        //   width: 2,
        // });

        // ===== 结束 =====
        if (progress >= 1) {
          clearInterval(this.cycleAnimateTimer);

          this.randomMoveTimer = setTimeout(() => {
            this.randomChangeAmountPoint();
          }, 3000);
        }
      }, interval);
    },
    // 渐隐轨迹(原本)
    addAnimateHideWaySource() {
      if (this.cycleAnimateTimer) clearInterval(this.cycleAnimateTimer);

      this.lineCoordinates = this.generateMultiCurves(this.movePoints);

      let length = this.lineCoordinates.length;
      let index = 0;

      const trailLength = 100; // 尾迹长度（可调）

      this.cycleAnimateTimer = setInterval(() => {
        this.lineLayerA.clear();

        let i = index % length;
        let iNext = (index + 2) % length; // 建议改小，避免跳跃

        let dx = this.lineCoordinates[i][0] - this.lineCoordinates[iNext][0];
        let dy = this.lineCoordinates[i][1] - this.lineCoordinates[iNext][1];
        let rotation = Math.atan2(dy, dx);

        // ===== 移动点 =====
        let points = [
          {
            coordinate: this.lineCoordinates[i],
            rotation: -rotation + Math.PI / 2,
          },
        ];

        this.pointLayerA.clear();

        const iconOption = [
          {
            icon: {
              src: require("@/modules/module-screenView/assets/icon/point.png"),
              rotation: (p) => p.rotation,
              scale: 0.35,
            },
          },
        ];

        // this.pointLayerA.setStyle(iconOption);
        this.pointLayerA.addFeature(points);

        // ===== 尾迹渐隐（核心改造）=====
        let start = Math.max(0, i - trailLength);
        let trailCoords = this.lineCoordinates.slice(start, i);

        for (let j = 0; j < trailCoords.length - 1; j++) {
          const p1 = trailCoords[j];
          const p2 = trailCoords[j + 1];

          // 渐隐（尾巴透明 → 头部更亮）
          const alpha = j / trailCoords.length;

          this.lineLayerA.addFeature({
            coordinates: [p1, p2],
            color: `rgba(255,255,255,${alpha})`,
            width: 2,
          });
        }

        index += 2;

        // ===== 结束条件 =====
        if (i >= length - 2) {
          clearInterval(this.cycleAnimateTimer);

          this.randomMoveTimer = setTimeout(() => {
            this.randomChangeAmountPoint();
          }, 3000);
        }
      }, 20);
    },
    addAnimateHideWay() {
      if (this.cycleAnimateTimer) clearInterval(this.cycleAnimateTimer);

      this.lineCoordinates = this.generateMultiCurves(this.movePoints);

      const line = turf.lineString(this.lineCoordinates);
      const totalDistance = turf.length(line); // km

      const speed = 70; // km/s 飞线图速度
      const duration = (totalDistance / speed) * 1000;

      const startTime = Date.now();
      const interval = 10;

      // 尾迹长度（按距离）
      const trailDistance = totalDistance * 0.2;

      this.lineLayerB.clear();
      this.lineLayerB.addFeature({
        coordinates: this.lineCoordinates,
        // color: "rgba(255,255,255,0.5)", // 淡色
        color: `${this.lineColor[this.curRandomLineType].color1}`, // 淡色
        width: 3,
        src: IMG_TYPE.hideArrow,
      });

      this.cycleAnimateTimer = setInterval(() => {
        this.lineLayerA.clear();

        const elapsed = Date.now() - startTime;
        let progress = elapsed / duration;
        if (progress > 1) progress = 1;

        // ===== 当前距离 =====
        const dist = totalDistance * progress;

        // ===== 当前点 =====
        const current = turf.along(line, dist).geometry.coordinates;

        // ===== 前一点（算方向）=====
        const prevDist = Math.max(0, dist - 0.01);
        const prev = turf.along(line, prevDist).geometry.coordinates;

        let dx = current[0] - prev[0];
        let dy = current[1] - prev[1];
        let rotation = Math.atan2(dy, dx);

        // ===== 移动点 =====
        this.pointLayerA.clear();
        const iconOption = [
          {
            icon: {
              // src: require("@/modules/module-screenView/assets/icon/point.png"),
              src: IMG_TYPE.lineHead,
              rotation: (p) => p.rotation,
              scale: 0.35,
            },
          },
        ];
        this.pointLayerA.setStyle(iconOption);
        this.pointLayerA.addFeature([
          {
            coordinate: current,
            rotation: -rotation + Math.PI / 2,
          },
        ]);

        // ===== 尾迹（均匀采样 + 渐隐）=====
        const startDist = Math.max(0, dist - trailDistance);

        const sampleCount = 100; // ✅ 越大越丝滑（推荐 50~100）
        const coords = [];

        for (let i = 0; i <= sampleCount; i++) {
          const d = startDist + (i / sampleCount) * (dist - startDist);
          const pt = turf.along(line, d).geometry.coordinates;
          coords.push(pt);
        }

        // 渐隐绘制
        for (let i = 0; i < coords.length - 1; i++) {
          const t = i / coords.length;

          // 👉 渐隐曲线（比线性更自然）
          const alpha = t * t;

          this.lineLayerA.addFeature({
            coordinates: [coords[i], coords[i + 1]],
            // color: `rgba(255,255,255,${alpha})`,
            // color: `rgba(255, 115, 116,${alpha})`,
            color: `rgba(${
              this.lineColor[this.curRandomLineType].color2
            },${alpha})`,
            width: 2,
          });
        }

        // ===== 结束 =====
        if (progress >= 1) {
          clearInterval(this.cycleAnimateTimer);

          this.randomMoveTimer = setTimeout(() => {
            this.randomChangeAmountPoint();
          }, 3000);
        }
      }, interval);
    },
    generateMultiCurves(points) {
      let allCoords = [];

      for (let i = 0; i < points.length - 1; i++) {
        const start = points[i];
        const end = points[i + 1];

        const midPoint = turf.midpoint(start, end).geometry.coordinates;
        const bearing = turf.bearing(start, end);

        const offsetMidPoint = turf.destination(midPoint, 3, bearing + 90)
          .geometry.coordinates;

        const curve = turf.bezierSpline(
          turf.lineString([start, offsetMidPoint, end])
        );

        let coords = curve.geometry.coordinates;

        // ⚠️ 去重（避免段与段之间重复点）
        if (i > 0) coords.shift();

        allCoords.push(...coords);
      }

      return allCoords;
    },
    getRandomArea() {
      const randomBankIndex = Math.floor(
        Math.random() * this.bigBankList.length
      );
      const curBank = this.bigBankList[randomBankIndex];
      const curAreaCode = curBank.areaCode;
      const curSupervision = this.allSupervision[curAreaCode];

      if (curSupervision) {
        return { curAreaCode, curBank, curSupervision };
      } else {
        return this.getRandomArea();
      }
    },
    getParentSupervisionList(areaCode) {
      if (!this.allSupervision[areaCode])
        return console.error("未获取到区域内金监数据");

      const arr = [];
      const lastSupervision = this.allSupervision[areaCode];
      const nextSupervision = this.allSupervision[lastSupervision.parentId];
      const firstSupervision = this.allSupervision[nextSupervision?.parentId];

      if (lastSupervision) arr.push([lastSupervision.lon, lastSupervision.lat]);
      if (nextSupervision) arr.push([nextSupervision.lon, nextSupervision.lat]);
      if (firstSupervision)
        arr.push([firstSupervision.lon, firstSupervision.lat]);

      return arr;
    },
    // 随机金监锚点轨迹
    randomChangeAmountPoint() {
      if (!this.amountPointLayer) return console.error("轨迹图层不存在!!!");
      if (!this.bankList.length) return console.error("当前网格不存在银行网点");

      // 清空轨迹图层
      this.pointLayerA && this.pointLayerA.clear();
      this.lineLayerA && this.lineLayerA.clear();
      this.lineLayerB && this.lineLayerB.clear();

      // 县及以下取消飞线
      if (this.orgInfo.level >= 3) {
        return;
      }

      // 随机区域统计+1
      // 获取区域内随机网点锚点
      const { curAreaCode, curBank, curSupervision } = this.getRandomArea();
      const randomPointIndex = Math.round(Math.random());
      this.curRandomLineType = randomPointIndex;
      this.recordTotalPoint[curAreaCode][randomPointIndex].count += 1;

      // 发起飞线事件
      this.$EventBus.$emit("flyWireStart", {
        pointType:
          this.recordTotalPoint[curAreaCode][randomPointIndex].pointType,
      });
      this.drawAmountLayer(false);

      // 获取区域内金监锚点
      // 获取对应上级金监锚点（根据parentId）
      const supervisionList = this.getParentSupervisionList(curAreaCode);
      const centerPoint = this.recordTotalPoint[curAreaCode][randomPointIndex];
      supervisionList.unshift(
        [centerPoint.lon, centerPoint.lat],
        [curBank.lon, curBank.lat]
      );
      this.movePoints = supervisionList;

      // 显示飞线锚点
      const flyPointList = [];
      this.pointLayer3.clear();
      supervisionList.forEach((point, index) => {
        if (index > 0) {
          if (index === 1) {
            const curBank = this.bankList.find((item) => {
              return item.lon == point[0] && item.lat == point[1];
            });
            const curItem = {
              coordinate: [...point],
              name: curBank?.orgName,
              src: BankImgEnum[curBank.bankType] || IMG_TYPE.bankPoint,
              lat: point[1],
              lon: point[0],
            };
            flyPointList.push(curItem);
            this.renderPoint([curItem], {
              layer: this.pointLayer3,
              isClear: false,
              img: curItem.src,
              innerData: {
                bankType: curBank.bankType,
              },
            });
          } else {
            const curSupervision =
              this.allSupervision[`${point[0]},${point[1]}`];
            if (!curSupervision) console.error("未找到飞线金监锚点: ", point);
            const curItem = {
              coordinate: [...point],
              name: curSupervision?.name,
              src: IMG_TYPE.supervisionPoint,
              lat: point[1],
              lon: point[0],
            };
            flyPointList.push(curItem);
            this.renderPoint([curItem], {
              layer: this.pointLayer3,
              isClear: false,
              img: curItem.src,
            });
          }
        }
      });

      // 绘制轨迹路线
      if (this.movePoints.length) this.test();
    },
  },
};
</script>
<style lang="scss">
.mypopper {
  background: rgba(6, 14, 57, 0.5);
  border: 1px solid #004085;

  .el-select-dropdown__item {
    background: rgba(6, 14, 57, 0.5);
    // border: 1px solid #004085;
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
  // position: absolute;
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
      547px 294px,
      calc(100% - 553px) 294px,
      calc(100% - 553px) calc(100% - 30px),
      547px calc(100% - 30px),
      547px 100%,
      100% 100%,
      100% 0
    );
  }
}

.left-icon {
  pointer-events: auto;
  position: absolute;
  left: 30px;
  top: 180px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px 5px;
  background: linear-gradient(to bottom, #4f6970, #121f25);
  border-radius: 5px;
  cursor: pointer;

  .text {
    color: #fff;
    // font-size: 16px;
    margin-top: 7.5px;
  }

  .img {
    width: 50%;
    height: 50%;
    display: block;
  }
}

.centerBox {
  width: 824px;
  position: absolute;
  left: 546px;
  top: 100px;
  height: 1009px;

  .center-header {
    width: 100%;
    height: 170px;

    &.p50 {
      padding: 0 60px;
    }

    .title {
      margin: 0 auto 5px;
      height: 20px;
      /* 自动布局 */
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
    left: -40px;
    top: 152px;
    background: url("~@/modules/module-screenView/assets/image/mapbg.png")
      no-repeat;
    background-size: 100% 100%;
    width: 904px;
    height: 844px;
  }
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

.view {
  position: relative;
  width: 100%;
  height: 100%;
}

.map {
  position: absolute;
  width: 100%;
  height: 729px;
  left: 0;
  bottom: 0;
}

.operate-wrapper {
  position: absolute;
  left: 20px;
  top: 214px;
  z-index: 999;
  pointer-events: auto;
}

.bottom-wrapper-left {
  position: absolute;
  bottom: 60px;
  left: 20px;
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
  top: 214px;
  z-index: 999;
  pointer-events: auto;
  display: flex;
  // align-items: center;
}

.button-name {
  margin-left: 10px;
  width: 100px;
  height: 36px;
  background: url("~@/modules/module-screenView/assets/image/btn_map.png")
    no-repeat;
  background-size: 100%;
  font-size: 16px;
  color: #fff;
  text-align: center;
  line-height: 36px;
  cursor: pointer;
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
    // background: linear-gradient(90deg, #ece9c612 0%, #8a6b1e40 100%);
  }

  &.group-blue > .top-cards {
    grid-template-columns: repeat(4, 1fr);
    // background: linear-gradient(90deg, #c6d8ec12 0%, #1e538a5c 100%);
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
