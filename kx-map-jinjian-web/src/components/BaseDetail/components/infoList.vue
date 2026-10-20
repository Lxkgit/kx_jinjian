<template>
  <ul class="info-list-wrapper" :style="{'--gridColumns': `repeat(${gridColumns}, 1fr)`}">
    <li v-for="(item,index) in infoData" :key="index" :style="{'grid-column':item.gridColumn}">
      <div v-if="!item.rightSlot">
        <label>{{ item.label }}:</label>
        <span v-if="!item.slot">{{ item.value }}</span>
        <slot v-else :name="item.key" :data="item"></slot>
      </div>
      <div v-else>
        <slot :name="item.rightName" :data="item"></slot>
      </div>
    </li>
  </ul>
</template>

<script>
export default ({
  props: {
    gridColumns: {
      type: Number,
      default: 3
    },
    infoData: {
      type: Array,
      default: () => []
    }
  },
})
</script>

<style lang="scss" scoped>
.info-list-wrapper {
  padding: 18px;
  font-size: 18px;
  display: grid;
  grid-template-columns: var(--gridColumns);
  li {
    line-height: 34px;
    display: flex;
    label {
      display: inline-block;
      color: #666;
      margin-right: 10px;
      white-space: nowrap;
    }
    span {
      font-weight: bold;
    }
  }
}
</style>