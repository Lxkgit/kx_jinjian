import request from "@/utils/request";
const gateway = "system";

const urls = {
  getActResult: `${gateway}/marketing/activities/gain`, //活动成果
  searchAct: `${gateway}/marketing/activities/page/list`, //活动列表查询
  addAct: `${gateway}/marketing/activities/save`, //新增活动
  updateAct: `${gateway}/marketing/activities/update`, //更新活动
  getPaAddrInfoList: `${gateway}/business/addrInfo/getPaAddrInfoList`, // 获取所属网格下拉
  getCoordinatesByCode: `${gateway}/business/addrInfo/getBaseCoordinatesByCode`, // 获取机构边界
  activitiesList: `${gateway}/marketing/activities/cust/list`, //  活动成果 客户列表
  activitiesPerList: `${gateway}/marketing/activities/bus/statistics/list`, //  活动业绩统计列表
  activitiesPerDetail: `${gateway}/marketing/activities/manager/statistics/list`, //  营销活动业绩统计客户经理
};

export default {
  getCoordinatesByCode(params) {
    return request({
      url: urls["getCoordinatesByCode"],
      method: "get",
      params,
    });
  },
  activitiesList(params) {
    return request({
      url: urls["activitiesList"],
      method: "get",
      params,
    });
  },
  activitiesPerList(params) {
    return request({
      url: urls["activitiesPerList"],
      method: "get",
      params,
    });
  },
  activitiesPerDetail(params) {
    return request({
      url: urls["activitiesPerDetail"],
      method: "get",
      params,
    });
  },
  getPaAddrInfoList(data) {
    return request({
      url: urls["getPaAddrInfoList"],
      method: "post",
      data,
    });
  },
  getActResult(id) {
    return request({
      url: `${urls["getActResult"]}/${id}`,
      method: "get",
    });
  },
  searchAct(params) {
    return request({
      url: urls["searchAct"],
      method: "get",
      params,
    });
  },
  addAct(data) {
    return request({
      url: urls["addAct"],
      method: "post",
      data,
    });
  },
  updateAct(data) {
    return request({
      url: urls["updateAct"],
      method: "post",
      data,
    });
  },
};
