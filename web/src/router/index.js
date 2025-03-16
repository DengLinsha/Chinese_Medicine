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
    redirect:"/home/addRecord",
    component: () => import('@/views/home/index.vue'),
    children: [
      {
        path:"/home/addRecord",
        name:"addRecord",
        component: () => import('@/views/medicalRecord/addRecord.vue')
      },
      {
        path:"/home/recordDetail",
        name:"recordDetail",
        component: () => import('@/views/medicalRecord/recordDetail.vue')
      },
      {
        path:"/home/allRecords",
        name:"allRecords",
        component: () => import('@/views/medicalRecord/allRecords.vue')
      },
      {
        path:"/home/docList",
        name:"docList",
        component: () => import('@/views/medicalRecord/docList.vue')
      },
      {
        path:"/home/diagnosisList",
        name:"diagnosisList",
        component: () => import('@/views/diagnosis/diagnosisList.vue'),
      },
      {
        path:"/diagnosis/detail",
        name:"diagnosisDetail",
        component: () => import('@/views/diagnosis/detail.vue'),
      }
    ]
  },
]

const router = new VueRouter({
  routes
})

export default router
