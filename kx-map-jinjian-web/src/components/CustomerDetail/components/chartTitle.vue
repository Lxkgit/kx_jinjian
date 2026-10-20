<template>
  <div class="chart-title">
    <div class="chart-title-circle"></div>
    <div class="chart-title-text" :style="{color,fontSize}">{{text}}</div>
  </div>
</template>

<script>
export default ({
  name: 'chartTitle',
  props: {
    text: {
      type: String,
      default: '财富产品偏好占比'
    },
    fontSize: {
      type: String,
      default: '14px'
    },
    color: {
      type: String,
      default: '#212121'
    }
  },
  data () {
    return {
    }
  }
})
</script>

<style lang="scss" scoped>
.chart-title{
  display: flex;
  align-items: center;
  &-circle{
    border-radius: 50%;
    margin-right: 5px;
    width: 7px;
    height: 7px;
    background-color: #2DA5EE;
  }
  &-text{
    font-size: 14px;
    columns: #212121;
  }
}
</style>