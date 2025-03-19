import request from '@/utils/request'

export function addRecord(data) {
    return request({
        url: '/record/add',
        method: 'post',
        data
    })
}

export function getRecordList(data) {
    return request({
        url: `/record?page=${data.page}&pageSize=${data.pageSize}`,
        method: 'get',
    })
}

export function getRecordInfo(recordId) {
  return request({
    url: `/record/${recordId}`,
    method: 'get',
  })
}

export function updateRecord(data) {
  return request({
    url: `/record/update`,
    method: 'post',
    data
  })
}