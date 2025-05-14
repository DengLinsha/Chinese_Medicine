<template>
  <div>
    <el-card style="margin-bottom: 20px; width: 100%">
      <el-form :model="queryForm" ref="queryFormRef" style="height: 43px">
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
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {};
  },
  mounted() {},
  methods: {},
};
</script>
<style scoped lang="scss"></style>
