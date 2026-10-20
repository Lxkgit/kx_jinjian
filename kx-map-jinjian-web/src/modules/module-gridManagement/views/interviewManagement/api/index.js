import request from "@/utils/request";
const gateway = "system";

const urls = {
  customerVisitList: `${gateway}/customerVisit/list`,
  listByManagerCode: `${gateway}/customerVisit/listByManagerCode`,
};

export default {
  customerVisitList(params) {
    return request({
      url: urls["customerVisitList"],
      method: "get",
      params,
    });
  },
  listByManagerCode(params) {
    return request({
      url: urls["listByManagerCode"],
      method: "get",
      params,
    });
  },
};
