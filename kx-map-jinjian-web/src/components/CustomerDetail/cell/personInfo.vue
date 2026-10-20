<template>
  <div class="person-info-wrapper">
    <div class="base-top">
      <popTitle :text="'客户基本信息'" :color="'#DD973C'" />
      <br />
      <div class="customerBasicTitle">
        <div class="customerName">
          <div class="text">
            {{ basicInfoValue ? basicInfoValue.name : "" }}
          </div>
          <div class="selfTag" :class="
                  tagName(
                    basicInfoValue ? basicInfoValue.startCustomLevelName : ''
                  )
                ">
            {{ basicInfoValue ? basicInfoValue.startCustomLevelName : "" }}
          </div>
        </div>
        <div class="GDP">
          GDP（营收）
          <span class="orange">{{
                basicInfoValue.gdpData ? basicInfoValue.gdpData.totalGDP : 0
              }}</span>
          <span class="unit">元</span>
        </div>
        <div v-show="
                basicInfoValue.riskDescribeList &&
                basicInfoValue.riskDescribeList.length
              " class="raskTag">
          <span>风险客户</span>
          <span class="el-icon-info"></span>
          <ul class="raskList">
            <li v-for="(item, i) in basicInfoValue.riskDescribeList" :key="i">
              {{ item }}
            </li>
          </ul>
        </div>
      </div>
      <showInfos :text-style="textStyle" :text-value-style="textValueStyle" :list="basicInfoList"
                 :data-value="basicInfoValue" @jump="jump" item-width="33%" style="padding: 1rem" />
    </div>
    <div class="divider"></div>
    <!-- 下部分 -->
    <div class="customerBasicInfo">
      <popTitle :text="'客户产品持有情况(时点)'" :color="'#DD973C'" />
      <!-- 客户视图 -->
      <prod-list :prod="prod" style="margin-top: 20px" />
      <div class="divider"></div>

      <popTitle :text="'客户数据(日均)'" :color="'#DD973C'" />
      <ul class="GDPUL" v-if="basicInfoValue.gdpData">
        <li v-for="item in GDPList" :key="item.prop">
          <span class="title">{{ item.label }}</span>
          <span class="value">{{
                  basicInfoValue.gdpData[item.prop]
                }}</span>
          <div class="unit">元</div>
        </li>
      </ul>
      <aum-list :aum-list="aumList" style="margin-top: 20px" />
      <template v-if="!isLskh">
        <div class="divider"></div>
        <popTitle :text="'客户标签'" :color="'#DD973C'" />
        <custom-label :system-label="systemLabel" :network-label="networkLabel" />
        <!-- @addCustomerLabel="addCustomerLabel" -->
      </template>
      <br />
      <div class="divider"></div>
      <popTitle :text="'产品到期一览表'" :color="adventProductTableData.length > 0 ? '#DD973C' : '#ccc'" />
      <!-- v-loading="listLoading" -->
      <el-table v-if="adventProductTableData.length > 0" :data="adventProductTableData" fit
                size="small" highlight-current-row height="calc(100vh - 440px)" style="width: 100%">
        <el-table-column v-for="item in productColumns" :key="item.prop" :prop="item.prop"
                         :label="item.label" header-align="center" align="center" />
      </el-table>
      <br />
    </div>
    <div class="divider"></div>
    <popTitle :text="'交易记录查询'" :color="cardNumbers.length > 0 ? '#DD973C' : '#ccc'" />
    <br>
    <transactionRecords :tableData="recordsTableData" :cardList="cardNumbers" />
  </div>
</template>

<script>
import popTitle from "../components/popTitle";
import showInfos from "../components/showInfos";
import chartTitle from "../components/chartTitle";
import preferenceProportion from "../components/preferenceProportion";
import showLegend from "../components/showLegend";
import recordList from "../components/recordList";
import custLabel from "../components/custLabel";
import custProduct from "../components/custProduct";
import aumList from "../components/aumList";
import prodList from "../components/prodList";
import customLabel from "../components/customLabel";
import transactionRecords from "../components/transactionRecords";
import baseInfo from "../components/baseInfo";

import mockData from '../mock/index.json'
import customInfo from '../mock/customInfo.json'
import labelObj from '../mock/label.json'

import mixin from '../mixins/mixin.vue'

import { basicInfoList } from '../config/baseInfo';
import service from '../api';
export default ({
  mixins: [mixin],
  components: {
    aumList,
    popTitle,
    showInfos,
    chartTitle,
    preferenceProportion,
    showLegend,
    recordList,
    custProduct,
    custLabel,
    prodList,
    customLabel,
    transactionRecords,
    baseInfo,
  },
  props: {
    customerId: {
      type: [String, Number]
    },
  },
  dicts: ['business_premises_type', 'marriage_status', 'sys_user_sex'],
  data() {
    return {
      activeName: "customer",
      GDPList: mockData.GDPList,
      profile: customInfo.profile, // 所有的数据
      aumList: mockData.aumList,
      customerData: {},
      option: {},
      basicInfoList: JSON.parse(JSON.stringify(basicInfoList)),
      otherInfoList: mockData.otherInfoList,
      basicInfoValue: customInfo,
      otherInfoValue: customInfo,
      interviewStatusList: [
        { label: "姓名", prop: "name" },
        { label: "联系电话", prop: "phone" },
        { label: "详细地址", prop: "address" },
        { label: "身份证号", prop: "idenNo" },
        { label: "工作单位", prop: "workUnit" },
        {
          label: "与户主关系",
          prop: "houseHolderRelation",
          formatter: (val) =>
            this.getDictsLabel(this.GRIDFAMILYRELATION, val, true),
        },
        { label: "网格标签", prop: "gridLabel", slot: true }, //
        {
          label: "他行存款到期需求", //
          prop: "gridLabel",
          formatter: (val) => this.getLabelName("G01001001", val),
        },
        {
          label: "是否需要消息通知", //
          prop: "needNotification",
          formatter: (val) => this.getDictsLabel(WHETHERORNOT, val),
        },
        { label: "到期日期", prop: "maturedDate" },
        { label: "到期金额(万元)", prop: "maturedAmount" },
        { label: "备注", prop: "maturedRemark" },
        {
          label: "其他需求",
          prop: "gridLabel",
          formatter: (val) => this.getLabelName("G01001007", val),
        }, //
        { label: "其他需求备注", prop: "otherNeedRemark" },
        // {
        //   label: "是否在家/触达", //
        //   prop: "reachStatus",
        //   formatter: (val) => this.getDictsLabel(WHETHERORNOT, val),
        // },
        {
          label: "是否为走访黑户", //
          prop: "visitBlackList",
          formatter: (val) => this.getDictsLabel(WHETHERORNOT, val),
        },
        { label: "风险客户备注", prop: "riskCustomerRemark" }, //
        { label: "走访备注", prop: "visitRemark" },
        { label: "走访照片", prop: "visitImgFile", type: "image" },
      ],

      interviewStatusValue: {},
      interviewList: [
        {
          name: "走访时间",
          prop: "createDate",
        },
        {
          name: "走访人员",
          prop: "createUserName",
        },
        {
          name: "走访方式",
          prop: "visitType",
          type: "select",
          formatter: (val) => {
            const arr = ["一键走访", "正常走访"];
            return arr[val - 1];
          },
        },
        {
          name: "操作",
          type: "operation",
          prop: "showDetailText",
          color: "#00B4FD",
        },
      ],
      systemList: [], // 系统标签列表
      nm: "", // 客户内码
      id: "", // 客户唯一标识
      visitsQuery: {
        customDetailId: "",
        pageSize: 10,
        pageIndex: 1,
      },
      newVisitList: {}, // 最新走访信息
      historyVisitList: [], // 历史走访信息
      findVisitList: {},
      showItemDetail: {}, // 查看客户走访信息详情
      showItemD: false, // 点击详情查看详情
      totalnum: 0,
      detailId: null,
      product: null,
      loading: false,
      financeCurrentPie: customInfo.financePie, // 财富信息
      propertyCurrentPie: customInfo.propertyPie, // 资产信息
      prod: labelObj.L03,
      systemLabel: labelObj.L01, // 系统标签
      networkLabel: labelObj.G01, // 网格标签
      productColumns: mockData.productColumns,//产品到期一览表 , 
      recordsColumns: mockData.recordsColumns, //交易记录  ,
      recordsTableData: [],
      adventProductTableData: [],
      cardNumbers: ['6230710109016329721'],
      GRIDFAMILYRELATION: [],
    };
  },
  mounted() {
    this.customerId && this.getCustomerBaseInfo()
  },
  methods: {
    getCustomerBaseInfo() {
      const param = {
        id: this.customerId
      }
      service.customerDetail(param).then(res => {
        const { data: { customerInfo } } = res
        this.basicInfoValue.name = customerInfo.customerName
        for (let item of this.basicInfoList) {
          if (item.options) {
            item.value = this.getText(customerInfo[item.prop], this.dict.type[item.options])
          } else {

            item.value = customerInfo[item.prop]
          }
        }
      })
    },
    loopForText(arr, customerInfo) {

      for (let i = 0; i < arr.length; i++) {
        const element = arr[i];
        if (element.options) {
          element.value = this.getText(customerInfo[element.prop], this.dict.type[element.options])
        }
      }
    },
    getText(val, arr) {
      const name = arr.find((e) => e.value == val);
      return name && name.label;
    }
  }
})
</script>

<style lang="scss" scoped>
.el-mTabs {
  position: relative;
  z-index: -1;
}
.customerBasicInfo {
  // display: flex;
  font-family: Noto Sans S Chinese;
  font-size: 14px;
  .labelInfo {
    .labelCustomer {
      width: 100%;
      max-height: 100px;
      border-radius: 5px;
      overflow: hidden;
      overflow-y: auto;
      padding: 0 10px 10px 0;
    }

    .labelCustomer::-webkit-scrollbar {
      width: 1px;
      height: 1px;
    }
  }

  .upd-tag {
    display: flex;
    justify-content: space-between;
    padding-right: 30px;
  }

  .GDPUL {
    display: flex;
    justify-content: space-between;
    padding: 10px 0;
    li {
      width: 24%;
      list-style: none;
      border-radius: 5px;
      background-color: rgba(64, 158, 255, 0.1);
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 10px;
      .title {
        color: #333 !important;
      }
      .value {
        color: #1677dc;
        flex: 1;
        text-align: right;
        font-weight: 700;
      }
      .unit {
        color: #999;
        padding: 0 0 0 5px;
      }
      &:nth-child(1) {
        background-color: rgba(255, 132, 41, 0.1);
        .value {
          color: #ff8492;
        }
      }
    }
    .product {
      display: flex;
      > div {
        color: #000000;
        font-weight: 600;
      }
      .saving,
      .paying,
      .wallet {
        width: 13%;
        display: block;
        text-align: center;
      }
    }

    .wealth2 {
      display: flex;
      padding: 10px 20px 0 10px;

      .preference-proportion {
        margin-right: 20px;
      }
      .wealthCard {
        background-color: #fafbfe;
        border-radius: 4px;
        // width: 40%;
        height: 13rem;
      }
    }

    .familyTable {
      float: right;
      width: 65%;
    }
  }
}

.interviewHistory {
  width: 60%;
  padding-bottom: 50px;
  font-size: 16px;
  font-family: Noto Sans S Chinese;
  font-weight: 400;
  color: #333333;
}
.base-top {
  width: 100%;
  .customerBasicTitle {
    margin-left: 0.3rem;
    display: flex;
    align-items: center;

    .customerName {
      display: flex;
      align-items: center;
      width: 20%;
      font-size: 20px;
      font-weight: bold;
      color: #000000;
      .selfTag {
        display: inline-block;
        font-weight: normal;
        font-size: 14px;
        padding: 3px 5px;
        border-radius: 2px;
        &.label0 {
          color: #838383;
          background: rgba(131, 131, 131, 0.18);
        }
        &.label1 {
          color: #58e078;
          background: rgba(89, 224, 121, 0.18);
        }
        &.label2 {
          color: #13dbc1;
          background: rgba(19, 219, 193, 0.18);
        }
        &.label3 {
          color: #f6be28;
          background: rgba(246, 190, 40, 0.18);
        }
        &.label4 {
          color: #f94a1b;
          background: rgba(249, 74, 27, 0.18);
        }
        &.label5 {
          color: #c30787;
          background: rgba(195, 7, 135, 0.18);
        }
      }
      .text {
        padding: 0 1rem;
        line-height: 1.7rem;
        // width: 130px;
      }
    }
    .GDP {
      color: #333;
      &::before {
        content: "";
        display: inline-block;
        width: 11px;
        height: 11px;
        border-radius: 50%;
        background: #ea931c;
      }
      .orange {
        color: #ea931c;
        text-decoration: underline;
        font-weight: 700;
        padding: 0 5px;
        font-size: 18px;
      }
      .unit {
        color: #999;
        font-size: 14px;
      }
    }
    .raskTag {
      position: relative;
      background-color: #f6ecef;
      color: #ea4418;
      border: 1px solid #ea4418;
      padding: 0px 8px;
      border-radius: 4px;
      margin: 0 5px;
      line-height: 22px;
      cursor: pointer;
      span.iconfont {
        color: #f3886c;
      }
      &:hover {
        .raskList {
          display: block;
        }
      }
      .raskList {
        font-weight: normal;
        position: absolute;
        z-index: 250;
        width: 400%;
        top: 40px;
        left: -125%;
        color: #ea4418;
        border-radius: 6px;
        background-color: #f6ecef;
        padding: 10px;
        display: none;
        list-style: none;
      }
    }
    .customerLevel {
      margin-left: 1rem;
      display: flex;
      width: 10%;
      font-size: 22px;
      color: #f0a250;
      font-weight: bold;

      .text {
        padding-left: 1rem;
        line-height: 1.8rem;
      }
    }

    .aum {
      margin-left: 1rem;
      display: flex;
      width: 18%;
      font-size: 16px;
      font-weight: 500;
      color: #000000;
      line-height: 1.8rem;
      opacity: 0.7;

      .aumNum {
        color: #00b5fd;
      }
    }
  }
}
.need {
  color: #1677dc;
  margin: 0 10px 0 0;
  border: 1px solid #1677dc;
  border-radius: 4px;
  padding: 2px 5px;
}
.divider {
  height: 1px;
  // width: 98%;
  margin: 10px auto;
  background-color: rgba($color: #000d10, $alpha: 0.05);
}
</style>