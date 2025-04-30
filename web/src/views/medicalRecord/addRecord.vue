<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <h2 v-if="recordId">编辑病历</h2>
        <h2 v-else>提交病历</h2>
      </div>
      <el-form
        :model="recordInfoForm"
        :rules="addRecordRules"
        ref="recordInfoFormRef"
        label-width="150px"
      >
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="患者姓名">
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
              <el-input type="textarea" v-model="recordInfoForm.oldHistory" placeholder="请输入既往史（既往是否有类似症状、是否患有慢性疾病，如高血压、糖尿病等）" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="过敏史">
              <el-input type="textarea" v-model="recordInfoForm.allergiesHistory" placeholder="请输入过敏史（对药物、食物或环境因素的过敏情况）" />
            </el-form-item>
            </el-col>
          <el-col :span="24">
            <el-form-item label="生活习惯">
              <el-input type="textarea" v-model="recordInfoForm.habits" placeholder="请输入过敏史（可以包括饮食、睡眠、情绪、烟酒史等）" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="doctorId" label="选择医生">
              <el-select
                v-model="cityName"
                clearable
                placeholder="请选择地区"
                style="margin-right: 10px"
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
                style="margin-right: 10px"
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
                style="margin-right: 10px"
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

        <!-- <el-form-item
          label="病症图片(包括面部，舌苔图片最多三张)"
          prop="picImg"
          style="display: table"
        >
          <el-upload
            name="file"
            :action="path + '/bingli/upload'"
            :on-success="success"
            :limit="3"
            :on-exceed="exceed"
            :file-list="fileList"
            list-type="picture"
          >
            <el-button size="small" type="primary" style="margin-right: 25px"
              >上传图片</el-button
            >
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item> -->
      </el-form>
      <div class="btns">
        <el-button @click="addRecord(false)">暂存</el-button>
        <el-button type="primary" @click="addRecord(true)">提交病历</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getPatientInfo } from "@/api/patient";
import { getDoctorList, getDoctorInfo } from "@/api/doctor";
import { addRecord, getRecordInfo } from "@/api/record";
export default {
  components: {},
  data() {
    return {
      recordId: parseInt(localStorage.getItem('recordId')),
      patientInfoForm: {},
      recordInfoForm: {
        doctorId: '',
        description: '',
      },
      addRecordRules: {
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
    };
  },

  computed: {
    userInfo() {
      return this.$store.state.user.userInfo;
    },
  },
  async mounted() {
    this.$bus.$on('user-info-updated', this.handleUserInfoUpdate);
    this.allDoctors = await getDoctorList();
    // 待提交回到编辑页面
    if (this.recordId) {
      this.recordInfoForm = await getRecordInfo(this.recordId)
      const {cityName, hospitalName, departmentName} = this.allDoctors.find(doctor => doctor.doctorId === this.recordInfoForm.doctorId)
      this.cityName = cityName
      this.hospitalName = hospitalName
      this.departmentName = departmentName
      localStorage.removeItem('recordId')
    } else {
      this.patientInfoForm = await getPatientInfo(this.userInfo.userId);
      this.recordInfoForm = {...this.patientInfoForm}
    }
    this.cities = [...new Set(this.allDoctors.map(doctor => doctor.cityName))].map(city => ({
      label: city,
      value: city
    }));
    this.onFilterChange()
  },

  beforeDestroy() {
    // 组件销毁时，移除事件监听
    this.$bus.$off('user-info-updated');
  },

  methods: {
    handleUserInfoUpdate(updatedInfo) {
      this.recordInfoForm = {...this.patientInfoForm, ...updatedInfo};
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
          const result = await addRecord({...this.patientInfoForm, ...this.recordInfoForm})
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

.btns {
  display: flex;
  justify-content: end;
  gap: 30px;
}
</style>
