<template>
  <div>
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="身份验证"></el-step>
      <el-step title="密码重置"></el-step>
      <el-step title="重置完成"></el-step>
    </el-steps>
    <!-- 身份验证模块 -->
    <div v-if="active === 0" class="main-container">
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="账号名" prop="userName">
          <el-input
            placeholder="请输入用户名/手机号/邮箱"
            v-model="ruleForm.userName"
            clearable
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" class="submit-btn" @click="next"
        >下一步</el-button
      >
    </div>
    <!-- 密码重置模块 -->
    <div v-if="active === 1" class="main-container">
      <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="80px">
        <el-form-item label="密码" prop="password">
          <el-input
            type="password"
            show-password
            placeholder="请输入密码"
            v-model="ruleForm.password"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            type="password"
            placeholder="请确认密码"
            v-model="ruleForm.checkPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" class="submit-btn" @click="next"
        >下一步</el-button
      >
    </div>
    <!-- 重置成功 -->
    <div v-if="active === 2" class="main-container">
      <div style="height: 115px;text-align: center;padding: 30px 0;">(*^_^*)密码已重置成功，请重新登录！</div>
      <el-button type="primary" class="submit-btn" @click="$emit('switchToLogin')"
        >重新登录</el-button
      >
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      active: 0,
      ruleFormRef: null,
      ruleForm: {
        userName: "",
        password: "",
        checkPass: "",
      },
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, message: "密码不少于6个字符", trigger: "blur" },
        ],
        checkPass: [
          { validator: validatePass, required: true, trigger: "blur" },
        ],
      },
    };
  },
  mounted() {},
  methods: {
    next() {
      if (this.active++ > 2) this.active = 0;
      // 根据用户名查找用户
    },
  },
};
</script>
<style scoped lang="scss">
.main-container {
    margin-top: 15px;
    height: 180px;
}
:deep(.el-step__title) {
    font-size: 14px;
}
</style>
