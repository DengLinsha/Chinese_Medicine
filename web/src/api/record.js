import request from '@/utils/request'

export function addRecord(data) {
    return request({
        url: '/record/add',
        method: 'post',
        data
    })
}