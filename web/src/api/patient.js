import request from '@/utils/request'

// 更新患者基础信息
export function updatePatientInfo(data) {
  return request({
    url: '/patient/update',
    method: 'post',
    data
  })
}

// 获取患者基础信息
export function getPatientInfo(userId) {
  return request({
    url: `/patient/get/${userId}`,
    method: 'get',
  })
}