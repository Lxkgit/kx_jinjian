<template>
  <div class="left-view-wrapper">
    <titleBox height="461px" title="网格概况" annotation="">
      <viewAll slot="operate" @click="handleTopViewAll"></viewAll>
      <boxTitle title="地区现金服务网格数量情况" />
      <ul class="gaugeList">
        <li>
          <gridGauge key="1" :info="gaugeInfo" />
        </li>
        <li>
          <gridGauge key="2" :info="gaugeInfo1" />
        </li>
      </ul>
      <boxTitle title="地区重点网格和重点用现对象数量情况" />
      <pieTwo :info="pieTwoData" />
      <boxTitle title="地区人民币知识宣传数量情况" />
      <girdPie3d :optionData="pie3dData" />
    </titleBox>
    <titleBox height="461px" title="网点概况">
      <viewAll slot="operate" @click="handleBottomViewAll"></viewAll>
      <cardThree :info="cardThreeData" />
      <boxTitle title="地区银行机构网点数量情况" />
      <gridGauge2 :info="gridGauge2Data" />
      <boxTitle title="地区网点服务类型的情况" />
      <listThree :info="listThreeData" />
      <listThree class="green" :info="listThreeData1" />
      <listTwo :info="listTwoData" />


      <!-- 查看全部 -->
      <viewDialog v-model="showDialog" :title="dialogTitle" :annotation="dialogAnnotation">
        <component :is="dialogComponentId" :showGrid="showGrid" :value="viewAllData"></component>
      </viewDialog>
    </titleBox>
  </div>
</template>

<script>
import services from "../api";
import titleBox from "@/modules/module-screenView/components/titleBox";

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
import leftDialog2 from "../components/leftDialog2";
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
  },
  data() {
    return {
      gaugeInfo: {
        max: 3332,
        value: 3026,
        colorStart: '#FFCF4C',
        colorStop: '#005EFF',
        title: '行政村网格'
      },
      gaugeInfo1: {
        max: 3332,
        value: 306,
        colorStart: '#00FFB7',
        colorStop: '#00A3FF',
        title: '社区网格'
      },

      pieTwoData: [{
        rate: "9%",
        num: '333',
        title: "重点网格数量"
      },
      {
        rate: "12%",
        num: '1,332',
        title: '重点用现对象数量'
      }
      ],
      pie3dData: [
        { name: "开展大篷车", value: 135, itemStyle: { color: "rgba(90, 243, 184, 1)", opacity: 0.8 } },
        { name: "开展小钱箱子", value: 387, itemStyle: { color: "rgba(77, 116, 255, 1)", opacity: 0.8 } },
        { name: "开展线下兑换", value: 296, itemStyle: { color: "rgba(147, 219, 255, 1)", opacity: 0.8 } }
      ],
      cardThreeData: [{
        title: '人工现金柜台数',
        value: "1,508",
        unit: '个'
      },
      {
        title: '自助现金机具数',
        value: "1,309",
        unit: '台'
      },
      {
        title: '小面额自助现金机具数',
        value: "210",
        unit: '台'
      }
      ],



      latticeData: {
        visitorNum: '3332',
        afterUnit: '',
        type: 'blue'
      },
      cardInfo: [{
        title: '宣传总次数',
        value: "3548",
        unit: '次'
      },
      {
        title: '宣传受众人数',
        value: "39.18",
        unit: '万人'
      }],

      latticeData1: {
        visitorNum: '1314',
        afterUnit: '个'
      },
      latticeData2: {
        visitorNum: '54',
        afterUnit: '个',
        type: 'yellow'
      },
      latticeData3: {
        visitorNum: '1089',
        afterUnit: '个',
        type: 'dblue'
      },
      latticeData4: {
        visitorNum: '2686',
        afterUnit: '个',
        type: 'green'
      },

      cardTwoData: [{
        title: '银行网点',
        value: "1314",
        unit: '个'
      },
      {
        title: '现金服务点',
        value: "54",
        unit: '个'
      },
      {
        title: '责任银行网点',
        value: "1089",
        unit: '个'
      },
      {
        title: '农村金融服务站',
        value: "2686",
        unit: '个'
      }],
      gridGauge2Data: [
        {
          title: '责任网点',
          value: 1083,
        },
        {
          title: '普通网点',
          value: 231,
        }
      ],
      listThreeData: [{
        title: '人民币现金存取',
        value: "1296",

      },
      {
        title: '本外部现金存取',
        value: "495",
      },
      {
        title: '小面额兑换支取',
        value: "1296",
      }
      ],
      listThreeData1: [{
        title: '数字人民币服务',
        value: "1269",

      },
      {
        title: '假币鉴定',
        value: "516",
      },
      {
        title: '真伪授权坚定',
        value: "22",
      }
      ],
      listTwoData: [{
        title: '特殊残损人民币兑换',
        value: "22",
      }, {
        title: '停止流通人民币兑换',
        value: "34",
      }],


      showTooltip: false,
      isTop: true,
      componentId: customList,
      showDialog: false,
      dialogComponentId: leftDialog2,
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
        }
      },
      immediate: true,
    },
    filterParam: {
      handler(val) {
        const { groupCategory } = val;
        if (!this.ifInit && groupCategory != "") {
          this.getCustomerType();
          this.smallLoan();
        }
      },
      deep: true,
    },
    roleParam: {
      handler() {
        if (!this.ifInit && this.orgList.length > 1) {
          this.smallLoan();
          this.smallOverView();
        }
      },
      deep: true,
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
    });
  },
  methods: {
    getCustomerType() {
      const { typeList, groupCategory } = this.filterParam;
      if (this.filterType === "1") {
        let arr = typeList.filter((item) => item !== "3");
        this.customerType = arr.join(",");
      } else {
        this.customerType = groupCategory == "3" ? null : groupCategory;
      }
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
      console.log(22222)
      this.dialogTitle = "网格概况";
      this.dialogAnnotation = "";
      this.showGrid = true;
      this.showDialog = true;
      this.viewAllData = {
        latticeData: this.latticeData,
        gaugeInfo: this.gaugeInfo,
        gaugeInfo1: this.gaugeInfo1,
        pieTwoData: this.pieTwoData,
        pie3dData: this.pie3dData,
        cardInfo: this.cardInfo
      };
    },
    handleBottomViewAll() {
      this.dialogTitle = "网点概况";
      this.dialogAnnotation = "";
      this.showGrid = false;
      this.showDialog = true;
      this.viewAllData = {
        cardThreeData: this.cardThreeData,
        latticeData1: this.latticeData1,
        latticeData2: this.latticeData2,
        latticeData3: this.latticeData3,
        latticeData4: this.latticeData4,
        gridGauge2Data: this.gridGauge2Data,
        listThreeData: this.listThreeData,
        listThreeData1: this.listThreeData1,
        listTwoData: this.listTwoData,
        cardTwoData: this.cardTwoData
      };
    },
    smallLoan() {
      return
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
      return
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
      return
      console.log("11111");
      const param = {
        gridCode: this.gridCode,
      };
      services.getByGridCode(param).then((res) => {
        const { data } = res;
        this.gridInfo = data;
      });
    },
    // 获取网格关键人信息
    getGridCruxInfo() {
      return
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

.gaugeList {
  margin-top: -20px;
  display: flex;

  li {
    flex: 1;
  }
}
</style>
