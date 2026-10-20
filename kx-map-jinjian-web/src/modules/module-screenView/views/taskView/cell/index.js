import request from "@/utils/request";
const gateway = "system";
const urls = {
  taskListPage: `${gateway}/business/paTask/list`, // 获取列表数据
  myTaskPage: `${gateway}/paTaskDetail/listDetail`, //任务详情筛选分页
  listTaskMethod: `${gateway}/business/paTask/listTaskMethod`, //任务方式下拉
};

export default {
  taskListPage(data) {
    return request({
      url: urls["taskListPage"],
      method: "post",
      data,
    });
  },
  myTaskPage(data) {
    return request({
      url: urls["myTaskPage"],
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
};
