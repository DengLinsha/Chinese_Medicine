<template>
  <div class="main-container">
    <div style="width: 25%; height: 100%"></div>
    <div class="login-container">
      <div class="login-warp-main">
        <div class="login-warp-main-title">
          中医在线问诊与个性化药方推荐系统
        </div>
        <div class="login-warp-main-form">
          <login
            v-if="isLogin"
            @switchToRegister="
              isLogin = false;
              isForget = false;
            "
            @switchToForget="
              isLogin = false;
              isForget = true;
            "
          />
          <register
            v-if="!isLogin && !isForget"
            @switchToLogin="isLogin = true"
          />
          <forget
            v-if="isForget"
            @switchToLogin="
              isLogin = true;
              isForget = false;
            "
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import login from "./components/login.vue";
import register from "./components/register.vue";
import forget from "./components/forget.vue";
export default {
  components: {
    login,
    register,
    forget,
  },
  data() {
    return {
      isLogin: true,
      isForget: false,
    };
  },
  mounted() {
  },
  methods: {},
};
</script>
<style scoped lang="scss">
.main-container {
  height: 100%;
  width: 100%;
  background: url("@/assets/images/loginBg.jpg") no-repeat;
  background-size: cover;
  display: flex;
}

.login-container {
  width: 470px;
  padding-bottom: 20px;
  border: 1px solid var(--base-color);
  margin: auto;
  position: relative;
  overflow: hidden;
  .login-warp-main {
    display: flex;
    flex-direction: column;
    height: 100%;
    .login-warp-main-title {
      height: 100px;
      line-height: 100px;
      font-size: 26px;
      font-weight: 800;
      text-align: center;
      color: var(--base-color);
    }
    .login-warp-main-form {
      padding: 0 50px;
    }
  }
}
</style>
