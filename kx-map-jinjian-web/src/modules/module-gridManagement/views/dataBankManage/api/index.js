import request from "@/utils/request";
const gateway = "system";
const urls = {
  standardList: `${gateway}/standard/data/list`, // 列表
  standardSave: `${gateway}/standard/data/save`, // 新增
  standardDel: `${gateway}/standard/data/del`, // 删除
  standardDownload: `${gateway}/standard/data/down/file`, // 下载
  standardPreview: `${gateway}/standard/data/file/preview`, // 查看
};

export default {
  /* get */
  standardList(params) {
    return request({
      url: urls["standardList"],
      method: "get",
      params,
    });
  },
  /* post */
  standardSave(data) {
    return request({
      url: urls["standardSave"],
      method: "post",
      data,
    });
  },
  standardDel(data) {
    return request({
      url: urls["standardDel"],
      method: "post",
      data,
    });
  },
  standardDownload(data) {
    return request({
      url: urls["standardDownload"],
      method: "post",
      data,
    });
  },
  standardPreview(data) {
    return request({
      url: urls["standardPreview"],
      method: "post",
      data,
    });
  },
};
