<template>
  <div>
    <!-- 展示医生列表 -->
    <el-card style="margin-bottom: 20px; width: 100%;">
      <el-form :model="queryForm" ref="queryFormRef" style="height: 43px;">
        <el-row :gutter="24">
          <el-col :span="4">
            <el-form-item prop="cityName">
              <el-select
                v-model="queryForm.cityName"
                clearable
                placeholder="请选择地区"
                style="margin-right: 10px"
              >
                <el-option
                  v-for="item in cities"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item prop="hospitalName">
              <el-select
                v-model="queryForm.hospitalName"
                clearable
                placeholder="请选择医院"
                style="margin-right: 10px"
              >
                <el-option
                  v-for="item in hospitalList"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item prop="departmentName">
              <el-select
                v-model="queryForm.departmentName"
                clearable
                placeholder="请选择科室"
                style="margin-right: 10px"
              >
                <el-option
                  v-for="item in departmentList"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-button type="primary" @click="handleQuery">查询</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <div v-if="queryDocList.length > 0">
      <el-row>
        <el-col :span="6" v-for="item in queryDocList" :key="item.id">
          <el-card
            shadow="hover"
            style="width: 300px; height: 150px;"
          >
            <!-- <img
              :src="path + item.pic"
              class="image"
              @click="Docdetail(item)"
              height="280px"
              width="280px"
            /> -->
            <div>
              <span style="font-size: 20px;">{{ item.doctorName }}</span>
              <span style="margin-left: 5px;">{{ item.departmentName}}</span>
            </div>
            <div style="margin: 5px 0;">{{ item.hospitalName }}</div>
            <div v-if="item.introduction" style="color: #9f9f9f;">简介：{{ item.introduction }}</div>
          </el-card>
        </el-col>
      </el-row>

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

    <div v-if="queryDocList.length == 0">
      <el-empty description="暂无数据"></el-empty>
    </div>
  </div>
</template>

<script>
import { getDoctorList, getDoctorListByPage } from "@/api/doctor";
export default {
  components: {},
  data() {
    return {
      tableParams: {
        page: 1,
        pageSize: 5,
      },
      total: 0,
      queryForm:{
       cityName:'',
       hospitalName:'',
       departmentName:'',
      },
      cities: [],
      allDoctors: [],
      hospitalList: [],
      departmentList: [],
      queryDocList: []
    };
  },
  async mounted() {
    this.allDoctors = await getDoctorList();
    this.cities = [...new Set(this.allDoctors.map(doctor => doctor.cityName))].map(city => ({
      label: city,
      value: city
    }));
    this.hospitalList = [...new Set(this.allDoctors.map(doctor => doctor.hospitalName))].map(hospital => ({
      label: hospital,
      value: hospital
    }));
    this.departmentList = [...new Set(this.allDoctors.map(doctor => doctor.departmentName))].map(department => ({
      label: department,
      value: department
    }));
    this.handleQuery()
  },
  methods: {
    async handleQuery(params = {}) {
      this.tableParams = { ...this.tableParams, ...params, ...this.queryForm };
      const result = await getDoctorListByPage(this.tableParams)
      this.queryDocList = result.doctorList
      this.total = result.total
    },
    resetForm() {
      this.$refs.queryFormRef.resetFields();
      this.handleQuery();
    }
  },
};
</script>
<style scoped lang="scss">
:deep(.el-card__body) {
  height: auto;
  overflow: initial;
  padding: 10px;
}

.pagination {
  height: 50px;
  margin-top: 16px;
  margin-left: 66%;
}
</style>
