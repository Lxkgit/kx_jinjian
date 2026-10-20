import request from "@/utils/request";
const gateway = "system";
const urls = {
  opportunityList: `${gateway}/business/list`,//分页数据
  opportunityGetById: `${gateway}/business/getById`,//根据id查询数据
  opportunityDelByIds: `${gateway}/business/deleteByIds`,//批量删除
  opportunitySave: `${gateway}/business/save`//新增或修改数据
}

export default {
  /* 任务类型 */
  opportunityList(data) {
    return request({
      url: urls["opportunityList"],
      method: "get",
      params: data,
    });
  },
  opportunityGetById(data) {
    return request({
      url: urls["opportunityGetById"],
      method: "get",
      params: data,
    });
  },
  opportunityDelByIds(data) {
    return request({
      url: urls["opportunityDelByIds"] + "?ids=" + data.ids,
      method: "post",
      data: data,
    });
  },
  opportunitySave(data) {
    return request({
      url: urls["opportunitySave"],
      method: "post",
      data: data,
    });
  },
}