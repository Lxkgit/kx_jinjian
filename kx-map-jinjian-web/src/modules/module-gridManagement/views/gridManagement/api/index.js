import request from "@/utils/request";
const gateway = "system";

const urls = {
  /* 网格体系管理 */
  gridTypeList: `${gateway}/business/gridType/list`, // 列表
  gridTypeAdd: `${gateway}/business/gridType/add`, // 新增
  gridTypeEdit: `${gateway}/business/gridType/edit`, // 编辑
  gridTypeDelete: `${gateway}/business/gridType/remove`, // 删除
  initAddrInfoType: `${gateway}/business/addrInfo/initAddrInfoType`, // 初始化网格

  /* 网格管理 */
  getPaAddrInfoTypeList: `${gateway}/business/addrInfo/getPaAddrInfoTypeList`, // 获取行政机构
  getPaAddrInfoType: `${gateway}/business/addrInfo/getPaAddrInfoType`, // 获取行政机构-单个
  getCoordinatesByCode: `${gateway}/business/addrInfo/getCoordinatesByCode`, // 获取机构边界
  cleanChildGrid: `${gateway}/business/addrInfo/cleanChildGrid`, // 清空子网格
  deleteGrid: `${gateway}/business/addrInfo/delete`, // 删除网格
  splitGrid: `${gateway}/business/addrInfo/splitGrid`, // 平分网格
  cutGrid: `${gateway}/business/addrInfo/cutGrid`, // 下切网格
  updateGrid: `${gateway}/business/addrInfo/update`, // 编辑网格
  updateBatch: `${gateway}/business/addrInfo/updateBatch`, // 边界调整
  mergeChildGrid: `${gateway}/business/addrInfo/mergeChildGrid`, // 合并子网格
  addChild: `${gateway}/business/addrInfo/add`, // 新增子网格
  updateGridDept: `${gateway}/business/addrInfo/updateGridDept`, //辖区分配
  updateGridUser: `${gateway}/business/addrInfo/updateGridUser`, //网格员分配
  gridExport: `${gateway}/gridMessage/gridExport`, //网格数据列表导出



  /* 辖区分配 */
  deptTree: `/system/user/deptTree`, // 机构树
  getAddrInfoTypeBottom: `${gateway}/business/addrInfo/getAddrInfoTypeBottom`, //列表
  getPaAddrInfoTypeUser: `${gateway}/business/addrInfo/getPaAddrInfoTypeUser`, //获取网格员

  /* 网格信息维护 */
  getByGridCode: `${gateway}/gridMessage/getByGridCode`, // 获取网格信息
  gridMessageSave: `${gateway}/gridMessage/save`, // 编辑
  getGridCruxInfo: `${gateway}/gridMessage/getGridCruxInfo`, // 关键
  getGridRatingInfo: `${gateway}/gridMessage/getGridRatingInfo`, // 评级
  saveGridCruxInfo: `${gateway}/gridMessage/saveGridCruxInfo`, // 编辑关键
  deleteGridCruxByIds: `${gateway}/gridMessage/deleteGridCruxByIds`, // 删除关键
  saveGridRatingInfo: `${gateway}/gridMessage/saveGridRatingInfo`, // 编辑年度
  deleteGridRatingByIds: `${gateway}/gridMessage/deleteGridRatingByIds`, // 删除年度

  gridMessageList: `${gateway}/gridMessage/list`,
  getOtherPointList: `${gateway}/gridMessage/gridInfo`,//网格内其他网点信息
  getAllanchor: `${gateway}/business/anchorPoint/allAnchor`,//网格内其他网点信息
  // 重点对象
  getGridImportant: `${gateway}/gridManageObjects/get`,//获取重点对象
  addGridImportant: `${gateway}/gridManageObjects/insert`,//新增重点对象
  editGridImportant: `${gateway}/gridManageObjects/update`,//修改重点对象
  deleteGridImportant: `${gateway}/gridManageObjects/delete`,//新增重点对象

  // 社会网格员
  getCommunityWatcherList: `${gateway}/grid/community/supervisor/select`,//获取社会网格员
  addCommunityWatcher: `${gateway}/grid/community/supervisor/insert`,//新增社会网格员
  editCommunityWatcher: `${gateway}/grid/community/supervisor/update`,//修改社会网格员
  deleteCommunityWatcher: `${gateway}/grid/community/supervisor/delete`,//删除社会网格员

  // 基层组织
  getGridBasicOrgList: `${gateway}/gridGrassrootsInfo/code`,//获取基层组织
  addGridBasicOrg: `${gateway}/gridGrassrootsInfo/insert`,//新增基层组织
  editGridBasicOrg: `${gateway}/gridGrassrootsInfo/update`,//修改基层组织
  deleteGridBasicOrg: `${gateway}/gridGrassrootsInfo/delete`,//删除基层组织
  // 网格内现金服务点
  getCashPointInGrid: `${gateway}/branch/view/branchInfo/cashServiceInfo`,
  getFinancialInGrid: `${gateway}/branch/view/branchInfo/financialServiceInfo`,
};

export default {
  /* get */
  getGridRatingInfo(params) {
    return request({
      url: urls["getGridRatingInfo"],
      method: "get",
      params,
    });
  },
  getGridCruxInfo(params) {
    return request({
      url: urls["getGridCruxInfo"],
      method: "get",
      params,
    });
  },
  getByGridCode(params) {
    return request({
      url: urls["getByGridCode"],
      method: "get",
      params,
    });
  },
  gridTypeList(params) {
    return request({
      url: urls["gridTypeList"],
      method: "get",
      params,
    });
  },
  getCoordinatesByCode(params) {
    return request({
      url: urls["getCoordinatesByCode"],
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

  /* post */
  updateGridUser(data) {
    return request({
      url: urls["updateGridUser"],
      method: "post",
      data
    });
  },
  updateGridDept(data) {
    return request({
      url: urls["updateGridDept"],
      method: "post",
      data
    });
  },
  deleteGridCruxByIds(data) {
    return request({
      url: `${urls["deleteGridCruxByIds"]}?ids=${data}`,
      method: "post",
    });
  },
  deleteGridRatingByIds(data) {
    return request({
      url: `${urls["deleteGridRatingByIds"]}?ids=${data}`,
      method: "post",
    });
  },
  saveGridRatingInfo(data) {
    return request({
      url: urls["saveGridRatingInfo"],
      method: "post",
      data,
    });
  },
  saveGridCruxInfo(data) {
    return request({
      url: urls["saveGridCruxInfo"],
      method: "post",
      data,
    });
  },
  gridMessageSave(data) {
    return request({
      url: urls["gridMessageSave"],
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
  getAddrInfoTypeBottom(data) {
    return request({
      url: urls["getAddrInfoTypeBottom"],
      method: "post",
      data,
    });
  },
  addChild(data) {
    return request({
      url: urls["addChild"],
      method: "post",
      data,
    });
  },
  mergeChildGrid(data) {
    return request({
      url: urls["mergeChildGrid"],
      method: "post",
      data,
    });
  },
  updateBatch(data) {
    return request({
      url: urls["updateBatch"],
      method: "post",
      data,
    });
  },
  cutGrid(data) {
    return request({
      url: urls["cutGrid"],
      method: "post",
      data,
    });
  },
  updateGrid(data) {
    return request({
      url: urls["updateGrid"],
      method: "post",
      data,
    });
  },
  splitGrid(data) {
    return request({
      url: urls["splitGrid"],
      method: "post",
      data,
    });
  },
  deleteGrid(data) {
    return request({
      url: urls["deleteGrid"],
      method: "post",
      data,
    });
  },
  cleanChildGrid(data) {
    return request({
      url: urls["cleanChildGrid"],
      method: "post",
      data,
    });
  },
  getPaAddrInfoTypeList(data) {
    return request({
      url: urls["getPaAddrInfoTypeList"],
      method: "post",
      data,
    });
  },
  getPaAddrInfoType(data) {
    return request({
      url: urls["getPaAddrInfoType"],
      method: "post",
      data,
    });
  },
  initAddrInfoType(data) {
    return request({
      url: urls["initAddrInfoType"],
      method: "post",
      data,
    });
  },
  gridTypeAdd(data) {
    return request({
      url: urls["gridTypeAdd"],
      method: "post",
      data,
    });
  },
  gridTypeEdit(data) {
    return request({
      url: urls["gridTypeEdit"],
      method: "post",
      data,
    });
  },
  gridTypeDelete(data) {
    return request({
      url: urls["gridTypeDelete"],
      method: "post",
      data,
    });
  },
  gridMessageList(data) {
    return request({
      url: urls["gridMessageList"],
      method: "post",
      data,
    });
  },

  getOtherPointList(params) {
    return request({
      url: urls["getOtherPointList"],
      method: "get",
      params
    });
  },
  getAllanchor(params) {
    return request({
      url: urls["getAllanchor"],
      method: "get",
      params
    });
  },

  // 重点对象
  getGridImportant(params) {
    return request({
      url: urls["getGridImportant"] + `/${params.gridCode}`,
      method: "get",
    });
  },
  addGridImportant(data) {
    return request({
      url: urls["addGridImportant"],
      method: "post",
      data,
    });
  },
  editGridImportant(data) {
    return request({
      url: urls["editGridImportant"],
      method: "put",
      data,
    });
  },
  deleteGridImportant(params) {
    return request({
      url: urls["deleteGridImportant"] + `/${params.id}`,
      method: "delete",
    });
  },

  // 社区监督员
  getCommunityWatcherList(params) {
    params.gridCode = params.gridCode ? params.gridCode : '330000';
    return request({
      url: urls["getCommunityWatcherList"] + `/${params.gridCode}`,
      method: "get",
    });
  },
  addCommunityWatcher(data) {
    return request({
      url: urls["addCommunityWatcher"],
      method: "post",
      data,
    });
  },
  editCommunityWatcher(data) {
    return request({
      url: urls["editCommunityWatcher"],
      method: "put",
      data,
    });
  },
  deleteCommunityWatcher(params) {
    return request({
      url: urls["deleteCommunityWatcher"] + `/${params.id}`,
      method: "delete",
    });
  },

  // 社区监督员
  getGridBasicOrgList(params) {
    return request({
      url: urls["getGridBasicOrgList"] + `/${params.gridCode}`,
      method: "get",
    });
  },
  addGridBasicOrg(data) {
    return request({
      url: urls["addGridBasicOrg"],
      method: "post",
      data,
    });
  },
  editGridBasicOrg(data) {
    return request({
      url: urls["editGridBasicOrg"] + `/${data.id}`,
      method: "put",
      data,
    });
  },
  deleteGridBasicOrg(params) {
    return request({
      url: urls["deleteGridBasicOrg"] + `/${params.id}`,
      method: "delete",
    });
  },

  getCashPointInGrid(data) {
    return request({
      url: urls["getCashPointInGrid"],
      method: "post",
      data,
    });
  },
  getFinancialInGrid(data) {
    return request({
      url: urls["getFinancialInGrid"],
      method: "post",
      data,
    });
  },
  gridExport(data) {
    return request({
      url: urls["gridExport"],
      method: "post",
      responseType: 'blob',
      data,
    })
  }
};
