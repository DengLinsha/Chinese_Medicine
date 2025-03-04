<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="全部病历" name="all"><all /></el-tab-pane>
      <el-tab-pane label="已完成病历" name="finished"><finished /></el-tab-pane>
      <el-tab-pane label="疑难杂症" name="incurable"><incurable /></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import all from "./components/all";
import finished from "./components/finished";
import incurable from "./components/incurable";
export default {
  components: {
    all,
    finished,
    incurable,
  },
  data() {
    return {
      activeName: "all",
    };
  },
  mounted() {},
  methods: {},
};
</script>
<style scoped lang="scss"></style>
