import request from "@/utils/request";
const gateway = "system";
const urls = {
  groupListNotPage: `${gateway}/customer/group/listNotPage`, // 客群数据
  queryCustomerByName: `${gateway}/business/anchorPoint/queryCustomerByName`, // 客户搜索
  listDeptMember: `${gateway}/small/deptMember/listDeptMember`, // 获取机构及用户

  getModelIdByType: `${gateway}/filingModel/getModelIdByType`, // 获取当前开启模版id
  getIsBankCustomer: `${gateway}/customer/getIsBankCustomer`, // 是否行内客户
  getFilingIsExist: `${gateway}/filing/getFilingIsExist`, // 是否建档客户
  getTypeByPhoneAndIdentity: `${gateway}/filing/getTypeByPhoneAndIdentity`, // 获取用户模版


  queryCustomerByAnchorName: `${gateway}/branch/view/queryCustomerByAnchorName`, //根据名称搜索网点
  queryCustomerByAnchor: `${gateway}/branch/view/queryCustomerByAnchor`, //根据网格
  queryCustomerByAddr: `${gateway}/branch/view/queryCustomerByAddr`, //地址  
  category: `${gateway}/branch/view/category/path`, 
  cashServiceTotal: `${gateway}/service/points/cashServiceTotal`, // 现金服务网点数量
  totalCount: `${gateway}/business/anchorPoint/totalCount`,

};

export default {
  getTypeByPhoneAndIdentity(params) {
    return request({
      url: urls["getTypeByPhoneAndIdentity"],
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
  getModelIdByType(params) {
    return request({
      url: urls["getModelIdByType"],
      method: "get",
      params,
    });
  },
  groupListNotPage(data) {
    return request({
      url: urls["groupListNotPage"],
      method: "post",
      data,
    });
  },
  queryCustomerByName(data) {
    return request({
      url: urls["queryCustomerByName"],
      method: "post",
      data,
    });
  },
  listDeptMember(params) {
    return request({
      url: urls["listDeptMember"],
      method: "get",
      params,
    });
  },
  queryCustomerByAnchorName(anchorName) {
    return request({
      url: urls["queryCustomerByAnchorName"],
      method: "get",
      params:{anchorName},
    });
  },
  queryCustomerByAnchor(branchName) {
    return request({
      url: urls["queryCustomerByAnchor"],
      method: "get",
      params:{branchName},
    });
  },
  queryCustomerByAddr(addressDesc) {
    return request({
      url: urls["queryCustomerByAddr"],
      method: "get",
      params:{addressDesc},
    });
  },
  category(params) {
    return request({
      url: urls["category"],
      method: "get",
      params
    });
  },
  cashServiceTotal(params) {
    return request({
      url: urls["cashServiceTotal"],
      method: "get",
      params
    });
  },
  totalCount(params) {
    return request({
      url: urls["totalCount"],
      method: "post",
      params
    });
  }

};
