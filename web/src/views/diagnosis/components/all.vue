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
            {{ item.realName }}
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
            <span class="text-ellipsis">{{ item.desc }}</span>
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
  mounted() {
    this.handleQuery()
  },
  methods: {
    handleQuery(params = {}) {
      this.tableParams = { ...this.tableParams, ...params };
      this.recordList = [
        {
          id: 1,
          realName: "张三",
          phone: "12345678901",
          age: 18,
          desc: "突然身上通红，起了很多小疹子",
          department: "皮肤科",
          docName: "邓林莎",
          status: 0,
        },
        {
          id: 2,
          realName: "张三",
          phone: "12345678901",
          age: 18,
          desc: "突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况。突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况",
          department: "皮肤科",
          docName: "邓林莎",
          status: 1,
        },
        {
          id: 3,
          realName: "张三",
          phone: "12345678901",
          age: 18,
          desc: "突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况。突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况",
          department: "皮肤科",
          docName: "邓林莎",
          status: 3,
        },
        {
          id: 4,
          realName: "张三",
          phone: "12345678901",
          age: 18,
          desc: "突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况。突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况",
          department: "皮肤科",
          docName: "邓林莎",
          status: 3,
        },
        {
          id: 5,
          realName: "张三",
          phone: "12345678901",
          age: 18,
          desc: "突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况。突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况",
          department: "皮肤科",
          docName: "邓林莎",
          status: 2,
        },
        {
          id: 6,
          realName: "张三",
          phone: "12345678901",
          age: 18,
          desc: "突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况。突然身上通红，起了很多小疹子，很痒，晚上都没怎么睡着觉，一直抠来抠去的。之前从来没有出现过这种情况",
          department: "皮肤科",
          docName: "邓林莎",
          status: 1,
        },
      ];
      if (this.activeName === 'noDiagnosis') {
        this.recordList = this.recordList.filter(item => item.status === 0 || item.status === 1)
      }
      this.total = this.recordList.length;
    },
    goRecordDetail(item) {
      this.$router.push({
        path: '/diagnosis/detail'
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
  margin-left: 60%;
}
</style>