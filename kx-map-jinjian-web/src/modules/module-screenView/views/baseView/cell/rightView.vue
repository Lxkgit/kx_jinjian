<template>
  <div class="right-view-wrapper">
    <!-- <titleBox class="wrapper-top" height="299px" title="风险预警">
      <riskalert />
    </titleBox> -->
    <titleBox
      class="wrapper-top risk"
      height="299px"
      title="风险预警"
      v-if="orgLevel != 5"
    >
      <div slot="bg" class="left"></div>
      <viewAll slot="operate"></viewAll>
      <riskalert
        :orgLevel="orgLevel"
        :orgCode="gridCode"
        :orgName="currentOrgName"
      />
    </titleBox>
    <titleBox
      class="wrapper-top"
      height="299px"
      title="风险趋势"
      v-if="orgLevel == 5"
    >
      <viewAll slot="operate"></viewAll>
      <barOne :info="barOneData" />
    </titleBox>
    <titleBox class="wrapper-top" height="299px" title="线索类型分布">
      <viewAll slot="operate"></viewAll>
      <pieRight :info="pieData" />
    </titleBox>
    <titleBox class="wrapper-top" height="299px" title="区域高风险TOP5">
      <viewAll slot="operate"></viewAll>
      <barBottom :items="bottomData" />
    </titleBox>
    <!-- 贷款模块查看全部 -->
    <viewDialog
      v-model="showDialog"
      :title="dialogTitle"
      :annotation="annotation"
    >
      <component
        :is="ComponentId"
        :showGrid="showGrid"
        :value="viewAllData"
      ></component>
    </viewDialog>
    <!-- 提示框 -->
    <!-- <tooltipDialog v-model="showTooltip" :is-top="false" :is-left="false">
      <component :is="componentId" :dataList="dataList" :tableData="tableData"></component>
    </tooltipDialog> -->
  </div>
</template>

<script>
import services from "../api";
import titleBox from "@/modules/module-screenView/components/titleBox";
import cardTwo from "@/modules/module-screenView/viewComponents/components/cardTwo";
import gridRankList from "@/modules/module-screenView/viewComponents/components/gridRankList";
import barBv from "@/modules/module-screenView/viewComponents/components/barBv";
import barTwo from "@/modules/module-screenView/viewComponents/components/barTwo";
import cardBigTwo from "@/modules/module-screenView/viewComponents/components/cardBigTwo";
import gridRadar from "@/modules/module-screenView/viewComponents/components/gridRadar";
import wordCloud from "@/modules/module-screenView/viewComponents/components/wordCloud";
import gridRankHot from "@/modules/module-screenView/viewComponents/components/gridRankHot";
import boxTitle from "@/modules/module-screenView/viewComponents/components/boxTitle";
import barOne from "@/modules/module-screenView/viewComponents/components/barOne";
import pieRight from "@/modules/module-screenView/viewComponents/components/pieRight/index.vue";
import barBottom from "@/modules/module-screenView/viewComponents/components/barBottom/index.vue";
import depositCard from "@/modules/module-screenView/viewComponents/components/depositCard";
import MediumBox from "@/modules/module-screenView/viewComponents/components/mediumBox";
import viewAll from "@/modules/module-screenView/viewComponents/components/viewAll";
import ftpBox from "@/modules/module-screenView/viewComponents/components/ftpBox";
import SmallBox from "@/modules/module-screenView/viewComponents/components/smallBox";
import progressInfo from "@/modules/module-screenView/viewComponents/components/progressInfo/index.vue";
import viewDialog from "@/modules/module-screenView/viewComponents/components/viewDialog";
import viewTooltip from "@/modules/module-screenView/viewComponents/components/viewTooltip";
import tooltipDialog from "@/modules/module-screenView/viewComponents/components/tooltipDialog";
import businessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/businessList";
import badnessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/badnessList";
import littleBox from "@/modules/module-screenView/viewComponents/components/littleBox";
import infoBox from "@/modules/module-screenView/viewComponents/components/infoBox.vue";
import infoList from "@/modules/module-screenView/viewComponents/components/infoList.vue";
import policeInfo from "@/modules/module-screenView/viewComponents/components/policeInfo";
import rightDialog from "../components/rightDialog";
import infoListBox from "../components/infoListBox/index.vue";
import riskalert from "@/modules/module-screenView/viewComponents/components/riskalert.vue";
import { dataList, tableData, creditData } from "../config/rightConfig";
import service from "@/modules/module-gridManagement/views/gridManagement/api";
import {
  getCashPointInGrid,
  getFinancialInGrid,
} from "@/components/NetworkDetail/api";
import InfoList from "../../../../../components/BaseDetail/components/infoList.vue";
export default {
  dicts: ["new_grid_type", "basic_org_type"],
  components: {
    titleBox,
    viewAll,
    pieRight,
    cardTwo,
    gridRankList,
    barBv,
    cardBigTwo,
    gridRadar,
    wordCloud,
    gridRankHot,
    barTwo,
    boxTitle,
    barOne,
    barBottom,
    depositCard,
    MediumBox,
    ftpBox,
    SmallBox,
    progressInfo,
    viewDialog,
    viewTooltip,
    tooltipDialog,
    littleBox,
    infoBox,
    infoList,
    policeInfo,
    infoListBox,
    InfoList,
    riskalert,
  },
  props: ["showLevelView", "chartsLabelList"],
  data() {
    return {
      showGrid: "top",
      viewAllData: {},
      cardTwoData: [
        {
          title: "预约兑换笔数",
          value: "13",
          unit: "万次",
        },
        {
          title: "预约兑换总金额",
          value: "7083",
          unit: "万元",
        },
        {
          title: "预约兑换完成率",
          value: "95",
          unit: "%",
        },
        {
          title: "预约兑换率",
          value: "99",
          unit: "%",
        },
      ],
      rankColumns: [
        { label: "劵别", prop: "type", flex: 1 },
        { label: "预约劵别数", prop: "type1", flex: 1 },
        { label: "占比", prop: "type2", flex: 1 },
      ],
      rankData: [
        { type: "20元", type1: "36万", type2: "12.7%" },
        { type: "10元", type1: "54万", type2: "19%" },
        { type: "5元", type1: "50万", type2: "17.6%" },
        { type: "1元", type1: "101万", type2: "36%" },
        { type: "5角", type1: "18万", type2: "6%" },
        { type: "1角", type1: "24万", type2: "8%" },
      ],

      barOneData: {
        title: "线索报送量",
        xData: [
          "1月",
          "2月",
          "3月",
          "4月",
          "5月",
          "6月",
          "7月",
          "8月",
          "9月",
          "10月",
          "11月",
          "12月",
        ],
        yData: [120, 98, 156, 142, 178, 135, 189, 167, 145, 198, 133, 172],
        lineTitle: "线索处置量",
        lineData: [95, 82, 130, 118, 155, 110, 165, 148, 125, 172, 115, 150],
      },

      barBvData: {
        legendData: ["零钞预约次数", "零钞预约兑换金额"],
        yData: [
          "杭州",
          "宁波",
          "温州",
          "绍兴",
          "湖州",
          "嘉兴",
          "金华",
          "衢州",
          "舟山",
          "台州",
          "丽水",
        ],
        leftDat: [],
        rightData: [
          23000, 12000, 18000, 9000, 5000, 14000, 19000, 11000, 7000, 12500,
          4000,
        ],
        // rightData: [46000, 240, 360, 180, 100, 280, 380, 220, 140, 250, 80],
      },

      cardBigTwoData: [
        {
          title: "网格内摸排商户数",
          value: "982,727",
        },
        {
          title: "网格内现金收付摸排次数",
          value: "123,129",
        },
      ],

      barTwoData: {
        legendData: ["红牌商户", "黄牌商户"],
        xData: [
          "杭州",
          "宁波",
          "温州",
          "绍兴",
          "湖州",
          "嘉兴",
          "金华",
          "衢州",
          "舟山",
          "台州",
          "丽水",
        ],
        yData1: [187, 452, 276, 123, 478, 354, 162, 367, 56, 355, 78],
        yData2: [
          2452, 3421, 2456, 1672, 5222, 2573, 2352, 1092, 3532, 362, 352,
        ],
      },
      radarData: {
        indicatorData: [
          { max: 200000, name: "杭州市" },
          { max: 200000, name: "宁波市" },
          { max: 200000, name: "温州市" },
          { max: 200000, name: "绍兴市" },
          { max: 200000, name: "湖州市" },
          { max: 200000, name: "嘉兴市" },
          { max: 200000, name: "金华市" },
          { max: 200000, name: "衢州市" },
          { max: 200000, name: "舟山市" },
          { max: 200000, name: "台州市" },
          { max: 200000, name: "丽水市" },
        ],
        date: [
          19722, 151241, 132697, 150916, 45675, 73554, 186995, 83856, 134,
          114332, 23615,
        ],
      },

      infoData: [
        {
          left: "责任网点",
          key: "dutyNetworkName",
          right: "",
          isRightSlot: true,
          rightSlotName: "deptName",
        },
        {
          left: "银行机构",
          right: "",
          key: "bankAgencyName",
        },
        {
          left: "网点地址",
          right: "",
          key: "dutyNetworkAddress",
        },
        {
          left: "网点责任人",
          right: "",
          key: "dutyName",
        },
        {
          left: "联系电话",
          right: "",
          key: "dutyPhone",
        },
      ],
      policeInfoColumns: [
        { label: "反假联系人", key: "unFakerName" },
        { label: "联系电话", key: "unFakerPhone" },
        { label: "地址", key: "policeAddress" },
      ],
      policeData: [
        // {
        //   policeName: "派出所",
        //   name: "王明亮",
        //   phone: "19455555555",
        //   address: "地址",
        // },
        // {
        //   policeName: "派出所",
        //   name: "王明亮",
        //   phone: "19455555555",
        //   address: "地址",
        // },
        // {
        //   policeName: "派出所",
        //   name: "王明亮",
        //   phone: "19455555555",
        //   address: "地址",
        // },
      ],
      showDialog: false,
      showLoans: true,
      ComponentId: rightDialog,
      dialogTitle: "贷款模块",
      annotation: "",
      showTooltip: false,
      componentId: null,
      ckFtpBoxData: "",

      creditData,
      pastData: {
        money: {
          label: "逾欠余额",
          num: "0",
        },
        rate: {
          label: "逾欠率",
          num: "0",
        },
      },
      badData: {
        money: {
          label: "不良余额",
          num: "0",
        },
        rate: {
          label: "不良率",
          num: "0",
        },
      },
      progressData: {
        incomeRate: "0",
        incomeLabel: "正常收息率",
        innerRate: "0",
        innerLabel: "内",
        outerRate: "0",
        outerLabel: "外",
      },
      ftpBoxData: "",
      interstData: {},
      managerCodes: "852001",
      tableData,
      dataList,
      ifInit: true,
      customerType: null,
      dataInfo: {},
      importantColumns: [
        { label: "重点管理对象", key: "importName" },
        { label: "地址", key: "importAddress" },
      ],
      importantList: [
        // { importName: "1111", importAddress: "2222" },
        // { importName: "3333", importAddress: "4444" },
      ],
      gridWatcherColumns: [
        { label: "社会监督员", key: "supervisorName" },
        { label: "联系电话", key: "supervisorPhone" },
      ],
      gridWatcherList: [
        // { supervisorName: "1111", supervisorPhone: "2222" },
        // { supervisorName: "3333", supervisorPhone: "4444" },
      ],
      personList: {
        one: [],
        two: [],
      },
      communityPersonColumns: [
        {
          title: "社区网格员",
          listKey: "one",
          columns: [{ key: "adminName" }, { key: "adminPhone" }],
        },
        {
          title: "社会监督员",
          listKey: "two",
          columns: [{ key: "supervisorName" }, { key: "supervisorPhone" }],
        },
      ],
      otherPointTotal: "",
      otherPointList: [],
      otherPointColumns: [
        { label: "银行机构", key: "branchBank" },
        { label: "网点负责人", key: "dutyName" },
        { label: "联系电话", key: "dutyPhone" },
        {
          label: "地址",
          key: "addressDesc",
          valueStyle: { "margin-left": "15px" },
        },
      ],
      cashPointList: [],
      cashPointColumns: [
        { label: "所在网格", key: "gridName" },
        { label: "现金服务点负责人", key: "dutyName" },
        { label: "联系电话", key: "dutyPhone" },
        {
          label: "地址",
          key: "servePointAddr",
          valueStyle: { "margin-left": "15px" },
        },
      ],
      finicialPointList: [],
      finicialPointColumns: [
        { label: "所在网格", key: "gridName" },
        { label: "站长姓名", key: "dutyName" },
        { label: "联系电话", key: "dutyPhone" },
        {
          label: "地址",
          key: "servePointAddr",
          valueStyle: { "margin-left": "15px" },
        },
      ],
      gridBasicOrgData: [],
      gridBasicOrgColumns: [
        { label: "基层组织类型", key: "grassrootsType" },
        { label: "联系人", key: "contactName" },
        { label: "联系电话", key: "contactPhone" },
      ],
      pieData: [
        { value: 35, name: "非法集资", share: "" },
        { value: 25, name: "网络传销", share: "" },
        { value: 20, name: "金融诈骗", share: "" },
        { value: 12, name: "违规借贷", share: "" },
        { value: 8, name: "其他", share: "" },
      ],
      bottomData: [
        { value: 45, name: "金都美地" },
        { value: 38, name: "富力三江都荟" },
        { value: 32, name: "金报.步阳华府" },
        { value: 28, name: "金都美苑" },
        { value: 25, name: "金地大境花园" },
        { value: 22, name: "三江里广场" },
        { value: 18, name: "溪霞苑" },
        { value: 15, name: "金地艺境" },
        { value: 12, name: "驿头小区" },
        { value: 10, name: "保利.阅江台" },
      ],
    };
  },
  computed: {
    orgLevel() {
      return this.$store.getters["screenView/orgInfo"]?.level;
    },
    currentOrgName() {
      return this.$store.getters["screenView/orgInfo"]?.name || "当前区域";
    },
    orgList() {
      return this.$store.getters["screenView/orgList"];
    },
    filterParam() {
      return this.$store.getters["screenView/filterParam"];
    },
    filterType() {
      return this.$store.getters["screenView/filterType"];
    },
    roleParam() {
      return this.$store.getters["screenView/roleParam"];
    },
    initRole() {
      return this.$store.getters["screenView/initRole"];
    },
    gridCode() {
      return this.$store.getters["screenView/orgCode"];
    },
    orgCodes() {
      return this.$store.getters["userInfo"].addrTypeCodeSet.join(",");
    },
    rightInfo() {
      return this.$store.getters["screenView/rightInfo"];
    },
  },
  watch: {
    gridCode: {
      handler(val) {
        if (val && val != "point") {
          // this.smallDeposit();
          // this.smallProsperousLoan();
          this.initData();
        }
      },
    },
    filterParam: {
      handler(val) {
        const { groupCategory } = val;
        if (!this.ifInit && groupCategory != "") {
          // this.getCustomerType();
          // this.smallDeposit();
          // this.smallProsperousLoan();
          this.initData();
        }
      },
      deep: true,
    },
    roleParam: {
      handler() {
        if (!this.ifInit && this.orgList.length > 1) {
          // this.smallDeposit();
          // this.smallProsperousLoan();
          this.initData();
        }
      },
      deep: true,
    },
    // rightInfo: {
    //   handler(val) {
    //     if (val && val.gridCode) {
    //       this.initData();
    //     }
    //   },
    //   deep: true,
    // },

    showLevelView: {
      handler(newVal) {
        if (this.orgLevel == 5) return;

        const level = Number(newVal) || 1;
        const lvl = Math.min(Math.max(level, 1), 5);
        const base = 2500 - (lvl - 1) * 500;
        const fluct = Math.max(100, 500 - (lvl - 1) * 100);

        const labels =
          Array.isArray(this.chartsLabelList) && this.chartsLabelList.length
            ? this.chartsLabelList
            : (this.barOneData && this.barOneData.xData) || [];

        if (!labels || labels.length === 0) return;

        const randVal = () => {
          const r = Math.floor(Math.random() * (fluct * 2 + 1) - fluct + base);
          return Math.max(0, r);
        };

        const yData = labels.map(() => randVal());
        const lineData = yData.map((v) => {
          const factor = 0.6 + Math.random() * 0.8;
          return Math.max(0, Math.floor(v * factor));
        });

        this.barOneData = Object.assign({}, this.barOneData, {
          xData: labels,
          yData: yData,
          lineData: lineData,
        });

        const pie = labels.map((name, idx) => ({
          value: yData[idx],
          name,
          share: "",
        }));
        this.pieData = pie;
        if (level != 1) {
          this.bottomData = pie.slice(0);
        }

        this.$forceUpdate && this.$forceUpdate();
      },
      immediate: true,
    },
    chartsLabelList: {
      immediate: true,
      deep: true,
      handler(labels) {
        const level = Number(this.showLevelView) || 1;
        if (level > 1) return;
        const lvl = Math.min(Math.max(level, 1), 5);
        const base = 2500 - (lvl - 1) * 500;
        const fluct = Math.max(100, 500 - (lvl - 1) * 100);

        if (!labels || labels.length === 0) return;

        const randVal = () => {
          const r = Math.floor(Math.random() * (fluct * 2 + 1) - fluct + base);
          return Math.max(0, r);
        };

        const yData = labels.map(() => randVal());
        const lineData = yData.map((v) => {
          const factor = 0.6 + Math.random() * 0.8;
          return Math.max(0, Math.floor(v * factor));
        });

        this.barOneData = Object.assign({}, this.barOneData, {
          xData: labels,
          yData: yData,
          lineData: lineData,
        });

        const pie = labels.map((name, idx) => ({
          value: yData[idx],
          name,
          share: "",
        }));
        this.pieData = pie;
        this.bottomData = pie.slice(0);

        this.$forceUpdate && this.$forceUpdate();
      },
    },
    orgLevel: {
      handler(val) {
        if (val == 5) {
          this.setStaticData();
        }
      },
      immediate: true,
    },
  },
  created() {
    this.$EventBus.$on("mapView", () => {
      this.initData();
      // this.smallDeposit();
      // this.smallProsperousLoan();
    });
  },
  beforeDestroy() {
    this.$EventBus.$off("mapView");
  },
  mounted() {
    this.$nextTick(() => {
      this.initData();
      // this.getCustomerType();
      // this.smallDeposit();
      // this.smallProsperousLoan();
    });
  },
  methods: {
    initData() {
      if (this.orgLevel == 5) {
        this.setStaticData();
        return;
      }
    },
    setStaticData() {
      const seed = this.hashCode(
        `${this.gridCode || "330000"}-${this.currentOrgName || "当前区域"}`
      );
      const baseReport = [120, 98, 156, 142, 178, 135, 189, 167, 145, 198, 133, 172];
      const yData = baseReport.map((value, index) =>
        Math.max(8, Math.round(value * this.seededRatio(seed, index, 0.68, 1.12)))
      );
      const lineData = yData.map((value, index) =>
        Math.max(5, Math.round(value * this.seededRatio(seed + 97, index, 0.72, 0.91)))
      );
      this.barOneData = {
        title: "线索报送量",
        xData: [
          "1月",
          "2月",
          "3月",
          "4月",
          "5月",
          "6月",
          "7月",
          "8月",
          "9月",
          "10月",
          "11月",
          "12月",
        ],
        yData,
        lineTitle: "线索处置量",
        lineData,
      };
      const riskTypes = ["非法集资", "网络传销", "金融诈骗", "违规借贷", "其他"];
      this.pieData = riskTypes.map((name, index) => ({
        name,
        value: Math.max(3, Math.round([35, 25, 20, 12, 8][index] * this.seededRatio(seed + 211, index, 0.76, 1.18))),
        share: "",
      }));
      const shortName = (this.currentOrgName || "当前网格").replace(/社区$|村$/, "");
      const places = ["邻里中心", "沿街商圈", "商务楼宇", "生活广场", "居民小区", "便民中心", "农贸市场", "商业街区", "文化广场", "综合市场"];
      this.bottomData = places.map((place, index) => ({
        name: `${shortName}${place}`,
        value: Math.max(2, Math.round((45 - index * 4) * this.seededRatio(seed + 389, index, 0.78, 1.16))),
      }));
      this.$forceUpdate && this.$forceUpdate();
    },
    hashCode(value) {
      return String(value)
        .split("")
        .reduce((result, char) => ((result * 31 + char.charCodeAt(0)) >>> 0), 17);
    },
    seededRatio(seed, index, min, max) {
      const mixed = (seed + (index + 1) * 2654435761) >>> 0;
      return min + (mixed % 1000) / 1000 * (max - min);
    },
    fetchList() {
      const param = {
        gridCode: this.gridCode,
      };
      service.getByGridCode(param).then((res) => {
        const { data } = res;
        this.dataInfo = data;
        this.saveData[0].name = data.gridName || "-";
        let arr = ["非农村网格", "农村网格"];
        this.saveData[0].girdTypeValue =
          this.dict.label.new_grid_type[data.ruralGridType] || "-";
        this.saveData[1].name = data.gridMainManagerName || "-";
        this.saveData[1].girdTypeValue = data.gridMainManagerPhone || "-";
        this.saveData[2].name = data.gridInspectorName || "-";
        this.saveData[2].girdTypeValue = data.gridInspectorPhone || "-";
        this.infoData.forEach((item) => {
          item.right = data[item.key];
        });
      });
    },
    getOtherPointList() {
      const param = {
        gridCode: this.gridCode,
      };
      service.getOtherPointList(param).then((res) => {
        const { rows, total } = res;
        this.otherPointList = rows;
        this.otherPointTotal = total;
        // const { data } = res;
        // this.otherPointList = data;
      });
    },
    getGridRatingInfo() {
      const param = {
        gridCode: this.gridCode,
      };
      service.getGridRatingInfo(param).then((res) => {
        const { data } = res;
        this.policeData = data;
      });
    },
    getCustomerType() {
      const { typeList, groupCategory } = this.filterParam;
      if (this.filterType === "1") {
        let arr = typeList.filter((item) => item !== "3");
        this.customerType = arr.join(",");
      } else {
        this.customerType = groupCategory == "3" ? null : groupCategory;
      }
    },
    handleTopDialog() {
      this.dialogTitle = "零钞预约概况";
      this.showDialog = true;
      this.showGrid = "top";
      this.viewAllData = {
        cardTwoData: this.cardTwoData,
        rankColumns: this.rankColumns,
        rankData: this.rankData,
        barOneData: this.barOneData,
      };
    },

    handleMiddleDialog() {
      this.dialogTitle = "商户摸排概况";
      this.showDialog = true;
      this.showGrid = "middle";
      this.viewAllData = {
        cardBigTwoData: this.cardBigTwoData,
        barTwoData: this.barTwoData,
        radarData: this.radarData,
      };
    },
    handleBottomDialog() {
      this.dialogTitle = "投诉事件概况";
      this.showDialog = true;
      this.showGrid = "bottom";
      this.viewAllData = {};
    },
    showBusiness() {
      this.showTooltip = true;
      this.componentId = businessList;
    },
    showBadness() {
      this.showTooltip = true;
      this.componentId = badnessList;
    },

    smallDeposit() {
      const data = {
        customerType: this.customerType,
        gridCode: this.gridCode,
        ...this.roleParam,
      };
      (this.orgList.length === 1 || this.gridCode == "point") &&
        delete data.gridCode;
      Object.keys(this.roleParam).forEach((key) => {
        if (this.initRole[key] === this.roleParam[key]) {
          delete data[key];
        }
      });
      services.smallDeposit(data).then((res) => {
        this.ifInit = false;
        const { data } = res;
        if (!data) {
          return;
        }

        for (const key in data) {
          if (data[key] == null) data[key] = "—";
        }

        this.ckFtpBoxData = data.saveFtp;
        // this.saveData.forEach((item) => {
        //   item.allNum = data[item.key];
        //   item.lastMonth = data[item.key + "LastMonth"];
        //   item.beginYear = data[item.key + "BeginYear"];
        // });
        // console.log(this.saveData);
      });
    },
    smallProsperousLoan() {
      const data = {
        customerType: this.customerType,
        gridCode: this.gridCode,
        ...this.roleParam,
      };
      (this.orgList.length === 1 || this.gridCode == "point") &&
        delete data.gridCode;
      Object.keys(this.roleParam).forEach((key) => {
        if (this.initRole[key] === this.roleParam[key]) {
          delete data[key];
        }
      });
      services.smallProsperousLoan(data).then((res) => {
        this.ifInit = false;
        const { data } = res;
        if (!data) {
          return;
        }

        for (const key in data) {
          if (data[key] == null) data[key] = "—";
        }

        this.creditData[0].leftValue = data.useCreditNum;
        this.creditData[0].rightValue = data.useCreditBal;
        this.creditData[1].leftValue = data.grantCreditNum;
        this.creditData[1].rightValue = data.grantCreditBal;
        this.pastData.money.num = data.totalOverdueBal;
        this.pastData.rate.num = data.totalOverdueRatio;
        this.badData.money.num = data.totalBadBal;
        this.badData.rate.num = data.totalBadRatio;
        this.progressData.incomeRate = data.totalInterestRatio;
        this.progressData.innerRate = data.totalInterestGridInsideRatio;
        this.progressData.outerRate = data.totalInterestGridOutsideRatio;

        this.dataList.forEach((item) => {
          for (let el of item) {
            el.value = data[el.key];
          }
        });

        let keyName = "";
        let type = {
          逾欠余额: "totalOverdue",
          不良余额: "totalBad",
        };
        this.tableData.forEach((item) => {
          Object.keys(item).forEach((key) => {
            if (key === "typeName") {
              keyName = type[item[key]];
            } else {
              item[key] = data[`${keyName}${key}`];
            }
          });
        });
      });
    },

    // 重点管理对象
    fetchImportantList() {
      let params = {
        gridCode: this.gridCode,
      };
      service
        .getGridImportant(params)
        .then((res) => {
          this.$nextTick(() => {
            this.importantList = res;
          });
        })
        .catch((err) => {
          this.$nextTick(() => {
            this.importantList = [];
          });
        });
    },
    // 社会监督员
    fetchCommunityWatcher() {
      let params = {
        gridCode: this.gridCode,
      };
      service
        .getCommunityWatcherList(params)
        .then((res) => {
          this.$nextTick(() => {
            this.gridWatcherList = res;
          });
        })
        .catch((err) => {
          this.$nextTick(() => {
            this.gridWatcherList = [];
          });
        });
    },
    // 基层组织
    fetchGridBasicOrg() {
      let params = {
        gridCode: this.gridCode,
      };
      service
        .getGridBasicOrgList(params)
        .then((res) => {
          res.forEach((i) => {
            if (i.grassrootsType) {
              i.grassrootsType =
                this.dict.label.basic_org_type[i.grassrootsType];
            }
          });
          this.$nextTick(() => {
            this.gridBasicOrgData = res;
          });
        })
        .catch((err) => {
          this.$nextTick(() => {
            this.gridBasicOrgData = [];
          });
        });
    },
    // 网格内现金服务点信息
    fecthCashPointList() {
      let data = [this.gridCode];
      getCashPointInGrid(data).then((res) => {
        this.cashPointList = res.data;
      });
    },
    // 网格内金融服务站信息
    fecthFinicialPointList() {
      let data = [this.gridCode];
      getFinancialInGrid(data).then((res) => {
        this.finicialPointList = res.data;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.right-view-wrapper {
  height: 100%;
  position: relative;

  .wrapper-top {
    .cunkuanBox {
      margin-top: 10px;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      position: relative;
      // height: 85%;
      width: 100%;

      .cukuan-item {
        width: 32%;
        margin-bottom: 20px;
      }

      .posiIMg {
        position: absolute;
        top: -7%;
        left: 24%;
      }
    }

    .deptName {
      height: 200px;
      margin-top: 10px;
      padding: 10px 10px 10px 20px;

      &-item {
        color: #8bf6ff;
      }

      ::v-deep .item-left {
        font-size: 14px;
      }
    }

    .littleBox {
      margin-top: 10px;
    }
  }
}

.otherPoint {
  display: flex;
  justify-content: space-between;
  align-items: center;

  &-total {
    margin-right: 15px;
    font-size: 16px;
    color: #fff;
  }
}

.policeInfoBox {
  max-height: 340px;
  overflow: auto;

  &::-webkit-scrollbar {
    width: 3px;
  }

  &::-webkit-scrollbar-track {
    background: transparent;
  }

  &::-webkit-scrollbar-thumb {
    background: rgba(255, 255, 255, 0.2);
    border-radius: 2px;
  }
}

.important-box {
  .title {
    color: #9dbec1;
    font-size: 18px;
    margin: 10px 0;
  }

  .row {
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    background-color: #8bf6ff30;

    &-1 {
      .name {
        color: #fff;
        margin-left: 5px;
      }
    }

    &-2 {
      color: #9dbec1;
      font-size: 16px;
      margin: 7.5px 0 0 28px;
      // margin-left: 28px;
    }
  }
}

.watcher-box {
  color: #9dbec1;

  .title {
    font-size: 18px;
    margin: 10px 0;
  }

  .row {
    display: flex;
    align-items: center;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    background-color: #8bf6ff30;

    .name {
      width: 100px;
    }

    .icon {
      margin: 0 5px;
    }
  }
}

.scroll {
  max-height: 260px;
  overflow: hidden;
  overflow-y: scroll;

  &::-webkit-scrollbar {
    width: 3px;
  }

  &::-webkit-scrollbar-track {
    background: transparent;
  }

  &::-webkit-scrollbar-thumb {
    background: rgba(255, 255, 255, 0.2);
    border-radius: 2px;
  }
}
.risk {
  ::v-deep .left {
    background-image: url("~@/modules/module-screenView/assets/image/webTraps.png") !important;
    background-size: 100% 100%;
    width: 125px;
    height: 48px;
    position: absolute;
    top: -5px;
  }
  ::v-deep .title-box-bg {
    background-size: cover !important;
    -webkit-mask-image: linear-gradient(
        to right,
        transparent 220px,
        black 220px
      ),
      linear-gradient(to bottom, transparent 20px, black 20px);
    mask-image: linear-gradient(to right, transparent 220px, black 220px),
      linear-gradient(to bottom, transparent 20px, black 20px);
    -webkit-mask-composite: source-over; /* 对于 Chrome/Safari */
    mask-composite: add; /* 标准语法，两者叠加取交集 */
  }
  ::v-deep .title-box-header {
    margin-left: 130px;
  }
}
</style>
