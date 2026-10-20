import request from "@/utils/request";
const gateway = "branch";

const urls = {
  eventList: `${gateway}/event/list`, //事件列表
  addEvent: `${gateway}/event/list`, //新增事件
  eventImport: `${gateway}/event/import`, //事件导入
  templateDownLoad: `${gateway}/event/template`, //事导入模板下载
  eventDetail: `${gateway}/event/detail/`, //网格事件详情
  eventReported: `${gateway}/event/reported`, //网格事件上报
};

export default {
  /* 网格事件列表 */
  getGridEventList(data) {
    return request({
      url: urls["eventList"],
      method: "post",
      data,
    });
  },
  /* 网格事件新增 */
  addEvent(data) {
    return request({
      url: urls["addEvent"],
      method: "post",
      data,
    });
  },
  
  /* 网格事件导入 */
  eventImport(data) {
    return request({
      url: urls["eventImport"],
      method: "post",
      responseType: 'blob',
      data,
    });
  },
  /* 网格事件导入模板下载 */
  templateDownLoad() {
    return request({
      url: urls["templateDownLoad"],
      method: "get",
    });
  },
  /* 网格事件详情 */
  templateDownLoad(params) {
    const { id } = params
    return request({
      url: `${urls["eventDetail"]}${id}`,
      method: "get",
    });
  },
  /* 网格事件上报 */
  eventReported(data) {
    return request({
      url: urls["eventReported"],
      method: "post",
      data,
    });
  },
};
