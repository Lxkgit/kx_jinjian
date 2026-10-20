<template>
  <div class="sub-title-wrapper">
    <span class="title-content">{{ title }}</span>

    <section>
      <slot />
    </section>
  </div>
</template>

<script>
export default ({
  props: {
    title: {
      type: String,
    }
  },
})
</script>

<style lang="scss" scoped>
.sub-title-wrapper {
  margin: 20px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .title-content {
    padding-left: 16px;
    line-height: 1;
    font-size: 16px;
    margin-left: 10px;
    position: relative;
    &::before {
      content: "";
      width: 6px;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      background: #1890ff;
    }
  }
}
</style>