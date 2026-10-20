<template>
  <div class="dialog-box">
    <div class="left-wrapper">
      <slot name="left"></slot>
    </div>
    <div class="right-wrapper">
      <slot name="right"></slot>
    </div>
  </div>
</template>

<script>
export default ({
})
</script>

<style lang="scss" scoped>
.dialog-box {
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0 10px;
  .left-wrapper,
  .right-wrapper {
    width: 48%;
  }
}
</style>