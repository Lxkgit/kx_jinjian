<template>
  <div class="view-all-wrapper" @click="$emit('click')">
    查看全部
  </div>
</template>

<script>
export default ({
})
</script>

<style lang="scss" scoped>
.view-all-wrapper {
  cursor: pointer;
  color: transparent;
  background: linear-gradient(180deg, #FFFFFF 35%, #699BFF 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0px 0px 2px rgba(190, 209, 255, 0.6);
}
</style>