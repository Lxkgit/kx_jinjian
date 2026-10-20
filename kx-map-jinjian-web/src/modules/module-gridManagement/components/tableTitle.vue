<template>
  <div class="title-main">
    <div class="title-left">
      <div class="line-left" style="background: #5e81f4 !important;"></div>
      <div class="title" :style="{'--fontSize':fontSize}">{{ title }}</div>
      <slot name="left"></slot>
    </div>
    <div class="title-right">
        <slot name="right"></slot>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: "标题",
    },
    fontSize:{
      type:String,
      default: "14px"
    }
  },
};
</script>
<style lang="scss" scoped>
.title-main {
  width: 100%;
  height: 36px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
}
.title-left {
  display: flex;
  align-items: center;
  .line-left {
    width: 2px;
    height: 14px;
    border-radius: 1px;
    margin-right: 8px;
  }
  .title {
    font-size: var(--fontSize);
    font-weight: 500;
    line-height: 20px;
    letter-spacing: 0.12px;
    color: #323548;
  }
}
.title-right{
  display: flex;
  align-items: center;
}
</style>
