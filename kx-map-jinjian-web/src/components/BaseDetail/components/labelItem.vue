<template>
  <span class="label-item">
    {{ labelName }}
  </span>
</template>

<script>
export default ({
  props: {
    labelName: {
      type: String
    }
  }
})
</script>

<style lang="scss" scoped>
.label-item {
  display: inline-block;
  margin-right: 10px;
  margin-bottom: 10px;
  background: rgba($color: #4a92ed, $alpha: 0.6);
  border-radius: 16px;
  line-height: 33px;
  font-size: 16px;
  padding: 0 20px;
  color: #fff;
}
</style>