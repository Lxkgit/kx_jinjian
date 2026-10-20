import request from "@/utils/request";
const gateway = "system";
const urls = {
  /* 问卷内容 */
  taskTypePage: `${gateway}/taskTypeParent/list`, // 获取列表数据   1
  taskTypeDetail: `${gateway}/taskTypeParent/getById`, // 详情  1
  addTaskType: `${gateway}/taskTypeParent/save`, // 新增   1
  editTaskType: `${gateway}/taskTypeParent/edit`, // 编辑   1
  deleteTaskType: `${gateway}/taskTypeParent/deleteByIds`, // 删除   1

  /* 问卷信息录入 */
  questionRecordList: `${gateway}/questionRecord/list`, // 问卷列表
  questionRecordDelete: `${gateway}/questionRecord/delete`, // 问卷列表
  questionRecordinsert: `${gateway}/questionRecord/insert`, // 填写问卷
  questionRecordupdate: `${gateway}/questionRecord/update`, // 编辑
};

export default {
  /* 问卷内容 */
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
  taskTypeDetail(data) {
    return request({
      url: urls["taskTypeDetail"],
      method: "get",
      params: data,
    });
  },
  questionRecordList(data) {
    return request({
      url: urls["questionRecordList"],
      method: "post",
      data: data,
    });
  },
  questionRecordDelete(data) {
    return request({
      url: urls["questionRecordDelete"],
      method: "get",
      params: data,
    });
  },
  questionRecordinsert(data) {
    return request({
      url: urls["questionRecordinsert"],
      method: "post",
      data: data,
    });
  },
  questionRecordupdate(data) {
    return request({
      url: urls["questionRecordupdate"],
      method: "post",
      data: data,
    });
  },
};
