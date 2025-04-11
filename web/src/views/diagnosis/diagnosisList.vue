<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="全部病历" name="all">
      </el-tab-pane>
      <el-tab-pane name="noDiagnosis">
        <span slot="label">
          <el-badge is-dot>未诊断病历</el-badge>
        </span>
      </el-tab-pane>
      <all :activeName="activeName"/>
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
:deep(.el-badge__content.is-fixed.is-dot) {
  top: 6px;
}
</style>
