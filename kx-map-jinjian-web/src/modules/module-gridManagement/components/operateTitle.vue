<template>
  <div class="operate-title">
    {{ title }}
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
.operate-title {
  width: 100%;
  font-size: 18px;
  line-height: 50px;
  text-align: center;
  background: #e7eaf6;
}
</style>