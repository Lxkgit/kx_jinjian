import request from '@/utils/request'

// 查询部门列表
export function listDept(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}

// 查询部门列表（排除节点）
export function listDeptExcludeChild(deptId) {
  return request({
    url: '/system/dept/list/exclude/' + deptId,
    method: 'get'
  })
}

// 查询部门详细
export function getDept(deptId) {
  return request({
    url: '/system/dept/' + deptId,
    method: 'get'
  })
}

// 新增部门
export function addDept(data) {
  return request({
    url: '/system/dept',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateDept(data) {
  return request({
    url: '/system/dept',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delDept(deptId) {
  return request({
    url: '/system/dept/' + deptId,
    method: 'delete'
  })
}

// 懒加载部门树；首次加载可同时返回根节点的直属下一层
export function listLazyDeptOptions(parentId, includeNextLevel = false) {
  const params = {}
  if (parentId !== undefined && parentId !== null && parentId !== '') {
    params.parentId = parentId
  }
  if (includeNextLevel) {
    params.includeNextLevel = true
  }
  return request({
    url: '/system/dept/lazy-options',
    method: 'get',
    params
  })
}
