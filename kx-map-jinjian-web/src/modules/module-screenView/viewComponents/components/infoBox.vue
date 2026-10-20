<template>
  <div class="info-box-wrapper">
    <slot></slot>
  </div>
</template>

<script>
export default {
  props: {},
};
</script>

<style lang="scss" scoped>
.info-box-wrapper {
  width: 100%;
  height: 100%;
  border: 1px solid;
  background: linear-gradient(
    0deg,
    rgba(53, 78, 88, 0.5),
    rgba(35, 55, 63, 0.08)
  );
  border-image: linear-gradient(180deg, #66cef5, #66cef522, #7ef4ff) 1 1;
}
</style>