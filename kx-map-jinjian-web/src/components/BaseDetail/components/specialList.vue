<template>
  <ul class="special-list-wrapper">
    <li v-for="(item,index) in infoData" :key="index" :style="{'--width':item.width}">
      <label>{{ item.label }}</label>
      <span class="list-value" :style="{color: item.color}">{{ item.value }}
        <span>{{ item.unit }}</span></span>
    </li>
  </ul>
</template>

<script>
export default ({
  props: {
    infoData: {
      type: Array,
      default: () => []
    }
  }
})
</script>

<style lang="scss" scoped>
.special-list-wrapper {
  padding: 0 18px;
  margin-top: 18px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  li {
    line-height: 43px;
    height: 43px;
    background: #f6f6f6;
    border-radius: 6px;
    width: var(--width);
    margin-bottom: 13px;
    padding: 0 24px 0 12px;
    display: flex;
    justify-content: space-between;
    label {
      color: #666;
      font-size: 16px;
    }
    .list-value {
      font-size: 20px;
      color: #333;
      font-weight: bold;
      > span {
        color: #a2a2a2;
        font-size: 14px;
      }
    }
  }
}
</style>