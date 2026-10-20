import request from "@/utils/request";
const gateway = "system";

const urls = {
  getCoordinatesByCode: `${gateway}/business/addrInfo/getBaseCoordinatesByCode`, // 获取机构边界
  getPaAddrInfoList: `${gateway}/business/addrInfo/getPaAddrInfoList`, // 获取所属网格下拉
  /* 锚点管理 */
  anchorPointList: `${gateway}/business/anchorPoint/list`, // 列表
  anchorPointEdit: `${gateway}/business/anchorPoint/edit`, // 编辑
  anchorPointDetail: `${gateway}/business/anchorPoint/`, // 详情
  anchorPointEditBatch: `${gateway}/business/anchorPoint/editBatch`, // 详情
  geoCode: `${gateway}/geo/geoCode`, // 地址解析
  reGeoCode: `${gateway}/geo/reGeoCode`,// 逆地址解析

  /* 楼宇管理 */
  buildingAnchorPointList: `${gateway}/business/buildingAnchorPoint/list`, // 列表
  buildingAnchorPointAdd: `${gateway}/business/buildingAnchorPoint/add`, // 新增
  buildingAnchorPointEdit: `${gateway}/business/buildingAnchorPoint/edit`, // 编辑
  buildingAnchorPointDelete: `${gateway}/business/buildingAnchorPoint/delete`, // 删除


};

export default {
  /* get */
  anchorPointDetail(params) {
    const { id } = params;
    return request({
      url: `${urls["anchorPointDetail"]}${id}`,
      method: "get",
    });
  },
  getCoordinatesByCode(params) {
    return request({
      url: urls["getCoordinatesByCode"],
      method: "get",
      params,
    });
  },
  geoCode(params) {
    return request({
      url: urls["geoCode"],
      method: "get",
      params,
    });
  },
  reGeoCode(params) {
    return request({
      url: urls["reGeoCode"],
      method: "get",
      params,
    });
  },
  /* post */
  anchorPointEditBatch(data) {
    return request({
      url: urls["anchorPointEditBatch"],
      method: "post",
      data,
    });
  },
  buildingAnchorPointDelete(data) {
    return request({
      url: urls["buildingAnchorPointDelete"],
      method: "post",
      data,
    });
  },
  getPaAddrInfoList(data) {
    return request({
      url: urls["getPaAddrInfoList"],
      method: "post",
      data,
    });
  },
  buildingAnchorPointEdit(data) {
    return request({
      url: urls["buildingAnchorPointEdit"],
      method: "post",
      data,
    });
  },
  buildingAnchorPointAdd(data) {
    return request({
      url: urls["buildingAnchorPointAdd"],
      method: "post",
      data,
    });
  },
  buildingAnchorPointList(data) {
    return request({
      url: urls["buildingAnchorPointList"],
      method: "post",
      data,
    });
  },
  anchorPointEdit(data) {
    return request({
      url: urls["anchorPointEdit"],
      method: "post",
      data,
    });
  },
  anchorPointList(data) {
    return request({
      url: urls["anchorPointList"],
      method: "post",
      data,
    });
  },
};
