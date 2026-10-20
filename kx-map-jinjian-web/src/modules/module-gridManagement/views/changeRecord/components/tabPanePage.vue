<template>
  <div class="tab-pane-page-wrapper">
    <!-- 过滤器 -->
    <slot name="filter"></slot>
    <!-- 按钮 -->
    <slot name="btn"></slot>
    <slot></slot>
  </div>
</template>

<script>
export default ({
  name: 'TabPanePage'
})
</script>

<style lang="scss" scoped>
.tab-pane-page-wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  height: 100%;
}
</style>