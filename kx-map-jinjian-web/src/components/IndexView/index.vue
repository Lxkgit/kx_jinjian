<template>
  <div class="index-view-wrapper">
    <div :class="['index-item',`line-type-${item.type}`]" v-for="(item,index) in indexData"
         :key="index">
      <img :src="require(`./assets/index_${item.type}.png`)" alt="">
      <div class="line"></div>
      <div class="item-content">
        <span class="item-label">{{ item.label }}</span>
        <div>
          <span class="item-value">{{ item.value }}</span>
          <span class="item-unit">{{ item.unit }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default ({
  name: 'IndexView',
  props: {
    indexData: {
      type: Array,
      default: () => [
        { label: '扫码总人数', value: '12', unit: '个', type: 1 },
        { label: '扫码总人数', value: '123', unit: '', type: 2 },
        { label: '扫码总人数', value: '1234', unit: '', type: 3 },
      ]
    }
  }
})
</script>

<style lang="scss" scoped>
$type-1-color: #8798ff;
$type-2-color: #7bc6ff;
$type-3-color: #5ad6fd;
.index-view-wrapper {
  width: 100%;
  display: flex;
  margin-bottom: 30px;
  .index-item {
    width: 220px;
    height: 80px;
    display: flex;
    align-items: center;
    border-radius: 10px;
    margin-right: 18px;
    padding: 0 24px;
    &.line-type-1 {
      background: rgba($color: $type-1-color, $alpha: 0.1);
      .line {
        background: rgba($color: $type-1-color, $alpha: 0.2);
      }
      .item-value {
        color: $type-1-color;
      }
    }
    &.line-type-2 {
      background: rgba($color: $type-2-color, $alpha: 0.1);
      .line {
        background: rgba($color: $type-2-color, $alpha: 0.2);
      }
      .item-value {
        color: $type-2-color;
        &::after {
          content: "%";
        }
      }
    }
    &.line-type-3 {
      background: rgba($color: $type-3-color, $alpha: 0.1);
      .line {
        background: rgba($color: $type-3-color, $alpha: 0.2);
      }
      .item-value {
        color: $type-3-color;
      }
    }
    img {
      width: 30px;
      height: 28px;
    }
    .line {
      width: 1px;
      height: 53px;
      margin: 0 30px 0 20px;
    }
    .item-label {
      line-height: 1.5;
      white-space: nowrap;
    }
    .item-value {
      font-size: 28px;
      margin-right: 10px;
    }
    .item-unit {
      font-size: 12px;
      color: #999999;
    }
  }
}
</style>