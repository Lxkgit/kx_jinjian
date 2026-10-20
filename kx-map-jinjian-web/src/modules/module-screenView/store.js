const state = {
  orgCode: "",
  orgInfo: null, // 当前节点信息
  orgList: [],

  mapType: "2", // 地图类型

  //系统介绍开关
  systemIntroduce: true,

  /* 左右收缩 */
  leftHide: null,
  rightHide: null,

  /* 客户搜索 */
  anchorInfo: null,

  /* 客户筛选 */
  filterType: "", //筛选类型
  filterParam: null,
  roleParam: null,
  initRole: null,

  /* 热力图开关 */
  customerHot: false,
  creditHot: false,
  layerCheck: [],
  activityDetail: null,
  serviceFlag: false,
  ruralFinancialFlag: false,
  riskPoint: false,
  police: false,
  pointStatus1: false, // 商户
  pointStatus2: false, // 企业

  /* 右侧指标参数 */
  rightFlag: false,
  rightInfo: {},

  // 头部层级
  layers: null,
  minGridCount: null,

  cashPointTotal: null,
  ruralPointTotal: null,
};
const mutations = {
  SET_SYSTEM_INTRODUCE: (state, val) => {
    console.log("val1111111", val);
    state.systemIntroduce = val;
  },
  SET_ORG_CODE: (state, orgCode) => {
    state.orgCode = orgCode;
  },
  SET_ORG_INFO: (state, orgInfo) => {
    state.orgInfo = orgInfo;
  },
  SET_ORG_LIST: (state, orgList) => {
    state.orgList = orgList;
  },
  SET_LEFT_HIDE: (state, leftHide) => {
    state.leftHide = leftHide;
  },
  SET_RIGHT_HIDE: (state, rightHide) => {
    state.rightHide = rightHide;
  },
  SET_FILTER_TYPE: (state, filterType) => {
    state.filterType = filterType;
  },
  SET_FILTER_PARAM: (state, filterParam) => {
    state.filterParam = filterParam;
  },
  SET_CUSTOMER_HOT: (state, customerHot) => {
    state.customerHot = customerHot;
  },
  SET_RISK_POINT: (state, riskPoint) => {
    state.riskPoint = riskPoint;
  },
  SET_POLICE: (state, police) => {
    state.police = police;
  },
  SET_POINT_STATUS1: (state, status) => {
    state.pointStatus1 = status;
  },
  SET_POINT_STATUS2: (state, status) => {
    state.pointStatus2 = status;
  },
  SET_CREDIT_HOT: (state, creditHot) => {
    state.creditHot = creditHot;
  },
  SET_ANCHOR_INFO: (state, anchorInfo) => {
    state.anchorInfo = anchorInfo;
  },
  SET_ROLE_PARAM: (state, roleParam) => {
    state.roleParam = roleParam;
  },
  SET_MAP_TYPE: (state, mapType) => {
    state.mapType = mapType;
  },
  SET_INIT_ROLE: (state, initRole) => {
    state.initRole = initRole;
  },
  SET_LAYER_CHECK: (state, layerCheck) => {
    state.layerCheck = layerCheck;
  },
  SET_ACTIVITY_DETAIL: (state, activityDetail) => {
    state.activityDetail = activityDetail;
  },
  SET_RIGHT_FLAG: (state, rightFlag) => {
    state.rightFlag = rightFlag;
  },
  SET_RIGHT_INFO: (state, rightInfo) => {
    state.rightInfo = rightInfo;
  },
  SET_SERVICE_FLAG: (state, serviceFlag) => {
    state.serviceFlag = serviceFlag;
  },
  SET_RURAL_FINANCIAL_FLAG: (state, flag) => {
    state.ruralFinancialFlag = flag;
  },
  SET_LAYERS: (state, layers) => {
    state.layers = layers;
  },
  SET_MINGRIDCOUNT: (state, num) => {
    state.minGridCount = num;
  },
  SET_CASHPOINTTOTAL: (state, num) => {
    state.cashPointTotal = num;
  },
  SET_RURALPOINTTOTAL: (state, num) => {
    state.ruralPointTotal = num;
  },
};
const actions = {};
const getters = {
  systemIntroduce: (state) => state.systemIntroduce,
  orgCode: (state) => state.orgCode,
  orgInfo: (state) => state.orgInfo,
  orgList: (state) => state.orgList,
  leftHide: (state) => state.leftHide,
  rightHide: (state) => state.rightHide,
  filterType: (state) => state.filterType,
  filterParam: (state) => state.filterParam,
  customerHot: (state) => state.customerHot,
  riskPoint: (state) => state.riskPoint,
  police: (state) => state.police,
  pointStatus1: (state) => state.pointStatus1,
  pointStatus2: (state) => state.pointStatus2,
  creditHot: (state) => state.creditHot,
  anchorInfo: (state) => state.anchorInfo,
  roleParam: (state) => state.roleParam,
  mapType: (state) => state.mapType,
  initRole: (state) => state.initRole,
  layerCheck: (state) => state.layerCheck,
  activityDetail: (state) => state.activityDetail,
  rightFlag: (state) => state.rightFlag,
  rightInfo: (state) => state.rightInfo,
  serviceFlag: (state) => state.serviceFlag,
  ruralFinancialFlag: (state) => state.ruralFinancialFlag,
  layers: (state) => state.layers,
  minGridCount: (state) => state.minGridCount,
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
