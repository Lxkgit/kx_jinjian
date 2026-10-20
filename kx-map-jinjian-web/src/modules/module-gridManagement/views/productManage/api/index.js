import request from "@/utils/request";
const gateway = "system";
const urls = {
  productList: `${gateway}/standard/product/page`, // 列表
  productSave: `${gateway}/standard/product/save`, // 新增
  productStatistic: `${gateway}/standard/product/qr/scanning/statistics`, // 列表
  productStatisticList: `${gateway}/standard/product/qr/scanning/cust/list`, // 列表
};

export default {
  /* get */
  productList(params) {
    return request({
      url: urls["productList"],
      method: "get",
      params,
    });
  },
  productStatistic(params) {
    return request({
      url: urls["productStatistic"],
      method: "get",
      params,
    });
  },
  productStatisticList(params) {
    return request({
      url: urls["productStatisticList"],
      method: "get",
      params,
    });
  },
  /* post */
  productSave(data) {
    return request({
      url: urls["productSave"],
      method: "post",
      data,
    });
  },
};
