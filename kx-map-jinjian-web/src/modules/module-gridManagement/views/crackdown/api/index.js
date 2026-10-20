import request from "@/utils/request";
const gateway = "system";

const urls = {
  // 客户信息
  customerInfo: `${gateway}/business/anchorPoint/selectCustomByAnchorId`,
  riskClueList: `${gateway}/risk-clue/report/list`,
  riskClueDetail: `${gateway}/risk-clue/report`,
  riskClueAdd: `${gateway}/risk-clue/report/add`,
  riskClueEdit: `${gateway}/risk-clue/report/edit`,
  riskClueResubmit: `${gateway}/risk-clue/report/resubmit`,
  riskClueReview: `${gateway}/risk-clue/report/review`,
  riskClueTransfer: `${gateway}/risk-clue/report/transfer`,
  riskClueDelete: `${gateway}/risk-clue/report/delete`,
};

export default {
  // 客户信息
  customerInfo(data) {
    return request({
      url: urls["customerInfo"],
      method: "post",
      data,
    });
  },

  riskClueList(data) {
    return request({ url: urls.riskClueList, method: "post", data });
  },
  riskClueDetail(id) {
    return request({ url: `${urls.riskClueDetail}/${id}`, method: "get" });
  },
  riskClueAdd(data) {
    return request({ url: urls.riskClueAdd, method: "post", data });
  },
  riskClueEdit(data) {
    return request({ url: urls.riskClueEdit, method: "post", data });
  },
  riskClueResubmit(data) {
    return request({ url: urls.riskClueResubmit, method: "post", data });
  },
  riskClueReview(data) {
    return request({ url: urls.riskClueReview, method: "post", data });
  },
  riskClueTransfer(data) {
    return request({ url: urls.riskClueTransfer, method: "post", data });
  },
  riskClueDelete(id) {
    return request({ url: `${urls.riskClueDelete}/${id}`, method: "post" });
  },
  downloadFile(objectName) {
    return request({
      url: "system/minio/download",
      method: "get",
      params: { fileName: objectName },
      responseType: "blob",
    });
  },

};
