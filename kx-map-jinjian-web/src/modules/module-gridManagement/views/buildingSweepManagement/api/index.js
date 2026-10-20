import request from '@/utils/request'

const prefix = 'system/building-sweep/report'

export function listBuildingSweepReports(data) {
  return request({ url: `${prefix}/list`, method: 'post', data })
}

export function getBuildingSweepReport(id) {
  return request({ url: `${prefix}/${id}`, method: 'get' })
}

export function addBuildingSweepReport(data) {
  return request({ url: `${prefix}/add`, method: 'post', data })
}

export function editBuildingSweepReport(data) {
  return request({ url: `${prefix}/edit`, method: 'post', data })
}

export function reviewBuildingSweepReport(data) {
  return request({ url: `${prefix}/review`, method: 'post', data })
}

export function listRelatedClueOptions(keyword) {
  return request({
    url: `${prefix}/related-clues`,
    method: 'get',
    params: { keyword }
  })
}

export function listDepartmentChildren(parentId) {
  return request({
    url: 'system/dept/lazy-options',
    method: 'get',
    params: parentId ? { parentId } : {}
  })
}

export function listDepartmentUsers(rootDeptIds, keyword) {
  return request({
    url: 'system/user/dept-scope-options',
    method: 'get',
    params: { rootDeptIds: rootDeptIds.join(','), keyword }
  })
}

export function resubmitBuildingSweepReport(data) {
  return request({ url: `${prefix}/resubmit`, method: 'post', data })
}

export function deleteBuildingSweepReport(id) {
  return request({ url: `${prefix}/delete/${id}`, method: 'post' })
}
