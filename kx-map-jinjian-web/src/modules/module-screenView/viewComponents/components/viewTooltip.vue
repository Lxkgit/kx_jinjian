<template>
  <div class="view-tooltip-wrapper">
    <div class="view-right" @click="$emit('click')">查看更多</div>
  </div>
</template>

<script>
export default ({
})
</script>

<style lang="scss" scoped>
.view-tooltip-wrapper {
  height: 25px;
  width: 100%;
  position: relative;
  margin: 10px 0 5px;
  .view-right {
    width: 60px;
    height: 21px;
    font-size: 14px;
    color: #8bf6ff;
    border-bottom: 2px solid #8bf6ff;
    text-align: center;
    position: absolute;
    right: 0;
    cursor: pointer;
  }
}
</style>