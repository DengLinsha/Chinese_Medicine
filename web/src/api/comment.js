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

// 删除评论
export function deleteComment(commentId) {
  return request({
    url: `/comment/delete/${commentId}`,
    method: 'get',
  })
}

// 回复评论
export function replyComment(data) {
  return request({
    url: '/reply',
    method: 'post',
    data
  })
}

// 获取回复列表
export function getReplyList(commentId) {
  return request({
    url: `/reply/${commentId}`,
    method: 'get',
  })
}

// 删除回复
export function deleteReply(replyId) {
  return request({
    url: `/reply/delete/${replyId}`,
    method: 'get',
  })
}
