<template>
  <div style="height: 100%;width: 100%;">
    <div class="box-card">
      <div v-for="item in recordList" :key="item.id">
        <el-descriptions title="病历信息" :column="3" border style="margin-bottom: 20px">
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              患者姓名
            </template>
            {{ item.patientName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              手机号
            </template>
            {{ item.phone }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time"></i>
              年龄
            </template>
            {{ item.age }}
          </el-descriptions-item>
          <el-descriptions-item :contentStyle="{ width: '350px' }">
            <template slot="label">
              <i class="el-icon-tickets"></i>
              病症详情
            </template>
            <span class="text-ellipsis">{{ item.description }}</span>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-notebook-1"></i>
              病历状态
            </template>
            <el-tag v-if="item.status == 0" size="small">未查看</el-tag>

            <el-tag v-if="item.status == 1" size="small">已查看</el-tag>
            <el-tag v-if="item.status == 2" size="small">已诊断</el-tag>
            <el-tag v-if="item.status == 3" size="small">诊断已结束</el-tag>
            <!-- <el-tag  @click="recordDetail(item)" style="margin-left: 10px" size="small">点击查看详情</el-tag> -->
            <el-link style="margin-left: 10px" type="primary" @click="goRecordDetail(item)"
              >点击查看详情</el-link
            >
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
    
    <div class="pagination">
      <el-pagination
        @current-change="(val) => handleQuery({ page: val })"
        @size-change="(val) => handleQuery({ pageSize: val })"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[5, 10, 20, 50, 100]"
        :current-page="tableParams.page"
        :page-size="tableParams.pageSize"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { getRecordList } from '@/api/record';
import { getDoctorInfo } from '@/api/doctor';
export default {
  components: {

  },
  props: ['activeName'],
  data () {
    return {
      recordList: [],
      tableParams: {
        page: 1,
        pageSize: 5,
      },
      total: 0,
    }
  },
  computed: {
    userInfo() {
      return this.$store.state.user.userInfo;
    },
  },
  mounted() {
    this.handleQuery()
  },
  watch: {
    activeName(newVal, oldVal) {
      if (newVal === 'noDiagnosis') {
        this.tableParams.status = '0,1,2'
      } else {
        this.tableParams.status = ''
      }
      this.handleQuery();
    }
  },
  methods: {
    async handleQuery(params = {}) {
      const doctor = await getDoctorInfo(this.userInfo.userId)
      this.tableParams = { ...this.tableParams, ...params, doctorId: doctor.doctorId };
      const result = await getRecordList(this.tableParams)
      this.recordList = result.recordList
      this.total = result.total;
      
    }, 
    goRecordDetail(item) {
      this.$router.push({
        path: '/diagnosis/detail?recordId=' + item.recordId
      })
    }
  },
}

</script>
<style scoped lang='scss'>
.box-card {
  height: calc(100vh - 200px);
  overflow: auto;
}

.box-card::-webkit-scrollbar {
  width: 8px; /* 滚动条的宽度 */
}

.box-card::-webkit-scrollbar-track {
  background: #f5f5f5; /* 轨道颜色 */
  border-radius: 4px; /* 轨道圆角 */
}

.box-card::-webkit-scrollbar-thumb {
  background: #a8824a; /* 滑块颜色 */
  border-radius: 4px; /* 滑块圆角 */
}

.box-card::-webkit-scrollbar-thumb:hover {
  background: #8b7a45; /* 滑块悬停时的颜色 */
}

.text-ellipsis {
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 330px; // 根据需要调整最大宽度
}

.pagination {
  height: 50px;
  margin-top: 16px;
  margin-left: 66%;
}
</style>