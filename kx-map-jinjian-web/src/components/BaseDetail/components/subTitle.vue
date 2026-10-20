<template>
  <div class="sub-title">
    <img :src="src">
    <span>{{ title }}</span>
  </div>
</template>

<script>
export default ({
  props: {
    src: {
      type: String,
    },
    title: {
      type: String
    }
  }
})
</script>

<style lang="scss" scoped>
.sub-title {
  display: flex;
  align-items: center;
  margin-top: 18px;
  img {
    width: 18px;
    height: 20px;
    margin-right: 6px;
  }
  span {
    font-size: 22px;
    font-weight: bold;
  }
}
</style>