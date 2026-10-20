import request from "@/utils/request";
const gateway = "branch";

const urls = {
  performanceList: `${gateway}/branch/list`, // 获取网点列表
  selectBankInfo: `${gateway}/branch/select/`, // 获取网点信息详情
  updateBankInfo: `${gateway}/branch/update`, // 网点信息详情编辑
  branchStatistics: `${gateway}/branch/statistics`, // 网点数据统计
  statisticsDetail: `${gateway}/branch/statistics/list`, // 网点数据统计明细列表
  statisticsDetailExport: `${gateway}/branch/statistics/list/export`, // 网点数据统计明细列表数据导出
  articleList: `${gateway}/branch/article/list`, // 网点动态信息列表展示页面
  articleInsert: `${gateway}/branch/article/insert`, // 网点动态信息新增接口
  articleSelect: `${gateway}/branch/article/select/`, // 网点动态信息阅读接口
  articleUpdate: `${gateway}/branch/article/update`, // 网点动态信息编辑接口
  articleDelete: `${gateway}/branch/article/delete/`, // 网点动态信息文章删除接口
  anchorExport:`${gateway}/branch/export` ,//导出网点
  branchImport:`${gateway}/branch/import` ,//导入网点


  // 授信客户
  creditList: `${gateway}/creditCustomerInfo/list`, // 获取列表

  // 收单客户
  acquiringList: `${gateway}/acquiring/customer/list`, // 获取列表

  // 保存客群
  saveCustomGroup: `${gateway}/customer/group/saveGroup`,

  // 银行机构
  listByBank:`${gateway}/performance/customer/listByBank`,
  // saveCustomGroup: `${gateway}/customer/group/addOrEditGroup`,
  // 模版下载
  modelDownload:`${gateway}/performance/customer/modelDownload`,
  importBatch:`${gateway}/performance/customer/importBatch` , //
};

export default {
  /* 绩效客户 */
  performanceList(data) {
    return request({
      url: urls["performanceList"],
      method: "post",
      data,
    });
  },
  selectBankInfo(params) {
    const { id } = params
    return request({
      url: `${urls["selectBankInfo"]}${id}`,
      method: "get",
    });
  },
  updateBankInfo(data) {
    return request({
      url: urls["updateBankInfo"],
      method: "post",
      data
    });
  },
  branchStatistics(params) {
    return request({
      url: urls["branchStatistics"],
      method: "get",
      params,
    });
  },
  statisticsDetail(params) {
    return request({
      url: urls["statisticsDetail"],
      method: "get",
      params,
    });
  },
  statisticsDetailExport() {
    return request({
      url: urls["statisticsDetailExport"],
      method: "post",
      responseType: 'blob',
    })
  },
  articleList(params) {
    return request({
      url: urls["articleList"],
      method: "get",
      params
    })
  },
  articleInsert(data) {
    return request({
      url: urls["articleInsert"],
      method: "post",
      data
    })
  },
  articleSelect(params) {
    const { id } = params
    return request({
      url: `${urls["articleSelect"]}${id}`,
      method: "get",
    })
  },
  articleUpdate(data) {
    return request({
      url: urls["articleUpdate"],
      method: "post",
      data
    })
  },
  articleDelete(params) {
    const { id } = params
    return request({
      url: `${urls["articleDelete"]}${id}`,
      method: "get",
    })
  },


  saveCustomGroup(data) {
    return request({
      url: urls["saveCustomGroup"],
      method: "post",
      data,
    });
  },

  /* 授信客户 */
  creditList(data) {
    return request({
      url: urls["creditList"],
      method: "post",
      data,
    });
  },

  /* 收单客户 */
  acquiringList(data) {
    return request({
      url: urls["acquiringList"],
      method: "post",
      data,
    });
  },
  listByBank(params) {
    return request({
      url: urls["listByBank"],
      method: "get",
      params,
    })
  },
  modelDownload() {
    return request({
      url: urls["modelDownload"],
      method: "get",
    })
  },
  importBatch(data) {
    return request({
      url: urls["importBatch"],
      method: "post",
      data,
    })
  },
  anchorExport(data) {
    return request({
      url: urls["anchorExport"],
      method: "post",
      responseType: 'blob',
      data
    })
  },
  branchImport(data) {
    return request({
      url: urls["branchImport"],
      method: "post",
      data,
    })
  },
};
