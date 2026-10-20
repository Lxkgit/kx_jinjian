import request from "@/utils/request";
const gateway = "system";
const urls = {
  //客户旅途
  customerTrip: `${gateway}/business/anchorPoint/selectCustomVisitByAnchorId`,
};

export default {
  // 客户旅途
  customerTrip(data) {
    return request({
      url: urls["customerTrip"],
      method: "post",
      data,
    });
  },
};
