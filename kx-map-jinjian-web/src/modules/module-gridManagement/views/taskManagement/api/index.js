import request from "@/utils/request";
const gateway = "system";
const urls = {
  /* 任务类型 */
  taskTypeObject: `${gateway}/taskTypeParent/listTaskObject`, //客户类型下拉
  taskTypePage: `${gateway}/taskTypeParent/list`, // 获取列表数据
  taskTypeDetail: `${gateway}/taskTypeParent/getById`, // 详情
  addTaskType: `${gateway}/taskTypeParent/save`, // 新增
  editTaskType: `${gateway}/taskTypeParent/edit`, // 编辑
  deleteTaskType: `${gateway}/taskTypeParent/deleteByIds`, // 删除
  taskTypeFillForm: `${gateway}/taskTypeParent/listFillForm`, //填写形式下拉
  taskTypeLabel: `${gateway}/label/selectChildLabel`, // 标签选择

  /* 任务列表 */
  taskListType: `${gateway}/taskTypeParent/selectAll`, // 任务类型下拉
  getListTaskType: `${gateway}/taskTypeParent/listTaskType`, //任务列表左侧的任务类型下拉
  taskListPriority: `${gateway}/taskTypeParent/listPriority`, // 任务优先级
  taskListPage: `${gateway}/business/paTask/list`, // 获取列表数据
  addTaskList: `${gateway}/business/paTask/add`, // 新增
  editTaskList: `${gateway}/business/paTask/edit`, // 编辑
  deleteTaskList: `${gateway}/business/paTask/deleteByIds`, // 删除
  taskListDetail: `${gateway}/business/paTask`, // 任务详情
  customerGroup: `${gateway}/customer/group/getChildCustomGroup`, // 客群列表
  taskDetailList: `${gateway}/paTaskDetail/listTaskClient`, // 任务名单筛选分页
  deleteByClientIds: `${gateway}/paTaskDetail/deleteByClientIds`, // 批量删除
  taskListUrge: `${gateway}/business/paTask/taskUrge`, // 催办

  /* 我的任务 */
  taskListOwnType: `${gateway}/taskTypeParent/selectOwnAll`, // 任务类型下拉
  listTaskStatus: `${gateway}/business/paTask/listTaskStatus`, //主任务状态下拉
  listChildTaskStatus: `${gateway}/business/paTask/listChildTaskStatus`, //子任务状态下拉
  myTaskPage: `${gateway}/paTaskDetail/listDetail`, //任务详情筛选分页
  editTaskDetail: `${gateway}/taskDetailContent/edit`, //任务规划
  listTaskMethod: `${gateway}/business/paTask/listTaskMethod`, //任务方式下拉
  queryBusinessManage: `${gateway}/customer/queryBusinessManage`, //动态渲染执行人
  cascadeLabel: `${gateway}/label/selectSysAndGridLabel/`, //获取标签
  taskDetailContentList: `${gateway}/taskDetailContent/list`, //任务反馈

  /* 任务统计 */
  taskPage: `${gateway}/paTaskDetail/listDetailInView`,//任务明细列表
  listTaskRank: `${gateway}/business/paTask/listTaskRank`, //主任务状态下拉
  taskDetailExport: `${gateway}/paTaskDetail/export`, //任务明细导出
  listTaskProgress: `${gateway}/business/paTask/listTaskProgress`, //主任务进度统计
  listPaTaskSearch: `${gateway}/business/paTask/listPaTask`, //任务模糊搜索下拉
};

export default {
  /* 任务类型 */
  taskTypeObject(data) {
    return request({
      url: urls["taskTypeObject"],
      method: "get",
      params: data,
    });
  },
  taskTypePage(data) {
    return request({
      url: urls["taskTypePage"],
      method: "get",
      params: data,
    });
  },
  addTaskType(data) {
    return request({
      url: urls["addTaskType"],
      method: "post",
      data: data,
    });
  },
  editTaskType(data) {
    return request({
      url: urls["editTaskType"],
      method: "post",
      data: data,
    });
  },
  deleteTaskType(data) {
    return request({
      url: urls["deleteTaskType"] + "?ids=" + data.ids,
      method: "post",
      data: data,
    });
  },
  taskTypeFillForm(data) {
    return request({
      url: urls["taskTypeFillForm"],
      method: "get",
      params: data,
    });
  },
  taskTypeDetail(data) {
    return request({
      url: urls["taskTypeDetail"],
      method: "get",
      params: data,
    });
  },
  taskTypeLabel(data) {
    return request({
      url: urls["taskTypeLabel"],
      method: "get",
      params: data,
    });
  },

  /* 任务列表 */
  taskListType(data) {
    return request({
      url: urls["taskListType"],
      method: "get",
      params: data,
    });
  },
  getListTaskType(data) {
    return request({
      url: urls["getListTaskType"],
      method: "get",
      params: data,
    });
  },
  taskListPriority(data) {
    return request({
      url: urls["taskListPriority"],
      method: "get",
      params: data,
    });
  },
  taskListPage(data) {
    return request({
      url: urls["taskListPage"],
      method: "post",
      data,
    });
  },
  addTaskList(data) {
    return request({
      url: urls["addTaskList"],
      method: "post",
      data,
    });
  },
  editTaskList(data) {
    return request({
      url: urls["editTaskList"],
      method: "post",
      data,
    });
  },
  deleteTaskList(data) {
    return request({
      url: urls["deleteTaskList"] + `?ids=${data.ids}`,
      method: "post",
      data,
    });
  },
  taskListDetail(data) {
    return request({
      url: urls["taskListDetail"],
      method: "get",
      params: data,
    });
  },
  customerGroup(data) {
    return request({
      url: urls["customerGroup"],
      method: "get",
      params: data,
    });
  },
  taskDetailList(data) {
    return request({
      url: urls["taskDetailList"],
      method: "post",
      data,
    });
  },
  deleteByClientIds(data) {
    return request({
      url: urls["deleteByClientIds"] + `?clientIds=${data.clientIds}`,
      method: "post",
      data,
    });
  },
  taskListUrge(data) {
    return request({
      url: urls["taskListUrge"] + "?ids=" + data.ids,
      method: "get",
    });
  },

  /* 我的任务 */
  taskListOwnType(data) {
    return request({
      url: urls["taskListOwnType"],
      method: "get",
      params: data,
    });
  },
  listTaskStatus(data) {
    return request({
      url: urls["listTaskStatus"],
      method: "get",
      params: data,
    });
  },
  listChildTaskStatus(data) {
    return request({
      url: urls["listChildTaskStatus"],
      method: "get",
      params: data,
    });
  },
  myTaskPage(data) {
    return request({
      url: urls["myTaskPage"],
      method: "post",
      data,
    });
  },
  editTaskDetail(data) {
    return request({
      url: urls["editTaskDetail"],
      method: "post",
      data,
    });
  },
  listTaskMethod(data) {
    return request({
      url: urls["listTaskMethod"],
      method: "get",
      params: data,
    });
  },
  queryBusinessManage(data) {
    return request({
      url: urls["queryBusinessManage"],
      method: "post",
      data,
    });
  },
  cascadeLabel(data) {
    return request({
      url: `${urls["cascadeLabel"]}?id=${data.id}`,
      method: "get",
    });
  },
  taskDetailContentList(data) {
    return request({
      url: urls["taskDetailContentList"],
      method: "post",
      data,
    });
  },

  /* 任务统计 */
  listTaskRank(data) {
    return request({
      url: urls["listTaskRank"],
      method: "post",
      data,
    });
  },
  taskDetailExport(data) {
    return request({
      url: urls["taskDetailExport"],
      method: "post",
      responseType: "blob", //注意 需要规定后台返回文件流格式
      data,
    });
  },
  listTaskProgress(data) {
    return request({
      url: urls["listTaskProgress"],
      method: "post",
      data,
    });
  },
  listPaTaskSearch(data) {
    return request({
      url: urls["listPaTaskSearch"],
      method: "post",
      data,
    });
  },
  taskPage(data) {
    return request({
      url: urls["taskPage"],
      method: "post",
      data,
    });
  },
};
