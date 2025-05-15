<template>
  <div>
    <el-card style="margin-bottom: 20px; width: 100%; height: 80px">
      <el-form :model="queryForm" ref="queryFormRef" label-width="80px" style="height: 43px">
        <el-row :gutter="24">
          <el-col :span="6">
            <el-form-item label="患者姓名">
              <el-input
                placeholder="请输入患者姓名"
                v-model="queryForm.patientName"
                clearable
                style="margin-right: 10px"
              />
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="医生姓名">
              <el-input
                placeholder="请输入医生姓名"
                v-model="queryForm.doctorName"
                clearable
                style="margin-right: 10px"
              />
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="病历状态">
              <el-select
                v-model="queryForm.status"
                clearable
                placeholder="请选择状态"
                style="margin-right: 10px"
              >
                <el-option
                  v-for="item in statusList"
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
            <el-button style="border-color: #a8824a; color: #a8824a;" @click="handleAdd">新增病历</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <el-table :data="recordList" border style="width: 100%">
        <el-table-column prop="patientName" label="患者姓名" />
        <el-table-column prop="description" label="病情自述" width="240" show-overflow-tooltip />
        <el-table-column label="病历状态">
            <template #default="scope">
                <el-tag size="small"> {{ getStatusLabel(scope.row.status) }} </el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="doctorName" label="医生姓名" />
        <el-table-column label="就诊日期">
            <template #default="scope">
                {{ scope.row.createTime.replace('T', ' ').split('.')[0] }}
            </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button size="small" type="text" @click="goRecordDetail(scope.row)">详情</el-button>
            <el-button size="small" type="text" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="text" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog :title="recordInfoForm.recordId ? '编辑病历' : '新增病历'" width = 60% :visible.sync="dialogVisible">
        <el-form
            :model="recordInfoForm"
            :rules="addRecordRules"
            ref="recordInfoFormRef"
            label-width="150px"
        >
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="患者姓名" prop="patientName">
                        <el-input v-model="recordInfoForm.patientName" placeholder="请输入姓名" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="年龄">
                        <el-input type="number" v-model="recordInfoForm.age" placeholder="请输入年龄" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="性别">
                        <el-radio-group v-model="recordInfoForm.sex">
                            <el-radio :label="0">男</el-radio>
                            <el-radio :label="1">女</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="phone" label="手机号">
                        <el-input v-model="recordInfoForm.phone" placeholder="请输入手机号" clearable />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="既往史">
                        <el-input type="textarea" v-model="recordInfoForm.oldHistory" placeholder="请输入既往史（是否患有慢性疾病，如高血压、糖尿病等）" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="过敏史">
                        <el-input type="textarea" v-model="recordInfoForm.allergiesHistory" placeholder="请输入过敏史（对药物、食物或环境因素的过敏情况）" />
                    </el-form-item>
                    </el-col>
                <el-col :span="24">
                    <el-form-item label="生活习惯">
                        <el-input type="textarea" v-model="recordInfoForm.habits" placeholder="请输入过敏史（包括饮食、睡眠、情绪、烟酒史等）" />
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item prop="doctorId" label="选择医生">
                        <el-select
                            v-model="cityName"
                            clearable
                            placeholder="请选择地区"
                            style="width: 160px; margin-right: 10px"
                            @change="onFilterChange"
                        >
                            <el-option
                            v-for="item in cities"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                            />
                        </el-select>
                        <el-select
                            v-model="hospitalName"
                            clearable
                            placeholder="请选择医院"
                            style="width: 160px; margin-right: 10px"
                            @change="onFilterChange"
                        >
                            <el-option
                            v-for="item in hospitalList"
                            :key="item.label"
                            :label="item.label"
                            :value="item.value"
                            />
                        </el-select>
                        <el-select
                            v-model="departmentName"
                            clearable
                            placeholder="请选择科室"
                            style="width: 160px; margin-right: 10px"
                            @change="onFilterChange"
                        >
                            <el-option
                            v-for="item in departmentList"
                            :key="item.label"
                            :label="item.label"
                            :value="item.value"
                            />
                        </el-select>
                        <el-select
                            v-model="recordInfoForm.doctorId"
                            clearable
                            placeholder="请选择医生"
                            style="width: 160px"
                            @change="onFilterChange"
                        >
                            <el-option
                            v-for="item in docList"
                            :key="item.doctorId"
                            :label="item.doctorName"
                            :value="item.doctorId"
                            />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="病情描述" prop="description">
                        <el-input type="textarea" v-model="recordInfoForm.description" placeholder="请输入详细的现在症状" />
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div class="btns">
            <el-button @click="addRecord(false)">暂存</el-button>
            <el-button type="primary" @click="addRecord(true)">提交病历</el-button>
        </div>
      </el-dialog>
  </div>
</template>

<script>
import { getRecordList, addRecord, getRecordInfo } from '@/api/record';
import { getDoctorList } from "@/api/doctor";
export default {
  components: {

  },
  data () {
    return {
        statusList: [
            { value: 4, label: '未提交' },
            { value: 0, label: '未查看' },
            { value: 1, label: '已查看' },
            { value: 2, label: '已诊断' },
            { value: 3, label: '诊断结束' },  
        ],
        tableParams: {
            page: 1,
            pageSize: 1000,
        },
        total: 0,
        queryForm:{
            patientName:'',
            doctorName: '',
            status:'',
        },
        queryFormRef: null,
        dialogVisible: false,
        recordList: [],
        recordInfoForm: {},
        recordInfoFormRef: null,
        addRecordRules: {
            patientName: [
                { required: true, message: '请输入患者姓名', trigger: 'blur' },
            ],
            phone: [
                { required: true, message: "手机号不能为空", trigger: "blur" },
                {
                    pattern: /^1[3-9]\d{9}$/,
                    message: "请输入有效的电话号码！",
                    trigger: "blur",
                },
            ],
            doctorId: [
                { required: true, message: "请选择一个医生", trigger: "blur" },
            ],
            description: [
                { required: true, message: "病症描述不能为空", trigger: "blur" },
            ],
        },
        cities: [],
        hospitalList: [],
        departmentList: [],
        cityName: '',
        hospitalName: '',
        departmentName: '',
        docList: [],
        allDoctors: []
    }
  },
  mounted() {
    this.handleQuery()
  },
  methods: {
    async handleQuery(params = {}) {
        this.allDoctors = await getDoctorList();
        const result = await getRecordList({
            ...this.tableParams,
            ...params,
            ...this.queryForm
        });
        this.recordList = result.recordList
    },
    resetForm() {
      this.$refs.queryFormRef.resetFields();
      this.handleQuery();
    },
    getStatusLabel(status) {
        switch (status) {
            case 0:
                return '未查看';
            case 1:
                return '已查看';
            case 2:
                return '已诊断';
            case 3:
                return '诊断结束';
            case 4:
                return '未提交';
        }
    },
    goRecordDetail(record) {
      localStorage.setItem('recordId', record.recordId)
      if (record.status == 4) {
        this.$router.push({
          path: '/home/addRecord',
          query: {
            recordId: record.recordId
          }
        })
      } else {
        this.$router.push({
          path: '/home/recordDetail'
        })
      }
    },
    async handleEdit(row) {
      this.recordInfoForm = await getRecordInfo(row.recordId)
      const {cityName, hospitalName, departmentName} = this.allDoctors.find(doctor => doctor.doctorId === this.recordInfoForm.doctorId)
      this.cityName = cityName
      this.hospitalName = hospitalName
      this.departmentName = departmentName
      this.dialogVisible = true
    },

    handleDelete(row) {
      this.$confirm('确定删除该病历记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.recordList = this.recordList.filter(r => r.id !== row.id)
        this.$message.success('删除成功')
      })
    },

    async handleAdd() {
        this.cities = [...new Set(this.allDoctors.map(doctor => doctor.cityName))].map(city => ({
            label: city,
            value: city
        }));
        this.onFilterChange()
        this.dialogVisible = true
    },
    addRecord(flag) {
      this.$refs.recordInfoFormRef.validate(async (valid) => {
        if (valid) {
          if (flag) {
            // 提交
            this.recordInfoForm.status = 0;
          } else {
            // 暂存
            this.recordInfoForm.status = 4;
          }
          const result = await addRecord({...this.userInfo, ...this.patientInfoForm, ...this.recordInfoForm})
          if (result) {
            this.$message.success("病历创建成功");
          }
          this.resetForm();
        }
      });
    },
    resetForm() {
      this.$refs.recordInfoFormRef.resetFields();
    },
    onFilterChange() {
      this.updateDoctorList();
      this.updateHospitalList();
      this.updateDepartmentList();
    },
    updateHospitalList() {
      const filtered = this.cityName
        ? this.allDoctors.filter(doc => doc.cityName === this.cityName)
        : this.allDoctors;

      const hospitals = [...new Set(filtered.map(doc => doc.hospitalName))];
      this.hospitalList = hospitals.map(h => ({ label: h, value: h }));

      // 若当前选中医院不在列表中，清空
      if (!hospitals.includes(this.hospitalName)) {
        this.hospitalName = '';
      }
    },

    updateDepartmentList() {
      const filtered = this.hospitalName
        ? this.allDoctors.filter(doc => doc.hospitalName === this.hospitalName)
        : this.allDoctors;

      const departments = [...new Set(filtered.map(doc => doc.departmentName))];
      this.departmentList = departments.map(d => ({ label: d, value: d }));

      // 若当前选中科室不在列表中，清空
      if (!departments.includes(this.departmentName)) {
        this.departmentName = '';
      }
    },

    updateDoctorList() {
      let filtered = this.allDoctors;

      if (this.cityName) {
        filtered = filtered.filter(doc => doc.cityName === this.cityName);
      }
      if (this.hospitalName) {
        filtered = filtered.filter(doc => doc.hospitalName === this.hospitalName);
      }
      if (this.departmentName) {
        filtered = filtered.filter(doc => doc.departmentName === this.departmentName);
      }

      this.docList = filtered.map(doc => ({
        doctorName: doc.doctorName,
        doctorId: doc.doctorId
      }));

      // 当前选择的 doctorId 是否还存在
      if (!this.docList.some(d => d.doctorId === this.recordInfoForm.doctorId)) {
        this.recordInfoForm.doctorId = '';
      }
    }
  },
}

</script>
<style scoped lang='scss'>
.btns {
  display: flex;
  justify-content: end;
  gap: 30px;
}
:deep(.el-dialog) {
    margin-top: 10vh !important;
}
</style>