import request from '@/utils/request'

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

// 更新密码
export function updatePassword(data) {
  return request({
    url: '/user/updatePassword',
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