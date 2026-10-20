import request from "@/utils/request";
const gateway = "system";

const urls = {
  importBatch: `${gateway}/service/points/list`, // 
  exportBatch: `${gateway}/service/points/exportBatch`, // 
  exportvillageFinancial: `${gateway}/villageFinancial/service/points/exportBatch`, // 
  details: `${gateway}/service/points/details`, // 列表
  villageFinancialList: `${gateway}/villageFinancial/service/points/list`, // 乡村金融服务点列表
};

export default {
  importBatch(data) {
    return request({
      url: urls['importBatch'],
      method: "post",
      data,
    });
  },
  exportBatch(data) {
    return request({
      url: urls["exportBatch"],
      method: "post",
      responseType: 'blob',
      data,
    })
  },
  details(params) {
    return request({
      url: urls["details"],
      method: "get",
      params,
    });
  },
  villageFinancialList(data) {
    return request({
      url: urls["villageFinancialList"],
      method: "post",
      data,
    });
  },
  exportvillageFinancial(data) {
    return request({
      url: urls["exportvillageFinancial"],
      method: "post",
      responseType: 'blob',
      data,
    })
  }
};
