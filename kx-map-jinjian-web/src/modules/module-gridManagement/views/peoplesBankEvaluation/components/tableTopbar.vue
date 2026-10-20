<template>
  <div class="content-header">
    <p>{{ leftTitle }}</p>
    <el-button :type="elButtonType" @click="$emit('click')">
      {{ buttonText }}
    </el-button>
  </div>
</template>
<script>
export default {
  props: {
    leftTitle: {
      type: String,
      default: "",
    },
    buttonText: {
      type: String,
      default: "",
    },
    elButtonType: {
      type: String,
      default: "primary",
    },
  },
};
</script>
<style lang="scss" scoped>
.content-header {
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  justify-content: space-between;
  p {
    height: 20px;
    font-size: 14px;
    line-height: 20px;
    color: #323548;
    position: relative;
    padding-left: 10px;
    &::after {
      position: absolute;
      content: "";
      width: 2px;
      height: 14px;
      border-radius: 1px;
      background: #5e81f4;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
    }
  }
}
</style>
