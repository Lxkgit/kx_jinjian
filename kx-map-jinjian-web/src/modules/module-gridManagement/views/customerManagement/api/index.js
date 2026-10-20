import request from "@/utils/request";
const gateway = "system";

const urls = {
  // 客户信息
  customerInfo: `${gateway}/business/anchorPoint/selectCustomByAnchorId`,

  //走访信息
  customerVisits: `${gateway}/customer/queryVisits`,

  //客户旅途
  customerTrip: `${gateway}/business/anchorPoint/selectCustomVisitByAnchorId`,
};

export default {
  // 客户信息
  customerInfo(data) {
    return request({
      url: urls["customerInfo"],
      method: "post",
      data,
    });
  },

  // 客户走访
  customerVisits(params) {
    return request({
      url: urls["customerVisits"],
      method: "get",
      params,
    });
  },

  // 客户旅途
  customerTrip(data) {
    return request({
      url: urls["customerTrip"],
      method: "post",
      data,
    });
  },
};
