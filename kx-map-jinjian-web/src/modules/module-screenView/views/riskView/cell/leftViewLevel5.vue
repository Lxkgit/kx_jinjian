<template>
  <div class="left-view-wrapper">
    <titleBox class="wrapper-all" height="944px" title="网格概况" annotation="">
      <!-- <viewAll slot="operate" @click="handleTopViewAll"></viewAll> -->
      <boxTitle title="网格信息" />
      <gridMessage :info="saveData" />
      <template v-if="importantList.length > 0">
        <boxTitle title="重点管理对象" />
        <importantBox :info="importantList" />
      </template>
      <boxTitle title="责任网点信息" />
      <deptBox :info="infoData" />
      <template v-if="otherPointList.length > 0">
        <boxTitle title="普通网点信息" />
        <div class="scroll">
          <info-list-box v-for="(item, idx) in otherPointList" :key="idx" :info="item" leftKey="anchorName"
            :columns="otherPointColumns">
            <div slot="img">
              <img slot="img" :src="require('@/modules/module-screenView/assets/image/pointIcon1.png')
                " style="margin-right: 7.5px" />
            </div>
          </info-list-box>
        </div>
      </template>
      <template v-if="cashPointList.length > 0">
        <boxTitle title="网格内现金服务点信息" />
        <div class="scroll" v-if="cashPointList.length > 0">

          <info-list-box v-for="(item, idx) in cashPointList" :key="idx" :info="item" leftKey="servePointName"
            :columns="cashPointColumns">
            <div slot="img">
              <img slot="img" :src="require('@/modules/module-screenView/assets/image/pointIcon2.png')
                " style="margin-right: 7.5px" />
            </div>
          </info-list-box>
        </div>
      </template>

      <template v-if="finicialPointList.length > 0">
        <boxTitle title="网格内农村金融服务站信息" />
        <div class="scroll" v-if="finicialPointList.length > 0">

          <info-list-box v-for="(item, idx) in finicialPointList" :key="idx" :info="item" leftKey="pointName"
            :columns="finicialPointColumns">
            <div slot="img">
              <img slot="img" :src="require('@/modules/module-screenView/assets/image/pointIcon3.png')
                " style="margin-right: 7.5px" />
            </div>
          </info-list-box>
        </div>
      </template>

      <template v-if="gridWatcherList.length > 0">
        <boxTitle title="社会监督员" />
        <info-list-box class="info-box-2" v-for="(item, idx) in gridWatcheList" :key="idx" :info="item"
          leftKey="supervisorName" :columns="gridWatcheColumns">
          <div slot="img">
            <img slot="img" :src="require('@/modules/module-screenView/assets/image/pointIcon4.png')
              " style="margin-right: 7.5px" />
          </div>
        </info-list-box>
      </template>

      <template v-if="gridBasicOrgData.length > 0">
        <boxTitle title="基层组织" />
        <info-list-box class="info-box-3" v-for="(item, idx) in gridBasicOrgData" :key="idx" :info="item"
          leftKey="grassroots" :columns="gridBasicOrgColumns">
          <div slot="img">
            <img slot="img" :src="require('@/modules/module-screenView/assets/image/pointIcon5.png')
              " style="margin-right: 7.5px" />
          </div>
        </info-list-box>
      </template>
      <template v-if="policeData.length > 0">
        <boxTitle title="管辖派出所" />

        <info-list-box class="info-box-3" v-for="(item, idx) in policeData" :key="idx" :info="item" leftKey="policeName"
          :columns="policeInfoColumns">
          <div slot="img">
            <img slot="img" :src="require('@/modules/module-screenView/assets/image/pointIcon6.png')
              " style="margin-right: 7.5px" />
          </div>
        </info-list-box>
      </template>


    </titleBox>



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
import {
  getCashPointInGrid,
  getFinancialInGrid,
} from "@/components/NetworkDetail/api";
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
  dicts: ["new_grid_type", "basic_org_type"],
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
    gridMessage,
    importantBox,
    deptBox,
    infoListBox,

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


      saveData: {
        gridType: '社区',
        gridName: '泉源社区',
        bankLeader: '章琳',
        leaderPhone: '13566769915',
        gridStaff: '张章',
        staffPhone: '13566769915',
        merchantsNum: 142,
        knowledgeTimes: 142,
        cashServiceTimes: 5885,
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
      importantList: [
        // { importName: "1111", importAddress: "2222" },
        // { importName: "3333", importAddress: "4444" },
      ],

      otherPointList: [],
      otherPointColumns: [
        { label: "银行机构", key: "branchBank" },
        { label: "网点负责人", key: "dutyName" },
        { label: "联系电话", key: "dutyPhone" },
        {
          label: "地址",
          key: "addressDesc",
        },
      ],


      cashPointList: [],
      cashPointColumns: [
        { label: "所在网格", key: "gridName" },
        { label: "网点负责人", key: "dutyName" },
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
        },
      ],

      gridWatcherList: [],
      gridWatcheColumns: [
        { label: "联系电话", key: "dutyPhone" },
      ],

      gridBasicOrgData: [],
      gridBasicOrgColumns: [
        { label: "基层组织类型", key: "grassrootsType" },
        { label: "联系人", key: "contactName" },
        { label: "联系电话", key: "contactPhone" },
      ],
      policeInfoColumns: [
        { label: "反假联系人", key: "unFakerName" },
        { label: "联系电话", key: "unFakerPhone" },
        { label: "地址", key: "policeAddress" },
      ],
      policeData: [
      ],

      pointList1: [{
        dutyGroup: '泉源社区',
        dutyPhone: '13483939334',
        dutyName: '丁程',
        pointName: 'xxxx基层组织'
      }, {
        dutyGroup: '泉源社区',
        dutyPhone: '13483939334',
        dutyName: '丁程',
        pointName: 'xxxx基层组织'

      }],
      pointColumns1: [
        { label: "联系电话", key: "dutyPhone" },
        { label: "联系人姓名", key: "dutyPhone" },
        { label: "基层组织类型", key: "dutyPhone" },
      ],

      girdPieData: {
        num1: 100,
        num2: 200,
        pieData: [
          { value: 23, name: '杭州市' },
          { value: 17, name: '宁波市' },
          { value: 52, name: '温州市' },
          { value: 26, name: '绍兴市' },
          { value: 13, name: '湖州市' },
          { value: 13, name: '嘉兴市' },
          { value: 2, name: '金华市' },
          { value: 3, name: '衢州市' },
          { value: 2, name: '舟山市' },
          { value: 2, name: '台州市' },
          { value: 1, name: '丽水市' },
        ],
      },
      latticeData: {
        visitorNum: '336148',
        afterUnit: '个',
        type: 'blue'
      },
      cardInfo: [{
        title: '宣传总次数',
        value: "3603",
        unit: '次'
      },
      {
        title: '宣传受众人数',
        value: "3630",
        unit: '万人'
      }],

      latticeData1: {
        visitorNum: '336336148',
        afterUnit: '个'
      },
      latticeData2: {
        visitorNum: '759',
        afterUnit: '个',
        type: 'yellow'
      },
      latticeData3: {
        visitorNum: '345759',
        afterUnit: '个',
        type: 'dblue'
      },
      latticeData4: {
        visitorNum: '759',
        afterUnit: '个',
        type: 'green'
      },


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
      // this.smallLoan();
      // this.smallOverView();
    });
  },
  beforeDestroy() {
    this.$EventBus.$off("mapView");
  },
  mounted() {
    this.$nextTick(() => {
      this.initData();
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
        this.saveData.gridName = data.gridName || "-";
        this.saveData.gridType =
          this.dict.label.new_grid_type[data.ruralGridType] || "-";
        this.saveData.bankLeader = data.gridMainManagerName || "-";
        this.saveData.leaderPhone = data.gridMainManagerPhone || "-";
        this.saveData.gridStaff = data.gridInspectorName || "-";
        this.saveData.staffPhone = data.gridInspectorPhone || "-";
        this.infoData.forEach((item) => {
          item.right = data[item.key];
        });
        // console.log('this.infoData', this.infoData)
      });
    },

    fetchImportantList() {
      let params = {
        gridCode: this.gridCode,
      };
      service
        .getGridImportant(params)
        .then((res) => {
          this.$nextTick(() => {
            // console.log('重点管理对象', res)
            this.importantList = res;
          });
        })
        .catch((err) => {
          this.$nextTick(() => {
            this.importantList = [];
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
        const { data } = res;
        if (data) {
          this.otherPointList = data;
          console.log('this.otherPointList', this.otherPointList)
        }
      });
    },
    fecthCashPointList() {
      let data = [this.gridCode];
      getCashPointInGrid(data).then((res) => {
        this.cashPointList = res.data;
        console.log('this.cashPointList', this.cashPointList)

      });
    },

    fecthFinicialPointList() {
      let data = [this.gridCode];
      getFinancialInGrid(data).then((res) => {
        this.finicialPointList = res.data;
      });
    },

    fetchCommunityWatcher() {
      let params = {
        gridCode: this.gridCode,
      };
      service
        .getCommunityWatcherList(params)
        .then((res) => {
          this.$nextTick(() => {
            this.gridWatcherList = res;
            console.log('this.gridWatcherList', this.gridWatcherList)
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
        latticeData: this.latticeData,
        cardInfo: this.cardInfo
      };
    },
    handleBottomViewAll() {
      this.dialogTitle = "网点概况";
      this.dialogAnnotation = "";
      this.showGrid = false;
      this.showDialog = true;
      this.viewAllData = {
        latticeData1: this.latticeData1,
        latticeData2: this.latticeData2,
        latticeData3: this.latticeData3,
        latticeData4: this.latticeData4,
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

.scroll {
  max-height: 260px;
  overflow: hidden;
  overflow-y: scroll;
  margin-bottom: 12px;

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
