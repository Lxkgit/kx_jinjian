<template>
  <div>
    <div class="little-box">
      <span class="little-box-left"></span>
      <span class="little-box-right">{{ title }}</span>
    </div>
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
  height: 20px;
  font-size: 14px;
  margin: 10px 0;

  &-left {
    display: inline-block;
    width: 8px;
    height: 8px;
    background: url('../../assets/rectIcon.png') no-repeat;
    background-size: 100%;
  }

  &-right {
    margin: 0 4px 0 6px;
    color: #fff;
  }
}
</style>