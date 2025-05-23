import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
import { Message } from "element-ui";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/',
    redirect: '/login' // 根路径重定向到登录页
  },
  {
    path: '/home',
    name: 'home',
    redirect:"/home/addRecord",
    component: () => import('@/views/home/index.vue'),
    children: [
      {
        path:"/home/addRecord",
        name:"病症诊断",
        icon: "el-icon-service",
        component: () => import('@/views/medicalRecord/addRecord.vue'),
        meta: { roles: [0] } // 只有患者可以访问
      },
      {
        path:"/home/recordDetail",
        name:"recordDetail",
        component: () => import('@/views/medicalRecord/recordDetail.vue'),
      },
      {
        path:"/home/allRecords",
        name:"咨询历史",
        icon: "el-icon-document",
        component: () => import('@/views/medicalRecord/allRecords.vue'),
        meta: { roles: [0] }
      },
      {
        path:"/home/docList",
        name:"医师介绍",
        icon: "el-icon-user",
        component: () => import('@/views/medicalRecord/docList.vue'),
        meta: { roles: [0] }
      },
      // 医生
      {
        path:"/home/diagnosisList",
        name:"诊断列表",
        icon: "el-icon-user",
        component: () => import('@/views/diagnosis/diagnosisList.vue'),
        meta: { roles: [1] }
      },
      {
        path:"/diagnosis/detail",
        name:"diagnosisDetail",
        component: () => import('@/views/diagnosis/detail.vue'),
      },
      {
        path:"/home/communicate",
        name:"医患交流",
        icon: "el-icon-chat-line-round",
        component: () => import('@/views/communicate/index.vue'),
        meta: { roles: [1] }
      },
      {
        path:"/home/statistics",
        name:"统计分析",
        icon: "el-icon-c-scale-to-original",
        component: () => import('@/views/statistics/index.vue'),
        meta: { roles: [1] }
      },
      // 管理员
      {
        path:"/admin/patients",
        name:"患者管理",
        icon: "el-icon-user",
        component: () => import('@/views/admin/patients.vue'),
        meta: { roles: [2] }
      },
      {
        path:"/admin/doctors",
        name:"医生管理",
        icon: "el-icon-service",
        component: () => import('@/views/admin/doctors.vue'),
        meta: { roles: [2] }
      },
      {
        path:"/admin/records",
        name:"病历管理",
        icon: 'el-icon-bank-card',
        component: () => import('@/views/admin/records.vue'),
        meta: { roles: [2] }
      },
      {
        path:"/admin/comments",
        name:"评论管理",
        icon: 'el-icon-message',
        component: () => import('@/views/admin/comments.vue'),
        meta: { roles: [2] }
      },
      {
        path:"/admin/prescriptions",
        name:"药方管理",
        icon: 'el-icon-document',
        component: () => import('@/views/admin/prescriptions.vue'),
        meta: { roles: [2] }
      },
    ]
  },
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  const userInfo = store.state.user.userInfo; // 从 Vuex 中获取用户角色
  const requiredRoles = to.meta.roles; // 获取路由所需的角色
  if (to.path === '/login') {
    next();
  } else {
    if (!userInfo) {
      Message.error('未登录');
      next('/login');
      return;
    }

    if (requiredRoles) {
      const userRole = userInfo.role;
      // 检查用户是否有权限访问该路由
      if (requiredRoles.includes(userRole)) {
        next(); // 允许访问
      } else {
        Message.error('无权限访问');
        next(false); 
      }
    } else {
      next(); // 无需权限，允许访问
    }
  }
})

export default router
