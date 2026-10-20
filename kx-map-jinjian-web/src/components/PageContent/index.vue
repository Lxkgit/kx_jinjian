<template>
  <div class="app-container">
    <!-- 过滤器 -->
    <slot name="filter"></slot>
    <!-- 按钮 -->
    <slot name="btn"></slot>
    <slot></slot>
  </div>
</template>

<script>
export default ({
  name: 'PageContent'
})
</script>

<style lang="scss" scoped>
.app-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}
</style>