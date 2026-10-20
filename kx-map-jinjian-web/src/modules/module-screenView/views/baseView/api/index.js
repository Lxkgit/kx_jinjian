import request from "@/utils/request";
const gateway = "system";

const urls = {
  smallProsperousLoan: `${gateway}/small/prosperousLoan/getByQuery`, // 凡荣贷模块
  smallDeposit: `${gateway}/small/deposit/getByQuery`, // 存款模块
  smallOverView: `${gateway}/small/overview/getByQuery`, // 网格概况模块
  smallLoan: `${gateway}/small/loan/getByQuery` /* 贷款模块 */,
  getByGridCode: `${gateway}/gridMessage/getByGridCode`, // 获取网格信息
  getGridCruxInfo: `${gateway}/gridMessage/getGridCruxInfo`, // 网格关键人

  getCoordinatesByCode: `${gateway}/business/addrInfo/getBaseCoordinatesByCode`, // 获取机构边界
  queryBySelect: `${gateway}/business/anchorPoint/queryBySelect`, // 获取锚点
  queryBySelectMini: `${gateway}/business/anchorPoint/queryBySelectMini`, // 获取热力图
  getHeatMap: `${gateway}/creditCustomerInfo/heatMap`, // 获取热力图数据
  //poc
  networkList: `${gateway}/dept/pilotCenterPart/networkList`, // 获取机构数据
  searchAct: `${gateway}/marketing/activities/page/list`, //活动列表查询
  getActResult: `${gateway}/marketing/activities/gain`, //活动成果
  activitiesList: `${gateway}/marketing/activities/cust/list`, //  活动成果 客户列表

  getServicePoints: `${gateway}/service/points/anchor`, // 获取现金服务点锚点
  getRuralFinancialPoints: `${gateway}/villageFinancial/service/points/anchor`, // 获取乡村金融服务点锚点
  category: `${gateway}/branch/view/category/path`,

  bankList: `${gateway}/bank/branch/list`, // 按行政区划代码查询银行网点
  supervisionList: `${gateway}/supervision/org/list`, // 按行政区划代码查询金融监管机构
  areaSplitPoints: `${gateway}/business/addrInfo/getAreaSplitPoints`, // 获取区域内随机两个点位
  customerPoints: `${gateway}/mock/yongjia/points`, // 商户、企业锚点
  boundaryList1: `${gateway}/mock/yongjia/boundary`, // 小区、写字楼、园区
};

export default {
  /* 凡荣贷模块 */
  smallProsperousLoan(params) {
    return request({
      url: urls["smallProsperousLoan"],
      method: "get",
      params,
    });
  },
  /* 存款模块 */
  smallDeposit(params) {
    return request({
      url: urls["smallDeposit"],
      method: "get",
      params,
    });
  },
  /* 网格概况模块 */
  smallOverView(params) {
    return request({
      url: urls["smallOverView"],
      method: "get",
      params,
    });
  },
  /* 贷款模块 */
  smallLoan(params) {
    return request({
      url: urls["smallLoan"],
      method: "get",
      params,
    });
  },
  getByGridCode(params) {
    return request({
      url: urls["getByGridCode"],
      method: "get",
      params,
    });
  },
  getGridCruxInfo(params) {
    return request({
      url: urls["getGridCruxInfo"],
      method: "get",
      params,
    });
  },
  /* get */
  getCoordinatesByCode(params) {
    return request({
      url: urls["getCoordinatesByCode"],
      method: "get",
      params,
    });
  },
  /* post */
  queryBySelectMini(data) {
    return request({
      url: urls["queryBySelectMini"],
      method: "post",
      data,
    });
  },
  queryBySelect(data) {
    return request({
      url: urls["queryBySelect"],
      method: "post",
      data,
    });
  },
  /* post */
  getHeatMap(data) {
    return request({
      url: urls["getHeatMap"],
      method: "post",
      data,
    });
  },
  /* get */
  networkList(params) {
    return request({
      url: urls["networkList"],
      method: "get",
      params,
    });
  },

  searchAct(params) {
    return request({
      url: urls["searchAct"],
      method: "get",
      params,
    });
  },
  getActResult(id) {
    return request({
      url: `${urls["getActResult"]}/${id}`,
      method: "get",
    });
  },
  activitiesList(params) {
    return request({
      url: urls["activitiesList"],
      method: "get",
      params,
    });
  },
  getServicePoints(params) {
    return request({
      url: urls["getServicePoints"],
      method: "get",
      params,
    });
  },
  getRuralFinancialPoints(params) {
    return request({
      url: urls["getRuralFinancialPoints"],
      method: "get",
      params,
    });
  },
  category(params) {
    return request({
      url: urls["category"],
      method: "get",
      params,
    });
  },
  bankList(params) {
    return request({
      url: urls["bankList"],
      method: "get",
      params,
    });
  },
  supervisionList(params) {
    return request({
      url: urls["supervisionList"],
      method: "get",
      params,
    });
  },
  areaSplitPoints(params) {
    return request({
      url: urls["areaSplitPoints"],
      method: "get",
      params,
    });
  },
  customerPoints(params) {
    return request({
      url: urls["customerPoints"],
      method: "get",
      params,
    });
  },
  boundaryList1(params) {
    return request({
      url: urls["boundaryList1"],
      method: "get",
      params,
    });
  },
};
