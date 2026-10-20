import request from "@/utils/request";
const gateway = "system";

const urls = {
  customerDetail: `${gateway}/customer/queryDetail/`, // 客户详情
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
};
