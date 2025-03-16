import request from '@/utils/request'

// 更新医生基础信息
export function updateDoctorInfo(data) {
  return request({
    url: '/doctor/update',
    method: 'post',
    data
  })
}

// 获取医生基础信息
export function getDoctorInfo(userId) {
  return request({
    url: `/doctor/get/${userId}`,
    method: 'get',
  })
}

export function getDoctorList() {
  return request({
    url: `/doctor`,
    method: 'get',
  })
}