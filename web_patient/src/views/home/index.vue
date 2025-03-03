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
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

    <!-- 修改密码 -->
    <el-dialog title="修改密码" width=30% :visible.sync="updatePasswordVisible" style="text-align:center;">
      <el-form :model="passwordForm" :rules="passwordRule" ref="passwordForm">
        <el-form-item label="原密码" prop="oldpassword">
          <el-input v-model="passwordForm.oldpassword" placeholder="请输入原密码" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newpassword">
          <el-input v-model="passwordForm.newpassword" placeholder="请输入新密码" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset('passwordForm')">取 消</el-button>
        <el-button type="primary" @click="updatePassword('passwordForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { updatePassword } from "@/api/user";
import { Encrypt } from "@/utils/secret";
export default {
  components: {},
  data() {
    return {
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      width: "200px",
      isCollapse: false,
      updatePasswordVisible: false,
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
    };
  },
  mounted() {},
  methods: {
    isOpen() {
      if (this.isCollapse) {
        this.width = "200px";
      } else {
        this.width = "65px";
      }
      this.isCollapse = !this.isCollapse;
    },

    reset(){
      this.$refs.passwordForm.resetFields();
      this.updatePasswordVisible=false;
    },

    async updatePassword() {
      const password = Encrypt(this.passwordForm.newpassword);
      await updatePassword({identity: this.userInfo.username, password});
      this.reset();
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
</style>
