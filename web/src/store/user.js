const userStore = {
  state: {
    userInfo: null,
  },
  mutations: {
    SET_USERINFO(state, userInfo) {
      state.userInfo = userInfo; // 设置用户信息
    },
    CLEAR_USERINFO(state) {
      state.userInfo = null; // 清除用户信息
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
