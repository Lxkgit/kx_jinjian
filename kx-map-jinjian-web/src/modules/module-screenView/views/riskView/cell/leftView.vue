<template>
  <div class="left-view-wrapper">

    <titleBox class="wrapper-all" height="630px" title=" 网格概况" annotation="">
      <viewAll slot="operate"></viewAll>
      <!-- 其他层级 -->
      <boxTitle title="浙江省全域各地市现金服务网格数量情况" v-if="orgLevel != 5" />
      <girdPie :info="girdPieData"  v-if="orgLevel != 5"/>
      <boxTitle title="浙江省全域各地市银行机构网点数量情况" v-if="orgLevel != 5"/>
      <girdLine :info="girdLineData"  v-if="orgLevel != 5"  :contentHeight="100" />


      <!-- 5级 -->
      <template v-if="orgLevel == 5">
        <boxTitle title="网格信息" />
        <gridMessage :info="saveData" />
       <boxTitle title="责任网点信息" />
        <deptBox :info="infoData" />
        <boxTitle title="社会网格员" />
        <info-list-box class="info-box-2" v-for="(item, idx) in gridWatcherList" :key="idx" :info="item"
          leftKey="supervisorName" :columns="gridWatcheColumns">
          <div slot="img">
            <img slot="img" :src="require('@/modules/module-screenView/assets/image/pointIcon4.png')
              " style="margin-right: 7.5px" />
          </div>
        </info-list-box>
        <boxTitle title="管辖派出所" />
        <info-list-box class="info-box-3" v-for="(item, idx) in policeData" :key="idx" :info="item" leftKey="policeName"
          :columns="policeInfoColumns">
          <div slot="img">
            <img slot="img" :src="require('@/modules/module-screenView/assets/image/pointIcon6.png')
              " style="margin-right: 7.5px" />
          </div>
        </info-list-box>
      </template>

       <!-- <gridMessage :info="{
        gridType: '村居',
        gridName: '朱涂村委会',
        bankLeader: '张毅',
        leaderPhone: '13749635761',
      }" v-if="orgLevel == 5"  />
      <deptBox :info="deptBoxData" v-if="orgLevel == 5" /> -->

      <!-- 查看全部 -->
      <viewDialog v-model="showDialog" :title="dialogTitle" :annotation="dialogAnnotation">
        <component :is="dialogComponentId" :showGrid="showGrid" :value="viewAllData"></component>
      </viewDialog>
    </titleBox>

    <titleBox height="461px" " title=" 网点概况" v-if="gridCode != '330324102243'">

    </titleBox>
     
     <titleBox class="wrapper-top" height="299px" title="风险趋势"  v-if="orgLevel != 5">
      <viewAll slot="operate"></viewAll>
      <barOne :info="barOneData" />
    </titleBox>

  </div>
</template>

<script>
import services from "../api";
import titleBox from "@/modules/module-screenView/components/titleBox";
import barOne from "@/modules/module-screenView/viewComponents/components/barOne";

import boxTitle from "@/modules/module-screenView/viewComponents/components/boxTitle";
import girdPie from "@/modules/module-screenView/viewComponents/components/girdPie";
import barBi from "@/modules/module-screenView/viewComponents/components/barBi";
import barBv from "@/modules/module-screenView/viewComponents/components/barBv";
import barTwo from "@/modules/module-screenView/viewComponents/components/barTwo";
import girdLine from "@/modules/module-screenView/viewComponents/components/girdLine";
import gridRadar from "@/modules/module-screenView/viewComponents/components/gridRadar";
import barStack from "@/modules/module-screenView/viewComponents/components/barStack";
import wordCloud from "@/modules/module-screenView/viewComponents/components/wordCloud";
import girdPie3d from "@/modules/module-screenView/viewComponents/components/girdPie3d";
import cardThree from "@/modules/module-screenView/viewComponents/components/cardThree";
import cardTwo from "@/modules/module-screenView/viewComponents/components/cardTwo";
import cardBigTwo from "@/modules/module-screenView/viewComponents/components/cardBigTwo";
import gridRankList from "@/modules/module-screenView/viewComponents/components/gridRankList";
import gridRankHot from "@/modules/module-screenView/viewComponents/components/gridRankHot";
import gridGauge from "@/modules/module-screenView/viewComponents/components/gridGauge";
import pieTwo from "@/modules/module-screenView/viewComponents/components/pieTwo";
import gridGauge2 from "@/modules/module-screenView/viewComponents/components/gridGauge2";
import listTwo from "@/modules/module-screenView/viewComponents/components/listTwo";
import listThree from "@/modules/module-screenView/viewComponents/components/listThree";
import gridMessage from "@/modules/module-screenView/viewComponents/components/gridMessage";
import importantBox from "@/modules/module-screenView/viewComponents/components/importantBox";
import deptBox from "@/modules/module-screenView/viewComponents/components/deptBox";
import infoListBox from "@/modules/module-screenView/viewComponents/components/infoListBox";

import ftpBox from "@/modules/module-screenView/viewComponents/components/ftpBox";
import introBox from "@/modules/module-screenView/viewComponents/components/introBox";
import gridBox from "@/modules/module-screenView/viewComponents/components/gridBox";
import customNum from "@/modules/module-screenView/viewComponents/components/customNum";
import viewAll from "@/modules/module-screenView/viewComponents/components/viewAll";
import progressInfo from "@/modules/module-screenView/viewComponents/components/progressInfo";
import ckCategory from "@/modules/module-screenView/viewComponents/components/ckCategory";
import gridDetailInfo from "@/modules/module-screenView/viewComponents/components/gridDetailInfo";
import tooltipDialog from "@/modules/module-screenView/viewComponents/components/tooltipDialog";
import businessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/businessList";
import badnessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/badnessList";
import customList from "@/modules/module-screenView/viewComponents/components/tooltipContent/customList";
import mediumBox from "@/modules/module-screenView/viewComponents/components/mediumBox";
import SmallBox from "@/modules/module-screenView/viewComponents/components/smallBox";
import viewDialog from "@/modules/module-screenView/viewComponents/components/viewDialog";
import viewTooltip from "@/modules/module-screenView/viewComponents/components/viewTooltip";
import leftDialog from "../components/leftDialog";
import service from "@/modules/module-gridManagement/views/gridManagement/api";
import riskalert from "@/modules/module-screenView/viewComponents/components/riskalert.vue";

import {
  dataList,
  tableData,
  introBoxData,
  gridBoxData,
  customerList,
  gridDetailData,
  customListData,
  creditData,
} from "../config/leftConfig";
export default {
  components: {
    boxTitle,
    titleBox,
    girdPie,
    barBi,
    barBv,
    girdLine,
    gridRadar,
    barStack,
    barTwo,
    wordCloud,
    girdPie3d,
    cardThree,
    cardTwo,
    cardBigTwo,
    gridRankList,
    gridRankHot,
    gridGauge,
    pieTwo,
    gridGauge2,
    listTwo,
    listThree,
    gridMessage,
    importantBox,
    deptBox,
    infoListBox,
    barOne,
    ftpBox,
    introBox,
    gridBox,
    customNum,
    viewAll,
    ckCategory,
    gridDetailInfo,
    tooltipDialog,
    progressInfo,
    mediumBox,
    SmallBox,
    viewDialog,
    viewTooltip,
    riskalert,
  },
  props: ["showLevelView", "chartsLabelList"],
  data() {
    return {
      showTooltip: false,
      isTop: true,
      componentId: customList,
      showDialog: false,
      dialogComponentId: leftDialog,
      dialogTitle: "网格概况",
      dialogAnnotation: "(金额:万元)",
      showGrid: true,
      smallOverViewObj: {},
      wgFtpBoxData: "0",
      customerTotalNum: "0",
      dkFtpBoxData: "0",
      defaultValue: [],
      gridInfo: {},
      keyTableData: [],

      introBoxData,
      gridBoxData,
      customerList,
      gridDetailData,
      customListData,
      creditData,
      policeInfoColumns: [
        { label: "反假联系人", key: "unFakerName" },
        { label: "联系电话", key: "unFakerPhone" },
        { label: "地址", key: "policeAddress" },
      ],
      gridGauge2Data: [
        {
          title: "责任网点",
          value: 1083,
        },
        {
          title: "普通网点",
          value: 231,
        },
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
      infoData: [
        {
          left: "责任网点",
          key: "dutyNetworkName",
          right: "",
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
      gridWatcheColumns: [{ label: "联系电话", key: "supervisorPhone" }],
      girdPieData: {
        num1: 17202,
        num2: 5272,
        pieData: [
          { value: 800, name: "杭州市" },
          { value: 2963, name: "宁波市" },
          { value: 3672, name: "温州市" },
          { value: 1200, name: "绍兴市" },
          { value: 1225, name: "湖州市" },
          { value: 2077, name: "嘉兴市" },
          { value: 3332, name: "金华市" },
          { value: 1649, name: "衢州市" },
          { value: 302, name: "舟山市" },
          { value: 3345, name: "台州市" },
          { value: 1910, name: "丽水市" },
        ],
      },
      deptBoxData: [
        {
          left: "责任网点",
          key: "dutyNetworkName",
          right: "温州银行股份有限公司永嘉万鑫锦园社区支行",
        },
        {
          left: "银行机构",
          right: "温州银行股份有限公司",
          key: "bankAgencyName",
        },
        {
          left: "网点地址",
          right: "浙江省永嘉县桥下镇镇前街万鑫锦园1幢111室",
          key: "dutyNetworkAddress",
        },
        {
          left: "网点责任人",
          right: "林伟雄",
          key: "dutyName",
        },
        {
          left: "联系电话",
          right: "0577-66968800",
        },
        {
          left: "网点介绍",
          right:
            "积极开展打击非法金融活动工作，切实维护社区金融安全。网点聚焦防范非法集资、电信网络诈骗及非法放贷等风险，通过厅堂宣讲、社区走访、发放宣传资料等形式，提升居民风险识别与防范能力。同时，网点严格落实客户身份识别和资金交易监测机制，强化异常交易排查与报告，筑牢风险防控底线；并联合社区、公安等多方力量，构建联防联控机制，形成打击非法金融活动的协同合力，营造安全、稳定、可信赖的社区金融环境。",
        },
      ],
      barBiData: {
        legendData: ["重点网络"],
        yData: [
          "杭州市",
          "宁波市",
          "温州市",
          "绍兴市",
          "湖州市",
          "嘉兴市",
          "金华市",
          "衢州市",
          "舟山市",
          "台州市",
          "丽水市",
        ],
        leftData: [80, 290, 360, 120, 120, 207, 333, 164, 30, 334, 191],
      },
      pie3dData: [
        {
          name: "开展大篷车",
          value: 3567,
          itemStyle: { color: "rgba(90, 243, 184, 1)", opacity: 0.8 },
        },
        {
          name: "开展小钱箱子",
          value: 2883,
          itemStyle: { color: "rgba(77, 116, 255, 1)", opacity: 0.8 },
        },
        {
          name: "开展线下兑换",
          value: 1711,
          itemStyle: { color: "rgba(147, 219, 255, 1)", opacity: 0.8 },
        },
      ],

      latticeData: {
        visitorNum: "22474",
        afterUnit: "个",
        type: "blue",
      },
      cardInfo: [
        {
          title: "宣传总次数",
          value: "43000",
          unit: "次",
        },
        {
          title: "宣传受众人数",
          value: "310",
          unit: "万人",
        },
      ],

      cardThreeData: [
        {
          title: "人工现金柜台数",
          value: "2.41",
          unit: "万个",
        },
        {
          title: "自助现金机具数",
          value: "2.92",
          unit: "万台",
        },
        {
          title: "小面额自助现金机具数",
          value: "2584",
          unit: "台",
        },
      ],

      girdLineData: {
        legendData: ["网点"],
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
        data: [350, 1135, 814, 778, 514, 624, 1318, 247, 147, 1161, 284],
      },

      barStackSimply: {
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
        yDate: [1782, 1308, 1028, 972, 698, 1092, 1508, 1156, 709, 1448, 874],
        yDate1: [1807, 1278, 1134, 987, 702, 1120, 1309, 890, 587, 1267, 765],
        yDate2: [287, 137, 106, 178, 120, 198, 210, 145, 134, 159, 185],
      },

      barStackData: {
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
        yDate: [1782, 1308, 1028, 972, 698, 1092, 1508, 1156, 709, 1448, 874],
        yDate1: [1807, 1278, 1134, 987, 702, 1120, 1309, 890, 587, 1267, 765],
        yDate2: [287, 137, 106, 178, 120, 198, 210, 145, 134, 159, 185],
        yDate3: [290, 300, 400, 600, 700, 300, 740, 200, 300, 740, 200],
        yDate4: [444, 500, 1000, 600, 700, 300, 740, 200, 300, 740, 200],
        yDate5: [444, 500, 1000, 290, 300, 400, 740, 200, 300, 740, 200],
        yDate6: [444, 300, 400, 600, 700, 300, 740, 200, 300, 740, 200],
        yDate7: [444, 500, 1000, 500, 700, 300, 740, 200, 500, 700, 300],
        sumList: [
          {
            name: "人工现金柜台",
            value: "2.41万",
          },
          {
            name: "本外部现金存取",
            value: "2.92万",
          },
          {
            name: "小面额兑换支取",
            value: 2584,
          },
          {
            name: "数字人民币服务",
            value: 3590,
          },
          {
            name: "假币鉴定",
            value: 2340,
          },
          {
            name: "真伪授权鉴定",
            value: 5290,
          },
          {
            name: "特殊残损人民币兑换",
            value: 3330,
          },
          {
            name: "停止流通人民币兑换",
            value: 3430,
          },
        ],
      },

      latticeData1: {
        visitorNum: "12778",
        afterUnit: "个",
      },
      latticeData2: {
        visitorNum: "750",
        afterUnit: "个",
        type: "yellow",
      },
      latticeData3: {
        visitorNum: "7372",
        afterUnit: "个",
        type: "dblue",
      },
      latticeData4: {
        visitorNum: "12073",
        afterUnit: "个",
        type: "green",
      },

      cardTwoData: [
        {
          title: "银行网点",
          value: "12778",
          unit: "个",
        },
        {
          title: "现金服务点",
          value: "750",
          unit: "个",
        },
        {
          title: "责任银行网点",
          value: "7372",
          unit: "个",
        },
        {
          title: "农村金融服务站",
          value: "12073",
          unit: "个",
        },
      ],

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
      ckCategoryData: [
        {
          title: "信用",
          key: "credit",
          icon: "xinyong.png",
          households: "",
          money: "",
          pastData: {
            money: {
              label: "逾欠余额",
              num: "",
            },
            rate: {
              label: "逾欠率",
              num: "",
            },
          },
          badData: {
            money: {
              label: "不良余额",
              num: "",
            },
            rate: {
              label: "不良率",
              num: "",
            },
          },
        },
        {
          title: "保证",
          key: "ensure",
          icon: "baozheng.png",
          households: "",
          money: "",
          pastData: {
            money: {
              label: "逾欠余额",
              num: "",
            },
            rate: {
              label: "逾欠率",
              num: "",
            },
          },
          badData: {
            money: {
              label: "不良余额",
              num: "",
            },
            rate: {
              label: "不良率",
              num: "",
            },
          },
        },
        {
          title: "抵押",
          key: "mortgage",
          icon: "diya.png",
          households: "",
          money: "",
          pastData: {
            money: {
              label: "逾欠余额",
              num: "",
            },
            rate: {
              label: "逾欠率",
              num: "",
            },
          },
          badData: {
            money: {
              label: "不良余额",
              num: "",
            },
            rate: {
              label: "不良率",
              num: "",
            },
          },
        },
        {
          title: "质押",
          key: "pledge",
          icon: "zhiya.png",
          households: "",
          money: "",
          pastData: {
            money: {
              label: "逾欠余额",
              num: "",
            },
            rate: {
              label: "逾欠率",
              num: "",
            },
          },
          badData: {
            money: {
              label: "不良余额",
              num: "",
            },
            rate: {
              label: "不良率",
              num: "",
            },
          },
        },
      ],
      viewAllData: {},
      dataList, // 贷款模块 用户/授信查看更多
      tableData, // 贷款 不良/逾欠
      ifInit: true,
      customerType: null,
      saveData: {
        gridType: "社区",
        gridName: "",
        bankLeader: "",
        leaderPhone: "",
        gridStaff: "",
        staffPhone: "",
        merchantsNum: 0,
        knowledgeTimes: 0,
        cashServiceTimes: 0,
      },
      policeData: [],
      gridWatcherList: [],
    };
  },
  computed: {
    orgList() {
      return this.$store.getters["screenView/orgList"];
    },
    orgLevel() {
      return this.$store.getters["screenView/orgInfo"]?.level;
    },
    filterParam() {
      return this.$store.getters["screenView/filterParam"];
    },
    roleParam() {
      return this.$store.getters["screenView/roleParam"];
    },
    initRole() {
      return this.$store.getters["screenView/initRole"];
    },
    filterType() {
      return this.$store.getters["screenView/filterType"];
    },
    gridCode() {
      return this.$store.getters["screenView/orgCode"];
    },
    orgCodes() {
      return this.$store.getters["userInfo"].addrTypeCodeSet.join(",");
    },
  },
  watch: {
    gridCode: {
      handler(val) {
        if (val && !this.ifInit && val != "point") {
          this.smallLoan();
          this.smallOverView();
        }
        if (val) {
          this.getGridInfo();
          this.getGridCruxInfo();
          this.fetchCommunityWatcher();
          this.getGridRatingInfo();
        }
      },
      immediate: true,
    },
    // filterParam: {
    //   handler(val) {
    //     const { groupCategory } = val;
    //     if (!this.ifInit && groupCategory != "") {
    //       this.getCustomerType();
    //       this.smallLoan();
    //     }
    //   },
    //   deep: true,
    // },
    roleParam: {
      handler() {
        if (!this.ifInit && this.orgList.length > 1) {
          this.smallLoan();
          this.smallOverView();
        }
      },
      deep: true,
    },
    showLevelView: {
      handler(newVal) {
        // 根据 newVal (1-5) 生成随机数据
        // 规则：
        //   base = 2500 - (level-1)*500
        //   fluct = 500 - (level-1)*100  （取正值）
        // 对应每个标签（以 this.chartsLabelList 为准）生成 base +/- 随机(fluct)
        // 更新 this.girdLineData.data （y 轴数组）以及 this.girdPieData.pieData（饼图数据），保持原有数据结构
        const level = Number(newVal) || 1;
        const lvl = Math.min(Math.max(level, 1), 5);
        const base = 2500 - (lvl - 1) * 500;
        const fluct = Math.max(100, 500 - (lvl - 1) * 100);

        // 如果没有 chartsLabelList，直接返回
        const labels =
          Array.isArray(this.chartsLabelList) && this.chartsLabelList.length
            ? this.chartsLabelList
            : (this.girdLineData && this.girdLineData.xData) || [];

        if (!labels || labels.length === 0) return;

        // 随机数生成器，返回整数并保证 >=0
        const randVal = () => {
          const r = Math.floor(Math.random() * (fluct * 2 + 1) - fluct + base);
          return Math.max(0, r);
        };

        // 生成 line 数据
        const lineData = labels.map(() => randVal());
        // 生成 pie 数据（与 labels 对应）
        const pieData = labels.map((name, idx) => ({
          value: lineData[idx],
          name,
        }));
        const yData = labels.map(() => randVal());

        this.barOneData = Object.assign({}, this.barOneData, {
          xData: labels,
          yData: yData,
          lineData: lineData,
        });
        // 更新 girdLineData，保持其原有字段不变
        if (!this.girdLineData) this.girdLineData = {};
        // 保留 legendData、xData 字段结构
        this.girdLineData = Object.assign({}, this.girdLineData, {
          legendData: this.girdLineData.legendData || ["网点"],
          xData: labels,
          data: lineData,
        });

        // 更新 girdPieData，保持 num1/num2/pieData 结构
        if (!this.girdPieData) this.girdPieData = {};
        const total = lineData.reduce((s, v) => s + v, 0);
        const second = Math.max(0, Math.floor(total * 0.3));
        this.girdPieData = Object.assign({}, this.girdPieData, {
          num1: total,
          num2: second,
          pieData: pieData,
        });

        // 强制视图更新（防止某些图表组件缓存引用）
        this.$forceUpdate && this.$forceUpdate();
      },
      immediate: true,
    },
  },
  created() {
    this.$EventBus.$on("mapView", () => {
      this.smallLoan();
      this.smallOverView();
    });
  },
  beforeDestroy() {
    this.$EventBus.$off("mapView");
  },
  mounted() {
    this.$nextTick(() => {
      this.getCustomerType();
      this.smallLoan();
      this.smallOverView();
      this.fetchCommunityWatcher();
    });
  },
  methods: {
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
    getCustomerType() {
      const { typeList, groupCategory } = this.filterParam;
      if (this.filterType === "1") {
        let arr = typeList.filter((item) => item !== "3");
        this.customerType = arr.join(",");
      } else {
        this.customerType = groupCategory == "3" ? null : groupCategory;
      }
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
    handleTooltipDialog() {
      this.showTooltip = true;
      this.componentId = customList;
      this.defaultValue = this.customListData;
      this.isTop = true;
    },
    showBusiness() {
      this.showTooltip = true;
      this.componentId = businessList;
      this.isTop = false;
    },
    showBadness() {
      this.showTooltip = true;
      this.componentId = badnessList;
      this.isTop = false;
    },
    handleTopViewAll() {
      this.dialogTitle = "网格概况";
      this.dialogAnnotation = "";
      this.showGrid = true;
      this.showDialog = true;
      this.viewAllData = {
        girdPieData: this.girdPieData,
        barBiData: this.barBiData,
        pie3dData: this.pie3dData,
        latticeData: this.latticeData,
        cardInfo: this.cardInfo,
      };
    },
    handleBottomViewAll() {
      this.dialogTitle = "网点概况";
      this.dialogAnnotation = "";
      this.showGrid = false;
      this.showDialog = true;
      this.viewAllData = {
        cardTwoData: this.cardTwoData,
        cardThreeData: this.cardThreeData,
        girdLineData: this.girdLineData,
        barStackData: this.barStackData,
        barStackSimply: this.barStackSimply,
        latticeData1: this.latticeData1,
        latticeData2: this.latticeData2,
        latticeData3: this.latticeData3,
        latticeData4: this.latticeData4,
      };
    },

    smallLoan() {
      return;
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
      services.smallLoan(data).then((res) => {
        this.ifInit = false;
        const { data } = res;
        if (!data) {
          return;
        }

        for (const key in data) {
          if (data[key] == null) data[key] = "—";
        }

        this.dkFtpBoxData = data.loanFtp;

        this.creditData[0].leftValue = data.grantCreditNum;
        this.creditData[0].rightValue = data.grantCreditBal;
        this.creditData[1].leftValue = data.useCreditNum;
        this.creditData[1].rightValue = data.useCreditBal;

        this.pastData.money.num = data.totalOverdueBal;
        this.pastData.rate.num = data.totalOverdueRatio;
        this.badData.money.num = data.totalBadBal;
        this.badData.rate.num = data.totalBadRatio;

        this.progressData.incomeRate = data.totalInterestRatio;
        this.progressData.innerRate = data.totalInterestGridInsideRatio;
        this.progressData.outerRate = data.totalInterestGridOutsideRatio;

        this.ckCategoryData.forEach((item) => {
          item.households = data[item.key + "Num"];
          item.money = data[item.key + "Bal"];
          item.pastData.money.num = data[item.key + "OverdueBal"];
          item.pastData.rate.num = data[item.key + "OverdueRatio"];
          item.badData.money.num = data[item.key + "BadBal"];
          item.badData.rate.num = data[item.key + "BadRatio"];
        });

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
    smallOverView() {
      return;
      const data = {
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
      services.smallOverView(data).then((res) => {
        this.ifInit = false;
        const { data } = res;
        if (!data) {
          return;
        }

        for (const key in data) {
          if (data[key] == null) data[key] = "—";
        }

        this.wgFtpBoxData = data.ftp;
        this.introBoxData.forEach((item) => {
          item.value = data[item.key];
        });
        this.gridBoxData.forEach((item) => {
          item.value = data[item.key];
        });
        this.customerTotalNum = data.customerTotalNum;
        this.customerList.forEach((item) => {
          item.num = data[item.key];
        });
        this.gridDetailData.forEach((item) => {
          item.value = data[item.key];
        });
        this.customListData.forEach((item) => {
          item.customerNum = data[item.key + "CustomerNum"];
          item.customerNumRatio = "100";
          item.InsideCustomerNum = data[item.key + "InsideCustomerNum"];
          item.InsideCustomerRatio = data[item.key + "InsideCustomerRatio"];
          item.OutsideCustomerNum = data[item.key + "OutsideCustomerNum"];
          item.OutsideCustomerRatio = data[item.key + "OutsideCustomerRatio"];
        });
      });
    },
    // 获取网格信息
    getGridInfo() {
      console.log("11111");
      const param = {
        gridCode: this.gridCode,
      };
      services.getByGridCode(param).then((res) => {
        const { data } = res;
        this.gridInfo = data;
        this.dataInfo = data;
        this.saveData.gridName = data.gridName || "-";
        // this.saveData.gridType =
        //   this.dict.label.new_grid_type[data.ruralGridType] || "-";
        this.saveData.bankLeader = data.gridMainManagerName || "-";
        this.saveData.leaderPhone = data.gridMainManagerPhone || "-";
        this.saveData.gridStaff = data.gridInspectorName || "-";
        this.saveData.staffPhone = data.gridInspectorPhone || "-";
        this.infoData.forEach((item) => {
          item.right = data[item.key];
        });
      });
    },
    // 获取网格关键人信息
    getGridCruxInfo() {
      return;
      const param = {
        gridCode: this.gridCode,
      };
      services.getGridCruxInfo(param).then((res) => {
        const { data } = res;
        this.keyTableData = data;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.left-view-wrapper {
  position: relative;
}

.allWarp {
  // .title-box-bg {
  //   background-image: url("src/modules/module-screenView/assets/image/titleBox2.png");
  //   background-size: 100% 100%;
  // }
}
</style>
