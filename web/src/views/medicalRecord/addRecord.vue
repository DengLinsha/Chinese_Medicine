<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <h2>提交病历</h2>
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
              <el-input v-model="patientInfoForm.patientName" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄">
              <el-input type="number" v-model="patientInfoForm.age" placeholder="请输入年龄" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别">
              <el-switch 
                v-model="userInfo.sex"
                active-color="#a8824a"
                inactive-color=""
                active-value="0"
                active-text="男"
                inactive-value="1"
                inactive-text="女"
                disabled
              >
              </el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号">
              <el-input v-model="userInfo.phone" placeholder="请输入手机号" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="既往史">
              <el-input type="textarea" v-model="patientInfoForm.oldHistory" placeholder="请输入既往史（既往是否有类似症状、是否患有慢性疾病，如高血压、糖尿病等）" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="过敏史">
              <el-input type="textarea" v-model="patientInfoForm.allergiesHistory" placeholder="请输入过敏史（对药物、食物或环境因素的过敏情况）" />
            </el-form-item>
            </el-col>
          <el-col :span="24">
            <el-form-item label="生活习惯">
              <el-input type="textarea" v-model="patientInfoForm.habits" placeholder="请输入过敏史（可以包括饮食、睡眠、情绪、烟酒史等）" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="doctorId" label="选择医生">
              <el-select
                v-model="cityName"
                clearable
                placeholder="请选择地区"
                style="margin-right: 10px"
                @change="updateHospitalList"
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
                @change="updateDepartmentList"
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
                @change="updateDoctorList"
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
              <el-input type="textarea" v-model="recordInfoForm.description" placeholder="包括现在的症状以及既往史" />
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
        <el-button type="primary" @click="addRecord">提交病历</el-button>
        <el-button @click="resetForm">取消</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getPatientInfo } from "@/api/patient";
import { getDoctorList } from "@/api/doctor";
import { addRecord } from "@/api/record";
export default {
  components: {},
  data() {
    return {
      patientInfoForm: {},
      recordInfoForm: {
        doctorId: '',
        description: '',
      },
      addRecordRules: {
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
    this.$bus.$on('user-info-updated', async () => {
      this.patientInfoForm = await getPatientInfo(this.userInfo.userId); // 当用户信息更新时，重新加载数据
    });
    this.patientInfoForm = await getPatientInfo(this.userInfo.userId);
    this.allDoctors = await getDoctorList();
    this.cities = [...new Set(this.allDoctors.map(doctor => doctor.cityName))].map(city => ({
      label: city,
      value: city
    }));
    this.updateHospitalList()
  },

  beforeDestroy() {
    // 组件销毁时，移除事件监听
    this.$bus.$off('user-info-updated');
  },

  methods: {
    addRecord() {
      this.$refs.recordInfoFormRef.validate(async (valid) => {
        if (valid) {
          const result = await addRecord({...this.userInfo, ...this.patientInfoForm, ...this.recordInfoForm})
          if (result) {
            this.$message.success("病历创建成功");
          }
          
        }
      });
    },
    resetForm() {
      this.$refs.recordInfoFormRef.resetFields();
    },

    updateHospitalList() {
      if (!this.cityName) {
        this.hospitalList = [];
        this.hospitalName = '';
        this.updateDepartmentList();
        return;
      }
      const hospitals = this.allDoctors
        .filter(doctor => doctor.cityName === this.cityName)
        .map(doctor => doctor.hospitalName);
      this.hospitalList = [...new Set(hospitals)].map(hospital => ({
        label: hospital,
        value: hospital
      }));
      this.hospitalName = ''; // 重置医院选择
      this.updateDepartmentList();
    },
    updateDepartmentList() {
      if (!this.hospitalName) {
        this.departmentList = [];
        this.departmentName = '';
        this.updateDoctorList();
        return;
      }
      const departments = this.allDoctors
        .filter(doctor => doctor.hospitalName === this.hospitalName)
        .map(doctor => doctor.departmentName);
      this.departmentList = [...new Set(departments)].map(department => ({
        label: department,
        value: department
      }));
      this.departmentName = ''; // 重置科室选择
      this.updateDoctorList();
    },
    updateDoctorList() {
      if (!this.departmentName) {
        this.docList = [];
        this.recordInfoForm.doctorId = '';
        return;
      }
      this.docList = this.allDoctors
        .filter(doctor => doctor.departmentName === this.departmentName)
        .map(doctor => ({
          doctorName: doctor.doctorName,
          doctorId: doctor.doctorId
        }));
      this.recordInfoForm.doctorId = ''; // 重置医生选择
    },
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
