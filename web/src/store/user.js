const userStore = {
  state: {
    userInfo: JSON.parse(localStorage.getItem("userInfo")) || null,
  },
  mutations: {
    SET_USERINFO(state, userInfo) {
      state.userInfo = userInfo; // 设置用户信息
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
    },
    CLEAR_USERINFO(state) {
      state.userInfo = null; // 清除用户信息
      localStorage.removeItem("userInfo");
    },
  },
  actions: {
    setUserInfo({ commit }, userInfo) {
      commit("SET_USERINFO", userInfo); // 调用 mutation 设置用户信息
    },
    clearUserInfo({ commit }) {
      commit("CLEAR_USERINFO"); // 调用 mutation 清除用户信息
    },
  },
};

export default userStore;
