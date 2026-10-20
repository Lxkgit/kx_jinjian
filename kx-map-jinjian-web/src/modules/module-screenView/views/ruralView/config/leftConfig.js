export const gridBoxData = [
  {
    label: "四级网络",
    value: '0',
    pic: "grid-four.png",
    color: "#49aaed",
    key: "levelFourGridNum",
  },
  {
    label: "五级网络",
    value: '0',
    pic: "grid-five.png",
    color: "#8bf6ff",
    key: "levelFiveGridNum",
  },
];

export const introBoxData = [
  { title: "网格名称", value: "", key: "gridOverviewId" },
  { title: "网格主管员", value: "", key: "gridOverviewId" },
  {
    title: "网格等级",
    value: "",
    className: "level",
    key: "gridOverviewId",
  },
  { title: "所属支行", value: "", key: "gridOverviewId" },
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
  // {
  //   color: "#F38D29",
  //   type: "收单客户",
  //   num: "0",
  //   show: false,
  //   key: "creditCustomerNum",
  // },
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
  //   num: "0",
  //   show: false,
  //   key: "valueCustomerNum",
  // },
];

export const gridDetailData = [
  { label: "网格人口（人）", value: "0", key: "gridPeopleNum" },
  { label: "经济总量（元）", value: "0", key: "gdp" },
  { label: "小区数量（个）", value: "0", key: "villageNum" },
  { label: "小区人口（人）", value: "0", key: "villagePeopleNum" },
  { label: "工业园数量（个）", value: "0", key: "industrialParkNum" },
  { label: "企业数量（个）", value: "0", key: "companyNum" },
  {
    label: "金融机构数量（个）",
    value: "0",
    key: "financialInstitutionNum",
  },
  { label: "商圈个数（个）", value: "0", key: "businessAreaNum" },
];

export const customListData = [
  {
    label: "绩效客户",
    key: "performance",
    InsideCustomerNum: "",
    InsideCustomerRatio: "",
    OutsideCustomerNum: "",
    OutsideCustomerRatio: "",
  },
  {
    label: "授信客户",
    key: "credit",
    InsideCustomerNum: "",
    InsideCustomerRatio: "",
    OutsideCustomerNum: "",
    OutsideCustomerRatio: "",
  },
  // {
  //   label: "收单客户",
  //   key: "acquire",
  //   InsideCustomerNum: "",
  //   InsideCustomerRatio: "",
  //   OutsideCustomerNum: "",
  //   OutsideCustomerRatio: "",
  // },
  {
    label: "建档客户",
    key: "record",
    InsideCustomerNum: "",
    InsideCustomerRatio: "",
    OutsideCustomerNum: "",
    OutsideCustomerRatio: "",
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

export const dataList = [
  // 授信
  // 户数-总数
  [
    {
      value: "",
      key: "grantCreditNum",
    },
    {
      value: "",
      key: "",
    },
    {
      value: "",
      key: "grantCreditCompareMonthNum",
    },

    {
      value: "",
      key: "grantCreditCompareYearNum",
    },
  ],
  // 户数-内
  [
    {
      value: "",
      key: "grantCreditGridInsideNum",
    },
    {
      value: "",
      key: "grantCreditInsideNumRatio",
    },
    {
      value: "",
      key: "grantCreditInsideCompareMonthNum",
    },

    {
      value: "",
      key: "grantCreditInsideCompareYearNum",
    },
  ],
  // 户数-外
  [
    {
      value: "",
      key: "grantCreditGridOutsideNum",
    },
    {
      value: "",
      key: "grantCreditOutsideNumRatio",
    },
    {
      value: "",
      key: "grantCreditOutsideCompareMonthNum",
    },

    {
      value: "",
      key: "grantCreditOutsideCompareYearNum",
    },
  ],
  // 金额-总数
  [
    {
      value: "",
      key: "grantCreditBal",
    },
    {
      value: "",
      key: "",
    },
    {
      value: "",
      key: "grantCreditCompareMonthBal",
    },

    {
      value: "",
      key: "grantCreditCompareYearBal",
    },
  ],
  // 金额-内
  [
    {
      value: "",
      key: "grantCreditGridInsideBal",
    },
    {
      value: "",
      key: "grantCreditInsideBalRatio",
    },
    {
      value: "",
      key: "grantCreditInsideCompareMonthBal",
    },

    {
      value: "",
      key: "grantCreditInsideCompareYearBal",
    },
  ],
  // 金额-外
  [
    {
      value: "",
      key: "grantCreditGridOutsideBal",
    },
    {
      value: "",
      key: "grantCreditOutsideBalRatio",
    },
    {
      value: "",
      key: "grantCreditOutsideCompareMonthBal",
    },

    {
      value: "",
      key: "grantCreditOutsideCompareYearBal",
    },
  ],
  // 用信
  // 户数-总数
  [
    {
      value: "",
      key: "useCreditNum",
    },
    {
      value: "",
      key: "",
    },
    {
      value: "",
      key: "useCreditCompareMonthNum",
    },

    {
      value: "",
      key: "useCreditCompareYearNum",
    },
  ],
  // 户数-内
  [
    {
      value: "",
      key: "useCreditGridInsideNum",
    },
    {
      value: "",
      key: "useCreditInsideNumRatio",
    },
    {
      value: "",
      key: "useCreditInsideCompareMonthNum",
    },

    {
      value: "",
      key: "useCreditInsideCompareYearNum",
    },
  ],
  // 户数-外
  [
    {
      value: "",
      key: "useCreditGridOutsideNum",
    },
    {
      value: "",
      key: "useCreditOutsideNumRatio",
    },
    {
      value: "",
      key: "useCreditOutsideCompareMonthNum",
    },

    {
      value: "",
      key: "useCreditOutsideCompareYearNum",
    },
  ],
  // 金额-总数
  [
    {
      value: "",
      key: "useCreditBal",
    },
    {
      value: "",
      key: "",
    },
    {
      value: "",
      key: "useCreditCompareMonthBal",
    },

    {
      value: "",
      key: "useCreditCompareYearBal",
    },
  ],
  // 金额-内
  [
    {
      value: "",
      key: "useCreditGridInsideBal",
    },
    {
      value: "",
      key: "useCreditInsideBalRatio",
    },
    {
      value: "",
      key: "useCreditInsideCompareMonthBal",
    },

    {
      value: "",
      key: "useCreditInsideCompareYearBal",
    },
  ],
  // 金额-外
  [
    {
      value: "",
      key: "useCreditGridOutsideBal",
    },
    {
      value: "",
      key: "useCreditOutsideBalRatio",
    },
    {
      value: "",
      key: "useCreditOutsideCompareMonthBal",
    },

    {
      value: "",
      key: "useCreditOutsideCompareYearBal",
    },
  ],
];

export const tableData = [
  {
    typeName: "逾欠余额",
    Bal: "",
    CompareMonthBal: "",
    CompareYearBal: "",
    GridInsideRatio: "",
    GridOutsideRatio: "",
  },
  {
    typeName: "不良余额",
    Bal: "",
    CompareMonthBal: "",
    CompareYearBal: "",
    GridInsideRatio: "",
    GridOutsideRatio: "",
  },
];

export const depositCardData = [
  {
    label: "存款余额",
    key: "saveBalance",
    allNum: "0",
    lastMonth: "0",
    beginYear: "0",
    imgUrl: "1",
  },
  {
    label: "存款日均",
    key: "saveDayAvg",
    allNum: "0",
    lastMonth: "0",
    beginYear: "0",
    imgUrl: "2",
  },
  {
    label: "储蓄存款余额",
    key: "depositSaveBalance",
    allNum: "0",
    lastMonth: "0",
    beginYear: "0",
    imgUrl: "3",
  },
  {
    label: "储蓄存款日均",
    key: "depositSaveDayAvg",
    allNum: "0",
    lastMonth: "0",
    beginYear: "0",
    imgUrl: "4",
  },
]

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

export const loansInfoData = [
  {
    title: '信用',
    src: 'xinyong',
    households: '0',
    money: '0',
    overDebtNum: '0',
    overDebtRate: '0',
    badnessNum: '0',
    badnessRate: '0',
  },
  {
    title: '保证',
    src: 'baozheng',
    households: '0',
    money: '0',
    overDebtNum: '0',
    overDebtRate: '0',
    badnessNum: '0',
    badnessRate: '0',
  },
  {
    title: '抵押',
    src: 'diya',
    households: '0',
    money: '0',
    overDebtNum: '0',
    overDebtRate: '0',
    badnessNum: '0',
    badnessRate: '0',
  },
  {
    title: '质押',
    src: 'zhiya',
    households: '0',
    money: '0',
    overDebtNum: '0',
    overDebtRate: '0',
    badnessNum: '0',
    badnessRate: '0',
  },
]