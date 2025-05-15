<template>
  <div style="width: 100%; height: 100%;">
    <el-container style="height: 100%">
      <el-header class="flex-align-center">
        <h2 style="color: white; margin-left: 20px; width: 96%;">中医在线诊断平台</h2>
        <el-dropdown trigger="hover">
          <span class="el-dropdown-link">
              <i style="font-size: 18px; color: white" class="el-icon-user"></i>
              <!-- <el-avatar :size="45" style="margin-top: 7px">
                <img :src="path + user.img" />
              </el-avatar> -->
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-user-solid">{{ userInfo.username }}</el-dropdown-item>
            <hr />
            <el-dropdown-item icon="el-icon-edit" @click.native="userInfoVisible = true">基础信息</el-dropdown-item>
            <el-dropdown-item v-if="userInfo.role !== 2" icon="el-icon-message" @click.native="updateInfoVisible = true">详细信息</el-dropdown-item>
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
        <el-aside width="135px">
          <el-menu :default-active="defaultActive" style="height: 100%" router>
            <el-menu-item
              v-for="item in allowedRoutes"
              :key="item.path"
              :index="item.path"
            >
              <i :class="item.icon"></i>
              <span slot="title">{{ item.name }}</span>
              <!-- <el-badge is-dot v-if="item.name==='医患交流'"></el-badge> -->
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

    <!-- 修改基础信息 -->
    <el-dialog title="修改基础信息" width=65% :visible.sync="userInfoVisible" :close-on-click-modal="false">
      <el-form :model="userInfo" :rules="userInfoRule" ref="userInfoRef" label-width="80px">
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="用户名">
              <el-input v-model="userInfo.username" placeholder="请输入手机号" clearable ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="userInfo.phone" placeholder="请输入手机号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色" prop="role">
              <el-radio-group v-model="userInfo.role" disabled>
                <el-radio :label="0">患者</el-radio>
                <el-radio :label="1">医师</el-radio>
                <el-radio :label="2">管理员</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="userInfo.email" placeholder="请输入邮箱" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="原密码" prop="oldPassword">
              <el-input v-model="userInfo.oldPassword" type="password" placeholder="请输入原密码" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="userInfo.newPassword" type="password" placeholder="请输入新密码" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset('userInfoRef')">取 消</el-button>
        <el-button type="primary" @click="updateBasicInfo">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 修改患者个人信息 -->
    <el-dialog v-if="userInfo.role === 0" title="修改患者信息" width=60% :visible.sync="updateInfoVisible" :close-on-click-modal="false">
      <el-form :model="infoForm" :rules="infoRule" ref="infoFormRef" label-width="80px">
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="姓名" prop="patientName">
              <el-input v-model="infoForm.patientName" placeholder="请输入姓名" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="infoForm.age" placeholder="请输入年龄" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="infoForm.phone" placeholder="请输入手机号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="infoForm.sex">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="既往史">
              <el-input type="textarea" v-model="infoForm.oldHistory" placeholder="请输入既往史（既往是否有类似症状、是否患有慢性疾病，如高血压、糖尿病等）" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="过敏史">
              <el-input type="textarea" v-model="infoForm.allergiesHistory" placeholder="请输入过敏史（对药物、食物或环境因素的过敏情况）" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="生活习惯">
              <el-input type="textarea" v-model="infoForm.habits" placeholder="请输入生活习惯（可以包括饮食、睡眠、情绪、烟酒史等）" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset('infoFormRef')">取 消</el-button>
        <el-button type="primary" @click="updatePatientInfo">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 修改医生个人信息 -->
    <el-dialog v-if="userInfo.role === 1" title="修改医生信息" width=65% :visible.sync="updateInfoVisible" :close-on-click-modal="false">
      <el-form :model="infoForm" :rules="infoRule" ref="infoFormRef" label-width="80px">
        <el-row :gutter="30">
          <!-- 上传头像 -->
          <el-col :span="12">
            <el-form-item label="头像">
              <el-upload
                class="avatar-uploader"
                action="/api/upload/avatar"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <img v-if="infoForm.avatarUrl" :src="infoForm.avatarUrl" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="doctorName">
              <el-input v-model="infoForm.doctorName" placeholder="请输入姓名" clearable></el-input>
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
import { update } from "@/api/user";
import { Encrypt } from "@/utils/secret";
import cities from "@/assets/data/cities.js";
import { updatePatientInfo, getPatientInfo } from "@/api/patient"
import { updateDoctorInfo, getDoctorInfo } from "@/api/doctor"
export default {
  components: {},
  data() {
    return {
      userInfoVisible: false,
      updateInfoVisible: false,
      userInfoRule:{
        oldPassword:[
          { required: true, message: '请输入原密码', trigger: 'blur' },
        ],
        newPassword:[
          { required: true, message: '请输入新密码', trigger: 'blur' },
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
      infoForm: {},
      infoRule: {
        patientName:[
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        doctorName:[
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
    };
  },

  computed: {
    defaultActive() {
      return this.$route.path
    },
    userInfo() {
      return this.$store.state.user.userInfo;
    },
    allowedRoutes() {
      return this.$router.options.routes
        .find(route => route.path === '/home')
        .children.filter(child => {
          return child?.meta?.roles.includes(this.userInfo.role);
        });
    },
  },

  watch: {
    async updateInfoVisible(newVal) {
      if (newVal) {
        if (this.userInfo.role === 0) {
          this.infoForm = await getPatientInfo(this.userInfo.userId)
        } else {
          this.infoForm = await getDoctorInfo(this.userInfo.userId)
        }
        this.infoForm.userId = this.userInfo.userId
      }
    }
  },
  
  async mounted() {
    this.cities = cities
    
  },
  methods: {
    reset(formName){
      this.$refs[formName].resetFields();
      if (formName === 'userInfoRef') {
        this.userInfoVisible=false;
      } else {
        this.updateInfoVisible=false;
      }
    },

    async updateBasicInfo() {
      const oldPassword = Encrypt(this.userInfo.oldPassword)
      const newPassword = Encrypt(this.userInfo.newPassword)
      const res = await update({...this.userInfo, oldPassword, newPassword})
      if (res) {
        this.$message.success("修改成功")
        this.updateBasicInfoVisible = false
      }
    },

    updatePatientInfo() {
      this.$refs.infoFormRef.validate(async (valid) => {
        if (valid) {
          try {
            this.infoForm.userId = this.userInfo.userId
            const res = await updatePatientInfo(this.infoForm)
            if (res) {
              this.$message.success("修改成功")
              this.updateInfoVisible = false;
              this.$bus.$emit('user-info-updated', this.infoForm);
            }
          } catch (error) {
            console.log(error);
            
          }
        }
      })
    },

    updateDoctorInfo() {
      this.$refs.infoFormRef.validate(async (valid) => {
        if (valid) {
          try {
            this.infoForm.userId = this.userInfo.userId
            const res = await updateDoctorInfo(this.infoForm)
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
        this.$store.dispatch('clearUserInfo');
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
    handleAvatarSuccess(res) {
      // 假设返回格式为 { url: 'http://xxx.com/avatar.jpg' }
      this.infoForm.avatarUrl = res.url;
    },
    beforeAvatarUpload(file) {
      const isImage = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        this.$message.error('只能上传 JPG/PNG 格式的图片');
      }
      if (!isLt2M) {
        this.$message.error('图片大小不能超过 2MB');
      }
      return isImage && isLt2M;
    }
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

.aside-container {
  transition: width 0.3s ease; /* 添加宽度变化的过渡效果 */
}

.avatar-uploader {
  width: 100px;
  height: 100px;
  border: 1px dashed #d9d9d9;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  border-radius: 6px;
  overflow: hidden;
}
.avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}


:deep(.el-badge__content.is-fixed.is-dot) {
  top: 6px;
}
</style>
