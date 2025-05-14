<template>
  <div>
    <el-card style="margin-bottom: 20px; width: 100%; height: 80px">
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
            <el-button style="border-color: #a8824a; color: #a8824a;" @click="dialogVisible = true">新增医生</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <el-table :data="doctorList" border style="width: 100%;">
        <el-table-column type="index" width="50" />
        <el-table-column prop="doctorName" label="医生姓名" />
        <el-table-column prop="cityName" label="地区" />
        <el-table-column prop="hospitalName" label="地区" />
        <el-table-column prop="departmentName" label="科室" />
        <el-table-column prop="introduction" label="介绍" show-overflow-tooltip />
        <el-table-column label="操作" width="180">
            <template #default="scope">
                <el-button size="small" type="text">详情</el-button>
                <el-button size="small" type="text" @click="handleEdit(scope.row)">编辑</el-button>
                <el-button size="small" type="text" @click="handleDelete(scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-dialog :title="infoForm.doctorId ? '编辑医生' : '新增医生'" width=65% :visible.sync="dialogVisible" :close-on-click-modal="false">
      <el-form :model="infoForm" :rules="infoRule" ref="infoFormRef" label-width="80px">
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="infoForm.username" placeholder="请输入用户名" clearable autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="infoForm.phone" placeholder="请输入手机号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="infoForm.email" placeholder="请输入邮箱" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="医生姓名" prop="patientName">
              <el-input v-model="infoForm.doctorName" placeholder="请输入医生姓名" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在地区">
              <el-select
                v-model="infoForm.cityName"
                clearable
                placeholder="请选择地区"
                style="width: 100%"
              >
                <el-option
                  v-for="item in allCities"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在医院">
              <el-input v-model="infoForm.hospitalName" placeholder="请输入所在医院" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在科室">
              <el-input v-model="infoForm.departmentName" placeholder="请输入所在科室" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="个人简介">
              <el-input type="textarea" v-model="infoForm.introduction" placeholder="请输入个人简介" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset('infoFormRef')">取 消</el-button>
        <el-button type="primary" @click="updateDoctorInfo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getDoctorList, getDoctorListByPage } from "@/api/doctor";
import allCities from "@/assets/data/cities.js";
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
        doctorList: [],
        dialogVisible: false,
        infoForm: {},
        infoFormRef: null,
        infoRule: {
            username: [
                { required: true, message: "请输入用户名", trigger: "blur" },
            ],
            doctorName:[
                { required: true, message: '请输入医生姓名', trigger: 'blur' },
            ],
            phone: [
                { required: true, message: "请输入手机号", trigger: "blur" },
                {
                    pattern: /^1[3-9]\d{9}$/,
                    message: "请输入有效的电话号码！",
                    trigger: "blur",
                },
            ],
            email: [
                { required: true, message: "请输入邮箱", trigger: "blur" },
                {
                    type: "email",
                    message: "请输入正确的邮箱地址",
                    trigger: "blur",
                },
            ],
        },
        allCities: []
    };
  },
  async mounted() {
    this.allCities = allCities;
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
      this.doctorList = result.doctorList
      this.total = result.total
    },
    resetForm() {
      this.$refs.queryFormRef.resetFields();
      this.handleQuery();
    },
    handleEdit(row) {
      this.infoForm = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确定删除该医生信息吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.doctorList = this.doctorList.filter(d => d.doctorId !== row.doctorId)
        this.$message.success('删除成功')
      })
    },
  },
};
</script>
<style scoped lang="scss"></style>
