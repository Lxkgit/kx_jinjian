import request from "@/utils/request";
const gateway = "system";
const urls = {
  queryCustomer: `${gateway}/customer/queryCustomer`, // 根据姓名、手机号、证件号查询客户信息
};

export default {
  queryCustomer(params) {
    return request({
      url: urls["queryCustomer"],
      method: "get",
      params,
    });
  },
};
