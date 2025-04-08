import request from '@/utils/request'

// 发布评论
export function publishComment(data) {
  return request({
    url: '/comment',
    method: 'post',
    data
  })
}

// 获取评论列表
export function getCommentList(recordId) {
  return request({
    url: `/comment/${recordId}`,
    method: 'get',
  })
}