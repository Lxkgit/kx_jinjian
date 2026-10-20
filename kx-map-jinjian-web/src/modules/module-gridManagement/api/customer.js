import request from "@/utils/request";
const gateway = "system";

const urls = {
  customerList: `${gateway}/customer/page`, // 客户列表
  customerMobilize: `${gateway}/customer/mobilize`, // 客户调动
  customerArchival: `${gateway}/customer/archival`, // 建档
  customerDetail: `${gateway}/customer/queryDetail/`, // 建档
};

export default {
  /* get */
  customerDetail(params) {
    const { id } = params;
    return request({
      url: `${urls["customerDetail"]}${id}`,
      method: "get",
    });
  },
  /* post */
  customerArchival(data) {
    return request({
      url: urls["customerArchival"],
      method: "post",
      data,
    });
  },
  customerMobilize(data) {
    return request({
      url: urls["customerMobilize"],
      method: "post",
      data,
    });
  },
  customerList(data) {
    return request({
      url: urls["customerList"],
      method: "post",
      data,
    });
  },
};
