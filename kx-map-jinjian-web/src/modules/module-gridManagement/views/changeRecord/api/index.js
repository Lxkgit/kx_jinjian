import request from "@/utils/request";
const gateway = "system";
const urls = {
  /* 变更记录 */
  paChangeLogList: `${gateway}/business/paChangeLog/list`, // 列表
  paChangeLogExport: `${gateway}/business/paChangeLog/export`, // 导出
  paChangeLogExportAnchor: `${gateway}/business/paChangeLog/exportAnchorChangeLog`, // 锚点导出
  listAnchorChangeLog: `${gateway}/business/paChangeLog/listAnchorChangeLog`, // 锚点记录
};

export default {
  /* get */
  /* post */
  listAnchorChangeLog(data) {
    return request({
      url: urls["listAnchorChangeLog"],
      method: "post",
      data,
    });
  },
  paChangeLogList(data) {
    return request({
      url: urls["paChangeLogList"],
      method: "post",
      data,
    });
  },
  paChangeLogExport(data) {
    return urls["paChangeLogExport"];
    //  request({
    //   url: urls["paChangeLogExport"],
    //   method: "post",
    //   data,
    // });
  },
  paChangeLogExportAnchor(data) {
    return urls["paChangeLogExportAnchor"]
    // request({
    //   url: urls["paChangeLogExportAnchor"],
    //   method: "post",
    //   data,
    // });
  },
};
