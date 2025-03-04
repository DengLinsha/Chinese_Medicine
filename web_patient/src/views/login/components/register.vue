<template>
  <div>
    <el-form
      ref="ruleFormRef"
      :model="ruleForm"
      :rules="rules"
      label-width="80px"
    >
      <el-form-item label="用户名" prop="username">
        <el-input
          placeholder="请输入用户名"
          v-model="ruleForm.username"
          clearable
          autocomplete="off"
        >
        </el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          type="password"
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
      <el-form-item label="手机号" prop="phone">
        <el-input
          placeholder="请输入手机号"
          v-model="ruleForm.phone"
        ></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input placeholder="请输入邮箱" v-model="ruleForm.email"></el-input>
      </el-form-item>
      <div class="extra-warp">
        <el-button class="submit-btn" @click="cancel">取消</el-button>
        <el-button type="primary" class="submit-btn" @click="register" :loading="loading"
          >注册</el-button
        >
      </div>
    </el-form>
  </div>
</template>

<script>
import { register } from "@/api/user";
import { Encrypt } from "@/utils/secret";
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
      ruleFormRef: null,
      ruleForm: {
        username: "",
        password: "",
        checkPass: "",
        phone: "",
        email: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [
            { required: true, message: "请输入密码", trigger: "blur" },
            { min: 6, message: '密码不少于6个字符', trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass, required: true, trigger: "blur" },
        ],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          {
            pattern: /^1[3-9]\d{9}$/,
            message: "请输入有效的电话号码！",
            trigger: ["blur", "change"],
          },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
      },
      loading: false
    };
  },
  mounted() {},
  methods: {
    cancel() {
      this.$refs.ruleFormRef.resetFields();
      this.$emit("switchToLogin");
    },
    register() {
      this.$refs.ruleFormRef.validate(async (valid) => {
        if (valid) {
          try {
            this.loading = true;
            // 对密码进行加密
            const password = Encrypt(this.ruleForm.password);
            delete this.ruleForm.checkPass;
            const res = await register({...this.ruleForm, password})
            if (res) {
              // 注册成功跳转到登录页面
              this.$emit("switchToLogin");
            }
          } finally {
            this.loading = false;
          }
        }
      })
    },
  },
};
</script>
<style scoped lang="scss">
.submit-btn {
  width: 25%;
  letter-spacing: 2px;
  padding: 10px;
  margin-top: 15px;
}
</style>
