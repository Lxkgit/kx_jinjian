import request from "@/utils/request";
const gateway = "system";
const urls = {
  getFilingModelInfoById: `${gateway}/filingModel/getFilingModelInfoById`, // 表单详情
  getByModelIdAndIdentity: `${gateway}/filing/getByModelIdAndIdentity`, // 客户详情
  getModelIdByType: `${gateway}/filingModel/getModelIdByType`, // 获取当前开启模版id
  getTypeByPhoneAndIdentity: `${gateway}/filing/getTypeByPhoneAndIdentity`, // 获取用户模版
};

export default {
  getTypeByPhoneAndIdentity(params) {
    return request({
      url: urls["getTypeByPhoneAndIdentity"],
      method: "get",
      params,
    });
  },
  getByModelIdAndIdentity(params) {
    return request({
      url: urls["getByModelIdAndIdentity"],
      method: "get",
      params,
    });
  },
  getFilingModelInfoById(params) {
    return request({
      url: urls["getFilingModelInfoById"],
      method: "get",
      params,
    });
  },
  getModelIdByType(params) {
    return request({
      url: urls["getModelIdByType"],
      method: "get",
      params,
    });
  },
};
