import request from "@/utils/request";
const gateway = "system";
const urls = {
  /* 标签 */
  selectChildLabel: `${gateway}/label/selectChildLabel`, // 标签选择
  upload: `${gateway}//minio//upload`, // 标签选择
};

export default {
  /* get */
  selectChildLabel(params) {
    return request({
      url: urls["selectChildLabel"],
      method: "get",
      params,
    });
  },
  upload(data) {
    return request({
      url: urls["upload"],
      method: "post",
      data,
    });
  },
};
