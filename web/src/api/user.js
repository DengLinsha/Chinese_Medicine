import request from '@/utils/request'

export function getUserInfo(userId) {
  return request({
    url: `/user/${userId}`,
    method: 'get',
  })
}

// 登录
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

// 找回密码
export function findPassword(identity) {
  return request({
    url: `/user/findPassword?identity=${identity}`,
    method: 'get',
  })
}

// 更新
export function update(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

// 注册
export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}