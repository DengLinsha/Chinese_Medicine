<template>
  <div style="width: 100%; height: 100%;">
    <el-container style="height: 100%">
      <el-header class="flex-align-center">
        <el-button type="text" @click="isOpen"
          ><i
            style="font-size: 18px; color: white"
            class="el-icon-s-operation"
          ></i
        ></el-button>
        <h2 style="color: white; margin-left: 20px; width: 96%;">中医在线诊断平台</h2>
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
              <i style="font-size: 18px; color: white" class="el-icon-user"></i>
              <!-- <el-avatar :size="45" style="margin-top: 7px">
                <img :src="path + user.img" />
              </el-avatar> -->
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-user-solid">{{ userInfo.username }}</el-dropdown-item>
            <hr />
            <el-dropdown-item icon="el-icon-key" @click.native="updatePasswordVisible = true">修改密码</el-dropdown-item>
            <el-dropdown-item icon="el-icon-message" @click.native="updateInfoVisible = true">编辑信息</el-dropdown-item>
            <!-- <el-dropdown-item
              icon="el-icon-upload"
              @click.native="uploadVisible = true"
              >修改头像</el-dropdown-item
            > -->
            <el-dropdown-item
              icon="el-icon-switch-button"
              @click.native="logout"
              >退出系统</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container>
        <el-aside :width="width">
          <el-menu style="height: 100%" :collapse="isCollapse" router>
            <el-menu-item index="/addRecord">
              <i class="el-icon-service"></i>
              <span slot="title">病症诊断</span>
            </el-menu-item>
            <el-menu-item index="/allRecords">
              <i class="el-icon-document"></i>
              <span slot="title">咨询列表</span>
            </el-menu-item>
            <el-menu-item index="/docList">
              <i class="el-icon-user"></i>
              <span slot="title">医师列表</span>
            </el-menu-item>
            <!-- 医生 -->
            <el-menu-item index="/diagnosisList">
              <i class="el-icon-user"></i>
              <span slot="title">诊断列表</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

    <!-- 修改密码 -->
    <el-dialog title="修改密码" width=30% :visible.sync="updatePasswordVisible" style="text-align:center;">
      <el-form :model="passwordForm" :rules="passwordRule" ref="passwordFormRef">
        <el-form-item label="原密码" prop="oldpassword">
          <el-input v-model="passwordForm.oldpassword" placeholder="请输入原密码" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newpassword">
          <el-input v-model="passwordForm.newpassword" placeholder="请输入新密码" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset('passwordFormRef')">取 消</el-button>
        <el-button type="primary" @click="updatePassword">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 修改患者个人信息 -->
    <el-dialog v-if="userInfo.role === 0" title="修改个人信息" width=65% :visible.sync="updateInfoVisible">
      <el-form :model="patientInfoForm" :rules="patientInfoRule" ref="patientInfoFormRef" label-width="80px">
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="用户名">
              <el-input v-model="patientInfoForm.username" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="patientInfoForm.name" placeholder="请输入姓名" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色" prop="role">
              <el-radio-group v-model="patientInfoForm.role" disabled>
                <el-radio :label="0">患者</el-radio>
                <el-radio :label="1">医师</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-switch 
                v-model="patientInfoForm.sex"
                active-color="#a8824a"
                inactive-color=""
                active-value="0"
                active-text="男"
                inactive-value="1"
                inactive-text="女">
              </el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="patientInfoForm.age" placeholder="请输入年龄" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="既往史">
              <el-input type="textarea" v-model="patientInfoForm.oldHistory" placeholder="请输入既往史（既往是否有类似症状、是否患有慢性疾病，如高血压、糖尿病等）" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="过敏史">
              <el-input type="textarea" v-model="patientInfoForm.allergiesHistory" placeholder="请输入过敏史（对药物、食物或环境因素的过敏情况）" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生活习惯">
              <el-input type="textarea" v-model="patientInfoForm.habits" placeholder="请输入过敏史（可以包括饮食、睡眠、情绪、烟酒史等）" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset('patientInfoFormRef')">取 消</el-button>
        <el-button type="primary" @click="updatePatientInfo">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 修改医生个人信息 -->
    <el-dialog v-if="userInfo.role === 1" title="修改个人信息" width=65% :visible.sync="updateInfoVisible">
      <el-form :model="doctorInfoForm" :rules="doctorInfoRule" ref="doctorInfoFormRef" label-width="80px">
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="用户名">
              <el-input v-model="doctorInfoForm.username" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="doctorInfoForm.name" placeholder="请输入姓名" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色" prop="role">
              <el-radio-group v-model="doctorInfoForm.role" disabled>
                <el-radio :label="0">患者</el-radio>
                <el-radio :label="1">医师</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在地区">
              <el-select
                v-model="doctorInfoForm.cityName"
                clearable
                placeholder="请选择地区"
                style="width: 100%"
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
          <el-col :span="12">
            <el-form-item label="所在医院">
              <el-input v-model="doctorInfoForm.hospitalName" placeholder="请输入所在医院" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在科室">
              <el-input v-model="doctorInfoForm.departmentName" placeholder="请输入所在科室" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="个人简介">
              <el-input type="textarea" v-model="doctorInfoForm.introduction" placeholder="请输入个人简介" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset('doctorInfoFormRef')">取 消</el-button>
        <el-button type="primary" @click="updateDoctorInfo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { updatePassword } from "@/api/user";
import { Encrypt } from "@/utils/secret";
import cities from "@/assets/data/cities.js";
import { updatePatientInfo, getPatientInfo } from "@/api/patient"
import { updateDoctorInfo, getDoctorInfo } from "@/api/doctor"
export default {
  components: {},
  data() {
    return {
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      width: "200px",
      isCollapse: false,
      updatePasswordVisible: false,
      updateInfoVisible: false,
      passwordForm:{
        oldpassword:'',
        newpassword:''
      },
      passwordRule:{
        oldpassword:[
          { required: true, message: '请输入原密码', trigger: 'blur' },
        ],
        newpassword:[
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ]
      },
      patientInfoForm: {},
      patientInfoRule: {
        name:[
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        sex:[
          { required: true, message: '请选择性别', trigger: 'blur' },
        ],
        age:[
          {required: true, message: '请输入年龄', trigger: 'blur'}
        ],
      },
      cities: [],
      doctorInfoForm: {},
      doctorInfoRule: {
        name:[
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
      }
    };
  },

  watch: {
  updateInfoVisible(newVal) {
    if (newVal) {
      if (this.userInfo.role === 0) {
        this.getPatientInfo();
      } else {
        this.getDoctorInfo();
      }
      
    }
  }
},
  mounted() {
    this.cities = cities
  },
  methods: {
    isOpen() {
      if (this.isCollapse) {
        this.width = "200px";
      } else {
        this.width = "65px";
      }
      this.isCollapse = !this.isCollapse;
    },

    reset(formName){
      this.$refs[formName].resetFields();
      if (formName === 'passwordFormRef') {
        this.updatePasswordVisible=false;
      } else {
        this.updateInfoVisible=false;
      }
    },

    async updatePassword() {
      const password = Encrypt(this.passwordForm.newpassword);
      await updatePassword({identity: this.userInfo.username, password});
      this.reset('passwordFormRef');
    },

    async getPatientInfo() {
      this.patientInfoForm = await getPatientInfo(this.userInfo.userId)
      this.patientInfoForm.userId = this.userInfo.userId
      this.patientInfoForm.username = this.userInfo.username
      this.patientInfoForm.role = this.userInfo.role
    },

    async getDoctorInfo() {
      this.doctorInfoForm = await getDoctorInfo(this.userInfo.userId)
      this.doctorInfoForm.userId = this.userInfo.userId
      this.doctorInfoForm.username = this.userInfo.username
      this.doctorInfoForm.role = this.userInfo.role
    },

    updatePatientInfo() {
      this.$refs.patientInfoFormRef.validate(async (valid) => {
        if (valid) {
          try {
            const res = await updatePatientInfo(this.patientInfoForm)
            if (res) {
              this.$message.success("修改成功")
              this.updateInfoVisible = false;
            }
          } catch (error) {
            console.log(error);
            
          }
        }
      })
    },

    updateDoctorInfo() {
      this.$refs.doctorInfoFormRef.validate(async (valid) => {
        if (valid) {
          try {
            const res = await updateDoctorInfo(this.doctorInfoForm)
            if (res) {
              this.$message.success("修改成功")
              this.updateInfoVisible = false;
            }
          } catch (error) {
            console.log(error);
          }
        }
      })
    },

    logout() {
      // 弹框二次确认
      this.$confirm('确定退出登录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 清空localStorage
        localStorage.clear();
        this.$router.push({path: '/'})
        this.$message({
          type: 'success',
          message: '退出登录成功!'
        });
      }).catch(() => {
        // this.$message({
        //   type: 'info',
        //   message: '已取消退出登录'
        // });          
      });
    },
  },
};
</script>
<style scoped lang="scss">
.el-header {
  width: 100%;
  background-color: var(--base-color);
}
.menu-container {
  height: 100%;
  width: 200px;
}


</style>
