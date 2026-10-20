<template>
  <div :class="{'margin-bottom': marginBottom === 'margin-bottom'}" class="pop-title-item">
    <div class="pop-title-item-bar" :style="{backgroundColor:color}"></div>
    <div class="pop-title-item-text" :style="{color,fontSize}">{{text}}</div>
    <slot></slot>
  </div>
</template>

<script>
export default ({
  name: 'popTitle',
  props: {
    color: {
      type: String,
      default: '#EA931C'
    },
    text: {
      type: String,
      default: '资产业务'
    },
    fontSize: {
      type: String,
      default: '16px'
    },
    marginBottom: {
      type: String,
      default: 'none'
    }
  },
  data () {
    return {
    }
  }
})
</script>

<style lang="scss" scoped>
.pop-title-item{
  display: flex;
  &-bar{
    margin-right: 8px;
    width: 5px;
    height: 16px;
    border-radius: 10px;
  }
  &-text{
    font-size: 16px;
  }
}
.margin-bottom{
  margin-bottom: 15px;
}
</style>