export const dataList = [
  // 授信
  // 户数-总数
  [
    {
      value: "0",
      key: "grantCreditNum",
    },
    {
      value: "0",
      key: "",
    },
    {
      value: "0",
      key: "grantCreditCompareMonthNum",
    },

    {
      value: "0",
      key: "grantCreditCompareYearNum",
    },
  ],
  // 户数-内
  [
    {
      value: "0",
      key: "grantCreditGridInsideNum",
    },
    {
      value: "0",
      key: "grantCreditInsideNumRatio",
    },
    {
      value: "0",
      key: "grantCreditInsideCompareMonthNum",
    },

    {
      value: "0",
      key: "grantCreditInsideCompareYearNum",
    },
  ],
  // 户数-外
  [
    {
      value: "0",
      key: "grantCreditGridOutsideNum",
    },
    {
      value: "0",
      key: "grantCreditOutsideNumRatio",
    },
    {
      value: "0",
      key: "grantCreditOutsideCompareMonthNum",
    },

    {
      value: "0",
      key: "grantCreditOutsideCompareYearNum",
    },
  ],
  // 金额-总数
  [
    {
      value: "0",
      key: "grantCreditBal",
    },
    {
      value: "0",
      key: "",
    },
    {
      value: "0",
      key: "grantCreditCompareMonthBal",
    },

    {
      value: "0",
      key: "grantCreditCompareYearBal",
    },
  ],
  // 金额-内
  [
    {
      value: "0",
      key: "grantCreditGridInsideBal",
    },
    {
      value: "0",
      key: "grantCreditInsideBalRatio",
    },
    {
      value: "0",
      key: "grantCreditInsideCompareMonthBal",
    },

    {
      value: "0",
      key: "grantCreditInsideCompareYearBal",
    },
  ],
  // 金额-外
  [
    {
      value: "0",
      key: "grantCreditGridOutsideBal",
    },
    {
      value: "0",
      key: "grantCreditOutsideBalRatio",
    },
    {
      value: "0",
      key: "grantCreditOutsideCompareMonthBal",
    },

    {
      value: "0",
      key: "grantCreditOutsideCompareYearBal",
    },
  ],
  // 用信
  // 户数-总数
  [
    {
      value: "0",
      key: "useCreditNum",
    },
    {
      value: "0",
      key: "",
    },
    {
      value: "0",
      key: "useCreditCompareMonthNum",
    },

    {
      value: "0",
      key: "useCreditCompareYearNum",
    },
  ],
  // 户数-内
  [
    {
      value: "0",
      key: "useCreditGridInsideNum",
    },
    {
      value: "0",
      key: "useCreditInsideNumRatio",
    },
    {
      value: "0",
      key: "useCreditInsideCompareMonthNum",
    },

    {
      value: "0",
      key: "useCreditInsideCompareYearNum",
    },
  ],
  // 户数-外
  [
    {
      value: "0",
      key: "useCreditGridOutsideNum",
    },
    {
      value: "0",
      key: "useCreditOutsideNumRatio",
    },
    {
      value: "0",
      key: "useCreditOutsideCompareMonthNum",
    },

    {
      value: "0",
      key: "useCreditOutsideCompareYearNum",
    },
  ],
  // 金额-总数
  [
    {
      value: "0",
      key: "useCreditBal",
    },
    {
      value: "0",
      key: "",
    },
    {
      value: "0",
      key: "useCreditCompareMonthBal",
    },

    {
      value: "0",
      key: "useCreditCompareYearBal",
    },
  ],
  // 金额-内
  [
    {
      value: "0",
      key: "useCreditGridInsideBal",
    },
    {
      value: "0",
      key: "useCreditInsideBalRatio",
    },
    {
      value: "0",
      key: "useCreditInsideCompareMonthBal",
    },

    {
      value: "0",
      key: "useCreditInsideCompareYearBal",
    },
  ],
  // 金额-外
  [
    {
      value: "0",
      key: "useCreditGridOutsideBal",
    },
    {
      value: "0",
      key: "useCreditOutsideBalRatio",
    },
    {
      value: "0",
      key: "useCreditOutsideCompareMonthBal",
    },

    {
      value: "0",
      key: "useCreditOutsideCompareYearBal",
    },
  ],
];

export const tableData = [
  {
    typeName: "逾欠余额",
    Bal: "0",
    CompareMonthBal: "0",
    CompareYearBal: "0",
    GridInsideRatio: "0",
    GridOutsideRatio: "0",
    Ratio: '0'
  },
  {
    typeName: "不良余额",
    Bal: "0",
    CompareMonthBal: "0",
    CompareYearBal: "0",
    GridInsideRatio: "0",
    GridOutsideRatio: "0",
    Ratio: '0'
  },
];

export const introBoxData = [
  { title: "网格名称", value: "0", key: "gridName" },
  { title: "网格主管员", value: "0", key: "gridUserName" },
  {
    title: "网格等级",
    value: "0",
    className: "level",
    key: "gridLevel",
  },
  { title: "所属机构", value: "0", key: "orgName" },
];

export const gridBoxData = [
  {
    label: "四级网络",
    value: "0",
    pic: "grid-four.png",
    color: "#49aaed",
    key: "levelFourGridNum",
  },
  {
    label: "五级网络",
    value: "0",
    pic: "grid-five.png",
    color: "#8bf6ff",
    key: "levelFiveGridNum",
  },
];

export const customerList = [
  {
    color: "#148BDB",
    type: "绩效客户",
    num: "0",
    show: false,
    key: "performanceCustomerNum",
  },
  {
    color: "#1CEDBB",
    type: "授信客户",
    num: "0",
    show: false,
    key: "creditCustomerNum",
  },
  {
    color: "#10E9DE",
    type: "建档客户",
    num: "0",
    show: false,
    key: "recordCustomerNum",
  },
  // {
  //   color: "#FFFB8B",
  //   type: "价值客户",
  //   num: "",
  //   show: false,
  //   key: "valueCustomerNum",
  // },
];

export const gridDetailData = [
  { label: "网格人口", value: "0", key: "gridPeopleNum", unit: '人' },
  { label: "经济总量", value: "0", key: "gdp", unit: '元' },
  { label: "小区数量", value: "0", key: "villageNum", unit: '个' },
  { label: "小区人口", value: "0", key: "villagePeopleNum", unit: '人' },
  { label: "工业园数量", value: "0", key: "industrialParkNum", unit: '个' },
  { label: "企业数量", value: "0", key: "companyNum", unit: '个' },
  {
    label: "金融机构数量",
    value: "0",
    key: "financialInstitutionNum",
    unit: '个'
  },
  { label: "商圈个数", value: "0", key: "businessAreaNum", unit: '个' },
];

export const customListData = [
  // {
  //   label: "绩效客户",
  //   key: "performance",
  //   InsideCustomerNum: "0",
  //   InsideCustomerRatio: "0",
  //   OutsideCustomerNum: "0",
  //   OutsideCustomerRatio: "0",
  // },
  {
    label: "行内客户",
    key: "credit",
    InsideCustomerNum: "0",
    InsideCustomerRatio: "0",
    OutsideCustomerNum: "0",
    OutsideCustomerRatio: "0",
  },
  {
    label: "建档客户",
    key: "record",
    InsideCustomerNum: "0",
    InsideCustomerRatio: "0",
    OutsideCustomerNum: "0",
    OutsideCustomerRatio: "0",
  },
  // {
  //   label: "价值客户",
  //   key: "value",
  //   InsideCustomerNum: "",
  //   InsideCustomerRatio: "",
  //   OutsideCustomerNum: "",
  //   OutsideCustomerRatio: "",
  // },
];

export const creditData = [
  {
    img: require("@/modules/module-screenView/assets/image/yongxin-household.png"),
    leftLabel: "授信户数",
    leftValue: "0",
    rightLabel: "授信金额",
    rightValue: "0",
  },
  {
    img: require("@/modules/module-screenView/assets/image/shouxin-household.png"),
    leftLabel: "贷款户数",
    leftValue: "0",
    rightLabel: "贷款余额",
    rightValue: "0",
  },
];
