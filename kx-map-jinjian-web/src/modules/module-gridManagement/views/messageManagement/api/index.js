import request from "@/utils/request";
const gateway = "system";

const urls = {
  listMessageStatistics: `${gateway}/message/listMessageStatistics`, // 左侧数据
  list: `${gateway}/message/list`, //右侧数据
  updateMessageStar: `${gateway}/message/updateMessageStar`, // 更新星标
  updateMessageState: `${gateway}/message/updateMessageState`, //更新 状态
};

export default {

  listMessageStatistics(params) {
    return request({
      url: urls["listMessageStatistics"],
      method: "get",
      params,
    });
  },

  list(params) {
    return request({
      url: urls["list"],
      method: "get",
      params,
    });
  },
  updateMessageStar(params) {
    return request({
      url: urls["updateMessageStar"],
      method: "get",
      params,
    });
  },
  updateMessageState(params) {
    return request({
      url: urls["updateMessageState"],
      method: "get",
      params,
    });
  },

};
