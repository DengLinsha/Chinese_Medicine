<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="全部病历" name="all"><all activeName="all"/></el-tab-pane>
      <el-tab-pane label="未诊断病历" name="noDiagnosis"><all activeName="noDiagnosis"/></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import all from "./components/all";
export default {
  components: { all },
  data() {
    return {
      activeName: "all",
    };
  },
  mounted() {},
  methods: {},
};
</script>
<style scoped lang="scss">

</style>
