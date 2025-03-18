<template>
  <div style="height: 100%;">
    <el-card style="width: 100%;">
      <div slot="header" class="clearfix">
        <h2>咨询列表</h2>
      </div>
      <div v-for="item in recordList" :key="item.recordId">
        <el-descriptions :column="3" border style="margin-bottom: 20px">
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              姓名
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
              <i class="el-icon-tickets"></i>
              所属科室
            </template>
            {{ item.departmentName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-service"></i>
              诊治医生
            </template>
            {{ item.doctorName }}
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
            <el-link style="margin-left: 10px" type="primary" @click="goRecordDetail(item.recordId)"
              >点击查看详情</el-link
            >
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-card>
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
export default {
  components: {},
  data() {
    return {
      title: '咨询列表',
      recordList: [],
      tableParams: {
        page: 1,
        pageSize: 5,
      },
      total: 0,
    };
  },
  mounted() {
    this.handleQuery();
  },
  methods: {
    async handleQuery(params = {}) {
      this.tableParams = { ...this.tableParams, ...params };
      const result = await getRecordList(this.tableParams)
      this.recordList = result.recordList
      this.total = result.total;
    },
    goRecordDetail(recordId) {
      localStorage.setItem('recordId', recordId)
      this.$router.push({
        path: '/home/recordDetail'
      })
    }
  },
};
</script>
<style scoped lang="scss">
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.text-ellipsis {
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 330px; // 根据需要调整最大宽度
}

:deep(.el-card__body) {
  height: calc(100vh - 200px);
  overflow: auto
}

.pagination {
  height: 50px;
  margin-top: 16px;
  margin-left: 60%;
}
</style>

