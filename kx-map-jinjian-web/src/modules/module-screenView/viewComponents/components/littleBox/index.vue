<template>
  <div>
    <span class="little-box">
      <span class="little-box-left"></span>
      <span class="little-box-right">{{ title }}</span>
    </span>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "网格信息",
    },
  },
};
</script>

<style scoped lang="scss">
.little-box {
  display: inline-block;
  border-radius: 25px;
  background: rgba(150, 239, 247, 0.5);
  padding: 7px 0;
  &-left {
    display: inline-block;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background: #8bf6ff;
    margin-left: 10px;
  }
  &-right {
    margin: 0 10px 0 15px;
    color: #8bf6ff;
  }
}
</style>