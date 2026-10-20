<template>
  <div class="sub-title-wrapper">
    {{ title }}
  </div>
</template>

<script>
export default ({
  name: 'SubTitle',
  props: {
    title: {
      type: String,
    }
  }
})
</script>

<style lang="scss" scoped>
.sub-title-wrapper {
  padding-left: 18px;
  font-size: 16px;
  position: relative;
  margin-bottom: 20px;
  &::before {
    content: "";
    background: #5e81f4;
    border-radius: 3px;
    width: 5px;
    height: 100%;
    position: absolute;
    left: 0;
    top: 0;
  }
}
</style>