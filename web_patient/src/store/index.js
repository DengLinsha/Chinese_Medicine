import Vue from 'vue'
import Vuex from 'vuex'
import userStore from './user'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    user: userStore
  }
})
