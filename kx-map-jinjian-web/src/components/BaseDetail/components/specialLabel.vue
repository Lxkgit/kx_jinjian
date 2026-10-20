<template>
  <div class="special-label-wrapper" :class="{none:!labelItem.flag}">
    <img :src="labelItem.flag?imgList[0]:imgList[1]" alt="">
    <span>{{ labelItem.label }}</span>
  </div>
</template>

<script>
export default ({
  props: {
    labelItem: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      imgList: [
        require('@/components/BaseDetail/assets/true.png'),
        require('@/components/BaseDetail/assets/false.png'),
      ]
    }
  },
})
</script>

<style lang="scss" scoped>
.special-label-wrapper {
  padding: 0 20px;
  line-height: 33px;
  border-radius: 4px;
  border: 1px solid #dc963c;
  background: rgba($color: #fef9f1, $alpha: 0.8);
  font-size: 16px;
  color: #db953c;
  display: flex;
  align-items: center;
  margin-right: 10px;
  margin-bottom: 10px;
  &.none {
    background: rgba($color: #eeeff3, $alpha: 0.3);
    border: 1px solid #999999;
    color: #999999;
  }
  img {
    margin-right: 8px;
  }
}
</style>