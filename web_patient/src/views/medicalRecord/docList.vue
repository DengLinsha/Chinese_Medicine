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
                style="width: 150px"
              >
                <el-option
                  v-for="item in cities"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item prop="hospitalName">
              <el-select
                v-model="queryForm.hospitalName"
                clearable
                placeholder="请选择医院"
                style="width: 150px"
              >
                <el-option
                  v-for="item in hospList"
                  :key="item.hospitalName"
                  :label="item.hospitalName"
                  :value="item.hospitalName"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item prop="departmentName">
              <el-select
                v-model="queryForm.departmentName"
                clearable
                placeholder="请选择科室"
                style="width: 150px"
              >
                <el-option
                  v-for="item in departmentList"
                  :key="item.departmentName"
                  :label="item.departmentName"
                  :value="item.departmentName"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item prop="id">
              <el-select
                v-model="queryForm.id"
                clearable
                placeholder="请选择医生"
                style="width: 150px"
              >
                <el-option
                  v-for="item in docList"
                  :key="item.id"
                  :label="item.docName"
                  :value="item.id"
                >
                </el-option>
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
            :body-style="{ padding: '0px' }"
            style="width: 280px; height: 360px"
          >
            <!-- <img
              :src="path + item.pic"
              class="image"
              @click="Docdetail(item)"
              height="280px"
              width="280px"
            /> -->
            <div style="padding: 5px">
              <span>{{ item.docName }}</span>
              <span style="margin-left: 8px; color: orange">{{
                item.departmentName
              }}</span>
            </div>
            <div style="margin-left: 5px; color: red">
              <span>联系方式:</span>
              <span>{{ item.phone }}</span>
            </div>
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
      <el-empty description="请选择查询条件"></el-empty>
    </div>
  </div>
</template>

<script>
import cities from "@/assets/data/cities.js";
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
       id:''
      },
      cities: [],
      hospitalList: [],
      departmentList: [],
      docList: [],
      queryDocList: []
    };
  },
  mounted() {
    this.cities = cities;
  },
  methods: {
    handleQuery(params = {}) {

    },
    resetForm() {
      this.$refs.queryFormRef.resetFields();
    }
  },
};
</script>
<style scoped lang="scss"></style>
