import request from "@/utils/request";

export function branchInfo(params) {
  return request({
    url: "/system/branch/view/branchInfo/info",
    method: "get",
    params,
  });
}

export function getbranchInfolabel(params) {
  return request({
    url: "/system/branch/view/branchInfo/service",
    method: "get",
    params,
  });
}
export function getEquipmentNum(params) {
  return request({
    url: "/system/business/anchorPoint/equipment",
    method: "get",
    params,
  });
}

// 最近一次事件
export function getEvensRecently(params) {
  return request({
    url: "/system/branch/view/branchInfo/evensRecently",
    method: "get",
    params,
  });
}

// 最近一次事件
export function getEvensAll(params) {
  return request({
    url: "/system/branch/view/branchInfo/evensAll",
    method: "get",
    params,
  });
}

// 增加事件
export function addInsertEven(data) {
  return request({
    url: "/system/branch/view/branchInfo/insertEven",
    method: "post",
    data,
  });
}

// 编辑事件
export function updateEven(data) {
  return request({
    url: "/system/branch/view/branchInfo/updateEven",
    method: "put",
    data,
  });
}

// 删除
export function deleteEven(params) {
  return request({
    url: "/system/branch/view/branchInfo/deleteEven",
    method: "delete",
    params,
  });
}
export function getManageGridInfo(params) {
  return request({
    url: "/system/branch/view/branchInfo/gridInfo",
    method: "get",
    params,
  });
}
export function getCashPointInGrid(data) {
  return request({
    url: "/system/branch/view/branchInfo/cashServiceInfo",
    method: "post",
    data,
  });
}
export function getFinancialInGrid(data) {
  return request({
    url: "/system/branch/view/branchInfo/financialServiceInfo",
    method: "post",
    data,
  });
}
// 查询最近一次网格投诉事件信息
export function getComplaint(params) {
  return request({
    url: "/system/complaints/getComplaint",
    method: "get",
    params,
  });
}

export function getComplaintlist(params) {
  return request({
    url: "/system/complaints/list",
    method: "get",
    params,
  });
}

// 增加事件
export function addComplaints(data) {
  return request({
    url: "/system/complaints/add",
    method: "post",
    data,
  });
}

export function getByIdComplaints(data) {
  return request({
    url: "/system/complaints/getById/" + `${data.id}`,
    method: "post",
    data,
  });
}