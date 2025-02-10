<template>
  <div>
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules">
      <el-form-item prop="userName">
        <el-input
          text
          placeholder="请输入用户名/手机号/邮箱"
          v-model="ruleForm.userName"
          clearable
          autocomplete="off"
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
        >
          <template #prefix>
            <i class="el-icon-lock"></i>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="submit-btn" round @click="login"
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
export default {
  components: {},
  data() {
    return {
      ruleFormRef: null,
      ruleForm: {
        userName: "",
        password: "",
      },
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  mounted() {},
  methods: {
    forgetPassword() {
      console.log("忘记密码");
    },
    register() {},
    login() {},
  },
};
</script>
<style lang="scss">
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

.el-input.is-active .el-input__inner,
.el-input__inner:focus {
  border-color: var(--base-color);
}
.el-button--primary {
  background-color: var(--base-color);
  border: var(--base-color);
}

.el-button {
  &:hover {
    color: #fff;
    background-color: #dbaa60;
    border-color: #dbaa60;
  }
  &:active {
    color: #fff;
    background-color: #dbaa60;
    border-color: #dbaa60;
  }
  &:focus {
    color: #fff;
    background-color: #dbaa60;
    border-color: #dbaa60;
  }
}
</style>
