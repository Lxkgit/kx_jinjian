import request from "@/utils/request";
const gateway = "system";

const urls = {
  //走访信息
  customerVisits: `${gateway}/customer/queryVisits`,
};

export default {
  // 客户走访
  customerVisits(params) {
    return request({
      url: urls["customerVisits"],
      method: "get",
      params,
    });
  },
};
