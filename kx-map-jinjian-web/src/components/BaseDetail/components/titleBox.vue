<template>
  <div class="box-wrapper">
    <p class="box-title" :style="{'--color':color}">{{ title }}</p>
    <slot></slot>
  </div>
</template>

<script>
export default ({
  props: {
    title: {
      type: String,
    },
    color: {
      type: String,
      default: '#DC963C',
    }
  },
})
</script>

<style lang="scss" scoped>
.box-wrapper {
  .box-title {
    padding-left: 14px;
    color: var(--color);
    font-weight: bold;
    font-size: 18px;
    position: relative;
    &::before {
      content: "";
      width: 5px;
      height: 100%;
      position: absolute;
      top: 50%;
      left: 0;
      transform: translateY(-50%);
      background: var(--color);
      border-radius: 2px;
    }
  }
}
</style>