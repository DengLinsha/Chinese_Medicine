<template>
  <div>
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules">
      <el-form-item prop="identity">
        <el-input
          text
          placeholder="请输入用户名/手机号/邮箱"
          v-model="ruleForm.identity"
          clearable
          autocomplete="off"
          @keyup.enter.native="login"
        >
          <template #prefix>
            <i class="el-icon-user"></i>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          placeholder="请输入密码"
          v-model="ruleForm.password"
          show-password
          autocomplete="off"
          @keyup.enter.native="login"
        >
          <template #prefix>
            <i class="el-icon-lock"></i>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          class="submit-btn"
          round
          @click="login"
          :loading="loading"
          >登录</el-button
        >
      </el-form-item>
    </el-form>
    <div class="extra-warp">
      <div class="t-cursor" @click="$emit('switchToForget')">忘记密码?</div>
      <div class="t-cursor" @click="$emit('switchToRegister')">立即注册</div>
    </div>
  </div>
</template>

<script>
import { login } from "@/api/user";
import { Encrypt } from "@/utils/secret";
export default {
  components: {},
  data() {
    return {
      ruleFormRef: null,
      ruleForm: {
        identity: "",
        password: "",
      },
      rules: {
        identity: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
      loading: false
    };
  },
  mounted() {},
  methods: {
    login() {
      this.$refs.ruleFormRef.validate((valid) => {
        if (valid) {
          try {
            this.loading = true;
            // 对密码进行加密
            const password = Encrypt(this.ruleForm.password);
            login({...this.ruleForm, password}).then(res => {
              if (res) {
                res.password = Encrypt(res.password)
                this.$store.dispatch('setUserInfo', res);
                // 登录成功跳转到主页
                this.$message.success("欢迎回来")
                if (this.$store.state.user.userInfo.role === 0) {
                  this.$router.push({path: '/home'})
                } else if (this.$store.state.user.userInfo.role === 1) {
                  this.$router.push({path: '/home/diagnosisList'})
                } else {
                  this.$router.push({path: '/admin/patients'})
                }
                
              }
            })
          } finally {
            this.loading = false;
          }
        }
      })
    },
  },
};
</script>
<style lang="scss" scoped>
.submit-btn {
  width: 100%;
  letter-spacing: 2px;
  font-weight: 300;
  margin-top: 10px;
}

.extra-warp {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
  font-size: 14px;
  color: #686b70;
}

:deep(.el-input--prefix .el-input__inner) {
  padding-left: 25px;
}

.el-form-item {
  margin-bottom: 16px;
}

</style>
