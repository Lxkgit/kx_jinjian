<template>
  <div class="section-title">
    {{ title }}
  </div>
</template>

<script>
export default {
  props: {
    title: String,
  },
};
</script>

<style scoped lang="scss">
.section-title {
  font-weight: bold;

  margin: 22px 0 12px;

  padding-left: 10px;

  border-left: 3px solid #409eff;
}
</style>
