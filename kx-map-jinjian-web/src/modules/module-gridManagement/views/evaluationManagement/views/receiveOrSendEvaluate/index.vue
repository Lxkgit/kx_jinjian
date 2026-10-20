<template>
  <div>
    <PageContent>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="我收到的" name="1"></el-tab-pane>
        <el-tab-pane label="我下发的" name="0"></el-tab-pane>
      </el-tabs>
      <component :is="componentId" :activeName="activeName"></component>
    </PageContent>
  </div>
</template>

<script>
import receiveEvaluate from "./cell/receiveEvaluate";
import sendEvaluate from "./cell/sendEvaluate";
const TYPE = {
  1: receiveEvaluate,
  0: sendEvaluate,
};
export default {
  data() {
    return {
      activeName: "1",
      componentId: receiveEvaluate,
    };
  },
  methods: {
    handleClick() {
      this.componentId = TYPE[this.activeName];
    },
  },
};
</script>

<style lang="scss" scoped>
</style>