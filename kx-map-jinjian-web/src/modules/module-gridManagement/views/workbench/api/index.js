import request from "@/utils/request";
const gateway = "system";
const urls = {
  dataOverview: `${gateway}/business/workBoard/dataOverview`, //数据概览
  toDoList: `${gateway}/business/workBoard/toDoList`, // 待办
  weekSchedule: `${gateway}/business/workBoard/weekSchedule`, // 本周日程
  workTrackOverview: `${gateway}/business/workBoard/workTrackOverview`, // 工作足迹概览
  workTrackList: `${gateway}/business/workBoard/workTrackList`, // 工作足迹列表
};

export default {
  workTrackList(data) {
    return request({
      url: urls["workTrackList"],
      method: "post",
      data,
    });
  },
  workTrackOverview(params) {
    return request({
      url: urls["workTrackOverview"],
      method: "get",
      params,
    });
  },
  weekSchedule(data) {
    return request({
      url: urls["weekSchedule"],
      method: "post",
      data,
    });
  },
  dataOverview(data) {
    return request({
      url: urls["dataOverview"],
      method: "post",
      data,
    });
  },
  toDoList(data) {
    return request({
      url: urls["toDoList"],
      method: "post",
      data,
    });
  },
};
