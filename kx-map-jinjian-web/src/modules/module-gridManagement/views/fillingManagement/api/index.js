import request from "@/utils/request";
const gateway = "system";
const urls = {
  /* 采集模版管理 */
  listModel: `${gateway}/filingModel/listModel`, // 列表
  startFilingModelInfo: `${gateway}/filingModel/startFilingModelInfo`, // 开启/关闭
  editFilingModelInfo: `${gateway}/filingModel/editFilingModelInfo`, //新增/编辑
  getFilingModelInfoById: `${gateway}/filingModel/getFilingModelInfoById`, // 详情
  deleteFilingModelInfo: `${gateway}/filingModel/deleteFilingModelInfo`, // 删除
  getModelIdByType: `${gateway}/filingModel/getModelIdByType`, // 获取模版id
  /* 客户档案 */
  getCustomerProfileHeaders: `${gateway}/filingModel/getCustomerProfileHeaders`, // 采集表
  listByMap: `${gateway}/filingMessage/listByMap`, // 列表
  filingMessageSave: `${gateway}/filing/save`, // 新增编辑
  filingMessageList: `${gateway}/filing/list`, // 列表
  getByModelIdAndFilingId: `${gateway}/filing/getByModelIdAndFilingId`, // 详情
  modelDownload: `${gateway}/filingModel/modelDownload`, // 模版下载
  deleteByIds: `${gateway}/filing/deleteByIds`, // 删除
  /* 标签 */
  selectChildLabel: `${gateway}/label/selectChildLabel`, // 标签选择

  /* 移交管理 */
  handoverPageList: `${gateway}/filing/pageList`, //列表

  saveCustomGroup: `${gateway}/customer/group/saveCustomGroup`,
  handoverMoveBatch: `${gateway}/filing/moveBatch`, //移交
  handoverJudgeBatch: `${gateway}/filing/judgeBatch`, //审批

  /* 历史记录 */
  recordList: `${gateway}/filing/recordList`, //
};

export default {
  /* get */
  recordList(params) {
    return request({
      url: urls["recordList"],
      method: "get",
      params,
    });
  },
  
  getByModelIdAndFilingId(params) {
    return request({
      url: urls["getByModelIdAndFilingId"],
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
  selectChildLabel(params) {
    return request({
      url: urls["selectChildLabel"],
      method: "get",
      params,
    });
  },
  modelDownload(params) {
    return request({
      url: urls["modelDownload"],
      method: "get",
      params,
    });
  },
  listModel() {
    return request({
      url: urls["listModel"],
      method: "get",
    });
  },
  startFilingModelInfo(params) {
    return request({
      url: urls["startFilingModelInfo"],
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
  getCustomerProfileHeaders(params) {
    return request({
      url: urls["getCustomerProfileHeaders"],
      method: "get",
      params,
    });
  },
  filingMessageList(params) {
    return request({
      url: urls["filingMessageList"],
      method: "get",
      params,
    });
  },
  /* post */
  handoverPageList(data) {
    return request({
      url: urls["handoverPageList"],
      method: "post",
      data,
    });
  },
  deleteByIds(params) {
    return request({
      url: urls["deleteByIds"],
      method: "post",
      params,
    });
  },
  saveCustomGroup(data) {
    return request({
      url: urls["saveCustomGroup"],
      method: "post",
      data,
    });
  },
  handoverJudgeBatch(data) {
    return request({
      url: urls["handoverJudgeBatch"],
      method: "post",
      data,
    });
  },
  handoverMoveBatch(data) {
    return request({
      url: urls["handoverMoveBatch"],
      method: "post",
      data,
    });
  },
  editFilingModelInfo(data) {
    return request({
      url: urls["editFilingModelInfo"],
      method: "post",
      data,
    });
  },
  listByMap(data) {
    return request({
      url: urls["listByMap"],
      method: "post",
      data,
    });
  },
  filingMessageSave(data) {
    return request({
      url: urls["filingMessageSave"],
      method: "post",
      data,
    });
  },
  deleteFilingModelInfo(id) {
    return request({
      url: `${urls["deleteFilingModelInfo"]}?id=${id}`,
      method: "post",
    });
  },
};
