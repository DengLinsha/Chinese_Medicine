import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/views/home/index.vue'),
    children: [
      {
        path:"/addRecord",
        name:"addRecord",
        component: () => import('@/views/medicalRecord/addRecord.vue')
      },
      {
        path:"/recordDetail",
        name:"recordDetail",
        component: () => import('@/views/medicalRecord/recordDetail.vue')
      },
      {
        path:"/allRecords",
        name:"allRecords",
        component: () => import('@/views/medicalRecord/allRecords.vue')
      },
      {
        path:"/docList",
        name:"docList",
        component: () => import('@/views/medicalRecord/docList.vue')
      },
    ]
  },
]

const router = new VueRouter({
  routes
})

export default router
