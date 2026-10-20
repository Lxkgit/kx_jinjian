<template>
  <div class="dialog-wrapper">
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
.dialog-wrapper {
  display: flex;
  justify-content: space-between;
  .left-wrapper {
    height: auto;
    min-width: 400px;
    max-width: 490px;
  }
  .right-wrapper {
    width: 400px;
    height: auto;
  }
}
</style>