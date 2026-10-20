<template>
  <ul class="info-list-wrapper">
    <li v-for="item in baseInfo" :key="item.prop">
      <span class="info-item item-label">{{ item.label }}: </span>
      <span class="info-item item-value">{{ item.value }}</span>
    </li>
  </ul>
</template>

<script>
export default ({
  props: {
    baseInfo: {
      type: Array
    },
  },
})
</script>

<style lang="scss" scoped>
.info-list-wrapper {
  // width: 100%;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  li {
    height: 30px;
    line-height: 30px;
    .info-item {
      display: inline-block;
      margin-right: 4px;
    }
    .item-label {
      color: #333;
      font-size: 14px;
    }
    .item-value {
      font-size: 14px;
      font-weight: bold;
    }
  }
}
</style>