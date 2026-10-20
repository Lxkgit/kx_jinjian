import request from "@/utils/request";
const gateway = "system";

const urls = {
  /* 标签目录 */
  labelDirectory: `${gateway}/label/group/get`, // 列表
  labelDirectoryAdd: `${gateway}/label/group/addOrEdit`, // 新增/编辑
  labelDirectoryDelete: `${gateway}/label/group/deleteByIds`, // 删除

  /* 标签 */
  labelList: `${gateway}/label/page`, // 列表
  labelAddOrEdit: `${gateway}/label/addOrEdit`, // 新增/编辑
  labelDeleteByIds: `${gateway}/label/deleteByIds`, // 删除

  /* 客群分类 */
  addOrEditCategory: `${gateway}/customer/group/addOrEditCategory`, // 新增/编辑
  delCategory: `${gateway}/customer/group/delCategory/`, // 删除
  categoryList: `${gateway}/customer/group/category/get`, // 删除

  /* 客群 */
  customerGroupList: `${gateway}/customer/group/page`, // 列表
  saveCustomGroup: `${gateway}/customer/group/saveCustomGroup`,
  addOrEditGroup: `${gateway}/customer/group/addOrEditGroup`, // 新增/编辑
  delGroup: `${gateway}/customer/group/delGroup/`, // 删除
  delGroupList: `${gateway}/customer/group/delGroupList`, // 删除
  cascadeLabel: `${gateway}/customer/group/cascadeLabel/`, // 级联获取标签

  /* 成员管理 */
  manageList: `${gateway}/customer/group/manage/page`, // 成员管理
  removeMember: `${gateway}/customer/group/removeMember`, // 移除
  addMember: `${gateway}/customer/group/addMember`, // 新增
  getPaAddrInfoList: `${gateway}/business/addrInfo/getPaAddrInfoList`, // 获取所属网格下拉
};

export default {
  /* get */
  categoryList(params) {
    return request({
      url: urls["categoryList"],
      method: "get",
      params,
    });
  },
  labelDirectory(params) {
    return request({
      url: urls["labelDirectory"],
      method: "get",
      params,
    });
  },
  cascadeLabel(params) {
    const { id } = params;
    return request({
      url: `${urls["cascadeLabel"]}${id}`,
      method: "get",
    });
  },
  delCategory(params) {
    const { id } = params;
    return request({
      url: `${urls["delCategory"]}${id}`,
      method: "get",
    });
  },
  delGroup(params) {
    const { id } = params;
    return request({
      url: `${urls["delGroup"]}${id}`,
      method: "get",
    });
  },
  /* post */
  removeMember(data) {
    return request({
      url: urls["removeMember"],
      method: "post",
      data,
    });
  },
  addMember(data) {
    return request({
      url: urls["addMember"],
      method: "post",
      data,
    });
  },
  delGroupList(data) {
    return request({
      url: urls["delGroupList"],
      method: "post",
      data,
    });
  },
  manageList(data) {
    return request({
      url: urls["manageList"],
      method: "post",
      data,
    });
  },
  saveCustomGroup(data) {
    return request({
      url: urls["saveCustomGroup"],
      method: "post",
      data,
    });
  },
  addOrEditGroup(data) {
    return request({
      url: urls["addOrEditGroup"],
      method: "post",
      data,
    });
  },
  addOrEditCategory(data) {
    return request({
      url: urls["addOrEditCategory"],
      method: "post",
      data,
    });
  },
  customerGroupList(data) {
    return request({
      url: urls["customerGroupList"],
      method: "post",
      data,
    });
  },
  labelDeleteByIds(data) {
    return request({
      url: urls["labelDeleteByIds"],
      method: "post",
      data,
    });
  },
  labelAddOrEdit(data) {
    return request({
      url: urls["labelAddOrEdit"],
      method: "post",
      data,
    });
  },
  labelDirectoryDelete(data) {
    return request({
      url: urls["labelDirectoryDelete"],
      method: "post",
      data,
    });
  },
  labelDirectoryAdd(data) {
    return request({
      url: urls["labelDirectoryAdd"],
      method: "post",
      data,
    });
  },
  labelList(data) {
    return request({
      url: urls["labelList"],
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
};
