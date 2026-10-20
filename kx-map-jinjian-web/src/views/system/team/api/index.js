import request from '@/utils/request'
const gateway = "system";
const urls = {
  teamManage:`${gateway}/team/manage/list`,
  teamManageMember:`${gateway}/team/manage/member/list`,
}

export default {
  teamManage(params) {
    return request({
      url: urls['teamManage'],
      method: 'get',
      params
    })
  },
  teamManageMember(params) {
    return request({
      url: urls['teamManageMember'],
      method: 'get',
      params
    })
  },
}