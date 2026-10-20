<template>
  <div class="index-view-wrapper">
    <span class="index-tip">可视区域共找到:</span>
    <ul class="index-list">
      <li v-for="(item,index) in indexData" :class="{active: activeIndex === index+1}" :key="index"
          @click="clickHandle(index)">
        <span class="item-value" :style="{'--color':item.color}">
          {{ item.value }}<span class="item-unit">{{ item.unit }}</span>
        </span>
        <span class="item-label">{{ item.label }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default ({
  props: {
    value: {
      type: Number,
    },
    indexData: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      activeIndex: this.value,
    }
  },
  watch: {
    value: {
      handler(val) {
        this.activeIndex = val
      }
    },
    activeIndex: {
      handler(val) {
        this.$emit('input', val)
      }
    }
  },
  methods: {
    clickHandle(index) {
      if (this.activeIndex === index + 1) {
        this.activeIndex = null
      } else {
        this.activeIndex = index + 1
      }


      this.$emit('clickHandle', this.activeIndex)
    }
  },
})
</script>

<style lang="scss" scoped>
.index-view-wrapper {
  width: 100%;
  position: sticky;
  top: 0;
  background-color: #fff;
  padding: 10px 10px 6px;
  .index-tip {
    color: #999;
    font-size: 12px;
  }
  .index-list {
    display: flex;
    width: 100%;
    margin: 8px 0;
    li {
      min-width: calc(16% - 4px);
      background: #f4f5fc;
      border-radius: 4px;
      padding: 1px 4px 2px;
      margin-right: 6px;
      cursor: pointer;
      &.active {
        border: 1px solid #1a53ff;
      }
      .item-value {
        font-size: 16px;
        line-height: 1.5;
        color: var(--color);
        .item-unit {
          font-size: 14px;
          color: #333;
        }
      }
      .item-label {
        display: block;
        font-size: 12px;
        line-height: 1.5;
        white-space: nowrap;
      }
    }
  }
}
</style>