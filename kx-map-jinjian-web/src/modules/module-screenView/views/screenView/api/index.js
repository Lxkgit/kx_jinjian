import request from "@/utils/request";
const gateway = "system";
const urls = {
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

};

export default {
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
};
