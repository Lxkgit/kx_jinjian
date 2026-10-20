<template>
  <div class="title-box">
    <h2>{{ title }}</h2>
    <el-button type="primary" @click="viewDetail">条件切换</el-button>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "",
    },
  },
  methods: {
    viewDetail() {
      this.$emit("viewDetail");
    },
  },
};
</script>

<style scoped lang="scss">
.title-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  h2 {
    font-size: 18px;
  }
}
</style>