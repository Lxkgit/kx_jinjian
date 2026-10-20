import request from "@/utils/request";
const gateway = "system";
const urls = {
  /* 日程管理 */
  scheduleList: `${gateway}/schedule/list`, // 列表
  scheduleAdd: `${gateway}/schedule/add`, // 新增
  scheduleEdit: `${gateway}/schedule/edit`, // 编辑
  scheduleDelete: `${gateway}/schedule/delete`, // 编辑
  listAllUser: `${gateway}/user/listAllUser`, // 参与人员
};

export default {
  /* get */
  listAllUser(params) {
    return request({
      url: urls["listAllUser"],
      method: "get",
      params,
    });
  },
  /* post */
  scheduleDelete(data) {
    return request({
      url: urls["scheduleDelete"],
      method: "post",
      data,
    });
  },
  scheduleList(data) {
    return request({
      url: urls["scheduleList"],
      method: "post",
      data,
    });
  },
  scheduleAdd(data) {
    return request({
      url: urls["scheduleAdd"],
      method: "post",
      data,
    });
  },
  scheduleEdit(data) {
    return request({
      url: urls["scheduleEdit"],
      method: "post",
      data,
    });
  },
};
