import request from "@/utils/request";
const gateway = "system";
const urls = {
  performList: `${gateway}/standard/product/per/statistics/page`,
  performDetail: `${gateway}/standard/product/per/statistics/detail`,
};

export default {
  performList(params) {
    return request({
      url: urls["performList"],
      method: "get",
      params,
    });
  },
  performDetail(params) {
    return request({
      url: urls["performDetail"],
      method: "get",
      params,
    });
  },
};
