import request from "@/utils/request";
const gateway = "system";

const urls = {
  selectCustomByAnchorId: `${gateway}/business/anchorPoint/selectCustomByAnchorId`, //获取基础信息
  selectLabelByAnchorId: `${gateway}/business/anchorPoint/selectLabelByAnchorId`, //标签
  selectCreditRecordByAnchorId: `${gateway}/business/anchorPoint/selectCreditRecordByAnchorId`, //信贷产品
};

export default {
  selectCreditRecordByAnchorId(data) {
    return request({
      url: urls["selectCreditRecordByAnchorId"],
      method: "post",
      data,
    });
  },
  selectLabelByAnchorId(data) {
    return request({
      url: urls["selectLabelByAnchorId"],
      method: "post",
      data,
    });
  },
  selectCustomByAnchorId(data) {
    return request({
      url: urls["selectCustomByAnchorId"],
      method: "post",
      data,
    });
  },
};
