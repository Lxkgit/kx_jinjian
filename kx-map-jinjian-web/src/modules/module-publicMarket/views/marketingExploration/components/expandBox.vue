<template>
  <div class="expand-box-wrapper">
    <slot></slot>
  </div>
</template>

<script>
export default ({
})
</script>

<style lang="scss" scoped>
.expand-box-wrapper {
  width: 100%;
  position: relative;
  max-height: 73vh;
  height: 100%;
  margin-top: 10px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  background: #fff;
  overflow: auto;
  overflow-x: hidden;
}
</style>