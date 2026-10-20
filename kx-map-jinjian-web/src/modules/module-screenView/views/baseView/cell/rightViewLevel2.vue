<template>
  <div class="right-view-wrapper">
    <titleBox class="wrapper-top" height="299px" title="零钞预约概况">
      <viewAll slot="operate" @click="handleTopDialog"></viewAll>
      <cardTwo :info="cardTwoData" />
      <boxTitle title="地区零钞预约券别需求量情况" />
      <gridRankList :columns="rankColumns" :tableData="rankData" />
    </titleBox>
    <titleBox class="wrapper-top" height="299px" title="商户摸排概况">
      <viewAll slot="operate" @click="handleMiddleDialog"></viewAll>
      <cardBigTwo :info="cardBigTwoData" />
      <boxTitle title="地区各地市商户分类情况" />
      <pieOne :info="pieOneData" />
      <boxTitle title="地区各地市商户摸排情况" />
      <barOne :info="barOneData" />

    </titleBox>
    <titleBox class="wrapper-top" height="299px" title="投诉事件概况">
      <viewAll slot="operate" @click="handleBottomDialog"></viewAll>
      <cardOne :info="cardOneData" />
      <boxTitle title="地区各地市投诉事件情况" />
      <girdLineOne :info="girdLineOneData" />
    </titleBox>
    <!-- 贷款模块查看全部 -->
    <viewDialog v-model="showDialog" :title="dialogTitle" :annotation="annotation">
      <component :is="ComponentId" :showGrid="showGrid" :value="viewAllData"></component>
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
import girdLineOne from "@/modules/module-screenView/viewComponents/components/girdLineOne";
import cardOne from "@/modules/module-screenView/viewComponents/components/cardOne";
import pieOne from "@/modules/module-screenView/viewComponents/components/pieOne";



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
import rightDialog2 from "../components/rightDialog2";
import infoListBox from "../components/infoListBox/index.vue";
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
    girdLineOne,
    cardOne,
    pieOne,

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
  },
  data() {
    return {
      showGrid: 'top',
      viewAllData: {},
      cardTwoData: [{
        title: '预约兑换笔数',
        value: "1.9",
        unit: '万次'
      },
      {
        title: '预约兑换总金额',
        value: "380",
        unit: '万元'
      }, {
        title: '预约兑换完成率',
        value: "96",
        unit: '%'
      },
      {
        title: '预约兑换率',
        value: "98",
        unit: '%'
      },
      ],
      rankColumns: [
        { label: '劵别', prop: 'type', flex: 1, },
        { label: '预约劵别数', prop: 'type1', flex: 1 },
        { label: '占比', prop: 'type2', flex: 1 },
      ],
      rankData: [
        { type: '20元', type1: '8万', type2: '16.7%' },
        { type: '10元', type1: '5万', type2: '10%' },
        { type: '5元', type1: '10万', type2: '20.1%' },
        { type: '1元', type1: '20万', type2: '41.4%' },
        { type: '5角', type1: '1.5万', type2: '3%' },
        { type: '1角', type1: '3.7万', type2: '7.7%' },
      ],


      cardBigTwoData: [{
        title: '网格内摸排商户数',
        value: "186,995",
      },
      {
        title: '网格内现金收付摸排次数',
        value: "12,236",
      }
      ],

      pieOneData: [
        { value: 2562, name: '红牌商户数' },
        { value: 23352, name: '黄牌商户数' },
        { value: 178340, name: '绿牌商户数' }
      ],
      barOneData: {
        title: '摸排商户数',
        xData: ['24-9', '24-10', '24-11', '24-12', '25-1', '25-2'],
        yData: [20000, 16000, 5083, 7892, 3897, 23000],
      },

      cardOneData: {
        title: '投诉事件数量',
        value: "25"
      },
      girdLineOneData: {
        xData: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
        yDate: [3, 2, 1, 2, 5, 2, 3, 2, 1, 2, 1, 1],
      },


      showDialog: false,
      showLoans: true,
      ComponentId: rightDialog2,
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
    };
  },
  computed: {
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
        if (val && !this.ifInit && val != "point") {
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
      // this.initData();
      // this.getCustomerType();
      // this.smallDeposit();
      // this.smallProsperousLoan();
    });
  },
  methods: {
    initData() {
      this.fetchList();
      setTimeout(() => {
        this.fetchImportantList();
        this.fetchCommunityWatcher();
        this.fetchGridBasicOrg();
      }, 0);
      this.getOtherPointList();
      this.fecthCashPointList();
      this.fecthFinicialPointList();
      this.getGridRatingInfo();
      this.ifInit = false;
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
      this.showGrid = 'top';
      this.viewAllData = {
        cardTwoData: this.cardTwoData,
        rankColumns: this.rankColumns,
        rankData: this.rankData,
      }
    },

    handleMiddleDialog() {
      this.dialogTitle = "商户摸排概况";
      this.showDialog = true;
      this.showGrid = 'middle';
      this.viewAllData = {
        cardBigTwoData: this.cardBigTwoData,
        pieOneData: this.pieOneData,
        barOneData: this.barOneData
      }
    },
    handleBottomDialog() {
      this.dialogTitle = "投诉事件概况";
      this.showDialog = true;
      this.showGrid = 'bottom';
      this.viewAllData = {
        cardOneData: this.cardOneData,
        girdLineOneData: this.girdLineOneData
      }
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
</style>
