import request from "@/utils/request";
const gateway = "system";

const urls = {
  getPaAddrInfoList: `${gateway}/business/addrInfo/getPaAddrInfoList`, // 获取机构
  deptTree: `/system/user/deptTree`, // 机构树
  getPaAddrInfoTypeUser: `${gateway}/business/addrInfo/getPaAddrInfoTypeUser`, //获取网格员
  getByManagerNum: `${gateway}/user/getByManagerNum`, //移交人

  getModelIdByType: `${gateway}/filingModel/getModelIdByType`, // 获取当前开启模版id
  getIsBankCustomer: `${gateway}/customer/getIsBankCustomer`, // 是否行内客户
  getFilingIsExist: `${gateway}/filing/getFilingIsExist`, // 是否建档客户
  getTypeByPhoneAndIdentity: `${gateway}/filing/getTypeByPhoneAndIdentity`, // 获取用户模版
};

export default {
  /* get */
  getTypeByPhoneAndIdentity(params) {
    return request({
      url: urls["getTypeByPhoneAndIdentity"],
      method: "get",
      params,
    });
  },
  getByManagerNum(params) {
    return request({
      url: urls["getByManagerNum"],
      method: "get",
      params,
    });
  },
  getFilingIsExist(params) {
    return request({
      url: urls["getFilingIsExist"],
      method: "get",
      params,
    });
  },
  getIsBankCustomer(params) {
    return request({
      url: urls["getIsBankCustomer"],
      method: "get",
      params,
    });
  },
  deptTree(params) {
    return request({
      url: urls["deptTree"],
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
  /* post */
  getPaAddrInfoList(data) {
    return request({
      url: urls["getPaAddrInfoList"],
      method: "post",
      data,
    });
  },
  getPaAddrInfoTypeUser(data) {
    return request({
      url: urls["getPaAddrInfoTypeUser"],
      method: "post",
      data,
    });
  },
};
